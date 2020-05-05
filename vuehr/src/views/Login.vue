<template>
  <div>
    <!-- 登录表单区 -->
    <el-form
      class="login_container"
      ref="loginFormRef"
      :model="loginForm"
      :rules="loginFormRule"
      v-loading="loading"
    >
      <h3 class="login_title">系统登录</h3>
      <!-- 用户名 -->
      <el-form-item prop="username">
        <el-input
          v-model="loginForm.username"
          prefix-icon="iconfont icon-account-fill"
          placeholder="请输入用户名"
        ></el-input>
      </el-form-item>
      <!-- 密码 -->
      <el-form-item prop="password">
        <el-input
          v-model="loginForm.password"
          prefix-icon="iconfont icon-password"
          placeholder="请输入密码"
          show-password
        ></el-input>
      </el-form-item>
      <!-- 验证码 -->
      <el-form-item prop="code">
        <el-input
          v-model="loginForm.code"
          prefix-icon="iconfont icon-password"
          style="width:200px;margin-top:10px"
          placeholder="点击图片刷新验证码"
          @keydown.enter.native="login"
        ></el-input>
        <el-image :src="codeUrl" @click="refreshCode" alt="加载失败"></el-image>
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
export default {
  name: 'Login',
  data() {
    return {
      // 加载标识
      loading: false,
      // 登录表单的数据绑定对象
      loginForm: {
        username: 'admin',
        password: '123',
        code: ''
      },
      // 验证码
      codeUrl: '/verifyCode?time=' + new Date().getTime(),
      checked: true,
      // 表单的验证规则对象
      loginFormRule: {
        // 验证用户名是否合法
        username: [
          { required: true, $message: '请输入用户名', trigger: 'blur' },
          {
            min: 3,
            max: 10,
            $message: '长度在 3 到 10 个字符',
            trigger: 'blur'
          }
        ],
        // 验证密码是否合法
        password: [
          { required: true, $message: '请输入密码', trigger: 'blur' },
          {
            min: 3,
            max: 20,
            $message: '长度在 3 到 20 个字符',
            trigger: 'blur'
          }
        ],
        code: [{ required: true, $message: '请输入验证码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    // 重置按钮的点击事件，重置表单元素
    resetLoginForm() {
      console.log(this.$refs)
      this.$refs.loginFormRef.resetFields()
    },
    login() {
      this.$refs.loginFormRef.validate(async valid => {
        // console.log(valid)
        if (!valid) {
          return this.$message.error(
            '用户名、密码或验证码的格式不正确，请重新输入'
          )
        }
        this.loading = true
        const resp = await this.postKeyValueRequest('/doLogin', this.loginForm)
        this.loading = false
        if (resp) {
          console.log(resp.obj)
          // 1. 将登录成功之后的 user 保存到客户端的 sessionStorage 中
          //    1.1 项目中出了登录之外的其它 API 接口，必须在登录之后才能访问
          //    1.2 user只应在当前网站打开期间生效，所以将 user 保存在 sessionStorage 中
          window.sessionStorage.setItem('user', JSON.stringify(resp.obj))       
          // 获取查询字符串中的 path是否包含 redirect
          let path = this.$route.query.redirect
          // 2. 通过编程式导航跳转到后台主页，路由地址是 /home
          await this.$router.replace(
            path === '/' || path === undefined ? '/home' : path
          )
        } else {
          // 登录失败刷新验证码
          this.refreshCode()
        }
      })
    },
    refreshCode() {
      this.codeUrl = '/verifyCode?time=' + new Date().getTime()
    }
  }
}
</script>

<style scoped>
.login_container {
  width: 450px;
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
  margin: 0 0 15px 0;
}
.btn {
  display: flex;
  justify-content: flex-end;
}
.el-image{
  width: 88px;
  margin-left: 20px;
}

</style>