import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import {
  initMenu
} from './utils/menus'

// 导入全局样式
import './assets/css/global.css'
// 导入iconfont字体图标库
import './assets/fonts/iconfont.css'
// 导入font-awesome图标库样式
import 'font-awesome/css/font-awesome.css'

// 导入api的全局函数
import {
  postKeyValueRequest,
  getRequest,
  postRequest,
  putRequest,
  deleteRequest
} from './utils/api'

Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;

Vue.config.productionTip = false

// 路由的前置导航守卫
router.beforeEach((to, from, next) => {
  if (to.path === '/') {
    next()
  } else {
    if (window.sessionStorage.getItem('user')) {
      initMenu(router, store)
      console.log(to)
      console.log(from)
      next()
    } else {
      // 未登录用户跳转到登录页
      next('/?redirect=' + to.path)
    }
  }
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')