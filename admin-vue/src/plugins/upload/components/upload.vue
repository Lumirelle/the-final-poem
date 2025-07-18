<script lang="ts" setup>
import type { PropType } from 'vue'
import { useForm } from '@cool-vue/crud'
import { CircleCloseFilled, PictureFilled, UploadFilled } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { assign, isArray, isEmpty, isNumber } from 'lodash-es'
import { computed, nextTick, ref, watch } from 'vue'
import { useI18n } from 'vue-i18n'
import VueDraggable from 'vuedraggable'
import { useUpload } from '../hooks'
import { getType, getUrls } from '../utils'
import UploadItem from './upload-item/index.vue'
import { CrudProps } from '/#/crud'
import { useBase } from '/$/base'
import { useCool } from '/@/cool'
import { isPromise, uuid } from '/@/cool/utils'

defineOptions({
  name: 'cl-upload',
})

const props = defineProps({
  ...CrudProps,
  // 绑定值，单选时字符串，多选时字符串数组
  modelValue: {
    type: [String, Array],
    default: () => [],
  },
  // 上传类型
  type: {
    type: String as PropType<'image' | 'file'>,
    default: 'image',
  },
  // 允许上传的文件类型
  accept: String,
  // 是否多选
  multiple: Boolean,
  // 限制数量
  limit: Number,
  // 限制大小
  limitSize: Number,
  // 是否自动上传
  autoUpload: {
    type: Boolean,
    default: true,
  },
  // 元素大小
  size: [String, Number, Array],
  // 小图模式
  small: Boolean,
  // 显示图标
  icon: null,
  // 显示文案
  text: String,
  // 显示角标
  showTag: {
    type: Boolean,
    default: true,
  },
  // 是否显示上传列表
  showFileList: {
    type: Boolean,
    default: true,
  },
  // 列表是否可拖拽
  draggable: Boolean,
  // 是否拖拽到特定区域以进行上传
  drag: Boolean,
  // 是否禁用
  disabled: Boolean,
  // 是否可删除
  deletable: Boolean,
  // 自定义样式名
  customClass: String,
  // 上传前钩子
  beforeUpload: Function,
  // 云端上传路径前缀
  prefixPath: String,
})

const emit = defineEmits(['update:modelValue', 'change', 'upload', 'success', 'error', 'progress'])

const { refs, setRefs } = useCool()
const { user } = useBase()
const Form = useForm()
const { options, toUpload } = useUpload()
const { t } = useI18n()

// 元素尺寸
const size = computed(() => {
  const d = props.size || options.size
  return (isArray(d) ? d : [d, d]).map((e: string | number) => (isNumber(e) ? `${e}px` : e))
})

// 是否禁用
const disabled = computed(() => {
  return props.isDisabled || props.disabled
})

// 最大上传数量
const limit = props.limit || options.limit.upload

// 图片大小限制
const limitSize = props.limitSize || options.limit.size

// 文案
const text = computed(() => {
  if (props.text !== undefined) {
    return props.text
  }
  else {
    switch (props.type) {
      case 'file':
        return t('选择文件')

      case 'image':
        return t('选择图片')

      default:
        return ''
    }
  }
})

// 请求头
const headers = computed(() => {
  return {
    Authorization: user.token,
  }
})

// 列表
const list = ref<Upload.Item[]>([])

// 显示上传列表
const showList = computed(() => {
  if (props.type == 'file') {
    return props.showFileList ? !isEmpty(list.value) : false
  }
  else {
    return true
  }
})

// 文件格式
const accept = computed(() => {
  return props.accept || (props.type == 'file' ? '' : 'image/*')
})

// 能否添加
const isAdd = computed(() => {
  const len = list.value.length

  if (props.multiple && !disabled.value) {
    return limit - len > 0
  }

  return len == 0
})

