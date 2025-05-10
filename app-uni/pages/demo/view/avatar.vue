<script lang="ts" setup>
import { ref } from 'vue'

const urls = ref([
  '/pages/demo/static/avatar1.png',
  '/pages/demo/static/avatar2.png',
  '/pages/demo/static/avatar3.png',
  '/pages/demo/static/avatar4.png',
])
</script>

<template>
  <cl-page :padding="20">
    <cl-card label="基础用法">
      <cl-row>
        <cl-col :span="4">
          <cl-avatar src="/pages/demo/static/avatar1.png" />
        </cl-col>

        <cl-col :span="4">
          <cl-avatar src="/pages/demo/static/avatar1.png" shape="square" />
        </cl-col>
      </cl-row>
    </cl-card>

    <cl-card label="占位">
      <cl-row>
        <cl-col :span="4">
          <cl-avatar />
        </cl-col>

        <cl-col :span="4">
          <cl-avatar name="神仙" background-color="red" />
        </cl-col>
      </cl-row>
    </cl-card>

    <cl-card label="不同大小">
      <cl-row>
        <cl-col :span="6">
          <cl-avatar :size="100" src="/pages/demo/static/avatar1.png" />
        </cl-col>

        <cl-col :span="6">
          <cl-avatar :size="120" src="/pages/demo/static/avatar2.png" />
        </cl-col>

        <cl-col :span="6">
          <cl-avatar :size="140" src="/pages/demo/static/avatar3.png" />
        </cl-col>
      </cl-row>
    </cl-card>

    <cl-card label="头像组">
      <cl-avatar-group :urls="urls" />
    </cl-card>
  </cl-page>
</template>
