<script lang="ts" setup>
import { onReady } from '@dcloudio/uni-app'
import { ref } from 'vue'

const v1 = ref([])
const v2 = ref([])
const v3 = ref([])

const pcas = ref()

onReady(() => {
  uni.request({
    url: 'https://cool-service.oss-cn-shanghai.aliyuncs.com/app%2Fbase%2F8fe445f06db5463386ae940d45b4bd6c_city-pcas.json',
    success(res) {
      pcas.value = res.data
    },
  })
})
</script>

<template>
  <cl-page :padding="20">
    <cl-card label="基础用法">
      <cl-select-city v-model="v1" />
    </cl-card>

    <cl-card label="省市区乡镇4级">
      <cl-select-city v-model="v2" :data="pcas" placeholder="省市区县、乡镇" />
    </cl-card>

    <cl-card label="自定义">
      <cl-select-city v-model="v3">
        <template #default="{ selection }">
          <cl-row>
            <cl-tag
              v-for="item in selection"
              :key="item.code"
              :margin="[0, 20, 20, 0]"
              plain
              round
            >
              {{ item.name }}
            </cl-tag>
          </cl-row>

          <cl-button round>
            选择所在地区
          </cl-button>
        </template>
      </cl-select-city>
    </cl-card>
  </cl-page>
</template>
