<template>
  <div>
    <el-card shadow="hover">
      <template #header>
        <div style="display:flex;justify-content:space-between;align-items:center">
          <strong>合作机构管理</strong>
          <el-button type="primary" @click="openAddDialog">
            <el-icon><Plus /></el-icon> 新增机构
          </el-button>
        </div>
      </template>
      <el-table :data="institutions" stripe v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="机构名称" width="180" />
        <el-table-column prop="type" label="类型" width="100" />
        <el-table-column prop="contact" label="联系方式" width="140" />
        <el-table-column prop="region" label="地区" width="120" />
        <el-table-column prop="demand" label="需求" min-width="200" show-overflow-tooltip />
        <el-table-column label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.enabled ? 'success' : 'danger'" size="small">
              {{ row.enabled ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" fixed="right">
          <template #default="{ row }">
            <el-button size="small" type="primary" text @click="openEditDialog(row)">
              <el-icon><Edit /></el-icon> 编辑
            </el-button>
            <el-popconfirm title="确定删除？" @confirm="handleDelete(row.id)">
              <template #reference>
                <el-button size="small" type="danger" text><el-icon><Delete /></el-icon></el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑机构' : '新增机构'" width="520px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="机构名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="类型">
          <el-select v-model="form.type" style="width:100%">
            <el-option label="公益机构" value="公益机构" />
            <el-option label="再生机构" value="再生机构" />
            <el-option label="受助单位" value="受助单位" />
          </el-select>
        </el-form-item>
        <el-form-item label="联系方式"><el-input v-model="form.contact" /></el-form-item>
        <el-form-item label="地区"><el-input v-model="form.region" /></el-form-item>
        <el-form-item label="需求"><el-input v-model="form.demand" type="textarea" :rows="2" /></el-form-item>
        <el-form-item label="地址"><el-input v-model="form.address" /></el-form-item>
        <el-form-item label="状态"><el-switch v-model="form.enabled" active-text="启用" inactive-text="禁用" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { fetchInstitutions, addInstitution, updateInstitution, deleteInstitution } from '../../api'
import { ElMessage } from 'element-plus'

const institutions = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)
const editId = ref(null)
const form = reactive({ name: '', type: '公益机构', contact: '', region: '', demand: '', address: '', enabled: true })

async function loadData() {
  loading.value = true
  try { const res = await fetchInstitutions(); institutions.value = res.data }
  catch (e) { /* ignore */ }
  finally { loading.value = false }
}

function openAddDialog() {
  isEdit.value = false
  Object.assign(form, { name: '', type: '公益机构', contact: '', region: '', demand: '', address: '', enabled: true })
  dialogVisible.value = true
}

function openEditDialog(row) {
  isEdit.value = true
  editId.value = row.id
  Object.assign(form, { name: row.name, type: row.type, contact: row.contact, region: row.region, demand: row.demand, address: row.address || '', enabled: row.enabled })
  dialogVisible.value = true
}

async function handleSave() {
  if (isEdit.value) {
    await updateInstitution(editId.value, form)
    ElMessage.success('更新成功')
  } else {
    await addInstitution(form)
    ElMessage.success('新增成功')
  }
  dialogVisible.value = false
  await loadData()
}

async function handleDelete(id) {
  await deleteInstitution(id)
  ElMessage.success('已删除')
  await loadData()
}

onMounted(loadData)
</script>
