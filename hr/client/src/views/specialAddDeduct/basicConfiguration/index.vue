<template>
  <div class="basicConfiguration">
    <div class="basicConfiguration_body">
      <header> <i class="el-icon-s-data basicConfiguration_body_icon"></i>
        <h3>公积金超额上限设置</h3>
      </header>
      <div class="basicConfiguration_body_numDiv" style="margin-left: 52px;"><span style="color: red;">*</span>
        <span style="font-size: 14px;width: 70px;line-height: 25px;">上限金额：</span>
        <el-input v-model="accumulationFund.limitAmount" style="width: calc(100% - 150px);"></el-input>
      </div>
      <div style="display: flex;margin-left: 30px;"><span style="font-size: 14px;width: 100px;">超额合并计税：</span>
        <el-radio-group v-model="accumulationFund.isMergeTax"> <el-radio label="1">是</el-radio>
          <el-radio label="0">否</el-radio> </el-radio-group>
      </div>
      <el-button type="primary" class="basicConfiguration_body_saveBotton"
        @click="saveData('accumulationFund')">保存</el-button>
    </div>
    <div class="basicConfiguration_body">
      <header> <i class="el-icon-s-marketing basicConfiguration_body_icon"></i>
        <h3>企业年金超额上限设置</h3>
      </header>
      <div class="basicConfiguration_body_numDiv" style="margin-left: 52px;"><span style="color: red;">*</span>
        <span style="font-size: 14px;width: 70px;line-height: 25px;">上限金额：</span>
        <el-input v-model="unitAnnuity.limitAmount" style="width: calc(100% - 150px);"></el-input>
      </div>
      <div style="display: flex;margin-left: 30px;"><span style="font-size: 14px;width: 100px;">超额合并计税：</span>
        <el-radio-group v-model="unitAnnuity.isMergeTax"> <el-radio label="1">是</el-radio>
          <el-radio label="0">否</el-radio> </el-radio-group>
      </div>
      <el-button type="primary" class="basicConfiguration_body_saveBotton" @click="saveData('unitAnnuity')">保存</el-button>
    </div>
  </div>
</template>
<script>
import { saveTaxDeclareConfig, getTaxDeclareConfig } from '@/api/specialAddDeduct/specialAddDeduct'
export default {
  data() {
    return {
      accumulationFund: {
        "code": "accumulation_fund",
        "name": "公积金超额上限设置",
        "limitAmount": "",
        "isMergeTax": "0",
        id: ''
      },
      unitAnnuity: {
        "code": "unit_annuity",
        "name": "企业年金超额上限设置",
        "limitAmount": "",
        "isMergeTax": "0",
        id: ''
      }
    };
  },
  computed: {},
  watch: {},
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      getTaxDeclareConfig().then(res => {
        if (res.code === '100' && res.data && res.data.length > 0) {
          res.data.forEach(item => {
            if (item.code === 'accumulation_fund') {
              this.accumulationFund = item
            }
            if (item.code === 'unit_annuity') {
              this.unitAnnuity = item
            }
          })
        }
      })
    },
    saveData(status) {
      let params
      if (status === 'accumulationFund') {
        params = this.accumulationFund
      } else {
        params = this.unitAnnuity
      }
      saveTaxDeclareConfig(params).then(res => {
        if (res.code === '100') {
          this.$message.success('保存数据成功！')
        } else {
          this.$message.error(res.msg)
        }

      })
    }
  }
};
</script>
<style lang="scss" scoped>
.basicConfiguration {
  display: flex;
  justify-content: space-around;
  padding-top: 20px;

  .basicConfiguration_body {
    width: 48%;
    height: 300px;
    border: 2px solid #d9d9d9;
    position: relative;

    header {
      display: flex;

      .basicConfiguration_body_icon {
        color: #676afb;
        font-size: 50px;
        margin: 30px;

      }

      h3 {
        top: 15px;
        position: relative;
      }
    }

    .basicConfiguration_body_numDiv {
      display: flex;
      margin-bottom: 20px;
    }

    .basicConfiguration_body_saveBotton {
      position: absolute;
      right: 40px;
      bottom: 30px;
    }

    .el-radio {
      line-height: 23px;
    }
  }
}
</style>
