<template>
  <div class="main-layout">
    <el-header class="top-nav">
      <div class="nav-inner">
        <router-link to="/" class="brand">
          <el-icon :size="24" color="#409eff"><Shirt /></el-icon>
          <span class="brand-text">衣物捐赠系统</span>
        </router-link>
        <div class="nav-links">
          <router-link to="/">
            <el-icon><HomeFilled /></el-icon> 首页
          </router-link>
          <router-link to="/donate">
            <el-icon><Box /></el-icon> 捐赠预约
          </router-link>
          <router-link to="/progress">
            <el-icon><Search /></el-icon> 进度查询
          </router-link>
          <router-link to="/certificate">
            <el-icon><Document /></el-icon> 捐赠证明
          </router-link>
          <router-link to="/public">
            <el-icon><DataAnalysis /></el-icon> 公示公开
          </router-link>
        </div>
        <div class="nav-right">
          <template v-if="userStore.isLoggedIn">
            <el-dropdown>
              <span class="user-info">
                <el-icon><User /></el-icon>
                {{ userStore.realName }}
                <el-icon><ArrowDown /></el-icon>
              </span>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item v-if="userStore.isCollector" @click="$router.push('/staff')">
                    <el-icon><Van /></el-icon> 回收工作台
                  </el-dropdown-item>
                  <el-dropdown-item v-if="userStore.isAdmin" @click="$router.push('/admin')">
                    <el-icon><Setting /></el-icon> 管理后台
                  </el-dropdown-item>
                  <el-dropdown-item divided @click="handleLogout">
                    <el-icon><SwitchButton /></el-icon> 退出登录
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
          <template v-else>
            <router-link to="/login">
              <el-button type="primary" size="small">登录</el-button>
            </router-link>
            <router-link to="/register">
              <el-button size="small">注册</el-button>
            </router-link>
          </template>
        </div>
      </div>
    </el-header>
    <el-main class="main-content">
      <router-view />
    </el-main>
    <footer class="site-footer">
      <p>衣物捐赠系统 — 废旧衣物回收、捐赠、再生一体化公益平台</p>
      <p>实现环保与公益结合，推动可持续发展</p>
    </footer>
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
.main-layout { min-height: 100vh; display: flex; flex-direction: column; }
.top-nav { background: #fff; box-shadow: 0 1px 4px rgba(0,0,0,0.08); padding: 0; height: 60px; position: sticky; top: 0; z-index: 100; }
.nav-inner { max-width: 1200px; margin: 0 auto; display: flex; align-items: center; height: 100%; padding: 0 20px; }
.brand { display: flex; align-items: center; gap: 8px; margin-right: 40px; }
.brand-text { font-size: 18px; font-weight: 700; color: #303133; }
.nav-links { display: flex; gap: 4px; flex: 1; }
.nav-links a { display: flex; align-items: center; gap: 4px; padding: 8px 14px; border-radius: 6px; font-size: 14px; color: #606266; transition: all 0.2s; }
.nav-links a:hover, .nav-links a.router-link-active { background: #ecf5ff; color: #409eff; }
.nav-right { display: flex; align-items: center; gap: 10px; }
.user-info { display: flex; align-items: center; gap: 4px; cursor: pointer; font-size: 14px; color: #606266; }
.main-content { max-width: 1200px; width: 100%; margin: 0 auto; padding: 24px 20px; flex: 1; }
.site-footer { text-align: center; padding: 24px; color: #909399; font-size: 13px; border-top: 1px solid #ebeef5; background: #fff; }
.site-footer p + p { margin-top: 4px; }
</style>
