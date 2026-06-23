<template>
  <div>
    <el-card shadow="hover">
      <template #header><strong><el-icon><Operation /></el-icon> 分拣分配</strong></template>
      <el-table :data="donations" stripe v-loading="loading">
        <el-table-column prop="certificateNo" label="编号" width="220" show-overflow-tooltip />
        <el-table-column prop="donorName" label="捐赠人" width="90" />
        <el-table-column prop="category" label="类别" width="110" />
        <el-table-column prop="weightKg" label="重量(kg)" width="90" />
        <el-table-column label="当前状态" width="110">
          <template #default="{ row }">
            <el-tag size="small" :type="statusType(row.status.name)">{{ row.status.label }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="flowDirection" label="流向" width="100" />
        <el-table-column prop="institutionName" label="分配机构" width="160" />
        <el-table-column label="操作" min-width="280" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="warning" @click="openSortDialog(row)"
              :disabled="!canSort(row.status.name)">
              <el-icon><Operation /></el-icon> 分拣
            </el-button>
            <el-button size="small" type="success" @click="openDeliverDialog(row)"
              :disabled="row.status.name !== 'HANDED_OVER'">
              <el-icon><Promotion /></el-icon> 送达
            </el-button>
            <el-button size="small" @click="openCertDialog(row)"
              :disabled="!canCert(row.status.name)">
              <el-icon><Document /></el-icon> 出证
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="sortDialogVisible" title="分拣分配" width="480px">
      <el-form label-width="100px">
        <el-form-item label="流向">
          <el-select v-model="sortForm.flowDirection" style="width:100%">
            <el-option label="公益发放" value="公益发放" />
            <el-option label="再生利用" value="再生利用" />
          </el-select>
        </el-form-item>
        <el-form-item label="分配机构">
          <el-select v-model="sortForm.institutionName" style="width:100%">
            <el-option v-for="inst in institutions" :key="inst.id" :label="inst.name" :value="inst.name" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="sortDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSort">确认分拣</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { fetchAdminDonations, adminUpdateStatus, fetchInstitutions } from '../../api'
import { ElMessage } from 'element-plus'

const donations = ref([])
const institutions = ref([])
const loading = ref(false)
const sortDialogVisible = ref(false)
const currentRow = ref(null)
const sortForm = reactive({ flowDirection: '公益发放', institutionName: '' })

function statusType(name) {
  const map = { SUBMITTED: 'info', ACCEPTED: '', PICKED_UP: 'warning', SORTED: 'warning', HANDED_OVER: 'success', DELIVERED: 'success', RECYCLED: 'success', CERTIFIED: 'success' }
  return map[name] || 'info'
}
function canSort(name) { return ['PICKED_UP', 'SUBMITTED', 'ACCEPTED'].includes(name) }
function canCert(name) { return ['DELIVERED', 'RECYCLED'].includes(name) }

async function loadData() {
  loading.value = true
  try {
    const [d, i] = await Promise.all([fetchAdminDonations(), fetchInstitutions()])
    donations.value = d.data
    institutions.value = i.data
  } catch (e) { /* ignore */ }
  finally { loading.value = false }
}

function openSortDialog(row) {
  currentRow.value = row
  sortForm.flowDirection = '公益发放'
  sortForm.institutionName = institutions.value[0]?.name || ''
  sortDialogVisible.value = true
}

async function handleSort() {
  await adminUpdateStatus(currentRow.value.id, {
    status: 'SORTED', operator: '分拣管理员',
    description: '按' + sortForm.flowDirection + '分拣完成，分配至' + sortForm.institutionName,
    flowDirection: sortForm.flowDirection, institutionName: sortForm.institutionName
  })
  ElMessage.success('分拣完成')
  sortDialogVisible.value = false
  await loadData()
}

async function openDeliverDialog(row) {
  await adminUpdateStatus(row.id, {
    status: 'DELIVERED', operator: '系统管理员',
    description: '物资已送达' + (row.institutionName || '合作机构')
  })
  ElMessage.success('已标记送达')
  await loadData()
}

async function openCertDialog(row) {
  await adminUpdateStatus(row.id, {
    status: 'CERTIFIED', operator: '系统管理员',
    description: '已生成捐赠证明'
  })
  ElMessage.success('已出证')
  await loadData()
}

onMounted(loadData)
</script>
