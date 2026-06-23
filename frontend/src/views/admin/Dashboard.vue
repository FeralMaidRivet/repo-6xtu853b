<template>
  <div>
    <div class="stat-cards">
      <div class="stat-card">
        <div class="icon-box" style="background:#ecf5ff"><el-icon :size="24" color="#409eff"><Box /></el-icon></div>
        <div class="info"><div class="value">{{ stats.totalDonations || 0 }}</div><div class="label">捐赠总数</div></div>
      </div>
      <div class="stat-card">
        <div class="icon-box" style="background:#f0f9eb"><el-icon :size="24" color="#67c23a"><TrendCharts /></el-icon></div>
        <div class="info"><div class="value">{{ stats.totalWeightKg || 0 }} kg</div><div class="label">总重量</div></div>
      </div>
      <div class="stat-card">
        <div class="icon-box" style="background:#fdf6ec"><el-icon :size="24" color="#e6a23c"><UserFilled /></el-icon></div>
        <div class="info"><div class="value">{{ stats.userCount || 0 }}</div><div class="label">注册用户</div></div>
      </div>
      <div class="stat-card">
        <div class="icon-box" style="background:#fef0f0"><el-icon :size="24" color="#f56c6c"><OfficeBuilding /></el-icon></div>
        <div class="info"><div class="value">{{ stats.institutionCount || 0 }}</div><div class="label">合作机构</div></div>
      </div>
    </div>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header><strong>各状态统计</strong></template>
          <div v-for="(count, status) in stats.statusCounts" :key="status" class="status-row">
            <span>{{ status }}</span>
            <el-progress :percentage="totalDonations ? Math.round(count / totalDonations * 100) : 0"
              :stroke-width="16" :format="() => count + ' 单'" />
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header><strong>分类重量统计</strong></template>
          <div v-for="(weight, category) in stats.categoryCounts" :key="category" class="status-row">
            <span>{{ category }}</span>
            <el-progress :percentage="totalWeight > 0 ? Math.round(Number(weight) / totalWeight * 100) : 0"
              :stroke-width="16" status="success" :format="() => weight + ' kg'" />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="hover" style="margin-top:20px">
      <template #header><strong>公益数据概览</strong></template>
      <el-descriptions :column="4" border>
        <el-descriptions-item label="公益发放重量">{{ stats.publicWelfareWeightKg || 0 }} kg</el-descriptions-item>
        <el-descriptions-item label="再生利用重量">{{ stats.recycledWeightKg || 0 }} kg</el-descriptions-item>
        <el-descriptions-item label="已出证数">{{ stats.certifiedCount || 0 }}</el-descriptions-item>
        <el-descriptions-item label="回收人员数">{{ stats.collectorCount || 0 }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { fetchStats } from '../../api'

const stats = ref({})
const totalDonations = computed(() => stats.value.totalDonations || 0)
const totalWeight = computed(() => Number(stats.value.totalWeightKg) || 0)

onMounted(async () => {
  const res = await fetchStats()
  stats.value = res.data
})
</script>

<style scoped>
.status-row { display: flex; align-items: center; gap: 12px; margin-bottom: 12px; }
.status-row span { min-width: 80px; font-size: 14px; color: #606266; }
.status-row .el-progress { flex: 1; }
</style>
