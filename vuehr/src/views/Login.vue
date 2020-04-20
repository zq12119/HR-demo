<template>
  <div>
    <!-- 登录表单区 -->
    <el-form class="login_container" ref="loginFormRef" label-width="0px" :model="loginForm" :rules="loginFormRule">
      <h3 class="login_title">系统登录</h3>
      <!-- 用户名 -->
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" prefix-icon="iconfont icon-account-fill"></el-input>
      </el-form-item>
      <!-- 密码 -->
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" prefix-icon="iconfont icon-password" show-password></el-input>
      </el-form-item>
      <!-- 记住我 -->
      <el-checkbox v-model="checked" class="login_remember">记住我</el-checkbox>
      <!-- 登录按钮 -->
      <el-form-item class="btn">
        <el-button type="primary" @click="login">登录</el-button>
        <el-button type="info" @click="resetLoginForm">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { postKeyValueRequest } from '../utils/api'
export default {
  name: '',
  components: {},
  data () {
    return {
      // 登录表单的数据绑定对象
      loginForm: {
        username: 'admin',
        password: '123'
      },
      checked: true,
      // 表单的验证规则对象
      loginFormRule: {
        // 验证用户名是否合法
        username: [
          { required: true, $message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, $message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        // 验证密码是否合法
        password: [
          { required: true, $message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 20, $message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    // 重置按钮的点击事件，重置表单元素
    resetLoginForm () {
      console.log(this.$refs)
      this.$refs.loginFormRef.resetFields()
    },
    login () {
      this.$refs.loginFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return this.$message.error('用户名或密码格式不正确，请重新输入')
        }
        const resp = await postKeyValueRequest('/doLogin', this.loginForm)
        console.log(resp)
        if (resp) {
          this.$message.success('登录成功')
          // 1. 将登录成功之后的user保存到客户端的sessionStorage中
          //    1.1 项目中出了登录之外的其它API接口，必须在登录之后才能访问
          //    1.2 user只应在当前网站打开期间生效，所以将user保存在sessionStorage中
           window.sessionStorage.setItem("user", JSON.stringify(resp.obj));
          // 2. 通过编程式导航跳转到后台主页，路由地址是 /home
           this.$router.replace('/home')
        }
      })
    }
  }
}
</script>

<style scoped>
.login_container {
  width: 400px;
  background-clip: padding-box;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 25px #cac6c6;
  border-radius: 15px;
  padding: 15px 35px 15px 35px;
  background: #fff;
  /* box居中设置 */
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.login_title {
  width: 100%;
  margin: 15px auto 20px auto;
  text-align: center;
  color: #505458;
}
.login_remember {
  text-align: left;
  margin: 0px 0px 15px 0px;
}
.btn {
  display: flex;
  justify-content: flex-end;
}
</style>