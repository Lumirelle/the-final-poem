<script lang="ts" setup>
import { Link as IconLink } from '@element-plus/icons-vue'
import { isArray, isString, last } from 'lodash-es'
import { computed } from 'vue'

defineOptions({
  name: 'cl-link',
})

const props = defineProps({
  modelValue: [String, Array],
  href: [String, Array],
  text: String,
  target: {
    type: String,
    default: '_blank',
  },
})

const urls = computed(() => {
  const urls: any = props.modelValue || props.href

  if (isArray(urls)) {
    return urls
  }

  if (isString(urls)) {
    return (urls || '').split(',').filter(Boolean)
  }

  return []
})

function filename(url: string) {
  return last(url.split('/'))
}
</script>

<template>
  <div class="cl-link">
    <a v-for="item in urls" :key="item" class="cl-link__item" :href="item" :target="target">
      <el-icon><icon-link /></el-icon>
      <span>{{ text || filename(item) }}</span>
    </a>
  </div>
</template>

<style lang="scss" scoped>
.cl-link {
  &__item {
    display: flex;
    align-items: center;
    color: var(--el-color-primary);
    padding: 0 5px;
    border-radius: 6px;
    margin: 2px;
    text-decoration: none;

    span {
      display: block;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    .el-icon {
      margin-right: 4px;
    }

    &:hover {
      text-decoration: underline;
    }
  }
}
</style>
