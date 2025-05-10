<script setup lang="ts">
import Avatar4 from '../static/avatar4.png'
import Bg1 from '../static/bg1.png'
import { useCool } from '/@/cool'

const { refs, setRefs } = useCool()
</script>

<template>
  <cl-page :padding="20">
    <view class="page">
      <cl-card label="拖动验证">
        <cl-button @tap="refs.sliderVerify?.open()">
          打开
        </cl-button>
        <cl-slider-verify :ref="setRefs('sliderVerify')" title="拖动验证" :image="Bg1" />
      </cl-card>

      <cl-card label="旋转验证">
        <cl-button @tap="refs.sliderVerify2?.open()">
          打开
        </cl-button>
        <cl-slider-verify
          :ref="setRefs('sliderVerify2')"
          title="旋转验证"
          type="rotate"
          :image="Avatar4"
        />
      </cl-card>
    </view>
  </cl-page>
</template>
