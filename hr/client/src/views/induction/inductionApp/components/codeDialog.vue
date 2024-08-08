<template>
  <el-dialog
    title="面试二维码查看"
    :visible.sync="dialogVisible"
    v-if="dialogVisible"
    class="codeDialog"
  >
    <div style="padding: 20px;">
      <img :src="pictureData" />
    </div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">关 闭</el-button>
    </span>
  </el-dialog>
</template>
<script>
import { getFirstQrCode } from "@/api/induction/tsmQRCode.js";
export default {
  data() {
    return {
      dialogVisible: false,
      pictureData: ""
    };
  },

  methods: {
    init() {
      this.dialogVisible = true;
      this.getPicture();
    },

    getPicture() {
      getFirstQrCode().then(res => {
        this.pictureData = "data:image/png;base64," + res;
      });
    }
  }
};
</script>
<style lang="scss">
.codeDialog {
  .el-dialog__body {
    border-top: 1px solid #dbd7d7;
    padding: 0;
    display: flex;
    justify-content: center;
  }
}
</style>