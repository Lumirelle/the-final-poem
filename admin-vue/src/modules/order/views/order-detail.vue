<script lang="ts" setup>
import { useCrud, useSearch, useTable, useUpsert } from '@cool-vue/crud'
import { useI18n } from 'vue-i18n'
import { useCool } from '/@/cool'

defineOptions({
  name: 'order-order-detail',
})

const { service } = useCool()
const { t } = useI18n()

// cl-upsert
const Upsert = useUpsert({
  items: [
    {
      label: t('选择商品'),
      prop: 'goodsId',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
    {
      label: t('数量'),
      prop: 'quantity',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('单价'),
      prop: 'price',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('总价'),
      prop: 'totalPrice',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('优惠金额'),
      prop: 'discountAmount',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('实付金额'),
      prop: 'actualAmount',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
    },
    {
      label: t('物流单号'),
      prop: 'logisticsNumber',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
    {
      label: t('售后状态'),
      prop: 'afterSaleStatus',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
    {
      label: t('选择订单'),
      prop: 'orderId',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
  ],
})

// cl-table
const Table = useTable({
  columns: [
    { type: 'selection' },
    { label: t('商品ID'), prop: 'goodsId', minWidth: 140 },
    {
      label: t('数量'),
      prop: 'quantity',
      minWidth: 140,
      sortable: 'custom',
    },
    { label: t('单价'), prop: 'price', minWidth: 140, sortable: 'custom' },
    {
      label: t('总价'),
      prop: 'totalPrice',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('优惠金额'),
      prop: 'discountAmount',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('实付金额'),
      prop: 'actualAmount',
      minWidth: 140,
      sortable: 'custom',
    },
    { label: t('物流单号'), prop: 'logisticsNumber', minWidth: 140 },
    { label: t('售后状态'), prop: 'afterSaleStatus', minWidth: 120 },
    { label: t('订单ID'), prop: 'orderId', minWidth: 140 },
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
    service: service.order.detail,
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
