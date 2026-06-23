<template>
  <div>
    <div class="page-header">
      <h2><el-icon><Document /></el-icon> 捐赠证明</h2>
      <p>输入证明编号查询和下载捐赠证明</p>
    </div>

    <el-card shadow="hover" style="margin-bottom:20px">
      <el-input v-model="certNo" placeholder="请输入捐赠证明编号（如 YW-GY-20260623-XXXXXXXX）" size="large"
        @keyup.enter="handleSearch" clearable>
        <template #prefix><el-icon><Ticket /></el-icon></template>
        <template #append>
          <el-button @click="handleSearch" :loading="loading">查询</el-button>
        </template>
      </el-input>
    </el-card>

    <el-card v-if="donation" shadow="hover" class="cert-card">
      <div class="cert-header">
        <el-icon :size="48" color="#67c23a"><CircleCheckFilled /></el-icon>
        <h2>捐赠证明</h2>
        <p class="cert-no">编号：{{ donation.certificateNo }}</p>
      </div>
      <el-divider />
      <el-descriptions :column="2" border>
        <el-descriptions-item label="捐赠人">{{ donation.donorName }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ donation.phone }}</el-descriptions-item>
        <el-descriptions-item label="衣物类别">{{ donation.category }}</el-descriptions-item>
        <el-descriptions-item label="衣物重量">{{ donation.weightKg }} kg</el-descriptions-item>
        <el-descriptions-item label="当前状态">
          <el-tag :type="donation.status.name === 'CERTIFIED' ? 'success' : 'info'" effect="dark">
            {{ donation.status.label }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="流向">{{ donation.flowDirection || '待分拣' }}</el-descriptions-item>
        <el-descriptions-item label="接收机构">{{ donation.institutionName || '待分配' }}</el-descriptions-item>
        <el-descriptions-item label="提交时间">{{ formatDate(donation.createdAt) }}</el-descriptions-item>
      </el-descriptions>
      <el-divider />
      <div class="cert-footer">
        <p>感谢您的爱心捐赠！您的善举帮助了需要帮助的人，推动了环保公益事业的可持续发展。</p>
        <p class="cert-org">衣物捐赠系统 · 废旧衣物回收公益平台</p>
      </div>
    </el-card>

    <el-empty v-if="searched && !donation" description="未找到该证明编号的记录" />
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { fetchDonationByCert } from '../../api'
import { ElMessage } from 'element-plus'

const certNo = ref('')
const donation = ref(null)
const loading = ref(false)
const searched = ref(false)

function formatDate(val) {
  if (!val) return '-'
  return new Date(val).toLocaleString('zh-CN', { hour12: false })
}

async function handleSearch() {
  if (!certNo.value.trim()) {
    ElMessage.warning('请输入证明编号')
    return
  }
  searched.value = true
  loading.value = true
  donation.value = null
  try {
    const res = await fetchDonationByCert(certNo.value.trim())
    donation.value = res.data
  } catch (e) {
    donation.value = null
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.cert-card { max-width: 700px; margin: 0 auto; }
.cert-header { text-align: center; padding: 20px 0; }
.cert-header h2 { font-size: 24px; margin-top: 12px; color: #303133; }
.cert-no { color: #909399; margin-top: 8px; }
.cert-footer { text-align: center; color: #606266; font-size: 14px; line-height: 1.8; }
.cert-org { font-weight: 600; color: #409eff; margin-top: 12px; }
</style>
