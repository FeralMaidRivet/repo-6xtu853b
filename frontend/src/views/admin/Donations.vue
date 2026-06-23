<template>
  <div>
    <el-card shadow="hover">
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <strong>捐赠数据管理</strong>
          <el-input v-model="searchKey" placeholder="搜索捐赠人/编号" style="width:260px" clearable prefix-icon="Search" />
        </div>
      </template>
      <el-table :data="filteredDonations" stripe v-loading="loading" style="width:100%">
        <el-table-column prop="certificateNo" label="证明编号" width="220" show-overflow-tooltip />
        <el-table-column prop="donorName" label="捐赠人" width="90" />
        <el-table-column prop="phone" label="电话" width="120" />
        <el-table-column prop="category" label="类别" width="110" />
        <el-table-column prop="weightKg" label="重量(kg)" width="90" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag size="small" :type="statusType(row.status.name)">{{ row.status.label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="flowDirection" label="流向" width="90" />
        <el-table-column prop="institutionName" label="机构" width="140" show-overflow-tooltip />
        <el-table-column label="时间" width="160">
          <template #default="{ row }">{{ formatDate(row.createdAt) }}</template>
        </el-table-column>
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-popconfirm title="确定删除该记录？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button size="small" type="danger" text><el-icon><Delete /></el-icon></el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { fetchAdminDonations, adminDeleteDonation } from '../../api'
import { ElMessage } from 'element-plus'

const donations = ref([])
const loading = ref(false)
const searchKey = ref('')

const filteredDonations = computed(() => {
  if (!searchKey.value) return donations.value
  const k = searchKey.value.toLowerCase()
  return donations.value.filter(d => d.donorName.toLowerCase().includes(k) || d.certificateNo.toLowerCase().includes(k))
})

function statusType(name) {
  const map = { SUBMITTED: 'info', ACCEPTED: '', PICKED_UP: 'warning', SORTED: 'warning', HANDED_OVER: 'success', DELIVERED: 'success', RECYCLED: 'success', CERTIFIED: 'success' }
  return map[name] || 'info'
}
function formatDate(val) {
  if (!val) return '-'
  return new Date(val).toLocaleString('zh-CN', { hour12: false })
}

async function loadData() {
  loading.value = true
  try { const res = await fetchAdminDonations(); donations.value = res.data }
  catch (e) { /* ignore */ }
  finally { loading.value = false }
}

async function handleDelete(id) {
  await adminDeleteDonation(id)
  ElMessage.success('已删除')
  await loadData()
}

onMounted(loadData)
</script>
