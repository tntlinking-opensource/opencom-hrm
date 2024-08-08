<template>
  <div class="edit_container">
    <div class="header">
      <div class="header_image">
        <img src="~@/assets/images/head_male.png" />
      </div>

      <span class="header_name"
        >早上好，{{ userName }}，今天是{{ todayTime }}，{{ todayWeek }}
        <br />
        祝你开心每一天!</span
      >

      <span class="header_time"
        >今天是您毕业工作的第
        <span class="header_time_number">{{ userForm.allWorkDays }}</span> 天
        <br />
        今天是您到公司工作的第
        <span class="header_time_number">{{ userForm.companyDays }}</span>
        天</span
      >
    </div>
    <div class="card">
      <div class="card_column">
        <div class="icon yg">
          <i class="el-icon-user card_column_icon"></i>
        </div>
        <div class="card_column_title">
          <span class="card_column_name">员工总数</span><br />
          <span class="card_column_number"
            >{{ userForm.peopleTotalNum }}人</span
          >
        </div>
      </div>
      <div class="card_column">
        <div class="icon zp">
          <i class="el-icon-notebook-2 card_column_icon"></i>
        </div>
        <div class="card_column_title">
          <span class="card_column_name">招聘办理</span><br />
          <span class="card_column_number"
            >{{ userForm.recruitProcessingNum }}人</span
          >
        </div>
      </div>
      <div class="card_column">
        <div class="icon rz">
          <i class="el-icon-document-checked card_column_icon"></i>
        </div>
        <div class="card_column_title">
          <span class="card_column_name">入职办理</span><br />
          <span class="card_column_number"
            >{{ userForm.inductionProcessingNum }}人</span
          >
        </div>
      </div>
      <div class="card_column">
        <div class="icon lz">
          <i class="el-icon-document-delete card_column_icon"></i>
        </div>
        <div class="card_column_title">
          <span class="card_column_name">离职办理</span><br />
          <span class="card_column_number"
            >{{ userForm.departProcessingNum }}人</span
          >
        </div>
      </div>
      <div class="card_column">
        <div class="icon xc">
          <i class="el-icon-money card_column_icon"></i>
        </div>
        <div class="card_column_title">
          <span class="card_column_name">上月薪酬支出</span><br />
          <span class="card_column_number"
            >{{ userForm.lastMonthSalaryExpenses }}元</span
          >
        </div>
      </div>
    </div>
    <div class="entry">
      <div class="entry_card">
        <div class="entry_card_title">我的待办</div>
        <div class="entry_card_wait" style="height: 150px;">
          <div @click="goingWait">
            <div class="entry_card_number a" style="color:#d99283">
              {{ userForm.recruitApproval }}
            </div>
            <div class="entry_card_name">招聘申请</div>
          </div>
          <div @click="goingWait">
            <div class="entry_card_number b" style="color:#018cb7;">
              {{ userForm.inductionApproval }}
            </div>
            <div class="entry_card_name">入职审批</div>
          </div>
          <div @click="goingWait">
            <div class="entry_card_number c" style="color:#6fb679">
              {{ userForm.departApproval }}
            </div>
            <div class="entry_card_name">离职审批</div>
          </div>
          <div @click="goingWait">
            <div class="entry_card_number d" style="color:#dea675">
              {{ userForm.salaryApproval }}
            </div>
            <div class="entry_card_name">薪资审批</div>
          </div>
        </div>
      </div>
      <div class="entry_card">
        <div class="entry_card_title">快捷入口</div>
        <div class="entry_card_wait" style="height: 150px;">
          <div class="entry_card_div a" @click="code">
            <i class="el-icon-s-grid entry_card_icon"></i>
            <span class="entry_card_div_name">入职二维码</span>
          </div>
          <div class="entry_card_div b" @click="recruit">
            <i class="el-icon-s-finance entry_card_icon"></i>
            <span class="entry_card_div_name">招聘申请</span>
          </div>
          <div class="entry_card_div c" @click="entry">
            <i class="el-icon-date entry_card_icon"></i>
            <span class="entry_card_div_name">入职申请</span>
          </div>
        </div>
      </div>
      <div class="entry_card">
        <div class="entry_card_title">续签和转正提醒</div>
        <div class="entry_card_remind">
          <span v-if="employmentContractList.length <= 0 && renewalOfContractList.length <= 0">
            暂无续签和转正提醒
          </span>
          <div v-for="(employ, index) in employmentContractList" :key="index">
