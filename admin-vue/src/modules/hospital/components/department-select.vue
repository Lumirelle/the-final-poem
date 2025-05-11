<script setup lang="ts">
import { ref, useModel } from 'vue'
import { useI18n } from 'vue-i18n'
import { CrudProps } from '/#/crud'
import { useCool } from '/@/cool'

defineOptions({
  name: 'hospital-department-select',
})

const props = defineProps({
  ...CrudProps,
  modelValue: null,
  multiple: Boolean,
})

const { service } = useCool()
const { t } = useI18n()

const value = useModel(props, 'modelValue')

const columns = ref([
  { label: t('名称'), prop: 'name', minWidth: 140 },
  { label: t('编码'), prop: 'code', minWidth: 140 },
  { label: t('类型'), prop: 'type', minWidth: 120 },
  { label: t('负责人ID（关联医生）'), prop: 'headDoctorId', minWidth: 120 },
  { label: t('状态'), prop: 'status', minWidth: 120 },
])
</script>

<template>
  <cl-select-table
    v-model="value"
    :title="t('选择科室信息')"
    :service="service.hospital.department"
    :columns="columns"
    :multiple="multiple"
    :dict="{ text: 'name' }"
    picker-type="text"
  />
</template>