// 上传前
async function onBeforeUpload(file: any, item?: Upload.Item) {
  function next() {
    const d = {
      uid: file.uid,
      size: file.size,
      name: file.name,
      type: getType(file.name),
      progress: props.autoUpload ? 0 : 100, // 非自动上传时默认100%
      url: '',
      preload: '',
      error: '',
    }

    // 图片预览地址
    if (d.type == 'image') {
      if (file instanceof File) {
        d.preload = window.webkitURL.createObjectURL(file)
      }
    }

    // 上传事件
    emit('upload', d, file)

    // 赋值
    if (item) {
      assign(item, d)
    }
    else {
      if (props.multiple) {
        if (!isAdd.value) {
          ElMessage.warning(t('最多只能上传{n}个文件', { n: limit }))
          return false
        }
        else {
          list.value.push(d)
        }
      }
      else {
        list.value = [d]
      }
    }

    return true
  }

  // 文件大小限制
  if (file.size / 1024 / 1024 >= limitSize) {
    ElMessage.error(t('上传文件大小不能超过 {n}MB!', { n: limitSize }))
    return false
  }

  // 自定义上传事件
  if (props.beforeUpload) {
    let r = props.beforeUpload(file, item, { next })

    if (isPromise(r)) {
      r.then(next).catch(() => null)
    }
    else {
      if (r) {
        r = next()
      }
    }

    return r
  }
  else {
    return next()
  }
}

// 移除
function remove(index: number) {
  list.value.splice(index, 1)
  update()
}

// 清空
function clear() {
  list.value = []
}

// 文件上传请求
async function httpRequest(req: any, item?: Upload.Item) {
  if (!item) {
    item = list.value.find(e => e.uid == req.file.uid)
  }

  if (!item) {
    return false
  }

  // 上传请求
  toUpload(req.file, {
    prefixPath: props.prefixPath,
    onProgress(progress) {
      item!.progress = progress
      emit('progress', item)
    },
  })
    .then((res) => {
      assign(item!, res)
      emit('success', item)
      update()
    })
    .catch((err) => {
      item!.error = err.message
      emit('error', item)
    })
}

// 检测是否还有未上传的文件
function check() {
  return list.value.find(e => !e.url)
}

// 更新
function update() {
  if (!check()) {
    const urls = getUrls(list.value)

    const val = props.multiple ? getUrls(list.value) : urls[0] || ''

    // 更新绑定值
    emit('update:modelValue', val)
    emit('change', val)

    nextTick(() => {
      if (props.prop) {
        Form.value?.validateField(props.prop)
      }

      // 清空
      refs.upload?.clearFiles()
    })
  }
}

// 手动上传
function upload(file: File) {
  clear()

  refs.upload?.clearFiles()

  nextTick(() => {
    refs.upload?.handleStart(file)
    refs.upload?.submit()
  })
}

// 监听绑定值
watch(
  () => props.modelValue,
  (val: any[] | string) => {
    if (check()) {
      return false
    }

    const urls = (isArray(val) ? val : [val]).filter(Boolean)

    list.value = urls
      .map((url, index) => {
        const old = list.value[index] || {}

        return assign(
          {
            progress: 100,
            uid: uuid(),
          },
          old,
          {
            type: getType(url),
            url,
            preload: old.url == url ? old.preload : url, // 防止重复预览
          },
        )
      })
      .filter((_, i) => {
        return props.multiple ? true : i == 0
      })
  },
  {
    immediate: true,
  },
)

// 导出
defineExpose({
  isAdd,
  list,
  check,
  clear,
  remove,
  upload,
})
</script>

