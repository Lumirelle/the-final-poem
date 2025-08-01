<script lang="ts" setup>
import { assign, isString } from 'lodash-es'
import { computed, nextTick, onMounted, ref, watch } from 'vue'
import { useI18n } from 'vue-i18n'
import SpaceInner from './space-inner.vue'
import { useCool } from '/@/cool'

defineOptions({
  name: 'cl-upload-space',
})

const props = defineProps({
  modelValue: [String, Array],
  // 标题
  title: String,
  // 按钮文本
  text: String,
  // 是否多选
  multiple: {
    type: Boolean,
    default: true,
  },
  // 可选数量
  limit: {
    type: Number,
    default: 9,
  },
  // 类型
  accept: String,
  // 显示按钮
  showBtn: {
    type: Boolean,
    default: true,
  },
  // 显示列表
  showList: {
    type: Boolean,
    default: true,
  },
})

const emit = defineEmits(['update:modelValue', 'change', 'confirm'])

const { t } = useI18n()

const { refs, setRefs } = useCool()

// 是否可见
const visible = ref(false)

// 配置
const config = ref({
  title: '',
  limit: 9,
})

// 展示列表
const urls = ref<any[]>([])

// 选中列表
const selection = computed<Eps.SpaceInfoEntity[]>(() => refs.inner?.selection || [])

// 按钮文案
const btnText = computed(() => {
  return props.text || t('选择文件')
})

// 打开
function open(options?: any) {
  visible.value = true

  // 合并配置
  config.value = assign(
    { ...props, title: props.title || t('文件空间'), text: props.text || t('点击上传') },
    options,
  )

  // 非多选情况
  if (!props.multiple) {
    config.value.limit = 1
  }

  nextTick(() => {
    refs.inner?.clear()
  })
}

// 关闭
function close() {
  visible.value = false
}

// 确认
function confirm(arr?: Eps.SpaceInfoEntity[]) {
  const list = arr || selection.value

  // 读取文件地址
  urls.value = list.map(e => e.url)

  // 返回值
  const v = props.multiple ? urls.value : urls.value[0]

  // 事件
  emit('update:modelValue', v)
  emit('change', v)
  emit('confirm', list)

  // 关闭
  close()
}

onMounted(() => {
  watch(
    () => props.modelValue,
    (val) => {
      if (val) {
        urls.value = isString(val) ? [val] : val
      }
    },
    {
      immediate: true,
    },
  )
})

defineExpose({
  open,
  close,
})
</script>

<template>
  <slot />

  <!-- 按钮 -->
  <el-button v-if="showBtn" @click="open">
    {{ btnText }}
  </el-button>

  <!-- 列表 -->
  <div v-if="urls.length > 0 && showList" class="cl-upload-space__list">
    <cl-upload v-model="urls" disabled deletable draggable :multiple="multiple" />
  </div>

  <!-- 弹框 -->
  <cl-dialog
    v-model="visible"
    :title="config.title"
    height="650px"
    width="1070px"
    padding="0"
    keep-alive
    :scrollbar="false"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
  >
    <space-inner :ref="setRefs('inner')" v-bind="config" @confirm="confirm" />

    <template #footer>
      <el-button @click="close">
        {{ $t('取消') }}
      </el-button>
      <el-button :disabled="selection.length == 0" type="success" @click="confirm()">
        {{ $t('选择 {count}/{limit} 个', { count: selection.length, limit: config.limit }) }}
      </el-button>
    </template>
  </cl-dialog>
</template>

<style lang="scss" scoped>
.cl-upload-space__list {
  margin-top: 10px;
}
</style>
