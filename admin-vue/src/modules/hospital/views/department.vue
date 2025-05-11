<script lang="ts" setup>
import { useCrud, useSearch, useTable, useUpsert } from '@cool-vue/crud'
import { useI18n } from 'vue-i18n'
import DoctorSelect from '../components/doctor-select.vue'
import HospitalSelect from '../components/hospital-select.vue'
import { useDict } from '/$/dict'
import { useCool } from '/@/cool'

defineOptions({
  name: 'hospital-department',
})

const { service } = useCool()
const { t } = useI18n()
const { dict } = useDict()

// cl-upsert
const Upsert = useUpsert({
  items: [
    () => {
      return {
        label: t('选择关联的医院'),
        prop: 'hospitalId',
        hidden: Upsert.value?.mode === 'update',
        component: { vm: HospitalSelect },
        span: 12,
        required: true,
      }
    },
    {
      label: t('名称'),
      prop: 'name',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
      required: true,
    },
    {
      label: t('编码'),
      prop: 'code',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
      required: true,
    },
    () => {
      return {
        label: Upsert.value?.mode === 'add' ? t('选择关联的负责人') : t('选择新的负责人（置空为不修改）'),
        prop: 'headDoctorId',
        component: { vm: DoctorSelect },
        span: 12,
        required: true,
      }
    },
    {
      label: t('类型'),
      prop: 'type',
      value: dict.getByLabel('department-type', '临床'),
      component: { name: 'el-radio-group', options: dict.get('department-type') },
      span: 12,
      required: true,
    },
    {
      label: t('状态'),
      prop: 'status',
      value: dict.getByLabel('base-status', '启用'),
      component: { name: 'el-radio-group', options: dict.get('base-status') },
      span: 12,
      required: true,
    },
  ],
})

// cl-table
const Table = useTable({
  columns: [
    { type: 'selection' },
    { label: t('科室 ID'), prop: 'id', minWidth: 140 },
    { label: t('名称'), prop: 'name', minWidth: 140 },
    { label: t('编码'), prop: 'code', minWidth: 140 },
    {
      label: t('负责人 ID'),
      prop: 'headDoctorId',
      minWidth: 120,
    },
    { label: t('负责人名称'), prop: 'headDoctorName', minWidth: 120 },
    { label: t('类型'), prop: 'type', dict: dict.get('department-type'), minWidth: 120 },
    { label: t('状态'), prop: 'status', dict: dict.get('base-status'), minWidth: 120 },
    { label: t('医院'), prop: 'hospitalId', minWidth: 120 },
    { label: t('医院名称'), prop: 'hospitalName', minWidth: 120 },
    { label: t('医院编码'), prop: 'hospitalCode', minWidth: 120 },
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
    service: service.hospital.department,
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
