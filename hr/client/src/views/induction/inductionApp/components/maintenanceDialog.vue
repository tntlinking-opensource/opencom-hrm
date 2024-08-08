<template>
  <el-dialog
    title="入职人员信息"
    :visible.sync="dialogVisible"
    v-if="dialogVisible"
    fullscreen
    class="maintenanceDialog"
  >
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="基本信息" name="base">
        <baseComponent
          ref="baseComponent"
          :rowData="rowData"
          v-if="activeName === 'base'"
        ></baseComponent>
      </el-tab-pane>
      <el-tab-pane label="补全信息" name="complement">
        <complementComponent
          ref="complementComponent"
          v-if="activeName === 'complement'"
          :rowData="rowData"
        ></complementComponent>
      </el-tab-pane>
      <el-tab-pane label="附件资料" name="attachment">
        <attachmentComponent
          ref="attachmentComponent"
          :rowData="rowData"
          v-if="activeName === 'attachment'"
        ></attachmentComponent>
      </el-tab-pane>
    </el-tabs>

    <div
      style="position: absolute;top: 70px;left: 50%;"
      v-if="rowData.dataSubmission && rowData.dataSubmission === '1'"
    >
      <el-checkbox v-model="checked" :disabled="rowData.dataValidation === '1'"
        >资料验收</el-checkbox
      >
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
    </span>
  </el-dialog>
</template>
<script>
import baseComponent from "./baseComponent.vue";
import complementComponent from "./complementComponent.vue";
import attachmentComponent from "./attachmentComponent.vue";
import { saveDataValidation } from "@/api/induction/tsmInductionPcOther";
export default {
  components: { baseComponent, complementComponent, attachmentComponent },
  data() {
    return {
      dialogVisible: false,
      activeName: "base",
      rowData: {},
      checked: false,
      form: {}
    };
  },
  watch: {
    checked(val) {
      if (val) {
        this.$refs.baseComponent.$refs["baseFormData"].validate(valid => {
          if (valid) {
            this.setValid();
          } else {
            this.checked = false;
            this.$message({
              type: "warning",
              message: "请填写完相关信息"
            });
          }
        });
      }
    }
  },
  methods: {
    init(row) {
      this.rowData = row;
      if (this.rowData.dataValidation === "1") {
        this.checked = true;
      } else {
        this.checked = false;
      }
      this.dialogVisible = true;
    },
    setValid() {
      saveDataValidation(this.rowData.id, "1").then(res => {
        if (res.code === "100") {
          this.$message({
            type: "success",
            message: "资料验收成功"
          });
          this.rowData.dataValidation = "1";
        } else {
          this.$message({
            type: "warning",
            message: "网络错误请重试"
          });
        }
      });
    },
    //获取表格表单数据
    getFormData() {
      getTsmInductionEnhanceBasicById(this.rowData.id).then(res => {
        if (res.code === "100") {
          this.baseFormData = Object.assign(
            this.baseFormData,
            JSON.parse(JSON.stringify(res.data))
          );
        }
      });
    }
  }
};
</script>
<style lang="scss">
.maintenanceDialog {
  .el-dialog__body {
    border-top: 1px solid #dbd7d7;
    padding: 0;
  }
}
</style>