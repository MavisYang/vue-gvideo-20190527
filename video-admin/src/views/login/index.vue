<template>
  <div class="login">
    <div class="login-wrap">
      <p class="label">登录</p>
      <div class="wrap">
        <el-form
          size="small"
          :model="formData"
          :rules="rules"
          ref="loginForm"
          :hide-required-asterisk="true"
        >
          <el-form-item label="账号" prop="userName">
            <el-input v-model="formData.userName" placeholder="请输入登录账号"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="formData.password" type="password" placeholder="输入登录密码"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button :loading="loading" @click="submitForm" size="mini" type="primary">登录</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import qs from "qs";
/**序列化 userName=12&password=12
 *  传给后台的格式，由后台或者协商决定
 */

export default {
  data() {
    return {
      loading: false,
      formData: {
        userName: "",
        password: ""
      },
      rules: {
        userName: [
          { required: true, message: "请输入登录账号", trigger: "blur" }
        ],
        password: [{ required: true, message: "输入登录密码", trigger: "blur" }]
      }
    };
  },
  methods: {
    submitForm() {
      console.log('this---',this);
      
      const form = this.$refs.loginForm;
      form.validate(valid => {
        if (valid) {
          this.loading = true;
          // '/console/user/login'
          console.log(this.formData);
          console.log(qs.stringify(this.formData)); //userName=12&password=12 序列化
          console.log(JSON.stringify(this.formData));
          console.log(
            qs.parse(
              "method=query_sql_dataset_data&projectId=85&appToken=7d22e38e-5717-11e7-907b-a6006ad3dba0"
            )
          );
          const data = {
            name: 1,
            date: [
              { id: 1, age: 12 },
              { id: 2, age: 18 }
            ]
          };
          console.log(qs.stringify(data));

          const data2 =
            "name=1&date%5B0%5D%5Bid%5D=1&date%5B0%5D%5Bage%5D=12&date%5B1%5D%5Bid%5D=2&date%5B1%5D%5Bage%5D=18";

          console.log(qs.parse(data2));
          this.$router.replace("/videos");

          // this.axios
          //   .post("/console/user/login", qs.stringify(this.formData))
          //   .then(({ data }) => {
          //     localStorage.setItem("token", data.data.consoleToken);
          //     this.$router.replace("/videos");
          //   })
          //   .catch(this.serviceError)
          //   .finally(() => {
          //     this.loading = false;
          //   });
        } else {
          return false;
        }
      });
    }
  }
};
</script>
<style lang="scss">
.login {
  width: 100%;
  height: 100%;
  position: relative;
  background-color: #f5f7fb;
}
.login-wrap {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -75%);
}
.label {
  font-size: 18px;
  font-weight: 400;
  color: #409eff;
  line-height: 24px;
  text-align: center;
}

.wrap {
  width: 45%;
  min-width: 330px;
  max-width: 450px;
  height: 270px;
  background: rgba(255, 255, 255, 1);
  margin: 30px auto;
  padding: 14px 48px;

  .el-form {
    color: rgba(0, 0, 0, 0.85);

    .el-form-item {
      margin-top: 21px;

      .el-input {
        border-bottom: 1px solid #eee;
      }

      input {
        border: 0 !important;
      }

      button {
        width: 100%;
      }
    }
  }
}
</style>