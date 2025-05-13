<script lang="ts" setup>
import { useCrud, useSearch, useTable, useUpsert } from '@cool-vue/crud'
import { useI18n } from 'vue-i18n'
import { useCool } from '/@/cool'

defineOptions({
  name: 'order-order-log',
})

const { service } = useCool()
const { t } = useI18n()

// cl-upsert
const Upsert = useUpsert({
  items: [
    {
      label: t('操作类型'),
      prop: 'operationType',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
    {
      label: t('操作内容'),
      prop: 'operationContent',
      component: {
        name: 'el-input',
        props: { type: 'textarea', rows: 4 },
      },
    },
    {
      label: t('选择操作人员'),
      prop: 'operatorId',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
    {
      label: t('操作时间'),
      prop: 'operationTime',
      component: {
        name: 'el-date-picker',
        props: { type: 'datetime', valueFormat: 'YYYY-MM-DD HH:mm:ss' },
      },
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
    { label: t('操作类型'), prop: 'operationType', minWidth: 120 },
    {
      label: t('操作内容'),
      prop: 'operationContent',
      showOverflowTooltip: true,
      minWidth: 200,
    },
    { label: t('操作人员ID'), prop: 'operatorId', minWidth: 140 },
    {
      label: t('操作时间'),
      prop: 'operationTime',
      minWidth: 170,
      sortable: 'custom',
      component: { name: 'cl-date-text' },
    },
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
    service: service.order.log,
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
