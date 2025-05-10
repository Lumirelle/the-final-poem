<script setup lang="ts">
import { ref, useModel } from 'vue'
import { useI18n } from 'vue-i18n'
import { useCool } from '/@/cool'

defineOptions({
  name: 'user-select',
})

const props = defineProps({
  modelValue: null,
  multiple: Boolean,
})

const { service } = useCool()
const { t } = useI18n()

const value = useModel(props, 'modelValue')

const columns = ref([
  {
    prop: 'avatarUrl',
    label: t('头像'),
    component: {
      name: 'cl-avatar',
    },
    minWidth: 100,
  },
  {
    prop: 'phone',
    label: t('手机号'),
    minWidth: 120,
  },
  {
    prop: 'nickName',
    label: t('姓名'),
    minWidth: 150,
  },
  {
    label: t('状态'),
    prop: 'status',
    minWidth: 100,
    dict: [
      {
        label: t('正常'),
        value: 1,
        type: 'success',
      },
      {
        label: t('禁用'),
        value: 0,
        type: 'danger',
      },
    ],
  },
])
</script>

<template>
  <cl-select-table
    v-model="value"
    :title="t('选择用户')"
    :service="service.user.info"
    :columns="columns"
    :multiple="multiple"
  />
</template>
