<template>
  <el-dialog
    title="入职邮件编辑"
    :visible.sync="dialogVisible"
    fullscreen
    v-if="dialogVisible"
    class="emailDialog"
  >
    <div class="emailForm">
      <el-form label-width="120px" :model="emailForm" ref="emailForm">
        <el-form-item
          label="收件人:"
          prop="rcpt"
          :rules="[
            { required: true, message: '收件人不能为空', trigger: 'blur' }
          ]"
        >
          <el-input
            v-model="emailForm.rcpt"
            placeholder="请输入收件人"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="发送邮箱:"
          prop="senderId"
          :rules="[
            { required: true, message: '发送邮箱不能为空', trigger: 'blur' }
          ]"
        >
          <el-select
            v-model="emailForm.senderId"
            :style="{ width: '100%' }"
            placeholder="请选择发送邮箱"
            value-key="id"
            filterable
            class="dc-el-select_SelectInput"
          >
            <el-option
              v-for="(item, index) in senderOptions"
              :key="index"
              :label="item.attribute0"
              :value="item.id"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="邮箱主题:"
          prop="subject"
          :rules="[
            { required: true, message: '邮箱主题不能为空', trigger: 'blur' }
          ]"
        >
          <el-input
            v-model="emailForm.subject"
            placeholder="请输入邮箱主题"
          ></el-input>
        </el-form-item>
      </el-form>
    </div>
    <vue-editor v-model="content"></vue-editor>
    <div></div>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="sendMessage">确 定</el-button>
    </span>
  </el-dialog>
