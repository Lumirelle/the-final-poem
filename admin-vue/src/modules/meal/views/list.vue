<script lang="ts" setup>
import { useCrud, useSearch, useTable, useUpsert } from '@cool-vue/crud'
import { useI18n } from 'vue-i18n'
import CategorySelect from '../components/category-select.vue'
import { useDict } from '/$/dict'
import { useCool } from '/@/cool'

defineOptions({
  name: 'meal-list',
})

const { service } = useCool()
const { t } = useI18n()
const { dict } = useDict()

// cl-upsert
const Upsert = useUpsert({
  items: [
    () => {
      return {
        label: t('选择分类'),
        prop: 'categoryId',
        hidden: Upsert?.value.mode === 'update',
        component: { vm: CategorySelect },
        span: 12,
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
      label: t('价格（元）'),
      prop: 'price',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
      span: 12,
      required: true,
    },
    {
      label: t('时长（分钟）'),
      prop: 'duration',
      hook: 'number',
      component: { name: 'el-input-number', props: { min: 0 } },
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
    {
      label: t('服务范围'),
      prop: 'serviceArea',
      hook: 'json',
      component: {
        name: 'el-checkbox-group',
        options: dict.get('meal-service-area'),
      },
      span: 12,
      required: true,
    },
    {
      label: t('简介'),
      prop: 'intro',
      component: {
        name: 'el-input',
        props: { type: 'textarea', rows: 4 },
      },
    },
    { label: t('封面图'), prop: 'cover', component: { name: 'cl-upload' } },
  ],
})

// cl-table
const Table = useTable({
  columns: [
    { type: 'selection' },
    { label: t('名称'), prop: 'name', minWidth: 140 },
    { label: t('价格（元）'), prop: 'price', minWidth: 140, sortable: 'custom' },
    {
      label: t('时长（分钟）'),
      prop: 'duration',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('状态'),
      prop: 'status',
      minWidth: 100,
      component: { name: 'cl-switch' },
    },
    { label: t('分类ID'), prop: 'categoryId', minWidth: 120 },
    { label: t('分类名称'), prop: 'categoryName', minWidth: 120 },
    {
      label: t('简介'),
      prop: 'intro',
      showOverflowTooltip: true,
      minWidth: 200,
    },
    {
      label: t('封面图'),
      prop: 'cover',
      minWidth: 100,
      component: { name: 'cl-image', props: { size: 60 } },
    },
    {
      label: t('服务次数'),
      prop: 'serviceCount',
      minWidth: 140,
      sortable: 'custom',
    },
    {
      label: t('服务范围'),
      prop: 'serviceArea',
      minWidth: 120,
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
    service: service.meal.info,
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
      <cl-table ref="Table">
        <template #column-serviceArea="{ scope }">
          <el-tag
            v-for="area in JSON.parse(scope.row.serviceArea || '[]')"
            :key="area"
            :type="dict.get('meal-service-area').value.find(item => item.value === area)?.type || 'primary'"
            class="m-2"
          >
            {{ dict.get('meal-service-area').value.find(item => item.value === area)?.label || area }}
          </el-tag>
        </template>
      </cl-table>
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
