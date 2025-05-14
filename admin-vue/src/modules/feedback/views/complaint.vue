<script lang="ts" setup>
import { useCrud, useSearch, useTable, useUpsert } from '@cool-vue/crud'
import { useI18n } from 'vue-i18n'
import UserSelect from '/$/user/components/user-select.vue'
import { useCool } from '/@/cool'

defineOptions({
  name: 'feedback-complaint',
})

const { service } = useCool()
const { t } = useI18n()

// cl-upsert
const Upsert = useUpsert({
  items: [
    {
      label: t('单号'),
      prop: 'complaintNo',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
      required: true,
    },
    {
      label: t('类型'),
      prop: 'type',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
      required: true,
    },
    {
      label: t('状态'),
      prop: 'status',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
      required: true,
    },
    {
      label: t('内容'),
      prop: 'content',
      component: {
        name: 'el-input',
        props: { type: 'textarea', rows: 4 },
      },
      required: true,
    },
    {
      label: t('选择用户'),
      prop: 'userId',
      component: { vm: UserSelect },
      required: true,
    },
    {
      label: t('选择处理人'),
      prop: 'handlerId',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
      required: true,
    },
    {
      label: t('处理结果'),
      prop: 'handleResult',
      component: {
        name: 'el-input',
        props: { type: 'textarea', rows: 4 },
      },
      required: true,
    },
    {
      label: t('备注'),
      prop: 'remark',
      component: {
        name: 'el-input',
        props: { type: 'textarea', rows: 4 },
      },
      required: true,
    },
    {
      label: t('创建时间'),
      prop: 'nickName',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
      required: true,
    },
  ],
})

// cl-table
const Table = useTable({
  columns: [
    { type: 'selection' },
    { label: t('单号'), prop: 'complaintNo', minWidth: 140 },
    { label: t('类型'), prop: 'type', minWidth: 120 },
    { label: t('状态'), prop: 'status', minWidth: 120 },
    {
      label: t('内容'),
      prop: 'content',
      showOverflowTooltip: true,
      minWidth: 200,
    },
    { label: t('处理人ID'), prop: 'handlerId', minWidth: 120 },
    {
      label: t('处理结果'),
      prop: 'handleResult',
      showOverflowTooltip: true,
      minWidth: 200,
    },
    {
      label: t('备注'),
      prop: 'remark',
      showOverflowTooltip: true,
      minWidth: 200,
    },
    { label: t('创建时间'), prop: 'nickName', minWidth: 140 },
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
    { type: 'op', buttons: ['delete'] },
  ],
})

// cl-search
const Search = useSearch()

// cl-crud
const Crud = useCrud(
  {
    service: service.feedback.complaint,
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
