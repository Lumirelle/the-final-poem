<script lang="ts" setup>
import { useCrud, useUpsert } from '@cool-vue/crud'
import { ArrowLeft, ArrowRight } from '@element-plus/icons-vue'
import dayjs from 'dayjs'
import isoWeek from 'dayjs/plugin/isoWeek'
import weekOfYear from 'dayjs/plugin/weekOfYear'
import { computed, onMounted, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import 'dayjs/locale/zh-cn'
import { useCool } from '/@/cool'

defineOptions({
  name: 'accompany-schedule',
})

// 配置dayjs
dayjs.extend(weekOfYear)
dayjs.extend(isoWeek)
dayjs.locale('zh-cn')

const { service } = useCool()
const { t } = useI18n()

// 当前日期
const currentDate = ref(dayjs())

interface WeekRangeConfig {
  startDate: dayjs.Dayjs
  endDate: dayjs.Dayjs
  weekNumber: number
  year: number
  dateRange: string
}

// 计算当前周的开始日期（周一）和结束日期（周日）
const weekRange = computed(() => {
  const startOfWeek = currentDate.value.startOf('isoWeek')
  const endOfWeek = currentDate.value.endOf('isoWeek')

  return {
    startDate: startOfWeek,
    endDate: endOfWeek,
    weekNumber: startOfWeek.isoWeek(),
    year: startOfWeek.year(),
    dateRange: `${startOfWeek.format('MM/DD')} ~ ${endOfWeek.format('MM/DD')}`,
  } as WeekRangeConfig
})

interface WeekDayConfig {
  date: dayjs.Dayjs
  dayName: string
  dayOfMonth: string
  fullDate: string
}

// 获取当前周的所有日期（周一到周日）
const weekDays = computed(() => {
  const days: WeekDayConfig[] = []
  for (let i = 0; i < 7; i++) {
    const day = weekRange.value.startDate.add(i, 'day')
    days.push({
      date: day,
      dayName: day.format('ddd'),
      dayOfMonth: day.format('DD'),
      fullDate: day.format('YYYY-MM-DD'),
    })
  }
  return days
})

// 时段定义
const timeSlots = [
  { label: '08:00-10:00', value: [8, 10] },
  { label: '10:00-12:00', value: [10, 12] },
  { label: '14:00-16:00', value: [14, 16] },
  { label: '16:00-18:00', value: [16, 18] },
]

// 切换到上一周
function prevWeek() {
  currentDate.value = currentDate.value.subtract(1, 'week')
  refresh({
    size: 10,
  })
}

// 切换到下一周
function nextWeek() {
  currentDate.value = currentDate.value.add(1, 'week')
  refresh({
    size: 10,
  })
}

// 切换到当前周
function currentWeek() {
  currentDate.value = dayjs()
  refresh({
    size: 10,
  })
}

// 根据日期和时段格式化数据
function formatScheduleData(data) {
  // 创建初始化的数据结构
  const formattedData = {}

  // 初始化每个时段和日期的数据
  timeSlots.forEach((slot) => {
    formattedData[slot.label] = {}
    weekDays.value.forEach((day) => {
      formattedData[slot.label][day.fullDate] = []
    })
  })

  // 填充实际数据
  data.forEach((item) => {
    const date = item.scheduleDate
    const startTime = dayjs(item.timeSlots[0]).format('HH:mm')
    const endTime = dayjs(item.timeSlots[1]).format('HH:mm')
    const timeSlot = `${startTime}-${endTime}`

    // 找到对应的时段
    const matchingSlot = timeSlots.find(slot => slot.label === timeSlot)
    if (matchingSlot && formattedData[matchingSlot.label] && formattedData[matchingSlot.label][date]) {
      formattedData[matchingSlot.label][date].push(item)
    }
  })

  return formattedData
}

// 处理单元格内容的渲染
function renderCell(schedules) {
  if (!schedules || schedules.length === 0) {
    return '空闲'
  }

  return schedules.map((schedule) => {
    return `${schedule.staffName || '无名'} (${schedule.staffPhone || '无电话'})`
  }).join('\n')
}

// cl-upsert
const Upsert = useUpsert({
  items: [
    {
      label: t('日期'),
      prop: 'scheduleDate',
      component: {
        name: 'el-date-picker',
        props: { type: 'date', valueFormat: 'YYYY-MM-DD' },
      },
      span: 12,
    },
    {
      label: t('时段'),
      prop: 'timeSlots',
      component: {
        name: 'el-select',
        props: {
          placeholder: '选择时段',
          options: timeSlots,
        },
      },
      span: 12,
    },
    {
      label: t('选择陪诊员'),
      prop: 'staffId',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
    {
      label: t('陪诊员姓名'),
      prop: 'staffName',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
    {
      label: t('陪诊员电话'),
      prop: 'staffPhone',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
    {
      label: t('陪诊员级别'),
      prop: 'staffLevel',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
    {
      label: t('状态'),
      prop: 'status',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
    {
      label: t('选择操作人'),
      prop: 'operatorId',
      component: { name: 'el-input', props: { clearable: true } },
      span: 12,
    },
  ],
})

interface ScheduleData {
  id: string
  scheduleDate: string
  timeSlots: string[]
  staffId: string
  staffName: string
  staffPhone: string
  staffLevel: string
  status: string
  operatorId: string
}

// 存储排班数据
const scheduleData = ref<ScheduleData[]>([])

// cl-crud
const Crud = useCrud(
  {
    service: service.accompany.schedule,

    // 监听刷新事件
    async onRefresh(params, { next }) {
      // 获取当前周的日期范围
      const startDate = weekRange.value.startDate.format('YYYY-MM-DD')
      const endDate = weekRange.value.endDate.format('YYYY-MM-DD')

      // 添加日期筛选参数
      const queryParams = {
        ...params,
        dateRange: [startDate, endDate],
      }

      // 调用接口获取数据
      const res = await next(queryParams)

      // 保存原始数据用于渲染
      scheduleData.value = res.list || []

      return res
    },
  },
  (app) => {
    app.refresh()
  },
)

// 刷新
function refresh(params) {
  Crud.value?.refresh(params)
}

// 初始加载
onMounted(() => {
  refresh({
    size: 10,
  })
})

// 添加排班
function addSchedule(date, timeSlot) {
  console.log(timeSlot)
  if (Array.isArray(timeSlot) && timeSlot.length === 2) {
    const [startHour, endHour] = timeSlot
    const startTime = `${date} ${startHour.toString().padStart(2, '0')}:00:00`
    const endTime = `${date} ${endHour.toString().padStart(2, '0')}:00:00`

    Upsert.value?.open({
      form: {
        scheduleDate: date,
        timeSlots: [startTime, endTime],
      },
    })
  }
  else {
    console.error('无效的时段格式', timeSlot)
  }
}

// 编辑排班
function editSchedule(schedule) {
  Upsert.value?.open({
    mode: 'update',
    form: schedule,
  })
}

// 表格列配置
const tableColumns = computed(() => {
  return [
    {
      label: '时段',
      prop: 'label',
      width: 120,
    },
    // 动态生成星期几的列
    ...weekDays.value.map(day => ({
      label: `${day.dayName} (${day.dayOfMonth})`,
      prop: day.fullDate,
      align: 'center',
    })),
  ]
})
</script>

<template>
  <div class="schedule-container">
    <cl-crud ref="Crud">
      <!-- 标题栏 -->
      <cl-row>
        <div class="schedule-header">
          <div class="week-nav">
            <el-button :icon="ArrowLeft" @click="prevWeek" />
            <div class="week-info">
              <h2>{{ weekRange.year }}年 第{{ weekRange.weekNumber }}周 {{ weekRange.dateRange }}</h2>
            </div>
            <el-button :icon="ArrowRight" @click="nextWeek" />
            <el-button type="primary" @click="currentWeek">
              本周
            </el-button>
          </div>

          <div class="action-buttons">
            <cl-add-btn text="新增排班" @click="Upsert.value?.open()" />
            <cl-refresh-btn @click="refresh({ size: 10 })" />
          </div>
        </div>
      </cl-row>

      <!-- 周排班表格 -->
      <cl-row>
        <cl-table :columns="tableColumns" :data="timeSlots" border>
          <!-- 时段列 -->
          <template #column-label="{ scope }">
            {{ scope.row.label }}
          </template>

          <!-- 动态生成每天的插槽 -->
          <template v-for="day in weekDays" :key="day.fullDate" #[`column-${day.fullDate}`]="{ scope }">
            <div class="schedule-cell" @click="addSchedule(day.fullDate, scope.row.value)">
              <!-- 查找该日期和时段的排班 -->
              <template v-if="scheduleData.length > 0">
                <!-- 过滤出当前日期和时段的排班 -->
                <div
                  v-for="schedule in scheduleData.filter(s =>
                    s.scheduleDate === day.fullDate
                    && dayjs(s.timeSlots[0]).format('HH:mm') === `${scope.row.value[0].toString().padStart(2, '0')}:00`
                    && dayjs(s.timeSlots[1]).format('HH:mm') === `${scope.row.value[1].toString().padStart(2, '0')}:00`,
                  )"
                  :key="schedule.id"
                  class="staff-info"
                  @click.stop="editSchedule(schedule)"
                >
                  <div class="staff-name">
                    {{ schedule.staffName || '待安排' }}
                  </div>
                  <div class="staff-phone">
                    {{ schedule.staffPhone || 'N/A' }}
                  </div>
                </div>

                <!-- 如果没有排班 -->
                <div
                  v-if="!scheduleData.some(s =>
                    s.scheduleDate === day.fullDate
                    && dayjs(s.timeSlots[0]).format('HH:mm') === `${scope.row.value[0].toString().padStart(2, '0')}:00`
                    && dayjs(s.timeSlots[1]).format('HH:mm') === `${scope.row.value[1].toString().padStart(2, '0')}:00`,
                  )" class="no-schedule"
                >
                  空闲
                </div>
              </template>
              <div v-else class="no-schedule">
                空闲
              </div>
            </div>
          </template>
        </cl-table>
      </cl-row>

      <!-- 新增、编辑 -->
      <cl-upsert ref="Upsert" :on-submit="(data, { close }) => { refresh({ size: 10 }); close(); }" />
    </cl-crud>
  </div>
</template>

<style lang="scss" scoped>
.schedule-container {
  padding: 15px;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.schedule-header {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.week-nav {
  display: flex;
  align-items: center;
  gap: 10px;

  .week-info {
    min-width: 300px;
    text-align: center;

    h2 {
      margin: 0;
      font-size: 18px;
    }
  }
}

.action-buttons {
  display: flex;
  gap: 10px;
}

.schedule-table {
  flex: 1;
  overflow: auto;
}

.schedule-cell {
  cursor: pointer;
  min-height: 80px;
  padding: 8px;
  transition: background-color 0.3s;

  &:hover {
    background-color: #f5f7fa;
  }
}

.staff-info {
  padding: 5px;
  border-radius: 4px;
  background-color: #ecf5ff;
  margin-bottom: 5px;
  cursor: pointer;

  &:hover {
    background-color: #d9ecff;
  }

  .staff-name {
    font-weight: bold;
  }

  .staff-phone {
    font-size: 12px;
    color: #606266;
  }
}

.no-schedule {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60px;
  color: #909399;
  font-style: italic;
}
</style>
