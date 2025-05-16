<script lang="ts" setup>
import { onLoad } from '@dcloudio/uni-app'
import { ref } from 'vue'
import Tabbar from './components/tabbar.vue'
import { useCool, usePager, useStore } from '/@/cool'

const { service } = useCool()
const { onRefresh } = usePager()

const list = ref<Eps.OrderInfoEntity[]>([])
const loading = ref(false)

const { dict } = useStore()

// 搜索条件
const searchForm = ref({
  status: '',
  orderNumber: '',
})

// 刷新列表
async function refresh() {
  const { data, next } = onRefresh()

  loading.value = true
  try {
    const res = await next(
      service.order.info.page({
        ...data,
        ...searchForm.value,
      }),
    )
    list.value = (res as any).list
  }
  finally {
    loading.value = false
  }
}

// 搜索
function search() {
  refresh()
}

// 重置搜索
function reset() {
  searchForm.value = {
    status: '',
    orderNumber: '',
  }
  refresh()
}

// 查看详情
function viewDetail(item: Eps.OrderInfoEntity) {
  uni.navigateTo({
    url: `/pages/order/detail?id=${item.id}`,
  })
}

// 获取状态文本
function getStatusText(status: number | undefined) {
  if (status === undefined)
    return '未知'
  return dict.get('order-status').find(item => item.value === status)?.label || '未知'
}

// 获取状态样式
function getStatusType(status: number | undefined) {
  if (status === undefined)
    return 'primary'
  return dict.get('order-status').find(item => item.value === status)?.type || 'primary'
}

// 继续支付
function handlePay(item: Eps.OrderInfoEntity) {
  uni.navigateTo({
    url: `/pages/order/pay?id=${item.id}&amount=${item.actualAmount}`,
  })
}

// 取消订单
async function handleCancel(item: Eps.OrderInfoEntity) {
  try {
    const { confirm } = await uni.showModal({
      title: '提示',
      content: '确定要取消该订单吗？',
    })

    if (!confirm)
      return

    await service.order.info.update({
      id: item.id,
      status: 4,
    })

    uni.showToast({
      title: '取消成功',
      icon: 'success',
    })

    refresh()
  }
  catch (err) {
    uni.showToast({
      title: '操作失败',
      icon: 'error',
    })
  }
}

// 申请退款
async function handleRefund(item: Eps.OrderInfoEntity) {
  try {
    const { confirm } = await uni.showModal({
      title: '提示',
      content: '确定要申请退款吗？',
    })

    if (!confirm)
      return

    await service.order.info.update({
      id: item.id,
      status: 5, // 已退款
    })

    uni.showToast({
      title: '申请成功',
      icon: 'success',
    })

    refresh()
  }
  catch (err) {
    uni.showToast({
      title: '操作失败',
      icon: 'error',
    })
  }
}

// 页面加载时刷新
onLoad(() => {
  refresh()
})
</script>

