<template>
  <div v-if='isLoaded'
       class='login-container'
       :class="sysData.loginLayout">
    <div class="login-header">
      <div class="login-logo">
        <img :src="sysData.projectLogo" alt="">
      </div>
      <div class="login-title">
        {{sysData.sysName}}
      </div>
      <div class="login-right"></div>
    </div>
    <div class="login-form">
      <el-form class="form" :model='loginForm' :rules='rules' ref='loginForm' autoComplete='on' label-position='left' @submit.native.prevent>
        <div class='login-body'>
          <h1 class="form-title">
            <img :src="sysData.loginLogo" alt="">
          </h1>
          <el-alert class="login-error" v-show="isError" :title="loginError" type="error" show-icon :closable="false"></el-alert>
          <el-form-item prop='username'>
            <el-input name='username' v-model='loginForm.username' autoComplete='on' autofocus='autofocus' placeholder='用户名' @input="isError = false">
              <div class='login-username' slot='prepend'></div>
            </el-input>
          </el-form-item>
          <el-form-item prop='password'>
            <el-input name='password' type='password' v-model='loginForm.password' placeholder='口令' @input="isError = false">
              <template slot='prepend'><div class='login-password'></div></template>
            </el-input>
          </el-form-item>
          <el-button type='primary' :loading='showLoading' @click.native.prevent='doLogin' native-type="submit">登录</el-button>
        </div>
      </el-form>
      <div class="graph" :style="{backgroundImage: 'url('+ sysData.loginGraph +')'}"></div>
    </div>

    <div class="footer">@Copyright 2021.08.12</div>
  </div>
</template>

<script>
import { getToken } from '@/api/auth'
import { getAppRoot } from '@/utils/auth'

import BaseUI from '@/views/components/baseUI'
import { setLocalToken, setLocalCurrentUser, setLocalCurrentCompany } from '@/utils/auth'

import sysMixin from './sysMixin'

// 微应用时，appKey作为路由前缀
const root = getAppRoot()
export default {
  extends: BaseUI,
  mixins: [sysMixin],
  data() {
    return {
      isLoaded: false,
      isLoading: false,
      displayLoading: false,
      loginForm: {
        username: process.env.NODE_ENV == 'development' ? 'system' : '',
        password: process.env.NODE_ENV == 'development' ? '123456' : ''
      },
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入口令', trigger: 'blur' }]
      },
      loginBg: require('../../assets/images/logbg.jpg'),
      loginSubBg: require('../../assets/images/logsubbg.jpg'),
      isError: false,
      loginError: ''
    };
  },
  computed: {
    ...Vuex.mapGetters(['showLoading'])
  },
  methods: {
    doLogin() {
      this.$refs['loginForm'].validate(valid => {
        if (valid) {
          this.$store.dispatch('setLoading', true)
          getToken(this.loginForm.username, this.loginForm.password)
            .then(responseData => {
              if(responseData.code == 100) {
                this.handleLoginInfo(responseData.data)
              } else {
                this.isError = true
                this.loginError = responseData.msg
                this.$store.dispatch('setLoading', false)
              }
            })
            .catch(error => {
              this.$store.dispatch('setLoading', false)
              this.outputError(error)
            })
        }
      })
    },
    handleLoginInfo(userData) {
      setLocalCurrentUser({
        id: userData.userId,
        name: userData.username,
        loginname: userData.loginname,
        company: userData.company,
        department: userData.department,
        tenant: userData.tenant
      })
      setLocalCurrentCompany({
        id: userData.company.id,
        code: userData.company.code,
        name: userData.company.name
      })
      // setLocalCurrentTenant({
      //   id: userData.tenant.id,
      //   name: userData.tenant.name
      // })
      setLocalToken(userData.token)

      this.$router.push(`${root}/afterlogin`);
    }
  },
  created: function() {
    this.getSysSetting()
  }
}
</script>

