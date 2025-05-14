<script lang="ts" setup>
import { useCrud, useSearch, useTable, useUpsert } from '@cool-vue/crud'
import { watch } from 'vue'
import { useI18n } from 'vue-i18n'
import { useDict } from '/$/dict'
import MealSelect from '/$/meal/components/meal-select.vue'
import UserSelect from '/$/user/components/user-select.vue'
import { useCool } from '/@/cool'

defineOptions({
  name: 'order-info',
})

const { service } = useCool()
const { t } = useI18n()
const { dict } = useDict()

// cl-upsert
const Upsert = useUpsert({
  items: [
    {
      label: t('编号'),
      prop: 'orderNumber',
      component: { name: 'el-input', props: { clearable: true } },
      span: 24,
      required: true,
    },
    {
      label: t('套餐'),
      prop: 'mealId',
      component: { vm: MealSelect },
      span: 24,
      required: true,
    },
    {
      label: t('选择用户'),
      prop: 'userId',
      component: { vm: UserSelect, props: { role: dict.getByLabel('user-role', '患者') || 1 } },
      required: true,
    },
    {
      label: t('总金额'),
      prop: 'totalAmount',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0.01 } },
      span: 12,
      required: true,
    },
    // 优惠金额
    {
      label: t('优惠金额'),
      prop: 'discountAmount',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
      required: true,
    },
    // 实付金额自动根据总金额和优惠金额计算
    () => {
      return {
        label: t('实付金额'),
        prop: 'actualAmount',
        hook: 'number',
        component: { name: 'el-input-number', props: { min: 0.01, disabled: true } },
        span: 12,
        required: true,
      }
    },
    {
      label: t('支付方式'),
      prop: 'payType',
      component: {
        name: 'el-select',
        options: dict.get('order-pay-type'),
        props: { clearable: true },
      },
      span: 12,
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

watch(
  () => Upsert.value?.form.totalAmount - Upsert.value?.form.discountAmount,
  (val) => {
    if (val < 0) {
      Upsert.value?.setForm('actualAmount', 0.01)
    }
    else {
      Upsert.value?.setForm('actualAmount', val)
    }
  },
)

// cl-table
const Table = useTable({
  columns: [
    { type: 'selection' },
    { label: t('编号'), prop: 'orderNumber', minWidth: 140 },
    { label: t('套餐ID'), prop: 'mealId', minWidth: 140 },
    { label: t('套餐'), prop: 'mealName', minWidth: 140 },
    { label: t('用户ID'), prop: 'userId', minWidth: 140 },
    { label: t('用户'), prop: 'userName', minWidth: 140 },
    {
      label: t('总金额'),
      prop: 'totalAmount',
      minWidth: 140,
      sortable: 'custom',
    },
    { label: t('优惠金额'), prop: 'discountAmount', minWidth: 140 },
    { label: t('实付金额'), prop: 'actualAmount', minWidth: 140 },
    { label: t('支付方式'), prop: 'payType', minWidth: 120, dict: dict.get('pay-type') },
    { label: t('状态'), prop: 'status', minWidth: 120, dict: dict.get('order-status') },
    {
      label: t('备注'),
      prop: 'remark',
      showOverflowTooltip: true,
      minWidth: 200,
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
  ],
})

// cl-search
const Search = useSearch()

// cl-crud
const Crud = useCrud(
  {
    service: service.order.info,
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
