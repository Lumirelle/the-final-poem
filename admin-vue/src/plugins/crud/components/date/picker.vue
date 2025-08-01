<script lang="ts" setup>
import type { PropType } from 'vue'
import { useCrud } from '@cool-vue/crud'
import dayjs from 'dayjs'
import { computed, ref, useModel } from 'vue'

defineOptions({
  name: 'cl-date-picker',
})

const props = defineProps({
  modelValue: null,
  // 日期类型
  type: {
    type: String as PropType<
      | 'year'
      | 'month'
      | 'date'
      | 'dates'
      | 'datetime'
      | 'week'
      | 'datetimerange'
      | 'daterange'
      | 'monthrange'
    >,
    default: 'datetimerange',
  },
  // 日期格式
  valueFormat: {
    type: null,
    default: 'YYYY-MM-DD HH:mm:ss',
  },
  // 搜索请求的字段
  prop: String,
  // 宽度
  width: String,
  // 是否显示快速按钮
  quickBtn: Boolean,
  // 默认按钮类型
  defaultQuickType: {
    type: String as PropType<'day' | 'week' | 'month' | 'year' | ''>,
    default: 'day',
  },
  // 筛选后是否刷新
  enableRefresh: {
    type: Boolean,
    default: true,
  },
})

const emit = defineEmits(['update:modelValue', 'change'])

const Crud = useCrud()

// 是否是范围
const isRange = computed(() => props.type.includes('range'))

// 默认时间
const defaultTime = ref<any>(
  isRange.value ? [new Date('2000-01-01 00:00:00'), new Date('2000-01-01 23:59:59')] : undefined,
)

// 日期
const date = useModel(props, 'modelValue')

// 按钮类型
const quickType = ref(props.defaultQuickType)

// 日期改变
function onChange(value: any) {
  // 重置按钮类型
  quickType.value = ''

  // 参数
  let params = {}

  if (value === null) {
    value = undefined
  }

  if (isRange.value) {
    let [startTime, endTime] = value || []

    if (props.type == 'monthrange') {
      startTime = dayjs(startTime).format('YYYY-MM-01 00:00:00')
      endTime = dayjs(endTime).endOf('month').format('YYYY-MM-DD 23:59:59')
    }

    params = {
      [props.prop ? `${props.prop}StartTime` : 'startTime']: startTime,
      [props.prop ? `${props.prop}EndTime` : 'endTime']: endTime,
    }
  }
  else {
    params = {
      // @ts-expect-error xxx
      [props.prop]: value,
    }
  }

  // 筛选列表
  if (props.enableRefresh) {
    Crud.value?.refresh({
      ...params,
      page: 1,
    })
  }

  emit('update:modelValue', value)
  emit('change', value)
}

// 按钮类型改变
function onQuickTypeChange() {
  date.value = isRange.value ? [] : undefined

  let start = dayjs()
  const end = dayjs()

  switch (quickType.value) {
    case 'day':
      break
    case 'week':
      start = dayjs().startOf('week').add(1, 'day')
      break
    case 'month':
      start = dayjs().startOf('month')
      break
    case 'year':
      start = dayjs().startOf('year')
      break
  }

  const startTime = start.format('YYYY-MM-DD')
  const endTime = end.format('YYYY-MM-DD')

  Crud.value?.refresh({
    page: 1,
    startTime,
    endTime,
  })

  emit('update:modelValue', [startTime, endTime])
  emit('change', [startTime, endTime])
}

function init() {
  onQuickTypeChange()
}

defineExpose({
  init,
})
</script>

<template>
  <div class="cl-date-picker">
    <el-date-picker
      v-model="date"
      :type="type"
      :editable="false"
      :default-time="defaultTime"
      :value-format="valueFormat"
      :style="{ width }"
      @change="onChange"
    />

    <el-radio-group v-if="quickBtn && isRange" v-model="quickType" @change="onQuickTypeChange">
      <el-radio-button value="day">
        {{ $t('今日') }}
      </el-radio-button>
      <el-radio-button value="week">
        {{ $t('本周') }}
      </el-radio-button>
      <el-radio-button value="month">
        {{ $t('本月') }}
      </el-radio-button>
      <el-radio-button value="year">
        {{ $t('今年') }}
      </el-radio-button>
    </el-radio-group>
  </div>
</template>

<style lang="scss" scoped>
.cl-date-picker {
  display: inline-flex;

  :deep(.el-date-editor) {
    box-sizing: border-box;
    margin-right: 10px;
  }
}
</style>
