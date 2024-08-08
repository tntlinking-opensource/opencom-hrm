<template>
  <div>
    <el-collapse v-model="activeNames" v-loading="loading">
      <el-collapse-item name="3">
        <template slot="title">
          <span class="collapse-title">{{title}}<span style="color: #ccc">（展开查看更多）</span></span>
          <div class="collapse-btn">
            <el-button style="width: 90px" type="primary" @click.stop="onUploadFile" v-if='action != "view"'>上传</el-button>
          </div>
        </template>
        <el-upload
          :disabled='action == "view"'
          class="upload-demo"
          multiple
          list-type="picture"
          action=""
          :auto-upload="false"
          accept=".jpg,.jpeg,.png"
          :file-list="fileList"
          :headers="uploadHeaders"
          :limit="limit"
          :on-preview="onPreviewFile"
          :on-remove="onRemoveFileList"
          :before-remove="beforeRemoveFile"
          :on-success="fileSuccess"
          :on-exceed="onExceedFile"
          :on-change="onChangeFileList">
          <el-button :id="uploadBtnId" size="small" type="primary">上传附件</el-button>
        </el-upload>
        <el-table :data='viewFileList' v-if="this.viewFileList.length>0" style='width: 100%' v-show="action != 'add'">
          <el-table-column prop='fileByte' label='图片'>
            <template slot-scope='scope'>
                  <img
                    :src="'data:image/png;base64,'+scope.row.fileByte"
                    min-width="70"
                    height="70"
                    alt="加载失败"
                />
            </template>
          </el-table-column>
          <el-table-column prop='name' label='图片名' width='160px'></el-table-column>
          <el-table-column prop="createBy" label='上传人' width='120px'></el-table-column>
          <el-table-column prop='createDate' label='上传日期' width='160px'></el-table-column>
          <el-table-column label='操作' header-align='center' width='120px' fixed='right'>
            <template slot-scope='scope'>
              <OperationIcon type='info' content='下载' placement='top-start' icon-name='el-icon-download'
                             @click='onUploadViewFile(scope.$index, scope.row)'></OperationIcon>
              <OperationIcon v-if="action != 'view'" type='info' content='删除' placement='top-start' icon-name='el-icon-close'
                             @click='onRemoveViewFile(scope.$index, scope.row)'></OperationIcon>
            </template>
          </el-table-column>
        </el-table>
      </el-collapse-item>
    </el-collapse>
  </div>

</template>

