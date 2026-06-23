import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 15000
})

// 认证
export const login = (data) => api.post('/auth/login', data)
export const register = (data) => api.post('/auth/register', data)

// 捐赠
export const fetchDonations = () => api.get('/donations')
export const createDonation = (data) => api.post('/donations', data)
export const fetchDonation = (id) => api.get(`/donations/${id}`)
export const fetchDonationByCert = (certNo) => api.get(`/donations/cert/${certNo}`)
export const searchDonations = (keyword) => api.get('/donations/search', { params: { keyword } })
export const updateDonationStatus = (id, data) => api.patch(`/donations/${id}/status`, data)

// 回收人员
export const fetchStaffTasks = () => api.get('/staff/tasks')
export const acceptTask = (id, recyclerName) => api.patch(`/staff/tasks/${id}/accept`, null, { params: { recyclerName } })
export const pickupTask = (id, recyclerName) => api.patch(`/staff/tasks/${id}/pickup`, null, { params: { recyclerName } })
export const handoffTask = (id, institutionName) => api.patch(`/staff/tasks/${id}/handoff`, null, { params: { institutionName } })

// 管理员
export const fetchStats = () => api.get('/admin/stats')
export const fetchAdminDonations = () => api.get('/admin/donations')
export const adminUpdateStatus = (id, data) => api.patch(`/admin/donations/${id}/status`, data)
export const adminDeleteDonation = (id) => api.delete(`/admin/donations/${id}`)
export const fetchInstitutions = () => api.get('/admin/institutions')
export const addInstitution = (data) => api.post('/admin/institutions', data)
export const updateInstitution = (id, data) => api.put(`/admin/institutions/${id}`, data)
export const deleteInstitution = (id) => api.delete(`/admin/institutions/${id}`)
export const fetchUsers = () => api.get('/admin/users')
export const updateUser = (id, data) => api.put(`/admin/users/${id}`, data)
export const deleteUser = (id) => api.delete(`/admin/users/${id}`)
export const fetchFlow = () => api.get('/admin/flow')

// 公开
export const fetchPublicStats = () => api.get('/public/stats')
export const fetchPublicFlow = () => api.get('/public/flow')
export const fetchPublicInstitutions = () => api.get('/public/institutions')

export default api
