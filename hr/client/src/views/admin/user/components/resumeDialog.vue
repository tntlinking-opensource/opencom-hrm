<template>
  <el-dialog
    title="模板选择"
    :visible.sync="dialogVisible"
    v-if="dialogVisible"
    width="60%"
    class="resumeDialog"
  >
    <div style="display: flex">
      <div class="image_radio">
        <el-radio v-model="radio" label="1" class="image_el_radio"
          >表格型</el-radio
        >
        <img src="~@/assets/images/tableResume.png" />
      </div>
      <div class="image_radio">
        <el-radio v-model="radio" label="2" class="image_el_radio"
          >简约型</el-radio
        >
        <img src="~@/assets/images/simpleResume.png" />
      </div>
    </div>

    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="downloadResume">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { resumeTranslation } from "@/api/induction/EmployeeInfo.js";
export default {
  data() {
    return {
      dialogVisible: false,
      row: {},
      radio: "1"
    };
  },

  methods: {
    init(row) {
      this.row = row;
      this.dialogVisible = true;
    },
    downloadResume() {
      resumeTranslation(this.row.empolyno, this.radio).then(res => {
        const blob = new Blob([res]);
        const url = window.URL.createObjectURL(blob);
        let dom = document.createElement("a");
        dom.style.display = "none";
        dom.href = url;
        dom.setAttribute("download", this.row.name + "的简历" + ".docx");
        document.body.appendChild(dom);
        dom.click();
        this.dialogVisible = false;
      });
    }
  }
};
</script>
<style lang="scss">
.resumeDialog {
  .el-dialog__body {
    border-top: 1px solid #dbd7d7;
    padding: 0;
    .image_radio {
      width: 50%;
      display: flex;
      flex-direction: column;
      align-items: center;
      .image_el_radio {
        margin: 20px;
      }
      img {
        width: 80%;
        height: 350px;
      }
    }
  }
}
</style>
