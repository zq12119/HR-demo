import Vue from 'vue'

// 导入element-ui 样式
import 'element-ui/lib/theme-chalk/index.css'
import {
    Button, Form, FormItem, Input, Checkbox,
    // 导入弹框提示组件
    Message
} from 'element-ui'

Vue.use(Button)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(Checkbox)

// 把Messag挂载到Vue的原型对象上
Vue.prototype.$message = Message