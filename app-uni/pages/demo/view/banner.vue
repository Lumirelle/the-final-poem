<script lang="ts" setup>
import { ref } from 'vue'

const urls = ref([
  {
    url: '/pages/demo/static/bg1.png',
  },
  {
    url: '/pages/demo/static/bg2.png',
  },
  {
    url: '/pages/demo/static/bg3.png',
  },
  {
    url: '/pages/demo/static/bg4.png',
  },
])

function onSelect(i: number) {
  console.log(i)
}
</script>

<template>
  <cl-page :padding="20">
    <cl-card label="基础用法">
      <cl-banner :list="urls" />
    </cl-card>

    <cl-card label="卡片">
      <cl-banner :list="urls" type="card" />
    </cl-card>

    <cl-card label="衔接">
      <cl-banner :list="urls" type="chain" @select="onSelect" />
    </cl-card>
  </cl-page>
</template>
