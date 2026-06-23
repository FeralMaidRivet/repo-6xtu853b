<template>
  <div class="home">
    <section class="hero">
      <div class="hero-text">
        <p class="eyebrow">Green Charity Platform</p>
        <h1>让闲置衣物重新流动<br/>让公益环保持续发生</h1>
        <p class="desc">支持捐赠预约、上门回收、分拣交接、机构对接、物资流向追踪和统计公示，构建可循环旧衣处理体系。</p>
        <div class="hero-actions">
          <el-button type="primary" size="large" @click="$router.push('/donate')">
            <el-icon><Box /></el-icon> 立即预约捐赠
          </el-button>
          <el-button size="large" @click="$router.push('/progress')">
            <el-icon><Search /></el-icon> 查看捐赠进度
          </el-button>
        </div>
      </div>
      <div class="hero-stats">
        <div class="hero-stat-card">
          <el-icon :size="32" color="#67c23a"><TrendCharts /></el-icon>
          <strong>{{ stats.totalWeightKg || 0 }} kg</strong>
          <span>累计回收衣物重量</span>
        </div>
        <div class="hero-stat-card">
          <el-icon :size="32" color="#409eff"><UserFilled /></el-icon>
          <strong>{{ stats.totalDonations || 0 }}</strong>
          <span>累计捐赠订单</span>
        </div>
        <div class="hero-stat-card">
          <el-icon :size="32" color="#e6a23c"><OfficeBuilding /></el-icon>
          <strong>{{ stats.institutionCount || 0 }}</strong>
          <span>合作机构</span>
        </div>
      </div>
    </section>

    <section class="features">
      <div class="section-header">
        <h2>平台功能</h2>
        <p>衣物回收、公益捐赠、资源再生闭环管理</p>
      </div>
      <div class="feature-grid">
        <div class="feature-card" v-for="f in features" :key="f.title">
          <div class="feature-icon" :style="{ background: f.bg }">
            <el-icon :size="28" :color="f.color"><component :is="f.icon" /></el-icon>
          </div>
          <h3>{{ f.title }}</h3>
          <p>{{ f.desc }}</p>
        </div>
      </div>
    </section>

    <section class="flow-section">
      <div class="section-header">
        <h2>捐赠流程</h2>
        <p>从预约到再生，全程透明可追溯</p>
      </div>
      <div class="flow-steps">
        <div class="flow-step" v-for="(step, i) in flowSteps" :key="i">
          <div class="step-num">{{ String(i + 1).padStart(2, '0') }}</div>
          <h3>{{ step.title }}</h3>
          <p>{{ step.desc }}</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { fetchPublicStats } from '../api'

const stats = ref({})

const features = [
  { icon: 'Calendar', title: '在线预约', desc: '捐赠者可在线填写衣物信息，预约上门回收时间', bg: '#ecf5ff', color: '#409eff' },
  { icon: 'Van', title: '上门回收', desc: '回收人员接单后按预约时间上门，完成称量确认', bg: '#f0f9eb', color: '#67c23a' },
  { icon: 'Operation', title: '分拣分配', desc: '仓库按可捐赠、可再生、需处理分类分拣', bg: '#fdf6ec', color: '#e6a23c' },
  { icon: 'MapLocation', title: '物流公益', desc: '管理员对接合作机构，展示物资去向和公益统计', bg: '#fef0f0', color: '#f56c6c' }
]

const flowSteps = [
  { title: '在线预约', desc: '捐赠者提交衣物类型、重量、地址和上门时间。' },
  { title: '上门回收', desc: '回收人员接单后按预约时间上门，完成称量确认。' },
  { title: '分拣打包', desc: '仓库按可捐赠、可再生、需处理分类分拣。' },
  { title: '物流公益', desc: '管理员对接合作机构，展示物资去向和公益统计。' }
]

onMounted(async () => {
  try {
    const res = await fetchPublicStats()
    stats.value = res.data
  } catch (e) { /* ignore */ }
})
</script>

<style scoped>
.hero { display: flex; gap: 40px; align-items: center; padding: 48px 0 40px; flex-wrap: wrap; }
.hero-text { flex: 1; min-width: 320px; }
.eyebrow { color: #409eff; font-size: 14px; font-weight: 600; text-transform: uppercase; letter-spacing: 1px; margin-bottom: 12px; }
.hero h1 { font-size: 32px; line-height: 1.3; color: #303133; margin-bottom: 16px; }
.desc { color: #909399; font-size: 15px; line-height: 1.6; margin-bottom: 24px; }
.hero-actions { display: flex; gap: 12px; }
.hero-stats { display: flex; flex-direction: column; gap: 16px; }
.hero-stat-card { background: #fff; border-radius: 12px; padding: 20px 28px; display: flex; align-items: center; gap: 14px; box-shadow: 0 2px 8px rgba(0,0,0,0.06); min-width: 260px; }
.hero-stat-card strong { font-size: 22px; font-weight: 700; color: #303133; }
.hero-stat-card span { color: #909399; font-size: 13px; }

.section-header { text-align: center; margin-bottom: 32px; }
.section-header h2 { font-size: 24px; color: #303133; }
.section-header p { color: #909399; margin-top: 8px; }

.features { padding: 40px 0; }
.feature-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap: 20px; }
.feature-card { background: #fff; border-radius: 12px; padding: 28px; box-shadow: 0 1px 4px rgba(0,0,0,0.06); }
.feature-icon { width: 56px; height: 56px; border-radius: 14px; display: flex; align-items: center; justify-content: center; margin-bottom: 16px; }
.feature-card h3 { font-size: 17px; color: #303133; margin-bottom: 8px; }
.feature-card p { font-size: 14px; color: #909399; line-height: 1.5; }

.flow-section { padding: 40px 0; }
.flow-steps { display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 20px; }
.flow-step { background: #fff; border-radius: 12px; padding: 28px; box-shadow: 0 1px 4px rgba(0,0,0,0.06); }
.step-num { font-size: 36px; font-weight: 800; color: #409eff; opacity: 0.3; margin-bottom: 8px; }
.flow-step h3 { font-size: 17px; color: #303133; margin-bottom: 8px; }
.flow-step p { font-size: 14px; color: #909399; line-height: 1.5; }
</style>
