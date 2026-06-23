import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref(JSON.parse(localStorage.getItem('userInfo') || 'null'))

  const isLoggedIn = computed(() => !!userInfo.value)
  const role = computed(() => userInfo.value?.role || '')
  const realName = computed(() => userInfo.value?.realName || '')
  const isAdmin = computed(() => role.value === 'ADMIN')
  const isCollector = computed(() => role.value === 'COLLECTOR')
  const isDonor = computed(() => role.value === 'DONOR')

  function setUser(data) {
    userInfo.value = data
    localStorage.setItem('userInfo', JSON.stringify(data))
  }

  function logout() {
    userInfo.value = null
    localStorage.removeItem('userInfo')
  }

  return { userInfo, isLoggedIn, role, realName, isAdmin, isCollector, isDonor, setUser, logout }
})