<!--            人事部张三(工号:171239)将于2023-6-18-->
            {{employ.departmentName}}
            {{employ.name}}
            （工号: {{employ.employeeNo}}）将于{{employ.daysApart}}
            <span class="entry_card_remind_ope">转正</span>
          </div>
          <div v-for="(renewal, index) in renewalOfContractList" :key="index">
            {{renewal.departmentName}}
            {{renewal.name}}
            （工号: {{renewal.employeeNo}}）将于{{renewal.daysApart}}
            <span class="entry_card_remind_ope">续签合约</span>
          </div>
        </div>
      </div>
      <div class="entry_card">
        <div class="entry_card_title">应用小工具</div>
        <div class="entry_card_tool">
          <div @click="openBaidu" >
            <img class="entry_card_tool_image" src="@/assets/images/baidu.png" alt="">
            <span class="entry_card_tool_name">百度</span>
          </div>
          <div @click="openCtrip">
            <img class="entry_card_tool_image" src="@/assets/images/ctrip.png" alt="">
            <span class="entry_card_tool_name">携程</span>
          </div>
          <div @click="openTax">
            <img class="entry_card_tool_image" src="@/assets/images/tax.png" alt="">
            <span class="entry_card_tool_name">个税计算器</span>
          </div>
          <div @click="openMortgage">
            <img class="entry_card_tool_image" src="@/assets/images/mortgage.png" alt="">
            <span class="entry_card_tool_name">房贷计算器</span>
          </div>
        </div>
      </div>
    </div>
    <codeDialog ref="codeDialog"></codeDialog>
  </div>
</template>

<script>
import codeDialog from "./../components/codeDialog";
import { listUserPage } from "@/api/admin/user";
import { getHrmHomeByEmployeeNo, getContractReminder } from "@/api/hrm/hrmHome";
import { validatenull } from "@/utils/validate";

export default {
  data() {
    return {
      userName: currentUser.name,
      userData: { name: "" },
      userForm: {
        allWorkDays: "0",
        companyDays: "0",
        departApproval: "0",
        departProcessingNum: "0",
        inductionApproval: "0",
        inductionProcessingNum: "0",
        lastMonthSalaryExpenses: 0,
        peopleTotalNum: "0",
        recruitApproval: "0",
        recruitProcessingNum: "0",
        salaryApproval: "0"
      },
      todayTime: "",
      todayWeek: "",
      renewalOfContractList: [], // 续签
      employmentContractList: [] // 转正
    };
  },
  created() {},
  mounted() {
    this.getUser();
    this.changeTime();
  },
  computed: {},
  methods: {
    //获取今天时间
    changeTime() {
      let d = new Date();
      var Month = d.getMonth() + 1;
      var Day = d.getDate();
      var Year = d.getFullYear();
      var week = d.getDay();
      if (week === 0) {
        this.todayWeek = "星期日";
      } else if (week === 1) {
        this.todayWeek = "星期一";
      } else if (week === 2) {
        this.todayWeek = "星期二";
      } else if (week === 3) {
        this.todayWeek = "星期三";
      } else if (week === 4) {
        this.todayWeek = "星期四";
      } else if (week === 5) {
        this.todayWeek = "星期五";
      } else if (week === 6) {
        this.todayWeek = "星期六";
      }

      this.todayTime = `${Year}年${Month}月${Day}日`;
    },
    //二维码查看
    code() {
      this.$refs.codeDialog.init();
    },
    recruit() {
      this.$router.push({ path: "/tsmRecruit" });
    },
    entry() {
      this.$router.push({ path: "/inductionApp" });
    },
    goingWait() {
      this.$router.push({ path: "/mytask" });
    },
    getUser() {
      let search = {
        columnName: "",
        limit: 10,
        offset: 0,
        order: "",
        params: [
          {
            columnName: "company_id",
            queryType: "=",
            value: "1537097808019062831"
          },
          { columnName: "department_id", queryType: "=", value: null },
          { columnName: "name", queryType: "like", value: null }
        ]
      };
      listUserPage(search).then(res => {
        if (res.code == 100) {
          let obj = JSON.parse(sessionStorage.getItem("currentUser"));
          this.userData = res.data.rows.find(item => item.id == obj.id);
          if (!validatenull(this.userData)) {
            this.getFormData()
          }
          this.contractReminder()
        }
      });
    },
    getFormData() {
      getHrmHomeByEmployeeNo(this.userData.empolyno).then(res => {
        if (res.code === "100") {
          this.userForm = res.data;
        }
      });
    },
    contractReminder() {
      getContractReminder().then(res => {
        if (res.code === "100") {
          this.renewalOfContractList = !validatenull(res.data.renewalOfContract) ? res.data.renewalOfContract : []
          this.employmentContractList = !validatenull(res.data.employmentContract) ? res.data.employmentContract : []
        }
      });
    },
    openBaidu() {
      window.open('https://www.baidu.com')
    },
    openCtrip() {
      window.open('https://www.ctrip.com')
    },
    openTax() {
      window.open('https://shui.xuenb.com')
    },
    openMortgage() {
      window.open('https://www.fangdaijisuanqi.com')
    }
  },
  components: { codeDialog }
};
</script>

