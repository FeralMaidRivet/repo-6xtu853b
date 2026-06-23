<template>
  <div class="staff-layout">
    <el-header class="staff-header">
      <div class="header-inner">
        <div class="brand">
          <el-icon :size="22" color="#67c23a"><Van /></el-icon>
          <span>回收工作台</span>
        </div>
        <div class="header-right">
          <span class="user-tag">
            <el-icon><User /></el-icon> {{ userStore.realName }}
          </span>
          <el-button size="small" @click="$router.push('/')">返回首页</el-button>
          <el-button size="small" type="danger" @click="handleLogout">退出</el-button>
        </div>
      </div>
    </el-header>
    <el-main class="staff-main">
      <router-view />
    </el-main>
  </div>
</template>

<script setup>
import { useUserStore } from '../stores/user'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const router = useRouter()

function handleLogout() {
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/')
}
</script>

<style scoped>
.staff-layout { min-height: 100vh; background: #f0f2f5; }
.staff-header { background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.08); height: 60px; }
.header-inner { max-width: 1200px; margin: 0 auto; display: flex; align-items: center; justify-content: space-between; height: 100%; padding: 0 20px; }
.brand { display: flex; align-items: center; gap: 8px; font-size: 18px; font-weight: 700; color: #303133; }
.header-right { display: flex; align-items: center; gap: 12px; }
.user-tag { display: flex; align-items: center; gap: 4px; font-size: 14px; color: #606266; }
.staff-main { max-width: 1200px; margin: 0 auto; padding: 24px 20px; }
</style>
