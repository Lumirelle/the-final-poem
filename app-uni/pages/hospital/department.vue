<script lang="ts" setup>
import { onLoad } from '@dcloudio/uni-app'
import { ref } from 'vue'
import { useCool } from '/@/cool'

const { service } = useCool()

const department = ref<any>({})
const doctors = ref<any[]>([])
const loading = ref(false)

// 获取科室详情
async function getDetail(id: string) {
  loading.value = true

  try {
    // 获取科室信息
    const info = await service.hospital.department.info({
      id,
    })
    department.value = info

    // 获取医生列表
    const { list } = await service.hospital.doctor.page({
      departmentId: id,
    })
    doctors.value = list
  }
  finally {
    loading.value = false
  }
}

// 查看医生详情
function viewDoctor(item: any) {
  uni.navigateTo({
    url: `/pages/hospital/doctor?id=${item.id}&departmentId=${department.value.id}&hospitalId=${department.value.hospitalId}`,
  })
}

// 页面加载
onLoad((options) => {
  if (options?.id) {
    getDetail(options.id)
  }
})
</script>

<template>
  <cl-page>
    <cl-topbar :title="department.name || '科室详情'" />

    <!-- 加载中 -->
    <cl-loading-mask v-if="loading" />

    <template v-else>
      <!-- 科室信息 -->
      <view class="department-info">
        <cl-card>
          <view class="info-header">
            <cl-icon
              :name="department.icon || 'cl-icon-help-border'"
              :size="80"
              color="primary"
            />
            <cl-text
              :value="department.name"
              size="36"
              bold
              :margin="[0, 0, 0, 20]"
            />
          </view>

          <cl-divider :margin="[30, 0]">
            科室简介
          </cl-divider>

          <cl-text
            :value="department.introduction"
            color="#666"
            :line-height="1.6"
          />
        </cl-card>
      </view>

      <!-- 医生列表 -->
      <view class="doctor-list">
        <cl-divider :margin="[30, 0]">
          医生列表
        </cl-divider>

        <cl-card
          v-for="item in doctors"
          :key="item.id"
          :margin="[0, 20, 20, 20]"
          @tap="viewDoctor(item)"
        >
          <view class="doctor-item">
            <cl-avatar :src="item.avatar" :size="120" />

            <view class="doctor-info">
              <view class="name-row">
                <cl-text :value="item.name" size="32" bold />
                <cl-tag
                  type="primary"
                  size="small"
                  :margin="[0, 0, 0, 10]"
                >
                  {{ item.title }}
                </cl-tag>
              </view>

              <view class="specialty-row">
                <cl-text :value="item.specialty" color="#666" :margin="[0, 0, 0, 10]" size="28" />
              </view>

              <view class="intro-row">
                <cl-text
                  :value="item.introduction"
                  color="#999"
                  :ellipsis="2"
                  :margin="[0, 0, 0, 10]"
                  :line-height="1.6"
                  size="26"
                />
              </view>
            </view>
          </view>
        </cl-card>
      </view>
    </template>
  </cl-page>
</template>

<style lang="scss" scoped>
.department-info {
  padding: 20rpx;

  .info-header {
    display: flex;
    align-items: center;
    padding: 20rpx 0;
  }
}

.doctor-list {
  padding: 0 20rpx;

  .doctor-item {
    display: flex;
    align-items: center;

    .doctor-info {
      flex: 1;
      margin-left: 20rpx;
      display: flex;
      flex-direction: column;
      justify-content: center;

      .name-row,
      .specialty-row,
      .intro-row {
        display: flex;
        align-items: center;
        margin-top: 16rpx;
      }
    }
  }
}
</style>
