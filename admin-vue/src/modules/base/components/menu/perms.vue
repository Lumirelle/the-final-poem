<script lang="ts" setup>
import { onMounted, reactive, ref, watch } from 'vue'
import { useCool } from '/@/cool'
import { deepPaths } from '/@/cool/utils'

defineOptions({
  name: 'cl-menu-perms',
})

const props = defineProps({
  modelValue: {
    type: String,
    default: '',
  },
  disabled: Boolean,
})

const emit = defineEmits(['update:modelValue'])

const { service } = useCool()

// 绑定值
const value = ref<string[][]>([])

// 权限列表
const data = ref<any[]>([])

// elm BUG
const cascaderProps = reactive({ multiple: true })

// 监听改变
function onChange(arr: any) {
  emit('update:modelValue', arr.map((e: string[]) => e.join(':')).join(','))
}

// 监听值
watch(
  () => props.modelValue,
  (val) => {
    value.value = val ? val.split(',').map(e => e.split(':')) : []
  },
  {
    immediate: true,
  },
)

onMounted(() => {
  const list: any[] = []

  function deep(s: any) {
    if (typeof s === 'object') {
      for (const i in s) {
        const { permission } = s[i]

        if (permission) {
          list.push(...Object.values(permission))
        }
        else {
          deep(s[i])
        }
      }
    }
  }

  deep(service)

  data.value = deepPaths(list, ':')
})
</script>

<template>
  <div class="cl-menu-perms">
    <el-cascader
      v-model="value"
      separator=":"
      clearable
      filterable
      collapse-tags
      collapse-tags-tooltip
      :disabled="disabled"
      :options="data"
      :props="cascaderProps"
      @change="onChange"
    />
  </div>
</template>

<style lang="scss" scoped>
.cl-menu-perms {
  line-height: 0;

  :deep(.el-cascader) {
    width: 100%;
  }
}
</style>