</template>
<script>
  import {VueEditor} from "vue2-editor";
  import {getInfoCompletionBasicById} from "@/api/induction/tsmInductionBasic";
  import {sendMail} from "@/api/common/mail.js";
  import {getSecondQrCode} from "@/api/induction/tsmQRCode.js";
  import {listUserPropDetailsAll} from "@/api/basic/userPropDetails";
  import baseUI from "@/views/components/baseUI";

  export default {
  extends: baseUI,
  components: { VueEditor },
  data() {
    return {
      dialogVisible: false,
      rowData: {},
      emailForm: {
        rcpt: "",
        senderId: "",
        subject: ""
      },
      userForm: {
        name: "",
        jobPosition: "",
        workCity: "",
        probationMonth: "",
        fullWages: "",
        probationWages: "",
        companyName: "",
        contractSignUnit: "",
        reportTime: "",
        reportAddress: "",
        department: "",
        today: ""
      },
      pictureData: "",
      senderOptions: []
    };
  },
  computed: {
    content() {
      return `<p>亲爱的<span style='color:blue;font-weight: bold;'> ${this.userForm.name} </span>(女士/先生)：</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好！经过综合评估和考察，您的学习背景、工作经验和面试中的出色表现使您从众多的候选人中脱颖而出。我们诚邀您加入<span style='color:blue'>${this.userForm.companyName}</span>大家庭，光荣的成为一名<span style='color:blue'>${this.userForm.contractSignUnit}</span>人！为了能更好地协助您顺利办理入职手续，请仔细阅读以下信息:</p><b>一、职位及报到信息：</b><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您将入职互联网行业<b><span style='color:blue'>${this.userForm.jobPosition}</span></b>职位，工作城市为<b><span style='color:blue'>${this.userForm.workCity}</span></b>，试用期为<b><span style='color:blue'>${this.userForm.probationMonth}</span></b>，试用期税前薪资为<b><span style='color:blue'>${this.userForm.probationWages}</span></b>元/月，转正后税前薪资为<b><span style='color:blue'>${this.userForm.fullWages}</span></b>元/月。请您于<b><span style='color:blue'>${this.userForm.reportTime} 10:00</span></b>至 <b><span >${this.userForm.reportAddress}</span></b> 上岗。</p><b>二、报到材料：</b><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、身份证原件及复印件1份（正反面）；</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、学历、学位证书以及职称、专业技术等各类资格证书的原件及复印件；</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、一寸免冠彩色照片2张（照片背面请写上名字）；</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、户口本首页（显示户籍类别）及本人页复印件；</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、办理一张中信银行借记卡（作为工资卡）</p><b>三、录用与转正条件：</b><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、应聘人身体健康（指定医院体检报告证明）；</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、诚实可信，保证所有提供的材料真实、有效，无任何虚假行为；</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、按时、保质地完成试用期工作任务，达到业绩要求，并通过试用期转正评价、考核；</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、遵守公司各项管理制度和规定，未出现违规行为；</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5、及时参加公司组织的新员工培训，新员工考试成绩合格；</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;6、具备满足职位需求的专业知识和实际操作技能；<p><b>四、特别说明：</b><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、此录用通知书的有效期为前述报到时间之前，逾期未提交信息或未报到的，公司将视为您主动放弃，本通知自动失效。如您同意接受上述条件，<b><span style='color:blue'>请您于3个工作日内回复确认入职邮件并请用手机微信或者手机浏览器扫描下方二维码访问网页，填写入职登记表。</span></b>提交入职信息即表明您已经知晓以上内容，如若报到材料未带齐者，公司将不予办理入职手续；或五天内仍不能提供完整报到材料者，公司原则上将不为其保留职位。</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、您对本通知书的确认并不表示双方劳动合同关系的确认，双方劳动合同关系的建立以及具体的权利义务最终将以我司与您签订的劳动合同为准。</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、本通知如有其他未尽事宜，将在劳动合同中予以进一步说明；若本通知内容与您签订的劳动合同内容存在冲突，以劳动合同约定为准。</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;4、应聘员工承诺不将原公司的任何资料以任何形式带入<b><span style='color:blue'>${this.userForm.companyName}</span></b>，或将资料复制到<b><span style='color:blue'>${this.userForm.companyName}</span></b>提供的工作电脑上。如有违反上述规定产生的知识产权纠纷由本人承担法律责任。</p><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;◆如您有任何问题，请随时联系我们13708206115。</b><br /><br /><p style='text-align: end;font-weight: bold;'>${this.userForm.companyName}</p><p style='text-align: end;'>${this.userForm.department}</p><p style='text-align: end;'>${this.userForm.today}</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;请扫二维码，填写入职信息，每项必填！<p/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="${this.pictureData}" width='250' height='250'/>`;
    }
  },
  methods: {
    init(row) {
      this.rowData = row;
      this.dialogVisible = true;
      this.getEmailData();
      this.userForm.today = this.changeTime();
      this.getPicture();
      this.getSender();
      this.emailForm.rcpt = this.rowData.email;
    },
    getEmailData() {
      getInfoCompletionBasicById(this.rowData.id).then(res => {
        if (res.code === "100") {
          this.userForm.name = res.data.name;
          this.userForm.jobPosition = res.data.jobPosition.postName;
          this.userForm.workCity = res.data.workCity;
          this.userForm.probationMonth = res.data.probationMonth.name;
          this.userForm.fullWages = res.data.fullWages;
          this.userForm.probationWages = res.data.probationWages;
          this.userForm.companyName = res.data.companyName;
          this.userForm.contractSignUnit = res.data.contractSignUnit;
          this.userForm.reportTime = res.data.reportTime;
          this.userForm.reportAddress = res.data.reportAddress;
          this.userForm.department = res.data.department.name;
        }
      });
    },
    //获取发送人邮件
    getSender() {
      let search = {
        columnName: "",
        order: "",
        params: [
          { columnName: "user_prop_list_id", queryType: "=", value: "1" }
        ]
      };
      listUserPropDetailsAll(search).then(res => {
        if (res.code === "100") {
          this.senderOptions = res.data;
        }
      });
    },
    getPicture() {
      getSecondQrCode(this.rowData.id).then(res => {
        this.pictureData = "data:image/png;base64," + res;
      });
    },
    //获取今天时间
    changeTime() {
      let d = new Date();
      var Month = d.getMonth() + 1;
      var Day = d.getDate();
      var Year = d.getFullYear();
      return `${Year}年${Month}月${Day}日`;
    },
    //发送邮件
    sendMessage() {
      this.$refs["emailForm"].validate(valid => {
        if (valid) {
          this.emailForm.info = this.content;
          sendMail(this.emailForm).then(res => {
            if (res.code === "100") {
              this.showMessage({ msg: "邮件发送成功", type: "success" });
              this.dialogVisible = false;
            } else {
              this.showMessage({ type: "warning", msg: "网络错误请重试！" });
            }
          });
        }
      });
    }
  }
};
</script>
<style lang="scss">
.emailDialog {
  .el-dialog__body {
    border-top: 1px solid #dbd7d7;
    padding: 0;
  }
  .emailForm {
    width: 50%;
    margin: 20px;
  }
}
</style>
