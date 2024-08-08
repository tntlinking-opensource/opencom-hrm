<template>
  <div class="attachmentData">
    <el-divider class="dc-el-divider_ElDivider">附件资料</el-divider>
    <el-button
      icon="el-icon-download"
      v-if="!attachmentDisabled"
      style="margin-bottom: 10px;"
      @click="downloadAll"
      >一键下载</el-button
    >
    <el-form
      ref="attachmentForm"
      :model="attachmentForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="attachmentForm.attachmentList"
        ref="tsmQualificationTable"
        border
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column type="index" width="80" title="序号"></ux-table-column>
        <ux-table-column
          field="attribute0"
          title="附件名称"
          tree-node
          resizable
          min-width="130px"
          align="left"
          header-align="center"
          show-overflow
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot="{ row }">
            {{ row.attribute0 }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="certificateNum"
          title="是否必传"
          tree-node
          resizable
          min-width="120px"
          align="left"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>

          <template v-slot="{ row }">
            {{ row.attribute1 === "1" ? "是" : "否" }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="fileStatus"
          title="是否上传"
          tree-node
          resizable
          min-width="120px"
          align="left"
          header-align="center"
          show-overflow
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>

          <template v-slot="{ row }">
            {{ row.fileStatus }}
          </template>
        </ux-table-column>
        <ux-table-column
          title="操作"
          tree-node
          resizable
          width="160px"
          v-if="!attachmentDisabled"
          min-width="80px"
          fixed="right"
          align="left"
          header-align="center"
          class="dc-ux-table-column_ElTableOptColumn"
        >
          <template v-slot:header="scope">
            <span>操作</span>
          </template>
          <template slot-scope="scope">
            <OperationIcon
              content="下载"
              placement="top"
              @click="onDownload(scope.row)"
              icon-name="el-icon-download"
              class="dc-OperationIcon_IconButton"
            ></OperationIcon>
            <OperationIcon
              content="预览"
              placement="top"
              @click="onView(scope.row)"
              icon-name="el-icon-view"
              class="dc-OperationIcon_IconButton"
            ></OperationIcon>
          </template>
        </ux-table-column>
      </ux-grid>
    </el-form>
  </div>
</template>
<script>
import { listUserPropDetailsAll } from "@/api/basic/userPropDetails";
/** 根据用户界面配置import组件 结束 */
import OperationIcon from "@/components/OperationIcon";
import { listSysFileAll, fileUploadById } from "@/api/sys/sysFile";
import { downByEmployeeNo } from "@/api/induction/tsmInductionPcOther";
let Base64 = require("js-base64").Base64;
export default {
  props: ["rowData", "attachmentDisabled"],
  name: "attachmentData",
  components: {
    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      attachmentForm: {
        attachmentList: []
      },
      files: {},
      rowData: {}
    };
  },
  watch: {
    "rowData.id"() {
      this.downLoadData();
    }
  },
  computed: {},
  methods: {
    //获取附件资料
    getAttachment() {
      let search = {
        columnName: "",
        order: "",
        params: [
          { columnName: "user_prop_list_id", queryType: "=", value: "2" }
        ]
      };
      listUserPropDetailsAll(search).then(res => {
        if (res.code === "100") {
          this.attachmentForm.attachmentList = res.data.filter(
            item => item.used === "1"
          );
          this.attachmentForm.attachmentList.map(item => {
            if (
              this.files[item.attribute2] &&
              this.files[item.attribute2].length > 0
            ) {
              item.fileStatus = "已上传";
            } else {
              item.fileStatus = "未上传";
            }
            return item;
          });
        }
      });
    },
    downloadAll() {
      this.attachmentForm.attachmentList.forEach(item => {
        this.onDownload(item, "all");
      });
    },
    onView(row) {
      if (
        !this.files[row.attribute2] ||
        this.files[row.attribute2].length === 0
      ) {
        this.$message({
          message: "该数据还未有文件可预览"
        });
      } else {
        this.files[row.attribute2].forEach(item => {
          this.onViewFileList(item);
        });
      }
    },
    onViewFileList(row) {
      // 预览
      let fileId = row.objectId;
      // 文件流访问路径
      let url =
        "http://377328t1x9.zicp.vip:52265/" + `sys/sysSeting/getFile/` + fileId;
      window.open(
        "http://61.172.179.56:9898/insideOnlinePreview?url=" +
          encodeURIComponent(Base64.encode(url))
      );
    },
    //入职附件下载
    onDownload(row, status = "personal") {
      if (
        !this.files[row.attribute2] ||
        this.files[row.attribute2].length === 0
      ) {
        if (status === "personal") {
          this.$message({
            message: "该数据还未有文件可下载"
          });
        }
      } else {
        this.files[row.attribute2].forEach(item => {
          fileUploadById(item.id)
            .then(responseData => {
              const content = responseData;
              this.download(content, item.name);
            })
            .catch(error => {
              this.outputError(error);
            });
        });
      }
    },
    downLoadData() {
      downByEmployeeNo(this.rowData.empolyno).then(res => {
        this.files = res.data;
        this.getAttachment();
      });
    },

    //下载方法
    download(content, fileName) {
      const blob = new Blob([content]);
      const url = window.URL.createObjectURL(blob);
      let dom = document.createElement("a");
      dom.style.display = "none";
      dom.href = url;
      dom.setAttribute("download", fileName);
      document.body.appendChild(dom);
      dom.click();
    }
  },

  mounted() {
    this.downLoadData();
  }
};
</script>
<style lang="scss" scoped>
.attachmentData {
  width: 100%;
  height: 100%;
  background: #ffffff;
  margin-left: 10px;
  padding: 10px;
  overflow: auto;
}
</style>