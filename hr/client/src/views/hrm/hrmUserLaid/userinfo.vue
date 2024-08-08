<template>
  <div>
    <el-divider>个人信息查看</el-divider>
    <el-form
      ref="userForm"
      :model="user"
      label-width="180px"
      class="dc-el-form_ElEditForm"
    >
      <el-col :span="6">
        <el-form-item
          label="姓名:"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="user.name"
            type="input"
            border="0"
            disabled="true"
            :maxLength="-1"
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          label="工号:"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="user.empolyno"
            type="input"
            disabled="true"
            :maxLength="-1"
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          label="部门:"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="user.department.name"
            type="input"
            disabled="true"
            :maxLength="-1"
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          label="职级:"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="user.ranks.name"
            type="input"
            disabled="true"
            :maxLength="-1"
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          label="入职时间:"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="user.createDate"
            type="input"
            disabled="true"
            :maxLength="-1"
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          label="职位:"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="user.post.name"
            type="input"
            disabled="true"
            :maxLength="-1"
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
      <el-col :span="6">
        <el-form-item
          label="所属公司:"
          class="dc-el-form-item_SingleInput"
        >
          <el-input
            v-model="user.company.name"
            type="input"
            disabled="true"
            :maxLength="-1"
            class="dc-el-input_SingleInput"
          ></el-input>
        </el-form-item>
      </el-col>
    </el-form>
  </div>
</template>

<script>
import { getUserById } from '@/api/admin/user'
export default {
  name: "userinfo",
  data() {
    return {
      user: {
        name:'',
        empolyno:'',
        post:{},
        company:{},
        department:{},
        ranks:{}
      }
    }
  },
  props:{
    userId:{
      type: String,
      require:true,
    }
  },
  mounted() {
    getUserById(this.userId).then(resp => {
      if(resp.code == 100){
         this.user = resp.data
      }
    })
  }
}
</script>

<style scoped>
/deep/ .el-input__inner {
  border: none;
}
</style>

