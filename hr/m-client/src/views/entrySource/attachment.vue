<template>
  <div class="attachment">
    <van-nav-bar
      title="面试信息填写"
      @click-left="onClickLeft"
      @click-right="onClickRight"
    >
      <template #left>
        <van-icon name="arrow-left" color="#000000" />
      </template>
      <template #right> <van-icon name="weapp-nav" color="#000000" /> </template
    ></van-nav-bar>
    <div class="container">
      <div
        v-for="(item, index) in sourceOptions"
        :key="index"
        class="attachmentList"
      >
        <div class="title">
          {{ item.attribute0 }}
          <span style="color:red" v-if="item.attribute1 === '1'">*</span>
        </div>
        <van-uploader
          :before-delete="deleteData"
          :key="index"
          :deletable="!formDisabled"
          :disabled="formDisabled"
          v-model="files[item.attribute2]"
          class="uploader"
        >
          <van-button icon="plus" type="primary" class="upload-button"
            >上传图片</van-button
          >
        </van-uploader>
      </div>
    </div>
    <div style="padding:16px">
      <van-button
        round
        block
        type="info"
        @click="next"
        :loading="disabled ? true : false"
        :disabled="disabled"
        >提交</van-button
      >
    </div>
    <processDialog
      ref="processDialog"
      @onClickLeft="onClickLeft"
      :iconType="iconType"
    ></processDialog>
  </div>
</template>
<script>
import { Toast, Dialog } from "vant";
import { getTsmInductionEnhanceBasicById } from "@/api/induction/tsmInductionBasic.js";
import {
  listUserPropDetailsAll,
  uploadMany,
  downByInductionId,
  deleteFile,
  dataSubmission
} from "@/api/induction/inductApp";
import processDialog from "./components/processDialog";

export default {
  data() {
    return {
      inductionId: "",
      sourceOptions: [],
      files: {},
      iconType: {
        base: true,
        bank: true,
        attachment: false,
        urgency: true,
        family: true
      },
      disabled: false,
      formDisabled: false
    };
  },
  components: { processDialog },
  mounted() {
    if (this.$route.query && this.$route.query["inductionId"]) {
      this.inductionId = this.$route.query["inductionId"];
    }
    this.downLoad();
    this.getSource();
    this.getFormData();
  },
  watch: {
    $route(val) {
      if (val.query && val.query["inductionId"]) {
        this.inductionId = val.query["inductionId"];
        this.downLoad();
      }
    }
  },
  methods: {
    //获取表格表单数据
    getFormData() {
      getTsmInductionEnhanceBasicById(this.inductionId).then(res => {
        if (res.code === "100") {
          if (res.data.dataValidation && res.data.dataValidation === "1") {
            this.formDisabled = true;
            this.iconType = {
              base: true,
              bank: true,
              attachment: true,
              urgency: true,
              family: true
            };
          }
        }
      });
    },
    onClickLeft() {
      window.history.go(-1);
    },
    onClickRight() {
      this.$refs.processDialog.init();
    },
    deleteData(file) {
      Dialog.confirm({
        message: "是否删除该图片？"
      })
        .then(() => {
          if (file.id) {
            deleteFile(file.id).then(res => {
              if (res && res.code === "100") {
                Toast.success("该图片已删除!");
                this.downLoad();
              } else {
                Toast.fail("网络错误请重试！");
              }
            });
          } else {
            let obj = {};
            Object.keys(this.files).forEach(item => {
              obj[item] = [];
            });
            Object.keys(this.files).forEach(item => {
              this.files[item].forEach(it => {
                if (it !== file) {
                  obj[item].push(it);
                }
              });
            });
            this.files = obj;
          }
        })
        .catch(() => {});
    },
    downLoad() {
      this.disabled = true;
      downByInductionId(this.inductionId).then(res => {
        let obj = res.data;
        Object.keys(obj).forEach(item => {
          obj[item].map(it => {
            let fileByte = it.sysFileContentList[0].fileByte;
            let arr = it.fileType.split(".");
            let type = "";
            if (arr[1] == "ico") {
              type = "x-icon";
            } else {
              type = arr[1];
            }
            let arrI = fileByte.split("base64");
            it.content = `data:image/${type};base64,` + arrI[1];
            return it;
          });
        });
        this.files = obj;
        this.disabled = false;
      });
    },
    next() {
      if (this.formDisabled) {
        this.$router.push({
          path: "/entrySuccess"
        });
      } else {
        let isValid = true;
        this.sourceOptions.forEach(item => {
          if (item.attribute1 === "1") {
            if (
              !this.files[item.attribute2] ||
              this.files[item.attribute2].length === 0
            ) {
              isValid = false;
            }
          }
        });
        if (isValid) {
          this.disabled = true;
          let obj = {};
          Object.keys(this.files).forEach(item => {
            obj[item] = [];
          });
          Object.keys(this.files).forEach(item => {
            this.files[item].forEach(it => {
              if (!it.id) {
                obj[item].push(it);
              }
            });
          });
          this.files = obj;
          let form = new FormData();
          let fileObject = {};
          for (let index in this.sourceOptions) {
            fileObject[this.sourceOptions[index].attribute2] = [];
            for (let inx in this.files[this.sourceOptions[index].attribute2]) {
              fileObject[this.sourceOptions[index].attribute2].push(
                this.files[this.sourceOptions[index].attribute2][inx].file.name
              );
            }
          }
          for (let index in this.sourceOptions) {
            for (let inx in this.files[this.sourceOptions[index].attribute2]) {
              form.append(
                "files",
                this.files[this.sourceOptions[index].attribute2][inx].file
              );
            }
          }

          form.append("names", JSON.stringify(fileObject));
          uploadMany(form, this.inductionId).then(res => {
            if (res && res.code === "100") {
              Toast.success("附件上传成功!");
              this.iconType.attachment = true;
              this.setStatus();
              this.$router.push({
                path: "/entrySuccess"
              });
            } else {
              Toast.fail("网络错误请重试！");
            }
            this.downLoad();
            this.disabled = false;
          });
        } else {
          Toast.fail("存在必填资料未上传！");
        }
      }
    },
    setStatus() {
      dataSubmission(this.inductionId).then(res => {});
    },

    //获取附件资料
    getSource() {
      let search = {
        columnName: "",
        order: "",
        params: [
          { columnName: "user_prop_list_id", queryType: "=", value: "2" }
        ]
      };
      listUserPropDetailsAll(search).then(res => {
        if (res.code === "100") {
          this.sourceOptions = res.data.filter(item => item.used === "1");
        }
      });
    }
  }
};
</script>

<style scoped lang="scss">
.attachment {
  height: 100vh;
  .container {
    margin: 2% 5%;
    .attachmentList {
      margin-bottom: 20px;
      .title {
        width: 114px;
        height: 42px;
        line-height: 42px;
        background-color: #fff;
        text-align: center;
        color: #0d8ddb;
        border-top-left-radius: 13px;
        border-top-right-radius: 13px;
      }
      .uploader {
        width: 100%;
        min-height: 150px;
        height: auto;
        background: white;
      }
    }
  }
}
</style>
<style lang="scss">
.uploader {
  .van-uploader__wrapper {
    width: 100%;
    height: 100%;
    .van-uploader__input-wrapper {
      width: 100%;
      min-height: 150px;
      height: auto;
      .upload-button {
        width: calc(100% - 30px);
        height: calc(100% - 30px);
        margin: 15px;
        background: #fafafa;
        border: none;
        color: #c7c7cc;
      }
    }
  }
}
</style>
