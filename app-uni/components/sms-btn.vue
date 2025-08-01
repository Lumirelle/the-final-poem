<script lang="ts" setup>
import type { PropType } from 'vue'
import { computed, reactive, ref } from 'vue'
import { useI18n } from 'vue-i18n'
import { useCool } from '../cool'
import { useUi } from '/$/cool-ui'

const props = defineProps({
  phone: String,
  type: String,
  height: Number,
  fontSize: Number,
  size: String as PropType<'large' | 'default' | 'small'>,
  border: {
    type: Boolean,
    default: true,
  },
  plain: Boolean,
})

const emit = defineEmits(['success'])

const { service, refs, setRefs } = useCool()
const ui = useUi()
const { t } = useI18n()

// 验证码
const captcha = reactive({
  visible: false,
  loading: false,
  sending: false,
  img: '',
})

// 倒计时
const countdown = ref(0)

// 是否禁用
const isDisabled = computed(() => countdown.value > 0 || !props.phone)

// 按钮文案
const btnText = computed(() =>
  countdown.value > 0 ? t('{n}s后重新获取', { n: countdown.value }) : t('获取验证码'),
)

// 表单
const form = reactive({
  code: '',
  captchaId: '',
})

// 开始倒计时
function startCountdown() {
  countdown.value = 60

  function fn() {
    countdown.value--

    if (countdown.value < 1) {
      clearInterval(timer)
    }
  }

  const timer = setInterval(fn, 1000)
  fn()
}

// 发送短信
async function send() {
  if (form.code) {
    captcha.sending = true

    await service.user.login
      .smsCode({
        phone: props.phone,
        ...form,
      })
      .then(() => {
        ui.showToast(t('短信已发送，请查收'))
        startCountdown()
        close()
        emit('success')
      })

      .catch((err) => {
        ui.showToast(err.message)
        getCaptcha()
      })

    captcha.sending = false
  }
  else {
    ui.showToast(t('请填写验证码'))
  }
}

// 获取图片验证码
async function getCaptcha() {
  clear()
  captcha.loading = true

  await service.user.login
    .captcha({ color: '#2c3142', phone: props.phone })
    .then((res) => {
      form.captchaId = res.captchaId
      captcha.img = res.data
    })
    .catch((err) => {
      ui.showToast(err.message)
    })

  captcha.loading = false
}

// 打开
function open() {
  if (props.phone) {
    if (/^(?:(?:\+|00)86)?1[3-9]\d{9}$/.test(props.phone)) {
      captcha.visible = true
      getCaptcha()
    }
    else {
      ui.showToast(t('请填写正确的手机号格式'))
    }
  }
}

// 关闭
function close() {
  captcha.visible = false
  clear()
}

// 清空
function clear() {
  form.code = ''
  form.captchaId = ''
}

defineExpose({
  open,
  send,
  getCaptcha,
  startCountdown,
})
</script>

<template>
  <view class="sms-btn">
    <slot :disabled="isDisabled" :countdown="countdown" :btn-text="btnText">
      <cl-button
        :border="false"
        background-color="transparent"
        color="#FE6B03"
        :height="height"
        :font-size="fontSize"
        fill
        :size="size"
        :disabled="isDisabled"
        @tap="open"
      >
        {{ btnText }}
      </cl-button>
    </slot>

    <cl-popup :ref="setRefs('popup')" v-model="captcha.visible" :padding="40" border-radius="24rpx">
      <cl-loading-mask :loading="captcha.loading">
        <view class="sms-popup">
          <view class="head">
            <cl-text bold :size="28" :value="$t('获取短信验证码')" />
            <cl-icon :size="32" name="close" @tap="close" />
          </view>

          <view class="row">
            <cl-input
              v-model="form.code"
              :placeholder="$t('验证码')"
              :maxlength="4"
              :height="70"
              :clearable="false"
              :focus="refs.popup?.isFocus"
              :border="false"
              background-color="#f7f7f7"
              @confirm="send"
            />

            <image :src="captcha.img" mode="aspectFit" @tap="getCaptcha" />
          </view>

          <cl-button
            type="primary"
            fill
            :disabled="!form.code"
            :loading="captcha.sending"
            :height="70"
            @tap="send"
          >
            {{ $t("发送短信") }}
          </cl-button>
        </view>
      </cl-loading-mask>
    </cl-popup>
  </view>
</template>

<style lang="scss" scoped>
.sms-popup {
  width: 400rpx;

  .head {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 30rpx;
  }

  .row {
    display: flex;
    align-items: center;
    margin-bottom: 30rpx;

    image {
      height: 70rpx;
      width: 200rpx;
      flex-shrink: 0;
    }
  }
}
</style>
