<script lang="ts" setup>
import { onReady } from '@dcloudio/uni-app'
import { reactive, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import { useUi } from '/$/cool-ui'
import SmsBtn from '/@/components/sms-btn.vue'
import { useCool, useStore } from '/@/cool'

const { service, router, refs, setRefs } = useCool()
const { user } = useStore()
const ui = useUi()
const { t } = useI18n()

// 验证码长度
const len = 4

// 保存状态

const saving = ref(false)

// 表单
const form = reactive({
  smsCode: '',
  phone: '',
})

// 下一步
function next() {
  saving.value = true

  service.user.login
    .phone(form)
    .then(async (res) => {
      // 设置token
      user.setToken(res)

      // 设置用户信息
      await user.get()

      // 登录跳转
      router.nextLogin()
    })
    .catch((err) => {
      ui.showTips(err.message || t('登录失效，请重试~'))
      saving.value = false
      form.smsCode = ''
    })
}

onReady(() => {
  form.phone = router.query.phone || ''
  refs.smsBtn.startCountdown()
})
</script>

<template>
  <cl-page background-color="#fff">
    <view class="page-captcha">
      <cl-topbar :border="false" />

      <view class="container">
        <text class="label">
          {{ t("输入验证码") }}
        </text>
        <text class="tips">
          {{ t("已发送至") }} +86 {{ form.phone }}
        </text>

        <view class="code">
          <cl-captcha v-model="form.smsCode" focus :length="len" :gutter="26" @done="next" />
        </view>

        <cl-button
          type="primary"
          :disabled="form.smsCode.length !== len"
          :loading="saving"
          fill
          :height="90"
          :font-size="30"
          @tap="next"
        >
          {{ t("确定") }}
        </cl-button>

        <view class="send">
          <sms-btn :ref="setRefs('smsBtn')" size="small" :border="false" :phone="form.phone" />
        </view>
      </view>
    </view>
  </cl-page>
</template>

<style lang="scss" scoped>
.page-captcha {
  .container {
    display: flex;
    flex-direction: column;
    width: 80%;
    margin: 0 auto;
    padding-top: 140rpx;
  }

  .label {
    font-size: 52rpx;
    font-weight: 500;
    margin-bottom: 44rpx;
    font-weight: bold;
    color: #151515;
  }

  .tips {
    font-size: 28rpx;
    color: #151515;
    font-weight: 500;
  }

  .code {
    margin: 34rpx -26rpx 62rpx -26rpx;
  }

  .send {
    display: flex;
    justify-content: center;
    font-size: 24rpx;
    margin-top: 30rpx;
  }
}
</style>
