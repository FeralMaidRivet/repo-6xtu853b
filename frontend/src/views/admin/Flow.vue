<template>
  <div>
    <el-card shadow="hover">
      <template #header><strong><el-icon><MapLocation /></el-icon> 物资流向跟踪</strong></template>
      <el-table :data="flowList" stripe v-loading="loading">
        <el-table-column prop="certificateNo" label="证明编号" width="220" show-overflow-tooltip />
        <el-table-column prop="donorName" label="捐赠人" width="90" />
        <el-table-column prop="category" label="类别" width="110" />
        <el-table-column prop="weightKg" label="重量(kg)" width="90" />
        <el-table-column prop="flowDirection" label="流向" width="100">
          <template #default="{ row }">
            <el-tag :type="row.flowDirection === '再生利用' ? 'warning' : 'success'" size="small">
              {{ row.flowDirection }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="institutionName" label="接收机构" width="180" />
        <el-table-column prop="recyclerName" label="回收员" width="100" />
        <el-table-column label="状态" width="100">
          <template #default="{ row }">
            <el-tag size="small">{{ row.status.label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="时间" width="160">
          <template #default="{ row }">{{ formatDate(row.createdAt) }}</template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { fetchFlow } from '../../api'

const flowList = ref([])
const loading = ref(false)

function formatDate(val) {
  if (!val) return '-'
  return new Date(val).toLocaleString('zh-CN', { hour12: false })
}

onMounted(async () => {
  loading.value = true
  try { const res = await fetchFlow(); flowList.value = res.data }
  catch (e) { /* ignore */ }
  finally { loading.value = false }
})
</script>
