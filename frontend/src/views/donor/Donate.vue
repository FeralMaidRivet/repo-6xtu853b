<template>
  <div>
    <div class="page-header">
      <h2><el-icon><Box /></el-icon> 捐赠预约</h2>
      <p>填写衣物信息，预约上门回收</p>
    </div>
    <el-row :gutter="24">
      <el-col :span="14">
        <el-card shadow="hover">
          <el-form :model="form" label-width="100px" @submit.prevent="submitDonation">
            <el-form-item label="捐赠人姓名" required>
              <el-input v-model="form.donorName" placeholder="请输入姓名" prefix-icon="User" />
            </el-form-item>
            <el-form-item label="联系电话" required>
              <el-input v-model="form.phone" placeholder="请输入手机号" prefix-icon="Phone" />
            </el-form-item>
            <el-form-item label="上门地址" required>
              <el-input v-model="form.address" placeholder="请输入详细地址" prefix-icon="Location" />
            </el-form-item>
            <el-form-item label="衣物类别" required>
              <el-select v-model="form.category" placeholder="请选择类别" style="width:100%">
                <el-option v-for="c in categories" :key="c" :label="c" :value="c" />
              </el-select>
            </el-form-item>
            <el-form-item label="预估重量(kg)" required>
              <el-input-number v-model="form.weightKg" :min="0.1" :step="0.5" :precision="1" style="width:100%" />
            </el-form-item>
            <el-form-item label="预约时间" required>
              <el-date-picker v-model="form.appointmentTime" type="datetime" placeholder="选择预约时间"
                style="width:100%" format="YYYY-MM-DD HH:mm" value-format="YYYY-MM-DDTHH:mm:ss" />
            </el-form-item>
            <el-form-item label="备注">
              <el-input v-model="form.note" type="textarea" :rows="3" placeholder="如：是否已清洗、是否要大件搬运等" />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" native-type="submit" :loading="loading" size="large">
                <el-icon><Check /></el-icon> 提交预约
              </el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-col>
      <el-col :span="10">
        <el-card shadow="hover">
          <template #header><strong><el-icon><InfoFilled /></el-icon> 捐赠须知</strong></template>
          <ul class="notice-list">
            <li>请尽量捐赠清洁、可二次穿着的衣物。</li>
            <li>破损衣物可进入再生处理流程。</li>
            <li>完成送达或再生后可生成捐赠证明。</li>
            <li>系统会记录每次流向变更，便于公开追踪。</li>
            <li>预约后回收人员将在预约时间内上门。</li>
          </ul>
        </el-card>
        <el-card shadow="hover" style="margin-top: 16px" v-if="result">
          <el-result icon="success" title="预约成功" :sub-title="'捐赠证明编号：' + result.certificateNo">
            <template #extra>
              <el-button type="primary" @click="$router.push('/progress')">查看进度</el-button>
            </template>
          </el-result>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { createDonation } from '../../api'
import { ElMessage } from 'element-plus'

const loading = ref(false)
const result = ref(null)
const categories = ['秋冬外套', '春夏衣物', '儿童衣物', '旧被褥床品', '破损织物(再生)', '鞋帽配饰']

const tomorrow = new Date(Date.now() + 24 * 60 * 60 * 1000)
const form = reactive({
  donorName: '', phone: '', address: '', category: '秋冬外套',
  weightKg: 5, appointmentTime: tomorrow.toISOString().slice(0, 16) + ':00', note: ''
})

async function submitDonation() {
  if (!form.donorName || !form.phone || !form.address) {
    ElMessage.warning('请填写必要信息')
    return
  }
  loading.value = true
  try {
    const res = await createDonation({ ...form })
    result.value = res.data
    ElMessage.success('预约成功！证明编号：' + res.data.certificateNo)
    form.donorName = ''; form.phone = ''; form.address = ''; form.note = ''
  } catch (e) {
    ElMessage.error('提交失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.notice-list { padding-left: 20px; color: #606266; font-size: 14px; line-height: 2; }
</style>
