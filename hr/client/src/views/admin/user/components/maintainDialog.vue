<template>
  <el-dialog
    title="信息查看维护"
    :visible.sync="dialogVisible"
    v-if="dialogVisible"
    fullscreen
    class="maintainDialog"
  >
    <maintain
      ref="maintain"
      :row="row"
      viewType="hr"
      v-if="dialogVisible"
    ></maintain>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
import maintain from "@/views/maintain/index.vue";
export default {
  data() {
    return {
      dialogVisible: false,
      row: {}
    };
  },
  components: {
    maintain
  },

  methods: {
    init(row) {
      this.row = row;
      this.dialogVisible = true;
    }
  }
};
</script>
<style lang="scss">
.maintainDialog {
  .el-dialog__body {
    border-top: 1px solid #dbd7d7;
    padding: 0;
  }
}
</style>