<script>
  import { validatenull } from '@/utils/validate'
  import { fileUploadById, deleteSysFile, listSysFileAll } from '@/api/sys/sysFile'
  import OperationIcon from '@/components/OperationIcon'
  import BaseUI from '@/views/components/baseUI'
  import { getLocalToken } from '@/utils/auth'

  let Base64 = require('js-base64').Base64
  export default {
    extends: BaseUI,
    name: "uploadFile",
    props: {
      value: {
        type: Object
      },
      action: {
        type: String
      },
      objectId: {
        type: String | Number
      },
      title: {
        type: String,
        default: () => {
          return ''
        }
      },
      limit:{
        type : String,
        default: () => {
          return '1'
        }
      },
      fileSizes:{
        type: String | Number
      },
      fileFormats:{
        type: Array
      },
    },
    data() {
      return {
        uploadBtnId: 'uploadBtn' + Math.random(),  // 上传文件动态id，解决一个页面多个文件上传组件存在问题

        activeNames: [], // 折叠面板
        uploadHeaders: {'X-Token': getLocalToken()},
        baseApi: process.env.BASE_API,
        fileList:  [],
        loading: false,
        viewFileList: [], // 附件回显列表
      }
    },
    components: {
      OperationIcon
    },
    methods: {
      onUploadFile() { // 上传附件
        if(this.viewFileList.length>= this.limit){
            return this.$message.warning(`当前限制上传`+ this.limit +`个文件`);
        }
        if (this.activeNames.indexOf('3') === -1) {
          this.activeNames.push("3")
        }
        document.getElementById(this.uploadBtnId).click()
      },
      onRemoveFileList: function (file, fileList) { // 移除文件
        this.value.uploads = fileList
      },
      onUploadViewFile(index, row) { // 下载
        fileUploadById(row.id).then(responseData => {
          const content = responseData;
          this.download(content, row.name);
        }).catch(error => {
          this.outputError(error)
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
      },
      onPreviewFile(file) {
        console.log(file);
      },
      onExceedFile(file, fileList) {
        this.$message.warning(`当前限制上传`+ this.limit +`个文件`);
      },
      beforeRemoveFile(file, fileList) {
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      fileSuccess(res, file){                 //上传文件成功
        console.log(file);
      },
      onChangeFileList(file, fileList) {
        //获取后缀名
        const suffix = file.name.substring(file.name.lastIndexOf('.') + 1)
        //判断是否符合文件大小的限制需求
        let isLt2M = true;
        if (this.fileSizes) {
          isLt2M = file.size / 1024 / 1024 < this.fileSizes
        }
        //判断是否符合文件类型需求
        // let accept ='jpg/png/gif/jpeg'
        let isLtType = true;
        if (this.fileFormats) {
          isLtType = this.fileFormats.indexOf(suffix) >= 0
        }
        if (!isLtType) {
          this.$message.error(`上传文件格式必须为${this.fileFormats}`);
        }
        if (!isLt2M) {
          this.$message.error(`上传文件大小不能超过${this.fileSizes}M!`);
        }
        if (isLtType && isLt2M) {
          this.fileList = fileList
          this.value.uploads = fileList
        }else{
          this.fileList.splice(fileList.length - 1, 1)
          return false;
        }
      },
      onViewFileList(index, row) { // 预览
        let suffix = row.name.substring(row.name.lastIndexOf("."))
        let fileId = row.id + "" + suffix // 拼接参数，文件ID + 后缀名
        let token = getLocalToken()

        // 文件流访问路径
        let url = process.env.FILE_PREVIEW_URL + `sys/sysSeting/getFile/` + fileId + `?token=` + token
        window.open(process.env.KK_FILE_URL + encodeURIComponent(Base64.encode(url)));
      },
      onRemoveViewFile(index, row) { // 从数据库上传已存在文件
        this.$confirm(`确定移除 ${ row.name }吗？`, '确认', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        }).then(() => {
          this.viewFileList.splice(index, 1)
          this.value.deletes.push(row.id)
        })
      },
      // 根据objectId，获取附件信息
      getSysFileAll() {
        this.setLoad()
        let file_search = {
          params: [{'columnName':'object_id', 'queryType': '=', 'value': this.objectId}]
        }
        listSysFileAll(file_search).then(responseData => {
          if(responseData.code == 100) {
            this.viewFileList = responseData.data
            if (!validatenull(this.viewFileList)) {
              this.activeNames.push("3")
            }
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch(error => {
          this.outputError(error)
        })
      },
      initOption() { // 初始化数据
        this.fileList = []  // 上传附件列表
        this.activeNames = [] // 折叠面板初始化
        this.viewFileList = []
        if (!validatenull(this.objectId)) {
          this.getSysFileAll()
        }
      }
    },
    watch: {
      'objectId': function(newVal, oldVal) {

        if (!validatenull(this.objectId)) {
          this.getSysFileAll()
        }
      }
    },
    mounted: function() {
      this.initOption()
    }
  }
</script>

<style lang="scss" scoped>
  /deep/ .upload-demo .el-upload {
    display: none!important;
  }
  .collapse-title {
    font-size: 14px;
    color: #606266;
  }
  .el-collapse-item__header {
    position: relative;
    border-bottom: 1px solid #EBEEF5!important;
    margin-bottom: 10px;
    .collapse-btn {
      position: absolute;
      right: 30px;
    }
  }
</style>
