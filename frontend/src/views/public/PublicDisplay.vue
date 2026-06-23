<template>
  <div>
    <div class="page-header">
      <h2><el-icon><DataAnalysis /></el-icon> 公示公开</h2>
      <p>统计展示、公益发放、合作机构信息公开透明</p>
    </div>

    <div class="stat-cards">
      <div class="stat-card">
        <div class="icon-box" style="background:#ecf5ff">
          <el-icon :size="24" color="#409eff"><Box /></el-icon>
        </div>
        <div class="info">
          <div class="value">{{ stats.totalDonations || 0 }}</div>
          <div class="label">累计捐赠订单</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="icon-box" style="background:#f0f9eb">
          <el-icon :size="24" color="#67c23a"><TrendCharts /></el-icon>
        </div>
        <div class="info">
          <div class="value">{{ stats.totalWeightKg || 0 }} kg</div>
          <div class="label">累计回收重量</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="icon-box" style="background:#fdf6ec">
          <el-icon :size="24" color="#e6a23c"><Promotion /></el-icon>
        </div>
        <div class="info">
          <div class="value">{{ stats.publicWelfareWeightKg || 0 }} kg</div>
          <div class="label">公益发放重量</div>
        </div>
      </div>
      <div class="stat-card">
        <div class="icon-box" style="background:#fef0f0">
          <el-icon :size="24" color="#f56c6c"><Refresh /></el-icon>
        </div>
        <div class="info">
          <div class="value">{{ stats.recycledWeightKg || 0 }} kg</div>
          <div class="label">再生利用重量</div>
        </div>
      </div>
    </div>

    <el-row :gutter="20">
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header><strong><el-icon><List /></el-icon> 物资流向公示</strong></template>
          <el-table :data="flowList" stripe>
            <el-table-column prop="certificateNo" label="证明编号" width="220" />
            <el-table-column prop="donorName" label="捐赠人" width="100" />
            <el-table-column prop="category" label="类别" width="120" />
            <el-table-column prop="weightKg" label="重量(kg)" width="90" />
            <el-table-column prop="flowDirection" label="流向" width="100" />
            <el-table-column prop="institutionName" label="接收机构" />
            <el-table-column label="状态" width="100">
              <template #default="{ row }">
                <el-tag size="small" effect="dark" :type="row.status.name === 'RECYCLED' ? 'warning' : 'success'">
                  {{ row.status.label }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header><strong><el-icon><OfficeBuilding /></el-icon> 合作机构</strong></template>
          <div v-for="inst in institutions" :key="inst.id" class="inst-item">
            <strong>{{ inst.name }}</strong>
            <el-tag size="small">{{ inst.type }}</el-tag>
            <p>{{ inst.region }} · {{ inst.contact }}</p>
            <p class="demand">需求：{{ inst.demand }}</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { fetchPublicStats, fetchPublicFlow, fetchPublicInstitutions } from '../../api'

const stats = ref({})
const flowList = ref([])
const institutions = ref([])

onMounted(async () => {
  const [s, f, i] = await Promise.all([fetchPublicStats(), fetchPublicFlow(), fetchPublicInstitutions()])
  stats.value = s.data
  flowList.value = f.data
  institutions.value = i.data
})
</script>

<style scoped>
.inst-item { padding: 12px 0; border-bottom: 1px solid #ebeef5; }
.inst-item:last-child { border-bottom: none; }
.inst-item strong { font-size: 15px; margin-right: 8px; }
.inst-item p { color: #909399; font-size: 13px; margin-top: 4px; }
.demand { color: #606266; }
</style>
