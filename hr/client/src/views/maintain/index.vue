<template>
  <div class="maintain">
    <dialogHeader ref="dialogHeader" :rowData="rowData"></dialogHeader>
    <div class="maintainDialog_tab">
      <el-tabs tab-position="right" class="tab" v-model="activeName">
        <el-tab-pane label="基本信息" name="base"></el-tab-pane>
        <el-tab-pane label="岗位信息" name="position"></el-tab-pane>
        <el-tab-pane label="简历信息" name="resume"></el-tab-pane>
        <el-tab-pane label="家庭情况" name="family"></el-tab-pane>
        <el-tab-pane label="联系信息" name="relation"></el-tab-pane>
        <el-tab-pane label="合同信息" name="contract"></el-tab-pane>
        <el-tab-pane label="卡片信息" name="card"></el-tab-pane>
        <el-tab-pane label="资产信息" name="property"></el-tab-pane>
        <el-tab-pane label="归档附件" name="attachment"></el-tab-pane>
      </el-tabs>
      <baseData
        ref="baseData"
        v-if="activeName === 'base'"
        :rowData="rowData"
        :baseDisabled="
          (this.limitOptions.length > 0
            ? this.limitOptions.find(item => item.attribute2 === '001').used ===
              '0'
            : false) && viewType === 'personal'
        "
      ></baseData>
      <positionData
        ref="positionData"
        :rowData="rowData"
        :positionDisabled="
          (this.limitOptions.length > 0
            ? this.limitOptions.find(item => item.attribute2 === '002').used ===
              '0'
            : false) && viewType === 'personal'
        "
        v-if="activeName === 'position'"
      ></positionData>
      <resumeData
        ref="resumeData"
        :resumeDisabled="
          (this.limitOptions.length > 0
            ? this.limitOptions.find(item => item.attribute2 === '003').used ===
              '0'
            : false) && viewType === 'personal'
        "
        :rowData="rowData"
        v-if="activeName === 'resume'"
      ></resumeData>
      <familyData
        :rowData="rowData"
        :familyDisabled="
          (this.limitOptions.length > 0
            ? this.limitOptions.find(item => item.attribute2 === '004').used ===
              '0'
            : false) && viewType === 'personal'
        "
        ref="familyData"
        v-if="activeName === 'family'"
      ></familyData>
      <relationData
        :rowData="rowData"
        ref="relationData"
        :relationDisabled="
          (this.limitOptions.length > 0
            ? this.limitOptions.find(item => item.attribute2 === '005').used ===
              '0'
            : false) && viewType === 'personal'
        "
        v-if="activeName === 'relation'"
      ></relationData>
      <contractData
        ref="contractData"
        :rowData="rowData"
        :contractDisabled="
          (this.limitOptions.length > 0
            ? this.limitOptions.find(item => item.attribute2 === '006').used ===
              '0'
            : false) && viewType === 'personal'
        "
        v-if="activeName === 'contract'"
      ></contractData>
      <cardData
        ref="cardData"
        v-if="activeName === 'card'"
        :cardDisabled="
          (this.limitOptions.length > 0
            ? this.limitOptions.find(item => item.attribute2 === '007').used ===
              '0'
            : false) && viewType === 'personal'
        "
        :rowData="rowData"
      ></cardData>
      <propertyData
        ref="propertyData"
        :rowData="rowData"
        v-if="activeName === 'property'"
        :propertyDisabled="
          (this.limitOptions.length > 0
            ? this.limitOptions.find(item => item.attribute2 === '008').used ===
              '0'
            : false) && viewType === 'personal'
        "
      ></propertyData>
      <attachmentData
        ref="attachmentData"
        :rowData="rowData"
        v-if="activeName === 'attachment'"
        :attachmentDisabled="
          (this.limitOptions.length > 0
            ? this.limitOptions.find(item => item.attribute2 === '009').used ===
              '0'
            : false) && viewType === 'personal'
        "
      ></attachmentData>
    </div>
  </div>
</template>
<script>
import dialogHeader from "./components/dialogHeader";
import baseData from "./components/baseData";
import positionData from "./components/positionData";
import resumeData from "./components/resumeData";
import familyData from "./components/familyData";
import relationData from "./components/relationData";
import contractData from "./components/contractData";
import cardData from "./components/cardData";
import propertyData from "./components/propertyData";
import attachmentData from "./components/attachmentData";
import { listUserPage } from "@/api/admin/user";
import { listUserPropDetailsAll } from "@/api/basic/userPropDetails";
export default {
  props: {
    viewType: {
      type: String,
      default: "personal"
    },
    row: {
      type: Object,
      default: {}
    }
  },
  data() {
    return {
      rowData: {},
      activeName: "base",
      limitOptions: []
    };
  },
  components: {
    dialogHeader,
    baseData,
    positionData,
    resumeData,
    familyData,
    relationData,
    contractData,
    cardData,
    propertyData,
    attachmentData
  },
  mounted() {
    this.init();
    if (this.viewType === "hr") {
      this.rowData = this.row;
    }
    if (this.viewType === "personal") {
      this.getUser();
    }
  },
  methods: {
    init() {
      this.getLimit();
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
          this.rowData = res.data.rows.find(item => item.id == obj.id);
        }
      });
    },
    //获取权限列表
    getLimit() {
      let search = {
        columnName: "",
        order: "",
        params: [
          {
            columnName: "user_prop_list_id",
            queryType: "=",
            value: "4"
          }
        ]
      };
      listUserPropDetailsAll(search).then(res => {
        if (res.code === "100") {
          this.limitOptions = res.data;
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.maintain {
  width: 100%;
  height: 100%;
  padding: 10px;
  background: #f2f6f6;
  .maintainDialog_tab {
    height: calc(100% - 120px);
    margin-top: 15px;
    display: flex;
    .tab {
      height: 100%;
      background: #f2f6f6;
      width: 150px;
    }
  }
}
</style>
<style lang="scss">
.maintain {
  .tab {
    .el-tabs__header {
      width: 100%;
      margin: 0;
      text-align: center;
      font-size: 12px;
    }
    .el-tabs__item {
      font-size: 12px;
      &:hover {
        background: #c9ebe8;
        color: #000000;
      }
      &.is-active {
        background: #009688;
        color: #ffffff;
      }
    }
  }
}
</style>