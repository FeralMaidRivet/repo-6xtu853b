<template>
  <div>
    <div class="page-header">
      <h2><el-icon><Van /></el-icon> 回收任务列表</h2>
      <p>接单上门、分类打包、物流交接</p>
    </div>

    <el-card shadow="hover">
      <el-table :data="tasks" stripe style="width: 100%" v-loading="loading">
        <el-table-column prop="donorName" label="预约人" width="100" />
        <el-table-column prop="address" label="地址" min-width="180" show-overflow-tooltip />
        <el-table-column label="类别/重量" width="160">
          <template #default="{ row }">{{ row.category }} / {{ row.weightKg }}kg</template>
        </el-table-column>
        <el-table-column label="预约时间" width="170">
          <template #default="{ row }">{{ formatDate(row.appointmentTime) }}</template>
        </el-table-column>
        <el-table-column label="状态" width="120">
          <template #default="{ row }">
            <el-tag :type="statusType(row.status.name)">{{ row.status.label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="260" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="primary" @click="handleAccept(row.id)"
              :disabled="row.status.name !== 'SUBMITTED'">
              <el-icon><Check /></el-icon> 接单
            </el-button>
            <el-button size="small" type="warning" @click="handlePickup(row.id)"
              :disabled="row.status.name !== 'ACCEPTED'">
              <el-icon><Van /></el-icon> 已收取
            </el-button>
            <el-button size="small" type="success" @click="handleHandoff(row.id)"
              :disabled="row.status.name !== 'PICKED_UP'">
              <el-icon><Promotion /></el-icon> 交接
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { fetchStaffTasks, acceptTask, pickupTask, handoffTask } from '../../api'
import { ElMessage } from 'element-plus'
import { useUserStore } from '../../stores/user'

const userStore = useUserStore()
const tasks = ref([])
const loading = ref(false)

function statusType(name) {
  const map = { SUBMITTED: 'info', ACCEPTED: '', PICKED_UP: 'warning' }
  return map[name] || 'info'
}
function formatDate(val) {
  if (!val) return '-'
  return new Date(val).toLocaleString('zh-CN', { hour12: false })
}

async function loadTasks() {
  loading.value = true
  try {
    const res = await fetchStaffTasks()
    tasks.value = res.data
  } catch (e) { /* ignore */ }
  finally { loading.value = false }
}

async function handleAccept(id) {
  await acceptTask(id, userStore.realName || '回收员')
  ElMessage.success('接单成功')
  await loadTasks()
}

async function handlePickup(id) {
  await pickupTask(id, userStore.realName || '回收员')
  ElMessage.success('已标记收取')
  await loadTasks()
}

async function handleHandoff(id) {
  await handoffTask(id)
  ElMessage.success('交接完成')
  await loadTasks()
}

onMounted(loadTasks)
</script>