<style rel='stylesheet/scss' lang='scss' scoped>
.login-container {
  background-image: url("../../assets/images/login.jpg");
  min-width: 1000px;
  width: 100%;
  height: 100%;
  position: relative;
  background-repeat: no-repeat;
  background-position: center;
  background-size: cover;
  overflow: auto;
  z-index: 1001;
  .login-header {
    height: 100px;
    min-width: 1000px;
    width: 80%;
    margin: 0 auto;
    z-index: 1000;
    box-sizing: border-box;
    transition: all .5s ease;
    .login-logo {
      float: left;
      margin: 0;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: flex-end;
      img {
        display: inline-block;
        overflow: hidden;
        height: 32px;
      }
    }
    .login-title {
      float: left;
      padding-left: 20px;
      font-weight: bold;
      font-size: 24px;
      color: #fff;
      line-height: 100px
    }
  }
  .el-input {
    width: 100%;
  }
  .el-button {
    width: 100%;
  }
  .login-form {
    position: absolute;
    border-radius: 4px;
    top: 50%;
    left: 50%;
    -webkit-box-shadow: 0 10px 40px rgba(1, 140, 183, .15);
    box-shadow: 0 10px 40px rgba(1, 140, 183, .15);
  }

  .footer {
    font-size: 12px;
    position: absolute;
    right: 20px;
    bottom: 20px;
    color: #fff;
  }
}
.simple-center {
  .login-form {
    width: 400px;
    margin: 0 auto;
    margin-top: -159px;
    margin-left: -200px;
    background-color: #fff;
    .footer {
      position: absolute;
      color: #f5f5f5;
      font-size: 14px;
      bottom: -200px;
      width: 100%;
      text-align: center;
    }
  }
  .form-title {
    font-size: 14px;
    text-align: center;
    margin-bottom: 50px;
    img {
      display: inline-block;
      height: 28px;
      overflow: hidden;
    }
  }
  .login-body {
    padding: 30px;
    text-align: center;
    .form-title {
      font-size: 16px;
    }
    .profile-img {
      width: 96px;
      height: 96px;
      margin: 0 auto 10px;
      display: block;
    }
    .login-error {
      position: relative;
      font-size: 14px;
      color: #f00;
      margin-bottom: 5px;
    }
    .login-username {
      width: 20px;
      height: 20px;
      margin: 0px -10px;
      background-image: url('../../assets/images/user.png');
    }
    .login-password {
      width: 20px;
      height: 20px;
      margin: 0px -10px;
      background-image: url('../../assets/images/lock.png');
    }
  }
  .graph {
    display: none;
  }
}
.left-graph {
  .login-form {
    min-width: 1000px;
    background-color: #fff;
    /*width: 80%;*/
    width: 1000px;
    margin: 0 auto;
    margin-top: -250px;
    /*margin-left: -40%;*/
    margin-left: -500px;
    &:after {
      content: '';
      height: 0;
      line-height: 0;
      display: block;
      visibility: hidden;
      clear: both;
    }
    .footer {
      position: absolute;
      color: #f5f5f5;
      font-size: 14px;
      bottom: -50px;
      width: 100%;
      text-align: center;
    }
    .graph {
      height: 100%;
      margin-right: 450px;
      background-position: center top;
      background-size: 100%;
      background-repeat: no-repeat;
      min-height: 500px;
      border-radius: 4px 0 0 4px;
      overflow: hidden;
    }
    .form {
      box-sizing: border-box;
      position: absolute;
      top: 0;
      right: 0;
      width: 450px;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 500px;
      .login-body {
        width: 80%;
        margin-top: -100px;
        .form-title {
          font-size: 14px;
          text-align: center;
          margin-bottom: 50px;
          img {
            display: inline-block;
            height: 28px;
            overflow: hidden;
          }
        }
        .login-username {
          width: 20px;
          height: 20px;
          margin: 0px -10px;
          background-image: url('../../assets/images/user.png');
        }
        .login-password {
          width: 20px;
          height: 20px;
          margin: 0px -10px;
          background-image: url('../../assets/images/lock.png');
        }
      }
    }
  }
}
.right-graph {
  .login-form {
    min-width: 1000px;
    background-color: #fff;
    /*width: 80%;*/
    width: 1000px;
    margin: 0 auto;
    margin-top: -250px;
    /*margin-left: -40%;*/
    margin-left: -500px;
    &:after {
      content: '';
      height: 0;
      line-height: 0;
      display: block;
      visibility: hidden;
      clear: both;
    }
    .footer {
      position: absolute;
      color: #f5f5f5;
      font-size: 14px;
      bottom: -50px;
      width: 100%;
      text-align: center;
    }
    .graph {
      height: 100%;
      border-radius: 0 4px 4px 0;
      margin-left: 450px;
      background-position: center top;
      background-size: 100%;
      background-repeat: no-repeat;
      min-height: 500px;
      overflow: hidden;
    }
    .form {
      box-sizing: border-box;
      width: 450px;
      float: left;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 500px;
      .login-body {
        width: 80%;
        margin-top: -100px;
        .form-title {
          font-size: 14px;
          text-align: center;
          margin-bottom: 50px;
          img {
            display: inline-block;
            height: 28px;
            overflow: hidden;
          }
        }
        .login-username {
          width: 20px;
          height: 20px;
          margin: 0px -10px;
          background-image: url('../../assets/images/user.png');
        }
        .login-password {
          width: 20px;
          height: 20px;
          margin: 0px -10px;
          background-image: url('../../assets/images/lock.png');
        }
      }
    }
  }
}
</style>
