<template>
  <div>
    <el-card shadow="hover">
      <template #header><strong><el-icon><UserFilled /></el-icon> 用户管理</strong></template>
      <el-table :data="users" stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="realName" label="真实姓名" width="100" />
        <el-table-column prop="phone" label="电话" width="130" />
        <el-table-column prop="email" label="邮箱" width="160" />
        <el-table-column label="角色" width="100">
          <template #default="{ row }">
            <el-tag :type="roleType(row.role)" size="small">{{ roleLabel(row.role) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.enabled ? 'success' : 'danger'" size="small">{{ row.enabled ? '启用' : '禁用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button size="small" text type="primary" @click="toggleEnabled(row)">
              {{ row.enabled ? '禁用' : '启用' }}
            </el-button>
            <el-select v-model="row.role" size="small" style="width:90px;margin:0 4px" @change="changeRole(row)">
              <el-option label="捐赠者" value="DONOR" />
              <el-option label="回收员" value="COLLECTOR" />
              <el-option label="管理员" value="ADMIN" />
            </el-select>
            <el-popconfirm title="确定删除？" @confirm="handleDelete(row.id)">
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
import { ref, onMounted } from 'vue'
import { fetchUsers, updateUser, deleteUser } from '../../api'
import { ElMessage } from 'element-plus'

const users = ref([])
const loading = ref(false)

function roleType(r) { return { ADMIN: 'danger', COLLECTOR: 'warning', DONOR: '' }[r] || '' }
function roleLabel(r) { return { ADMIN: '管理员', COLLECTOR: '回收员', DONOR: '捐赠者' }[r] || r }

async function loadData() {
  loading.value = true
  try { const res = await fetchUsers(); users.value = res.data }
  catch (e) { /* ignore */ }
  finally { loading.value = false }
}

async function toggleEnabled(row) {
  await updateUser(row.id, { enabled: !row.enabled })
  ElMessage.success(row.enabled ? '已禁用' : '已启用')
  await loadData()
}

async function changeRole(row) {
  await updateUser(row.id, { role: row.role })
  ElMessage.success('角色已更新')
}

async function handleDelete(id) {
  await deleteUser(id)
  ElMessage.success('已删除')
  await loadData()
}

onMounted(loadData)
</script>
