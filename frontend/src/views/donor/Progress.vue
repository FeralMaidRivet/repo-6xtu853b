<template>
  <div>
    <div class="page-header">
      <h2><el-icon><Search /></el-icon> 捐赠进度查询</h2>
      <p>查看预约、回收、分拣、交接、证明全流程</p>
    </div>

    <el-card shadow="hover" style="margin-bottom: 20px">
      <el-input v-model="keyword" placeholder="输入姓名或手机号搜索" size="large" @keyup.enter="handleSearch" clearable>
        <template #prefix><el-icon><Search /></el-icon></template>
        <template #append>
          <el-button @click="handleSearch" :loading="loading">搜索</el-button>
        </template>
      </el-input>
    </el-card>

    <div v-if="donations.length === 0 && searched" style="text-align:center;padding:40px;">
      <el-empty description="暂无捐赠记录" />
    </div>

    <el-card v-for="donation in donations" :key="donation.id" shadow="hover" class="donation-card">
      <div class="donation-header">
        <div>
          <strong>{{ donation.donorName }} · {{ donation.category }}</strong>
          <span class="addr">{{ donation.address }}</span>
        </div>
        <el-tag :type="statusType(donation.status.name)" effect="dark" size="large">
          {{ donation.status.label }}
        </el-tag>
      </div>
      <el-descriptions :column="4" size="small" border style="margin: 12px 0">
        <el-descriptions-item label="证明编号">{{ donation.certificateNo }}</el-descriptions-item>
        <el-descriptions-item label="重量">{{ donation.weightKg }} kg</el-descriptions-item>
        <el-descriptions-item label="流向">{{ donation.flowDirection || '待分拣' }}</el-descriptions-item>
        <el-descriptions-item label="合作机构">{{ donation.institutionName || '待分配' }}</el-descriptions-item>
      </el-descriptions>
      <el-timeline>
        <el-timeline-item v-for="record in donation.trackingRecords" :key="record.createdAt + record.status.name"
          :type="timelineType(record.status.name)" :timestamp="formatDate(record.createdAt)" placement="top">
          <strong>{{ record.status.label }}</strong> — {{ record.description }}
          <div style="color:#909399;font-size:12px;">操作人：{{ record.operator }}</div>
        </el-timeline-item>
      </el-timeline>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { fetchDonations, searchDonations } from '../../api'

const keyword = ref('')
const donations = ref([])
const loading = ref(false)
const searched = ref(false)

function statusType(name) {
  const map = { SUBMITTED: 'info', ACCEPTED: '', PICKED_UP: 'warning', SORTED: 'warning', HANDED_OVER: 'success', DELIVERED: 'success', RECYCLED: 'success', CERTIFIED: 'success' }
  return map[name] || 'info'
}
function timelineType(name) {
  const map = { SUBMITTED: 'primary', ACCEPTED: 'primary', PICKED_UP: 'warning', SORTED: 'warning', HANDED_OVER: 'success', DELIVERED: 'success', RECYCLED: 'success', CERTIFIED: 'success' }
  return map[name] || 'primary'
}
function formatDate(val) {
  if (!val) return '-'
  return new Date(val).toLocaleString('zh-CN', { hour12: false })
}

async function loadAll() {
  loading.value = true
  try {
    const res = await fetchDonations()
    donations.value = res.data
  } catch (e) { /* ignore */ }
  finally { loading.value = false }
}

async function handleSearch() {
  searched.value = true
  if (!keyword.value.trim()) {
    await loadAll()
    return
  }
  loading.value = true
  try {
    const res = await searchDonations(keyword.value.trim())
    donations.value = res.data
  } catch (e) { /* ignore */ }
  finally { loading.value = false }
}

onMounted(loadAll)
</script>

<style scoped>
.donation-card { margin-bottom: 16px; }
.donation-header { display: flex; justify-content: space-between; align-items: center; }
.donation-header strong { font-size: 16px; }
.addr { margin-left: 12px; color: #909399; font-size: 13px; }
</style>
