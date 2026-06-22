<script setup lang="ts">
import { computed, ref } from 'vue'
import { useWsLoginStore, LoginStatus } from '@/stores/ws'
import { useUserStore } from '@/stores/user'
import apis from '@/services/apis'
import { computedToken } from '@/services/request'
import wsIns from '@/utils/websocket'

const loginStore = useWsLoginStore()
const userStore = useUserStore()

const visible = computed({
  get() {
    return loginStore.showLogin
  },
  set(value) {
    loginStore.showLogin = value
  },
})

const isRegisterMode = ref(false)
const username = ref('')
const password = ref('')
const nickname = ref('')
const loading = ref(false)
const errMsg = ref('')

async function handleLogin() {
  if (!username.value || !password.value) {
    errMsg.value = '请输入用户名和密码'
    return
  }
  loading.value = true
  errMsg.value = ''
  try {
    const token = await apis.loginByPassword({ username: username.value, password: password.value }).send()
    onLoginSuccess(token)
  } catch (e: any) {
    errMsg.value = e?.message || '登录失败'
  } finally {
    loading.value = false
  }
}

async function handleRegister() {
  if (!username.value || !password.value) {
    errMsg.value = '请输入用户名和密码'
    return
  }
  loading.value = true
  errMsg.value = ''
  try {
    const token = await apis.register({
      username: username.value,
      password: password.value,
      nickname: nickname.value || undefined,
    }).send()
    onLoginSuccess(token)
  } catch (e: any) {
    errMsg.value = e?.message || '注册失败'
  } finally {
    loading.value = false
  }
}

function onLoginSuccess(token: string) {
  localStorage.setItem('TOKEN', token)
  computedToken.clear()
  computedToken.get()
  userStore.isSign = true
  loginStore.loginStatus = LoginStatus.Success
  loginStore.showLogin = false
  // 重新连接 websocket 以带上 token
  wsIns.initConnect()
  // 获取用户详情
  userStore.getUserDetailAction()
  // 重置表单
  username.value = ''
  password.value = ''
  nickname.value = ''
}

function toggleMode() {
  isRegisterMode.value = !isRegisterMode.value
  errMsg.value = ''
}
</script>

<template>
  <ElDialog class="login-box-modal" :width="376" v-model="visible" center>
    <div class="login-box">
      <img class="login-logo" src="@/assets/logo.jpeg" alt="MallChat" />
      <p class="login-slogan">边聊边买，岂不快哉~</p>

      <div class="login-form">
        <ElInput
          v-model="username"
          placeholder="用户名"
          size="large"
          class="login-input"
          @keyup.enter="isRegisterMode ? handleRegister() : handleLogin()"
        />
        <ElInput
          v-model="password"
          type="password"
          placeholder="密码"
          size="large"
          class="login-input"
          show-password
          @keyup.enter="isRegisterMode ? handleRegister() : handleLogin()"
        />
        <ElInput
          v-if="isRegisterMode"
          v-model="nickname"
          placeholder="昵称（可选）"
          size="large"
          class="login-input"
          @keyup.enter="handleRegister()"
        />

        <p class="login-error" v-if="errMsg">{{ errMsg }}</p>

        <ElButton
          v-if="!isRegisterMode"
          type="primary"
          size="large"
          class="login-btn"
          :loading="loading"
          @click="handleLogin"
        >
          登录
        </ElButton>
        <ElButton
          v-else
          type="primary"
          size="large"
          class="login-btn"
          :loading="loading"
          @click="handleRegister"
        >
          注册
        </ElButton>

        <p class="login-toggle" @click="toggleMode">
          {{ isRegisterMode ? '已有账号？去登录' : '没有账号？去注册' }}
        </p>
      </div>
    </div>
  </ElDialog>
</template>

<style lang="scss" src="./styles.scss" scoped />