<template>
  <cl-page>
    <cl-topbar title="我的订单" />

    <cl-loading-mask v-if="loading" />

    <!-- 搜索区域 -->
    <cl-filter-bar>
      <cl-form :model="searchForm" class="search-form">
        <view class="basic-search">
          <cl-form-item label="订单状态" :margin="[0, 20, 20, 0]">
            <cl-select v-model="searchForm.status" :options="dict.get('order-status')" />
          </cl-form-item>

          <cl-form-item label="订单编号" :margin="[0, 20, 20, 0]">
            <cl-input v-model="searchForm.orderNumber" placeholder="请输入订单编号" />
          </cl-form-item>
        </view>

        <view class="filter-btns">
          <cl-button type="primary" :width="180" round @tap="search">
            <cl-icon name="search" :margin="[0, 10, 0, 0]" />
            搜索
          </cl-button>
          <cl-button :width="180" round :margin="[0, 20, 0, 20]" @tap="reset">
            <cl-icon name="refresh" :margin="[0, 10, 0, 0]" />
            重置
          </cl-button>
        </view>
      </cl-form>
    </cl-filter-bar>

    <!-- 列表区域 -->
    <cl-scroller @down="refresh">
      <view class="order-list">
        <cl-card
          v-for="item in list"
          :key="item.id"
          :margin="[0, 20, 20, 20]"
          :radius="16"
          @tap="viewDetail(item)"
        >
          <!-- 订单信息 -->
          <view class="order-item">
            <view class="header">
              <cl-text :value="item.orderNumber" size="28" />
              <cl-tag :type="getStatusType(item.status)" :text="getStatusText(item.status)" />
            </view>

            <view class="content">
              <view class="meal-info">
                <cl-image :src="item.mealCover" :size="160" radius="12" />
                <view class="meal-detail">
                  <cl-text :value="item.mealName" size="32" bold />
                  <cl-text :value="item.hospitalName" color="#666" size="28" :margin="[10, 0, 0, 0]" />
                </view>
              </view>

              <view class="price-info">
                <view class="price-row">
                  <cl-text value="订单金额" color="#666" size="28" />
                  <cl-text :value="`¥${item.totalAmount}`" color="#333" size="28" bold />
                </view>
                <view v-if="item.discountAmount !== '0'" class="price-row">
                  <cl-text value="优惠金额" color="#666" size="28" />
                  <cl-text :value="`-¥${item.discountAmount}`" color="#333" size="28" bold />
                </view>
                <view class="price-row">
                  <cl-text value="实付金额" color="#666" size="28" />
                  <cl-text :value="`¥${item.actualAmount}`" color="danger" size="32" bold />
                </view>
              </view>
            </view>

            <view class="footer">
              <cl-button
                v-if="item.status === 0"
                type="primary"
                round
                :width="160"
                :margin="[0, 0, 0, 20]"
                @tap.stop="handlePay(item)"
              >
                继续支付
              </cl-button>

              <cl-button
                v-if="item.status === 0"
                round
                :width="160"
                @tap.stop="handleCancel(item)"
              >
                取消订单
              </cl-button>

              <cl-button
                v-if="item.status === 1"
                type="danger"
                round
                :width="160"
                @tap.stop="handleRefund(item)"
              >
                申请退款
              </cl-button>
            </view>
          </view>
        </cl-card>
      </view>

      <!-- 加载更多 -->
      <cl-loadmore :loading="loading" />
    </cl-scroller>

    <tabbar />
  </cl-page>
</template>

<style lang="scss" scoped>
.search-form {
  padding: 20rpx;
  max-width: 900rpx;
  margin: 0 auto;

  .basic-search {
    :deep(.cl-form-item) {
      margin-bottom: 20rpx;

      .cl-form-item__content {
        flex: 1;

        .cl-input,
        .cl-select {
          width: 100%;
        }
      }
    }
  }
}

.filter-btns {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 20rpx;
  padding: 30rpx 20rpx;
  border-top: 2rpx solid #f5f5f5;
  margin-top: 20rpx;

  .cl-button {
    margin: 0;
  }
}

.order-list {
  padding: 20rpx 0;
}

.order-item {
  .header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 20rpx;
    border-bottom: 2rpx solid #f5f5f5;
  }

  .content {
    padding: 20rpx;

    .meal-info {
      display: flex;
      align-items: flex-start;

      .meal-detail {
        flex: 1;
        margin-left: 20rpx;
      }
    }

    .price-info {
      margin-top: 20rpx;
      padding-top: 20rpx;
      border-top: 2rpx solid #f5f5f5;

      .price-row {
        display: flex;
        align-items: center;
        justify-content: space-between;
        margin-bottom: 10rpx;

        &:last-child {
          margin-bottom: 0;
        }
      }
    }
  }

  .footer {
    display: flex;
    justify-content: flex-end;
    padding: 20rpx;
    border-top: 2rpx solid #f5f5f5;
  }
}
</style>
