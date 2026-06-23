<template>
  <div class="auth-page">
    <el-card class="auth-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <el-icon :size="24" color="#409eff"><User /></el-icon>
          <span>用户登录</span>
        </div>
      </template>
      <el-form :model="form" label-width="80px" @submit.prevent="handleLogin">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" prefix-icon="Lock" show-password />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit" :loading="loading" style="width:100%">登录</el-button>
        </el-form-item>
        <el-form-item>
          <span class="tip">还没有账号？<router-link to="/register" class="link">立即注册</router-link></span>
        </el-form-item>
      </el-form>
      <el-divider>测试账号</el-divider>
      <div class="test-accounts">
        <el-tag @click="fillAccount('admin', 'admin123')">管理员 admin</el-tag>
        <el-tag type="success" @click="fillAccount('collector1', '123456')">回收员 collector1</el-tag>
        <el-tag type="warning" @click="fillAccount('donor1', '123456')">捐赠者 donor1</el-tag>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { login } from '../api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const form = reactive({ username: '', password: '' })

function fillAccount(u, p) {
  form.username = u
  form.password = p
}

async function handleLogin() {
  if (!form.username || !form.password) {
    ElMessage.warning('请填写用户名和密码')
    return
  }
  loading.value = true
  try {
    const res = await login(form)
    if (res.data.ok) {
      userStore.setUser(res.data.data)
      ElMessage.success('登录成功')
      const role = res.data.data.role
      if (role === 'ADMIN') router.push('/admin')
      else if (role === 'COLLECTOR') router.push('/staff')
      else router.push('/')
    }
  } catch (e) {
    ElMessage.error(e.response?.data?.message || '登录失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.auth-page { display: flex; justify-content: center; padding-top: 60px; }
.auth-card { width: 420px; border-radius: 12px; }
.card-header { display: flex; align-items: center; gap: 8px; font-size: 18px; font-weight: 600; }
.tip { font-size: 13px; color: #909399; }
.link { color: #409eff; }
.test-accounts { display: flex; gap: 8px; flex-wrap: wrap; }
.test-accounts .el-tag { cursor: pointer; }
</style>
