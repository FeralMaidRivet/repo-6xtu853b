<template>
  <div>
    <div class="stat-cards">
      <div class="stat-card">
        <div class="icon-box" style="background:#ecf5ff"><el-icon :size="24" color="#409eff"><Box /></el-icon></div>
        <div class="info"><div class="value">{{ stats.totalDonations || 0 }}</div><div class="label">累计捐赠</div></div>
      </div>
      <div class="stat-card">
        <div class="icon-box" style="background:#f0f9eb"><el-icon :size="24" color="#67c23a"><TrendCharts /></el-icon></div>
        <div class="info"><div class="value">{{ stats.totalWeightKg || 0 }} kg</div><div class="label">总重量</div></div>
      </div>
      <div class="stat-card">
        <div class="icon-box" style="background:#fdf6ec"><el-icon :size="24" color="#e6a23c"><Promotion /></el-icon></div>
        <div class="info"><div class="value">{{ stats.publicWelfareWeightKg || 0 }} kg</div><div class="label">公益发放</div></div>
      </div>
      <div class="stat-card">
        <div class="icon-box" style="background:#fef0f0"><el-icon :size="24" color="#f56c6c"><Refresh /></el-icon></div>
        <div class="info"><div class="value">{{ stats.recycledWeightKg || 0 }} kg</div><div class="label">再生利用</div></div>
      </div>
    </div>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header><strong>各状态数量分布</strong></template>
          <div v-for="(count, status) in stats.statusCounts" :key="status" class="bar-row">
            <span class="bar-label">{{ status }}</span>
            <div class="bar-track">
              <div class="bar-fill" :style="{ width: barWidth(count) + '%' }">{{ count }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header><strong>衣物类别重量分布</strong></template>
          <div v-for="(weight, cat) in stats.categoryCounts" :key="cat" class="bar-row">
            <span class="bar-label">{{ cat }}</span>
            <div class="bar-track">
              <div class="bar-fill green" :style="{ width: catBarWidth(weight) + '%' }">{{ weight }} kg</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card shadow="hover" style="margin-top:20px">
      <template #header><strong>详细统计</strong></template>
      <el-descriptions :column="3" border>
        <el-descriptions-item label="注册用户">{{ stats.userCount || 0 }}</el-descriptions-item>
        <el-descriptions-item label="回收人员">{{ stats.collectorCount || 0 }}</el-descriptions-item>
        <el-descriptions-item label="合作机构">{{ stats.institutionCount || 0 }}</el-descriptions-item>
        <el-descriptions-item label="已出证数">{{ stats.certifiedCount || 0 }}</el-descriptions-item>
        <el-descriptions-item label="公益发放">{{ stats.publicWelfareWeightKg || 0 }} kg</el-descriptions-item>
        <el-descriptions-item label="再生利用">{{ stats.recycledWeightKg || 0 }} kg</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { fetchStats } from '../../api'

const stats = ref({})
const maxCount = computed(() => {
  if (!stats.value.statusCounts) return 1
  return Math.max(1, ...Object.values(stats.value.statusCounts))
})
const maxWeight = computed(() => {
  if (!stats.value.categoryCounts) return 1
  return Math.max(1, ...Object.values(stats.value.categoryCounts).map(Number))
})

function barWidth(count) { return Math.round((count / maxCount.value) * 100) }
function catBarWidth(weight) { return Math.round((Number(weight) / maxWeight.value) * 100) }

onMounted(async () => {
  const res = await fetchStats()
  stats.value = res.data
})
</script>

<style scoped>
.bar-row { display: flex; align-items: center; gap: 12px; margin-bottom: 10px; }
.bar-label { min-width: 80px; font-size: 13px; color: #606266; text-align: right; }
.bar-track { flex: 1; background: #f5f7fa; border-radius: 4px; height: 24px; overflow: hidden; }
.bar-fill { background: #409eff; height: 100%; border-radius: 4px; color: #fff; font-size: 12px; display: flex; align-items: center; justify-content: center; min-width: 30px; transition: width 0.5s; }
.bar-fill.green { background: #67c23a; }
</style>
