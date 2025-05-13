<script lang="ts" setup>
import { useCrud, useSearch, useTable, useUpsert } from '@cool-vue/crud'
import { useI18n } from 'vue-i18n'
import { useCool } from '/@/cool'

defineOptions({
  name: 'order-order-statistics',
})

const { service } = useCool()
const { t } = useI18n()

// cl-upsert
const Upsert = useUpsert({
  items: [
    {
      label: t('订单总数'),
      prop: 'totalOrders',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('总金额'),
      prop: 'totalAmount',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('退款数'),
      prop: 'refundCount',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('完成数'),
      prop: 'completedCount',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('取消数'),
      prop: 'cancelledCount',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('支付订单数'),
      prop: 'paidOrders',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('配送订单数'),
      prop: 'deliveringOrders',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('统计日期'),
      prop: 'statisticsDate',
      component: {
        name: 'el-date-picker',
        props: { type: 'date', valueFormat: 'YYYY-MM-DD' },
      },
      span: 12,
    },
  ],
})

// cl-table
const Table = useTable({
  columns: [
    { type: 'selection' },
    {
      label: t('订单总数'),
      prop: 'totalOrders',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('总金额'),
      prop: 'totalAmount',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('退款数'),
      prop: 'refundCount',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('完成数'),
      prop: 'completedCount',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('取消数'),
      prop: 'cancelledCount',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('支付订单数'),
      prop: 'paidOrders',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('配送订单数'),
      prop: 'deliveringOrders',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('统计日期'),
      prop: 'statisticsDate',
      minWidth: 140,
      sortable: 'custom',
      component: {
        name: 'cl-date-text',
        props: { format: 'YYYY-MM-DD' },
      },
    },
    {
      label: t('创建时间'),
      prop: 'createTime',
      minWidth: 170,
      sortable: 'desc',
      component: { name: 'cl-date-text' },
    },
    {
      label: t('更新时间'),
      prop: 'updateTime',
      minWidth: 170,
      sortable: 'custom',
      component: { name: 'cl-date-text' },
    },
    { type: 'op', buttons: ['edit', 'delete'] },
  ],
})

// cl-search
const Search = useSearch()

// cl-crud
const Crud = useCrud(
  {
    service: service.order.statistics,
  },
  (app) => {
    app.refresh()
  },
)

// 刷新
function refresh(params?: any) {
  Crud.value?.refresh(params)
}
</script>

<template>
  <cl-crud ref="Crud">
    <cl-row>
      <!-- 刷新按钮 -->
      <cl-refresh-btn />
      <!-- 新增按钮 -->
      <cl-add-btn />
      <!-- 删除按钮 -->
      <cl-multi-delete-btn />
      <cl-flex1 />
      <!-- 条件搜索 -->
      <cl-search ref="Search" />
    </cl-row>

    <cl-row>
      <!-- 数据表格 -->
      <cl-table ref="Table" />
    </cl-row>

    <cl-row>
      <cl-flex1 />
      <!-- 分页控件 -->
      <cl-pagination />
    </cl-row>

    <!-- 新增、编辑 -->
    <cl-upsert ref="Upsert" />
  </cl-crud>
</template>
