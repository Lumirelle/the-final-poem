<script lang="ts" setup>
import { useCrud, useSearch, useTable, useUpsert } from '@cool-vue/crud'
import dayjs from 'dayjs'
import { useI18n } from 'vue-i18n'
import { useDict } from '/$/dict'
import UserSelect from '/$/user/components/user-select.vue'
import { useCool } from '/@/cool'
import { phoneValidator } from '/@/cool/utils'

defineOptions({
  name: 'accompany-staff',
})

const { service } = useCool()
const { t } = useI18n()
const { dict } = useDict()

// cl-upsert
const Upsert = useUpsert({
  items: [
    () => {
      return {
        label: t('选择关联用户'),
        prop: 'userId',
        hidden: Upsert.value?.mode === 'update',
        component: {
          vm: UserSelect,
          props: {
            role: dict.getByLabel('user-role', '陪诊人员'),
          },
        },
        required: true,
      }
    },
    {
      label: t('姓名'),
      prop: 'name',
      component: { name: 'el-input', props: { clearable: true } },
      required: true,
      span: 12,
    },
    {
      label: t('电话'),
      prop: 'phone',
      component: { name: 'el-input', props: { clearable: true } },
      rules: { required: true, trigger: 'blur', validator: phoneValidator },
      span: 12,
    },
    {
      label: t('性别'),
      prop: 'gender',
      value: dict.getByLabel('gender', '男'),
      component: { name: 'el-radio-group', options: dict.get('gender') },
      required: true,
      span: 12,
    },
    {
      label: t('级别'),
      prop: 'level',
      value: dict.getByLabel('acc-staff-level', '初级'),
      component: {
        name: 'el-radio-group',
        options: dict.get('acc-staff-level'),
      },
      span: 12,
      required: true,
    },
    {
      label: t('生日'),
      prop: 'birthday',
      component: {
        name: 'el-date-picker',
        props: { type: 'date', valueFormat: 'YYYY-MM-DD HH:mm:ss' },
      },
      required: true,
    },
    () => {
      return {
        label: t('状态'),
        prop: 'status',
        value: dict.getByLabel('acc-staff-status', '正常'),
        hidden: Upsert.value?.mode === 'add',
        component: {
          name: 'el-radio-group',
          options: dict.get('acc-staff-status'),
        },
        required: true,
      }
    },
    {
      label: t('简介'),
      prop: 'introduction',
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
    },
  ],
})

// cl-table
const Table = useTable({
  columns: [
    { type: 'selection' },
    { label: t('陪诊员 ID'), prop: 'id', minWidth: 120 },
    { label: t('姓名'), prop: 'name', minWidth: 140 },
    { label: t('性别'), prop: 'gender', minWidth: 120, dict: dict.get('gender') },
    {
      label: t('年龄'),
      prop: 'birthday',
      minWidth: 140,
      sortable: 'custom',
      formatter: (row: any) => {
        return row.birthday ? dayjs().diff(dayjs(row.birthday), 'year') : ''
      },
    },
    { label: t('电话'), prop: 'phone', minWidth: 140 },
    { label: t('级别'), prop: 'level', dict: dict.get('acc-staff-level'), minWidth: 120 },
    { label: t('状态'), prop: 'status', dict: dict.get('acc-staff-status'), minWidth: 120 },
    {
      label: t('简介'),
      prop: 'introduction',
      showOverflowTooltip: true,
      minWidth: 200,
    },
    {
      label: t('备注'),
      prop: 'remark',
      showOverflowTooltip: true,
      minWidth: 200,
    },
    { label: t('账户 ID'), prop: 'userId', minWidth: 140 },
    { label: t('账户昵称'), prop: 'nickName', minWidth: 140 },
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
    service: service.accompany.staff,
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
