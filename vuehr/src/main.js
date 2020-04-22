import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
// 导入font-awesome图标库样式
 import 'font-awesome/css/font-awesome.css'

// 导入api的全局函数
import { postKeyValueRequest, getRequest, postRequest,
  putRequest, deleteRequest } from './utils/api'

Vue.prototype.postKeyValueRequest = postKeyValueRequest;
Vue.prototype.getRequest = getRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;


// 导入全局样式
import './assets/css/global.css'
// 导入iconfont字体图标库
import './assets/fonts/iconfont.css'

Vue.config.productionTip = false

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')