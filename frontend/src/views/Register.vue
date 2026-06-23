<template>
  <div class="auth-page">
    <el-card class="auth-card" shadow="hover">
      <template #header>
        <div class="card-header">
          <el-icon :size="24" color="#67c23a"><UserFilled /></el-icon>
          <span>注册账号</span>
        </div>
      </template>
      <el-form :model="form" label-width="80px" @submit.prevent="handleRegister">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请设置用户名" prefix-icon="User" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" placeholder="请设置密码" prefix-icon="Lock" show-password />
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input v-model="form.realName" placeholder="请输入真实姓名" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" placeholder="请输入邮箱（选填）" />
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="form.address" placeholder="请输入地址（选填）" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit" :loading="loading" style="width:100%">注册</el-button>
        </el-form-item>
        <el-form-item>
          <span class="tip">已有账号？<router-link to="/login" class="link">去登录</router-link></span>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { register } from '../api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)
const form = reactive({ username: '', password: '', realName: '', phone: '', email: '', address: '' })

async function handleRegister() {
  if (!form.username || !form.password || !form.realName) {
    ElMessage.warning('请填写必要信息')
    return
  }
  loading.value = true
  try {
    const res = await register(form)
    if (res.data.ok) {
      ElMessage.success('注册成功，请登录')
      router.push('/login')
    }
  } catch (e) {
    ElMessage.error(e.response?.data?.message || '注册失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.auth-page { display: flex; justify-content: center; padding-top: 40px; }
.auth-card { width: 460px; border-radius: 12px; }
.card-header { display: flex; align-items: center; gap: 8px; font-size: 18px; font-weight: 600; }
.tip { font-size: 13px; color: #909399; }
.link { color: #409eff; }
</style>
