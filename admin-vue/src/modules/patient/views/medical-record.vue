<script lang="ts" setup>
import { useCrud, useSearch, useTable, useUpsert } from '@cool-vue/crud'
import { useI18n } from 'vue-i18n'
import PatientSelect from '/$/patient/components/patient-select.vue'
import { useCool } from '/@/cool'

defineOptions({
  name: 'medical-record',
})

const { service } = useCool()
const { t } = useI18n()

// cl-upsert
const Upsert = useUpsert({
  items: [
    () => {
      return {
        label: t('选择患者'),
        prop: 'patientId',
        hidden: Upsert.value?.mode === 'update',
        component: { vm: PatientSelect },
        required: true,
      }
    },
    {
      label: t('就诊日期'),
      prop: 'visitDate',
      component: {
        name: 'el-date-picker',
        props: { type: 'date', valueFormat: 'YYYY-MM-DD HH:mm:ss' },
      },
      required: true,
      span: 12,
    },
    {
      label: t('医院'),
      prop: 'hospital',
      component: { name: 'el-input', props: { clearable: true } },
      required: true,
      span: 12,
    },
    {
      label: t('医生姓名'),
      prop: 'doctorName',
      component: { name: 'el-input', props: { clearable: true } },
      required: true,
      span: 12,
    },
    {
      label: t('诊断结果'),
      prop: 'diagnosis',
      component: {
        name: 'el-input',
        props: { type: 'textarea', rows: 4 },
      },
      required: true,
    },
    {
      label: t('处方内容'),
      prop: 'prescription',
      component: {
        name: 'el-input',
        props: { type: 'textarea', rows: 4 },
      },
      required: true,
    },
    {
      label: t('费用'),
      prop: 'cost',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      required: true,
      span: 12,
    },
  ],
})

// cl-table
const Table = useTable({
  columns: [
    { type: 'selection' },

    { label: t('就诊记录 ID'), prop: 'id', minWidth: 140 },
    {
      label: t('就诊日期'),
      prop: 'visitDate',
      minWidth: 140,
      sortable: 'custom',
      component: {
        name: 'cl-date-text',
        props: { format: 'YYYY-MM-DD' },
      },
    },
    { label: t('医院'), prop: 'hospital', minWidth: 140 },
    { label: t('医生姓名'), prop: 'doctorName', minWidth: 140 },
    {
      label: t('诊断结果'),
      prop: 'diagnosis',
      showOverflowTooltip: true,
      minWidth: 200,
    },
    {
      label: t('处方内容'),
      prop: 'prescription',
      showOverflowTooltip: true,
      minWidth: 200,
    },
    { label: t('费用'), prop: 'cost', minWidth: 140, sortable: 'custom' },
    { label: t('患者ID'), prop: 'patientId', minWidth: 140 },
    { label: t('患者姓名'), prop: 'patientName', minWidth: 140 },
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
    // service: service.<module name>.<controller name split by dot>
    service: service.patient.medical.record,
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
