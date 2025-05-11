<script lang="ts" setup>
import { useCrud, useSearch, useTable, useUpsert } from '@cool-vue/crud'
import { reactive } from 'vue'
import { useI18n } from 'vue-i18n'
import { useDict } from '/$/dict'
import { useCool } from '/@/cool'
import { phoneValidator } from '/@/cool/utils'

defineOptions({
  name: 'user-list',
})

const { t } = useI18n()
const { service } = useCool()
const { dict } = useDict()
const options = reactive({
  loginType: [
    {
      label: t('小程序'),
      value: 0,
      type: 'danger',
    },
    {
      label: t('公众号'),
      value: 1,
      type: 'success',
    },
    {
      label: t('H5'),
      value: 2,
    },
  ],
})

// cl-table
const Table = useTable({
  columns: [
    {
      type: 'selection',
      width: 60,
    },
    {
      label: t('用户 ID'),
      prop: 'id',
      minWidth: 100,
    },
    {
      label: t('昵称'),
      prop: 'nickName',
      minWidth: 150,
    },
    {
      label: t('头像'),
      prop: 'avatarUrl',
      minWidth: 100,
      component: {
        name: 'cl-avatar',
      },
    },
    {
      label: t('手机号'),
      prop: 'phone',
      minWidth: 120,
    },
    {
      label: t('性别'),
      prop: 'gender',
      dict: dict.get('gender'),
      minWidth: 120,
    },
    {
      label: t('角色'),
      prop: 'role',
      minWidth: 120,
      dict: dict.get('user-role'),
    },
    {
      label: t('登录方式'),
      prop: 'loginType',
      dict: options.loginType,
      minWidth: 120,
    },
    {
      label: t('状态'),
      prop: 'status',
      minWidth: 120,
      dict: dict.get('user-status'),
    },
    {
      label: t('创建时间'),
      prop: 'createTime',
      sortable: 'desc',
      minWidth: 170,
    },
    {
      type: 'op',
    },
  ],
})

// cl-upsert
const Upsert = useUpsert({
  items: [
    {
      prop: 'avatarUrl',
      label: t('头像'),
      component: { name: 'cl-upload' },
    },
    {
      prop: 'nickName',
      label: t('昵称'),
      component: { name: 'el-input' },
      required: true,
    },
    {
      prop: 'phone',
      label: t('手机号'),
      component: {
        name: 'el-input',
        props: {
          maxlength: 11,
        },
      },
      rules: { required: true, trigger: 'blur', validator: phoneValidator },
    },
    {
      prop: 'gender',
      label: t('性别'),
      value: dict.getByLabel('gender', '男'),
      component: {
        name: 'el-radio-group',
        options: dict.get('gender'),
      },
    },
    {
      prop: 'role',
      label: t('角色'),
      value: dict.getByLabel('user-role', '患者'),
      component: {
        name: 'el-radio-group',
        options: dict.get('user-role'),
      },
    },
    {
      prop: 'status',
      label: t('状态'),
      value: dict.getByLabel('user-status', '启用'),
      component: {
        name: 'el-radio-group',
        options: dict.get('user-status'),
      },
    },
  ],
})

// cl-search
const Search = useSearch()

// cl-crud
const Crud = useCrud(
  {
    service: service.user.info,
  },
  (app) => {
    app.refresh()
  },
)
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

      <!-- 搜索 -->
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
