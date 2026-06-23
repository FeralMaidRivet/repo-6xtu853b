import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('../layouts/MainLayout.vue'),
    children: [
      { path: '', name: 'Home', component: () => import('../views/Home.vue'), meta: { title: '首页' } },
      { path: 'login', name: 'Login', component: () => import('../views/Login.vue'), meta: { title: '登录' } },
      { path: 'register', name: 'Register', component: () => import('../views/Register.vue'), meta: { title: '注册' } },
      { path: 'donate', name: 'Donate', component: () => import('../views/donor/Donate.vue'), meta: { title: '捐赠预约' } },
      { path: 'progress', name: 'Progress', component: () => import('../views/donor/Progress.vue'), meta: { title: '进度查询' } },
      { path: 'certificate', name: 'Certificate', component: () => import('../views/donor/Certificate.vue'), meta: { title: '捐赠证明' } },
      { path: 'public', name: 'Public', component: () => import('../views/public/PublicDisplay.vue'), meta: { title: '公示公开' } },
    ]
  },
  {
    path: '/staff',
    component: () => import('../layouts/StaffLayout.vue'),
    children: [
      { path: '', name: 'StaffTasks', component: () => import('../views/staff/Tasks.vue'), meta: { title: '回收任务' } },
    ]
  },
  {
    path: '/admin',
    component: () => import('../layouts/AdminLayout.vue'),
    children: [
      { path: '', name: 'AdminDashboard', component: () => import('../views/admin/Dashboard.vue'), meta: { title: '管理概览' } },
      { path: 'donations', name: 'AdminDonations', component: () => import('../views/admin/Donations.vue'), meta: { title: '捐赠管理' } },
      { path: 'sorting', name: 'AdminSorting', component: () => import('../views/admin/Sorting.vue'), meta: { title: '分拣分配' } },
      { path: 'institutions', name: 'AdminInstitutions', component: () => import('../views/admin/Institutions.vue'), meta: { title: '机构管理' } },
      { path: 'flow', name: 'AdminFlow', component: () => import('../views/admin/Flow.vue'), meta: { title: '物资流向' } },
      { path: 'users', name: 'AdminUsers', component: () => import('../views/admin/Users.vue'), meta: { title: '用户管理' } },
      { path: 'stats', name: 'AdminStats', component: () => import('../views/admin/Stats.vue'), meta: { title: '统计公示' } },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {
  document.title = (to.meta.title ? to.meta.title + ' - ' : '') + '衣物捐赠系统'
  next()
})

export default router
