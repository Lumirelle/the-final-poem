<script lang="ts" setup>
import { onLoad } from '@dcloudio/uni-app'
import { ref } from 'vue'
import BackHome from '/@/components/back-home.vue'
import { useCool, useStore } from '/@/cool'

const { service } = useCool()
const { dict, user } = useStore()

// 详情数据
const detail = ref<Eps.OrderInfoEntity>({})
const loading = ref(false)

// 支付方式映射
const payTypeMap: Record<number, string> = {
  0: '微信',
  1: '支付宝',
  2: '线下银行卡',
  3: '线下现金',
}

// 加载详情
async function loadDetail(id: string) {
  loading.value = true
  try {
    const res = await service.order.info.info({
      id,
    })
    detail.value = res
  }
  finally {
    loading.value = false
  }
}

// 获取状态文本
function getStatusText(status: number | undefined) {
  if (status === undefined)
    return '未知'
  const result = dict.getLabel('order-status', status) || '未知'
  return user.info?.role === 2 ? `患者${result}` : result
}

// 获取状态样式
function getStatusType(status: number | undefined) {
  if (status === undefined)
    return 'primary'
  const result = dict.get('order-status').find(item => item.value === status)?.type
  return result || 'primary'
}

// 页面加载
onLoad((options) => {
  if (options?.id) {
    loadDetail(options.id)
  }
})
</script>

<template>
  <cl-page fullscreen>
    <cl-topbar title="订单详情" />

    <cl-loading-mask v-if="loading" />

    <cl-scroller>
      <!-- 订单状态 -->
      <view class="status-section">
        <cl-text
          :value="getStatusText(detail.status)"
          :color="getStatusType(detail.status)"
          :size="48"
          bold
        />
        <br>
        <br>
        <cl-text v-if="user.info?.role == 1" :value="`核销码：${detail.verifyCode}`" color="success" :size="36" bold />
      </view>

      <!-- 订单信息 -->
      <view class="info-section">
        <view class="info-row">
          <cl-text value="订单编号" color="#999" />
          <cl-text :value="detail.orderNumber" />
        </view>

        <view class="info-row">
          <cl-text value="下单时间" color="#999" />
          <cl-text :value="detail.createTime" />
        </view>

        <view class="info-row">
          <cl-text value="支付方式" color="#999" />
          <cl-text :value="payTypeMap[detail.payType || 0]" />
        </view>

        <view v-if="detail.payTime" class="info-row">
          <cl-text value="支付时间" color="#999" />
          <cl-text :value="detail.payTime" />
        </view>

        <view class="info-row">
          <cl-text value="备注信息" color="#999" />
          <cl-text :value="detail.remark || '无'" />
        </view>
      </view>

      <!-- 金额信息 -->
      <view class="amount-section">
        <view class="info-row">
          <cl-text value="订单金额" color="#999" />
          <cl-text :value="`¥${detail.totalAmount || '0'}`" />
        </view>

        <view class="info-row">
          <cl-text value="优惠金额" color="#999" />
          <cl-text :value="`¥${detail.discountAmount || '0'}`" />
        </view>

        <view class="info-row total">
          <cl-text value="实付金额" color="#999" :size="32" />
          <cl-text :value="`¥${detail.actualAmount || '0'}`" color="danger" :size="32" bold />
        </view>
      </view>
    </cl-scroller>

    <!-- 回到首页按钮 -->
    <back-home />
  </cl-page>
</template>

<style lang="scss" scoped>
.status-section {
  padding: 60rpx 30rpx;
  background-color: #fff;
  text-align: center;
}

.info-section {
  margin-top: 20rpx;
  padding: 30rpx;
  background-color: #fff;

  .info-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20rpx;

    &:last-child {
      margin-bottom: 0;
    }
  }
}

.amount-section {
  margin-top: 20rpx;
  padding: 30rpx;
  background-color: #fff;

  .info-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20rpx;

    &:last-child {
      margin-bottom: 0;
    }

    &.total {
      margin-top: 30rpx;
      padding-top: 30rpx;
      border-top: 2rpx solid #f5f5f5;
    }
  }
}
</style>
