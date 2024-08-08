<template>
  <div class="maintainDialog_header">
    <div class="image">
      <img src="~@/assets/images/picture.png" />
    </div>
    <div class="content">
      <div style="margin-bottom: 10px;">
        <span class="name">{{ formData.name }}</span>
        <span class="position">{{ formData.jobPosition.postName }}</span>
      </div>
      <div>
        <span><i class="el-icon-message"></i> {{ formData.email }}</span>
        <el-divider direction="vertical"></el-divider
        ><span> {{ formData.department.name }}</span>
        <el-divider direction="vertical"></el-divider
        ><span> {{ formData.jobPosition.postName }} </span>
        <el-divider direction="vertical"></el-divider
        ><span
          ><i class="el-icon-phone-outline"></i>
          {{ formData.phoneNumber }}</span
        >
      </div>
    </div>
  </div>
</template>
<script>
import { getInfo } from "@/api/induction/EmployeeInfo.js";
export default {
  props: ["rowData"],
  data() {
    return {
      formData: {
        email: "", // 个人邮箱
        department: "",
        jobPosition: {},
        phoneNumber: "",
        name: ""
      }
    };
  },
  mounted() {
    this.getData();
  },
  watch: {
    "rowData.id"() {
      this.getData();
    }
  },
  methods: {
    getData() {
      getInfo(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.formData = Object.assign(
            this.formData,
            JSON.parse(JSON.stringify(res.data))
          );
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.maintainDialog_header {
  width: 100%;
  height: 100px;
  background: #ffffff;
  display: flex;
  align-items: center;
  .image {
    width: 80px;
    height: 80px;
    margin-right: 30px;
    margin-left: 10px;
    img {
      width: 80px;
      border-radius: 50%;
      height: 80px;
    }
  }
  .content {
    .name {
      font-size: 20px;
      font-weight: bold;
    }
    .position {
      color: #009688;
      margin-left: 30px;
    }
  }
}
</style>