<template>
  <div class="cl-upload__wrap" :class="[customClass]">
    <div
      class="cl-upload"
      :class="[
        `cl-upload--${type}`,
        {
          'is-disabled': disabled,
          'is-multiple': multiple,
          'is-small': small,
        },
      ]"
    >
      <template v-if="!drag">
        <div v-if="type == 'file'" class="cl-upload__file-btn">
          <el-upload
            :ref="setRefs('upload')"
            :drag="drag"
            action=""
            :accept="accept"
            :show-file-list="false"
            :before-upload="onBeforeUpload"
            :http-request="httpRequest"
            :headers="headers"
            :multiple="multiple"
            :disabled="disabled"
          >
            <slot>
              <el-button type="success">
                {{ text }}
              </el-button>
            </slot>
          </el-upload>
        </div>
      </template>

      <!-- 列表 -->
      <vue-draggable
        v-if="showList"
        v-model="list"
        class="cl-upload__list"
        tag="div"
        ghost-class="Ghost"
        drag-class="Drag"
        item-key="uid"
        :disabled="!draggable"
        @end="update"
      >
        <!-- 触发器 -->
        <template #footer>
          <div v-if="(type == 'image' || drag) && isAdd" class="cl-upload__footer">
            <el-upload
              :ref="setRefs('upload')"
              action=""
              :drag="drag"
              :accept="accept"
              :show-file-list="false"
              :before-upload="onBeforeUpload"
              :http-request="httpRequest"
              :headers="headers"
              :multiple="multiple"
              :disabled="disabled"
            >
              <slot>
                <!-- 拖拽方式 -->
                <div v-if="drag" class="cl-upload__demo is-dragger">
                  <el-icon :size="46">
                    <upload-filled />
                  </el-icon>
                  <div>
                    {{
                      t('点击上传或将文件拖动到此处，文件大小限制{n}M', {
                        n: limitSize,
                      })
                    }}
                  </div>
                </div>

                <!-- 点击方式 -->
                <div v-else class="cl-upload__demo">
                  <el-icon :size="36">
                    <component :is="icon" v-if="icon" />
                    <picture-filled v-else />
                  </el-icon>
                  <span v-if="text" class="text">{{ text }}</span>
                </div>
              </slot>
            </el-upload>
          </div>
        </template>

        <!-- 列表 -->
        <template #item="{ element: item, index }">
          <el-upload
            action=""
            :accept="accept"
            :show-file-list="false"
            :http-request="
              req => {
                return httpRequest(req, item);
              }
            "
            :before-upload="
              file => {
                onBeforeUpload(file, item);
              }
            "
            :headers="headers"
            :disabled="disabled"
          >
            <slot name="item" :item="item" :index="index">
              <div class="cl-upload__item">
                <upload-item
                  :show-tag="showTag"
                  :item="item"
                  :list="list"
                  :disabled="disabled"
                  :deletable="deletable"
                  @remove="remove(index)"
                />

                <!-- 小图模式 -->
                <el-icon v-if="small" class="cl-upload__item-remove" @click.stop="remove(index)">
                  <circle-close-filled />
                </el-icon>
              </div>
            </slot>
          </el-upload>
        </template>
      </vue-draggable>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.cl-upload {
  line-height: normal;

  .Ghost {
    .cl-upload__item {
      border: 1px dashed var(--el-color-primary) !important;
    }
  }

  &__file {
    width: 100%;

    &-btn {
      width: fit-content;
    }
  }

  &__list {
    display: flex;
    flex-wrap: wrap;
  }

  &__item,
  &__demo {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: v-bind('size[0]');
    width: v-bind('size[1]');
    background-color: var(--el-fill-color-light);
    color: var(--el-text-color-regular);
    border-radius: 8px;
    cursor: pointer;
    box-sizing: border-box;
    position: relative;
    user-select: none;
  }

  &__demo {
    font-size: 13px;

    .el-icon {
      font-size: 46px;
    }

    .text {
      margin-top: 5px;
    }

    &.is-dragger {
      padding: 20px;
    }
  }

  &__file-btn {
    & + .cl-upload__list {
      margin-top: 10px;
    }
  }

  :deep(.el-upload) {
    display: block;

    .el-upload-dragger {
      padding: 0;
      border: 0;
      background-color: transparent !important;
      position: relative;

      &.is-dragover {
        &::after {
          display: block;
          content: '';
          position: absolute;
          left: 0;
          top: 0;
          height: 100%;
          width: 100%;
          pointer-events: none;
          border-radius: 8px;
          box-sizing: border-box;
          border: 1px dashed var(--el-color-primary);
        }
      }
    }
  }

  &.is-disabled {
    .cl-upload__demo {
      color: var(--el-text-color-placeholder);
    }

    :deep(.cl-upload__item) {
      cursor: not-allowed;
      background-color: var(--el-disabled-bg-color);
    }
  }

  &.is-multiple {
    .cl-upload__list {
      margin-bottom: -5px;
    }

    .cl-upload__item {
      margin: 0 5px 5px 0;
    }

    .cl-upload__footer {
      margin-bottom: 5px;
    }
  }

  &.is-small {
    .cl-upload__demo {
      .el-icon {
        font-size: 20px !important;
      }

      .text {
        display: none;
      }
    }

    .cl-upload__item-remove {
      position: absolute;
      right: 0px;
      top: 0px;
      color: var(--el-color-danger);
      background-color: #fff;
      border-radius: 100%;
    }

    :deep(.cl-upload-item) {
      .cl-upload-item__progress-bar,
      .cl-upload-item__actions,
      .cl-upload-item__tag {
        display: none;
      }

      .cl-upload-item__progress-value {
        font-size: 12px;
      }
    }
  }

  &:not(.is-disabled) {
    .cl-upload__demo {
      &:hover {
        color: var(--el-color-primary);
      }
    }
  }
}
</style>
