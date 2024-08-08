<template>
  <div class="personal">
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
      <div class="title">个人技能</div>
      <van-form :disabled="formDisabled">
        <div v-for="(item, index) in form" :key="index">
          <div :class="{ addForm: index > 0 }">
            <van-cell
              title=""
              v-if="index > 0"
              style="border-top-left-radius: 5px;border-top-right-radius: 5px;"
            >
              <template #right-icon>
                <van-icon
                  name="delete-o"
                  color="#999999"
                  size="18"
                  @click="formDisabled ? null : deleteData(index, item)"
                />
              </template>
            </van-cell>
            <van-field v-model="form[index].skillName" label="技能名称" />
            <van-field
              clickable
              readonly
              label="熟练程度"
              v-model="form[index].skillLevel.name"
              @click="formDisabled ? null : changeSkillLevelIndex(index)"
            />
            <van-field v-model="form[index].remarks" label="备注" />
          </div>
        </div>
        <van-popup v-model="showSkillLevelPicker" position="bottom">
          <van-picker
            show-toolbar
            value-key="name"
            :columns="skillLevelColumns"
            @confirm="onSkillLevelConfirm"
            @cancel="showSkillLevelPicker = false"
          >
          </van-picker>
        </van-popup>
        <div style="padding:15px 0 ">
          <van-button
            round
            block
            @click="addData"
            style="margin-bottom:15px;"
            :disabled="formDisabled"
            >新增</van-button
          >
          <van-button
            round
            block
            type="info"
            :loading="disabled ? true : false"
            native-type="submit"
            :disabled="disabled"
            @click="next"
            >下一步</van-button
          >
        </div>
      </van-form>
    </div>
    <processDialog
      ref="processDialog"
      @onClickLeft="onClickLeft"
      :iconType="iconType"
    ></processDialog>
  </div>
</template>
<script>
import processDialog from "./components/processDialog";
import { getDictTypeById } from "@/api/sys/dictType";
import { saveInductionPersonalSkills } from "@/api/induction/inductApp";
import { Toast } from "vant";
import { getInfoCompletionBasicById } from "@/api/induction/tsmInductionBasic";
import { Dialog } from "vant";
import { deleteTsmPersonalSkills } from "@/api/induction/tsmPersonalSkills.js";
export default {
  data() {
    return {
      iconType: {
        base: true,
        certificate: false,
        personal: false,
        work: true,
        education: true
      },
      disabled: false,
      formDisabled: false,
      form: [
        {
          skillName: "",
          skillLevel: { name: "" },
          remarks: ""
        }
      ],
      skillLevelColumns: [],
      showSkillLevelPicker: false,
      skillLevelIndex: 0,
      inductionId: ""
    };
  },
  components: {
    processDialog
  },
  mounted() {
    if (this.$route.order && this.$route.query["order"]) {
      this.order = this.$route.query["order"];
    }
    if (this.$route.query && this.$route.query["inductionId"]) {
      this.inductionId = this.$route.query["inductionId"];
      this.getFormData();
    }
    if (this.order === "2") {
      this.iconType = {
        base: true,
        certificate: true,
        personal: true,
        work: true,
        education: true
      };
    }
    this.getSkillLevelOptions();
  },
  watch: {
    $route(val) {
      if (val.query && val.query["inductionId"]) {
        this.inductionId = val.query["inductionId"];
        this.getFormData();
      }
    }
  },
  methods: {
    getFormData() {
      this.disabled = true;
      getInfoCompletionBasicById(this.inductionId).then(res => {
        if (res.code === "100") {
          this.form = res.data.tsmPersonalSkillsList;
          if (this.form.length === 0) {
            this.addData();
          }
          if (res.data.dataValidation && res.data.dataValidation === "1") {
            this.formDisabled = true;
          }
        }
        this.disabled = false;
      });
    },
    changeSkillLevelIndex(index) {
      this.skillLevelIndex = index;
      this.showSkillLevelPicker = true;
    },
    onSkillLevelConfirm(val) {
      this.form[this.skillLevelIndex].skillLevel = val;
      this.showSkillLevelPicker = false;
    },

    onClickLeft() {
      window.history.go(-1);
    },
    onClickRight() {
      this.$refs.processDialog.init();
    },
    next() {
      if (this.formDisabled) {
        this.$router.push({
          path: "/certificate",
          query: { inductionId: this.inductionId, order: this.order }
        });
      } else {
        this.disabled = true;
        this.form.map(item => {
          item.inductionId = this.inductionId;
          return item;
        });
        saveInductionPersonalSkills(this.form).then(res => {
          if (res && res.code === "100") {
            Toast.success("个人技能保存成功!");
            this.iconType.personal = true;
            this.$router.push({
              path: "/certificate",
              query: { inductionId: this.inductionId, order: this.order }
            });
          } else {
            Toast.fail("网络错误请重试！");
          }
          this.disabled = false;
        });
      }
    },
    deleteData(index, item) {
      if (item.skillName || item.skillLevel.name || item.remarks) {
        Dialog.confirm({
          message: "已经有填写内容，确认删除？"
        })
          .then(() => {
            if (item["id"]) {
              deleteTsmPersonalSkills(item).then(res => {
                if (res && res.code === "100") {
                  Toast.success("该条数据已删除!");
                  this.getFormData();
                } else {
                  Toast.fail("网络错误请重试！");
                }
              });
            } else {
              this.form.splice(index, 1);
            }
          })
          .catch(() => {});
      } else {
        this.form.splice(index, 1);
      }
    },
    addData() {
      this.form.push({
        skillName: "",
        skillLevel: { name: "" },
        remarks: ""
      });
    },
    getSkillLevelOptions() {
      getDictTypeById("1496815670528286765").then(res => {
        this.skillLevelColumns = res.data.dictItemList;
      });
    }
  }
};
</script>

<style scoped lang="scss">
.personal {
  height: 100vh;
  .container {
    margin: 2% 5%;
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
    .addForm {
      margin-top: 15px;
    }
  }
}
</style>
