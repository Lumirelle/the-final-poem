<script lang="ts" setup>
import { ref } from 'vue'

const v1 = ref()
const v2 = ref()
const v3 = ref([])
</script>

<template>
  <cl-page :padding="20">
    <cl-card label="基础用法">
      <cl-upload v-model="v1" test />
    </cl-card>

    <cl-card label="自定义大小">
      <cl-upload v-model="v2" :size="[200, 300]" test />
    </cl-card>

    <cl-card label="多图">
      <cl-upload v-model="v3" multiple test />
    </cl-card>
  </cl-page>
</template>
