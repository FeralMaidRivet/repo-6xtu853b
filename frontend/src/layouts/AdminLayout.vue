<template>
  <div class="admin-layout">
    <el-aside width="220px" class="admin-sidebar">
      <div class="sidebar-brand">
        <el-icon :size="22" color="#409eff"><Setting /></el-icon>
        <span>管理后台</span>
      </div>
      <el-menu :default-active="activeMenu" router class="sidebar-menu">
        <el-menu-item index="/admin">
          <el-icon><Odometer /></el-icon>
          <span>管理概览</span>
        </el-menu-item>
        <el-menu-item index="/admin/donations">
          <el-icon><Box /></el-icon>
          <span>捐赠管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/sorting">
          <el-icon><Operation /></el-icon>
          <span>分拣分配</span>
        </el-menu-item>
        <el-menu-item index="/admin/institutions">
          <el-icon><OfficeBuilding /></el-icon>
          <span>机构管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/flow">
          <el-icon><MapLocation /></el-icon>
          <span>物资流向</span>
        </el-menu-item>
        <el-menu-item index="/admin/users">
          <el-icon><UserFilled /></el-icon>
          <span>用户管理</span>
        </el-menu-item>
        <el-menu-item index="/admin/stats">
          <el-icon><TrendCharts /></el-icon>
          <span>统计公示</span>
        </el-menu-item>
      </el-menu>
      <div class="sidebar-bottom">
        <el-button text @click="$router.push('/')">
          <el-icon><Back /></el-icon> 返回前台
        </el-button>
        <el-button text type="danger" @click="handleLogout">
          <el-icon><SwitchButton /></el-icon> 退出
        </el-button>
      </div>
    </el-aside>
    <div class="admin-body">
      <el-header class="admin-header">
        <span class="admin-title">{{ currentTitle }}</span>
        <span class="admin-user">
          <el-icon><User /></el-icon> {{ userStore.realName }}（管理员）
        </span>
      </el-header>
      <el-main class="admin-main">
        <router-view />
      </el-main>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const activeMenu = computed(() => route.path)
const currentTitle = computed(() => route.meta.title || '管理后台')

function handleLogout() {
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/')
}
</script>

<style scoped>
.admin-layout { display: flex; min-height: 100vh; }
.admin-sidebar { background: #304156; display: flex; flex-direction: column; position: fixed; left: 0; top: 0; bottom: 0; width: 220px; z-index: 100; }
.sidebar-brand { display: flex; align-items: center; gap: 8px; padding: 20px; font-size: 17px; font-weight: 700; color: #fff; }
.sidebar-menu { border: none; background: transparent; flex: 1; }
.sidebar-menu .el-menu-item { color: #bfcbd9; }
.sidebar-menu .el-menu-item:hover { background: #263445; }
.sidebar-menu .el-menu-item.is-active { background: #409eff; color: #fff; }
.sidebar-bottom { padding: 12px 16px; border-top: 1px solid rgba(255,255,255,0.1); display: flex; flex-direction: column; gap: 4px; }
.sidebar-bottom .el-button { color: #bfcbd9; justify-content: flex-start; }
.admin-body { flex: 1; margin-left: 220px; display: flex; flex-direction: column; }
.admin-header { background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.06); display: flex; align-items: center; justify-content: space-between; padding: 0 24px; height: 56px; }
.admin-title { font-size: 16px; font-weight: 600; color: #303133; }
.admin-user { font-size: 14px; color: #909399; display: flex; align-items: center; gap: 4px; }
.admin-main { padding: 20px; background: #f5f7fa; flex: 1; }
</style>
