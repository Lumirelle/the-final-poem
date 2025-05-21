<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useCool, usePager, useStore } from '/@/cool'
import { useI18n } from 'vue-i18n'
import Tabbar from './components/tabbar.vue'

const { service } = useCool()
const { t } = useI18n()
const { onRefresh } = usePager()
const { dict, user } = useStore()

const list = ref<any[]>([])
const loading = ref(false)

// 刷新列表
async function refresh() {
  const { data, next } = onRefresh()

  loading.value = true

  try {
    const res = await next(
      service.feedback.complaint.page({
        ...data,
      }),
    )

    // @ts-expect-error xxx
    list.value = res.list
  }
  finally {
    loading.value = false
  }
}

onMounted(() => {
  refresh()
})


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

function getTypeName(type: number) {
  // 0=服务态度 1=价格问题 2=服务质量 3=其他
  switch (type) {
    case 0: return t('服务态度')
    case 1: return t('价格问题')
    case 2: return t('服务质量')
    case 3: return t('其他')
    default: return type
  }
}
</script>

<template>
  <cl-page fullscreen>
    <cl-topbar :title="t('我的陪诊反馈')" />
    <cl-scroller :loading="loading">
      <view v-if="list.length === 0" class="empty">
        <cl-empty :text="t('暂无反馈记录')" />
      </view>
      <view v-else class="feedback-list">
        <cl-card v-for="item in list" :key="item.id" :title="item.content" :desc="item.createTime">
          <template #extra>
            <cl-tag v-if="item.status === 0" type="warning">{{ t('待处理') }}</cl-tag>
            <cl-tag v-else-if="item.status === 1" type="info">{{ t('处理中') }}</cl-tag>
            <cl-tag v-else-if="item.status === 2" type="success">{{ t('已解决') }}</cl-tag>
            <cl-tag v-else-if="item.status === 3" type="danger">{{ t('未解决') }}</cl-tag>
            <cl-tag v-else-if="item.status === 4" type="default">{{ t('已关闭') }}</cl-tag>
          </template>
          <view class="row"><cl-text type="info">{{ t('订单号：') }}</cl-text>{{ item.orderNumber || '-' }}</view>
          <view class="row"><cl-text type="info">{{ t('反馈人：') }}</cl-text>{{ item.userNickName || '-' }}</view>
          <view class="row">{{ t('类型：') }}{{ getTypeName(item.type) }}</view>
          <view class="row">{{ t('联系方式：') }}{{ item.contactInfo || '-' }}</view>
          <view v-if="item.images && (Array.isArray(item.images) ? item.images.length : item.images)" class="images">
            <cl-image v-for="img in (Array.isArray(item.images) ? item.images : (item.images ? item.images.split(',') : []))" :key="img" :src="img" :size="120" radius="8" :margin="[0,12,0,0]" />
          </view>
          <view v-if="item.handleResult" class="handle-result">{{ t('处理结果：') }}{{ item.handleResult }}</view>
          <view v-if="item.remark" class="remark">{{ t('备注：') }}{{ item.remark }}</view>
        </cl-card>
      </view>
    </cl-scroller>
    <tabbar />
  </cl-page>
</template>

<style lang="scss" scoped>
.feedback-list {
  padding: 24rpx;
  .cl-card {
    margin-bottom: 24rpx;
    .row {
      margin-bottom: 8rpx;
      color: #666;
      font-size: 28rpx;
      display: flex;
      align-items: center;
    }
    .images {
      margin: 12rpx 0;
      display: flex;
      flex-wrap: wrap;
    }
    .handle-result {
      color: #4caf50;
      margin-top: 8rpx;
    }
    .remark {
      color: #999;
      margin-top: 8rpx;
    }
  }
}
.empty {
  margin-top: 120rpx;
}
</style>