<style lang="scss" scoped>
.edit_container {
  /*background-color: #ebeef5;*/
  /*background-image: radial-gradient(#ccc 4%, transparent 0);*/
  background-size: 45px 45px;
  .header {
    display: flex;
    align-items: center;
    width: 100%;
    /*border: 1px solid #cacaca;*/
    height: 120px;
    margin-bottom: 20px;
    background: #ffffff;
    position: relative;
    border-radius: 10px;
    box-shadow: 0 0 10px rgba(0,0,0,.02);
    .header_image {
      width: 50px;
      height: 50px;
      margin: 0 20px;
      img {
        width: 100%;
        height: 100%;
        border-radius: 50%;
      }
    }
    .header_name {
      font-weight: bold;
    }
    .header_time {
      position: absolute;
      right: 100px;
      color: #bababa;
      font-size: 14px;
      line-height: 30px;
      .header_time_number {
        color: #eb9f7c;
        font-size: 18px;
      }
    }
  }
  .card {
    display: flex;
    align-items: center;
    justify-content: space-between;
    /*background: #ffffff;*/
    /*height: 150px;*/
    /*border: 1px solid #cacaca;*/
    .card_column {
      width: 19%;
      height: 100px;
      display: flex;
      align-items: center;
      justify-content: center;
      border-radius: 10px;
      background: #fff;
      box-shadow: 0 0 10px rgba(0,0,0,.02);
      .icon {
        width: 60px;
        height: 60px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        &.yg {
          background-color: #fbefed;
        }
        &.zp {
          background-color: #e7f5f9;
        }
        &.rz {
          background-color: #e3f1e8;
        }
        &.lz {
          background-color: #fdf4eb;
        }
        &.xc {
          background-color: #eff0fe;
        }
        .el-icon-user {
          color: #d99283;
        }
        .el-icon-notebook-2 {
          color: #018cb7;
        }
        .el-icon-document-checked {
          color: #6fb679;
        }
        .el-icon-document-delete {
          color: #dea675;
        }
        .el-icon-money {
          color: #9398e0;
        }
        margin: 0 20px 0 0;
      }
      .card_column_icon {
        font-size: 25px;
        color: #018cb7;
        /*margin: 0 30px 0 20px;*/
      }
      .card_column_title {
        text-align: center;
        .card_column_name {
          font-size: 14px;
          color: #808080;
        }
        .card_column_number {
          font-size: 18px;
          font-weight: bold;
        }
      }
    }
  }
  .entry {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    margin-top: 20px;
    .entry_card {
      width: 49.5%;
      background: #ffffff;
      margin-bottom: 20px;
      border-radius: 10px;
    }
    .entry_card_title {
      border-bottom: 1px solid #f5f5f5;
      height: 40px;
      line-height: 40px;
      font-size: 14px;
      padding-left: 20px;
      font-weight: bold;
    }
    .entry_card_wait {
      width: 100%;
      display: flex;
      align-items: center;
      justify-content: space-around;
      margin-bottom: 20px;
      .entry_card_number {
        height: 75px;
        width: 75px;
        text-align: center;
        line-height: 75px;
        border-radius: 50%;
        font-size: 28px;
        font-weight: bold;
        cursor: pointer;
        transition: all .3s;
        &:hover {
          font-size: 32px;
        }
        &.a {
          background-color: #fbefed;
        }
        &.b {
          background-color: #e7f5f9;
        }
        &.c {
          background-color: #e3f1e8;
        }
        &.d {
          background-color: #fdf4eb;
        }
        &.e {
          background-color: #eff0fe;
        }
      }
      .entry_card_name {
        text-align: center;
        font-size: 14px;
        margin-top: 10px;
        cursor: pointer;
      }
      .entry_card_div {
        width: 180px;
        height: 90px;
        border: 1px solid #f5f5f5;
        border-radius: 4px;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        .entry_card_icon {
          font-size: 35px;
          margin-right: 10px;
          transition: all .3s;
        }
        .el-icon-s-grid {
          color: #018cb7;
        }
        .el-icon-s-finance {
          color: #6fb679;
        }
        .el-icon-date {
          color: #dea675;
        }
        .entry_card_div_name {
          position: relative;
        }
        &:hover {
          box-shadow: 0 0 4px rgba(0,0,0,.1);
          .entry_card_icon {
            transform: scale(1.1);
          }
        }
      }
    }
    .entry_card_remind {
      font-size: 12px;
      height: 150px;
      line-height: 25px;
      padding: 20px;
      overflow: auto;
      .entry_card_remind_ope {
        color: #ff5900;
      }
      .entry_card_remind_notice {
        cursor: pointer;
      }
    }
    .entry_card_tool {
      height: 150px;
      display: flex;
      align-items: center;
      > div {
        width: 25%;
        display: flex;
        align-items: center;
        justify-content: space-around;
        flex-direction: column;
        cursor: pointer;
        .entry_card_tool_image {
          width: 50px;
          height: 50px;
          display: inline-block;
          transition: all .5s;
        }
        .entry_card_tool_name{
        }
        &:hover {
          .entry_card_tool_image {
            transform: scale(1.1);
          }
        }
      }
    }
  }
}
</style>
