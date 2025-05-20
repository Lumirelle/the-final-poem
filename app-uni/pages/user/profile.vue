<script lang="ts" setup>
import { ref, reactive, onMounted } from 'vue'
import { useCool } from '/@/cool'
import { useUi } from '/$/cool-ui'
import { useI18n } from 'vue-i18n'
import { cloneDeep } from 'lodash-es'

const { service, router } = useCool()
const { t } = useI18n()
const ui = useUi()

// 角色选项
const roleOptions = [
  { label: t('患者'), value: 1 },
  { label: t('陪诊人员'), value: 2 },
]

// 当前角色
const role = ref<1 | 2>(1)

// 患者档案表单
const patientForm = reactive<Eps.PatientInfoEntity>({
  name: '',
  gender: 0,
  birthday: undefined,
  phone: '',
  address: '',
  type: 0,
  medicalRecordNumber: '',
  medicalHistory: '',
  allergyHistory: '',
  remark: '',
  height: undefined,
  weight: undefined,
  systolicPressure: undefined,
  diastolicPressure: undefined,
  avatarUrl: '',
})

// 陪诊员档案表单
const staffForm = reactive<Eps.AccompanyStaffEntity>({
  name: '',
  gender: 0,
  birthday: undefined,
  phone: '',
  level: 0,
  status: 0,
  introduction: '',
  remark: '',
  avatarUrl: '',
})

// 是否已存在档案
const hasProfile = ref(false)

// 保存
async function onSave() {
  const data = role.value === 1 ? cloneDeep(patientForm) : cloneDeep(staffForm)
  data.role = role.value
  try {
    if (hasProfile.value) {
      await service.user.info.updateProfile(data)
      ui.showToast(t('保存成功'))
    } else {
      await service.user.info.addProfile(data)
      ui.showToast(t('创建成功'))
      hasProfile.value = true
    }
    // 保存后跳转到主页
    router.push('/pages/index/my')
  } catch (e: any) {
    ui.showToast(e.message || t('保存失败'))
  }
}

// 拉取档案
async function fetchProfile() {
  try {
    const res = await service.user.info.profile()
    if (res && res.role) {
      role.value = res.role
      hasProfile.value = true
      if (role.value === 1) Object.assign(patientForm, res)
      else Object.assign(staffForm, res)
    }
  } catch (e) {
    // 无档案
    hasProfile.value = false
  }
}

onMounted(() => {
  fetchProfile()
})
</script>

<template>
  <cl-page background-color="#fff">
    <cl-topbar :border="false" background-color="transparent" />
    <view class="profile-page">
      <view class="profile-title">
        <cl-text block bold :size="36">{{ t('创建用户档案') }}</cl-text>
      </view>
      <view class="role-select">
        <cl-radio-group v-model="role">
          <cl-radio v-for="item in roleOptions" :key="item.value" :label="item.value">
            {{ item.label }}
          </cl-radio>
        </cl-radio-group>
      </view>
      <div class="form-container">
        <!-- 患者表单 -->
        <template v-if="role === 1">
          <cl-form>
            <cl-form-item :label="t('姓名')">
              <cl-input v-model="patientForm.name" :placeholder="t('请输入姓名')" />
            </cl-form-item>
            <cl-form-item :label="t('性别')">
              <cl-radio-group v-model="patientForm.gender">
                <cl-radio :label="1">{{ t('男') }}</cl-radio>
                <cl-radio :label="2">{{ t('女') }}</cl-radio>
                <cl-radio :label="0">{{ t('未知') }}</cl-radio>
              </cl-radio-group>
            </cl-form-item>
            <cl-form-item :label="t('生日')">
              <cl-input v-model="patientForm.birthday" :placeholder="t('请选择生日')" />
            </cl-form-item>
            <cl-form-item :label="t('手机号')">
              <cl-input v-model="patientForm.phone" :placeholder="t('请输入手机号')" />
            </cl-form-item>
            <cl-form-item :label="t('地址')">
              <cl-input v-model="patientForm.address" :placeholder="t('请输入地址')" />
            </cl-form-item>
            <cl-form-item :label="t('病历号')">
              <cl-input v-model="patientForm.medicalRecordNumber" :placeholder="t('请输入病历号')" />
            </cl-form-item>
            <cl-form-item :label="t('病史')">
              <cl-input v-model="patientForm.medicalHistory" :placeholder="t('请输入病史')" />
            </cl-form-item>
            <cl-form-item :label="t('过敏史')">
              <cl-input v-model="patientForm.allergyHistory" :placeholder="t('请输入过敏史')" />
            </cl-form-item>
            <cl-form-item :label="t('备注')">
              <cl-input v-model="patientForm.remark" :placeholder="t('备注')" />
            </cl-form-item>
          </cl-form>
        </template>
        <!-- 陪诊员表单 -->
        <template v-else>
          <cl-form>
            <cl-form-item :label="t('姓名')">
              <cl-input v-model="staffForm.name" :placeholder="t('请输入姓名')" />
            </cl-form-item>
            <cl-form-item :label="t('性别')">
              <cl-radio-group v-model="staffForm.gender">
                <cl-radio :label="1">{{ t('男') }}</cl-radio>
                <cl-radio :label="2">{{ t('女') }}</cl-radio>
                <cl-radio :label="0">{{ t('未知') }}</cl-radio>
              </cl-radio-group>
            </cl-form-item>
            <cl-form-item :label="t('生日')">
              <cl-input v-model="staffForm.birthday" :placeholder="t('请选择生日')" />
            </cl-form-item>
            <cl-form-item :label="t('手机号')">
              <cl-input v-model="staffForm.phone" :placeholder="t('请输入手机号')" />
            </cl-form-item>
            <cl-form-item :label="t('级别')">
              <cl-radio-group v-model="staffForm.level">
                <cl-radio :label="0">{{ t('初级') }}</cl-radio>
                <cl-radio :label="1">{{ t('中级') }}</cl-radio>
                <cl-radio :label="2">{{ t('高级') }}</cl-radio>
              </cl-radio-group>
            </cl-form-item>
            <cl-form-item :label="t('简介')">
              <cl-input v-model="staffForm.introduction" :placeholder="t('请输入简介')" />
            </cl-form-item>
            <cl-form-item :label="t('备注')">
              <cl-input v-model="staffForm.remark" :placeholder="t('备注')" />
            </cl-form-item>
          </cl-form>
        </template>
        <cl-button type="primary" @tap="onSave" :margin="[40,0,0,0]">
          {{ t('保存') }}
        </cl-button>
      </div>
    </view>
  </cl-page>
</template>

<style lang="scss" scoped>
.profile-page {
  padding: 40rpx 20rpx;
  .profile-title {
    text-align: center;
    margin-bottom: 30rpx;
  }
  .role-select {
    margin-bottom: 40rpx;
    text-align: center;
  }
  .form-container {
    background: #fff;
    border-radius: 16rpx;
    box-shadow: 0 4rpx 24rpx rgba(0,0,0,0.04);
    padding: 32rpx 20rpx 40rpx 20rpx;
  }
}
</style>
