<script lang="ts" setup>
import { Upload } from '@element-plus/icons-vue'
</script>

<template>
  <cl-upload type="file" multiple draggable custom-class="custom-upload">
    <el-button :icon="Upload">
      {{ $t('上传') }}
    </el-button>

    <template #item="{ item }">
      <div v-show="item.url" class="item">
        {{ item.url }}
      </div>
    </template>
  </cl-upload>
</template>

<style lang="scss" scoped>
.custom-upload {
  .item {
    border: 1px solid var(--el-border-color);
    border-radius: 4px;
    padding: 5px 10px;
    margin-bottom: 5px;
    font-size: 12px;
    width: 100%;
    box-sizing: border-box;
  }
}
</style>
