<script setup lang="ts">
import type { PropType } from 'vue'
import { isEmpty } from 'lodash-es'
import { demo } from 'virtual:demo'
import { computed } from 'vue'
import { useCool } from '/@/cool'
import { basename } from '/@/cool/utils'

defineOptions({
  name: 'demo-code',
})

const props = defineProps({
  files: {
    type: Array as PropType<string[]>,
    default: () => [],
  },
})

const { refs, setRefs } = useCool()

// 是否隐藏
const isHide = computed(() => isEmpty(demo))

// 文件列表
const tabs = computed(() => {
  return props.files?.map((e) => {
    return {
      name: basename(e),
      language: e.includes('.vue') ? 'html' : 'typescript',
      data: demo[e],
    }
  })
})

// 打开
function open() {
  refs.preview.open()
}
</script>

<template>
  <cl-editor-preview v-if="!isHide" :ref="setRefs('preview')" type="code" :tabs="tabs">
    <el-button @click="open">
      代码
    </el-button>
  </cl-editor-preview>
</template>
