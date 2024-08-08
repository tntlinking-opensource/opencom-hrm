<template>
  <div class="recruitNum">
    <div>
      <span class="recruit-title">招聘需求：</span
      ><span class="demand-num">{{ demandNum }}人</span>
    </div>
    <div>
      <span class="recruit-title">已招聘人数：</span
      ><span class="alRecruit-num">{{ alRecruitNum }}人</span>
    </div>
    <div>
      <span class="recruit-title">待招聘人数：</span
      ><span class="awaitRecruit-num">{{ awaitRecruitNum }}人</span>
    </div>
  </div>
</template>
<script>
import { getNumberOfPeople } from "@/api/recruit/tsmRecruit";
export default {
  data() {
    return {
      demandNum: 0,
      alRecruitNum: 0,
      awaitRecruitNum: 0
    };
  },
  mounted() {
    this.getNumber();
  },
  methods: {
    getNumber() {
      getNumberOfPeople('8044').then(res => {
        if (res.code === "100") {
          this.demandNum = res.data.recruitNeeds;
          this.alRecruitNum = res.data.recruited;
          this.awaitRecruitNum = res.data.waitRecruit;
        }
      });
    }
  }
};
</script>
<style  lang='scss'>
.recruitNum {
  height: 40px;
  border: 1px solid #d7d9e3;
  display: flex;
  align-items: center;
  line-height: 40px;
  .recruit-title {
    margin-left: 40px;
    font-size: 14px;
  }
  .demand-num,
  .alRecruit-num,
  .awaitRecruit-num {
    font-size: 20px;
    font-weight: bolder;
  }
  .demand-num {
    color: #ffa500;
  }
  .alRecruit-num {
    color: #009688;
  }
  .awaitRecruit-num {
    color: #018cb7;
  }
}
</style>