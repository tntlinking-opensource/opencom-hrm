<template>
  <div class="contractData">
    <el-divider class="dc-el-divider_ElDivider">合同信息</el-divider>

    <el-form
      ref="contractForm"
      :model="contractForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="contractForm.dataList"
        ref="tsmFamilyMemberInfoTable"
        height="200px"
        border
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="number"
          title="合约编号"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot="{ row }">
            {{ row.number }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="theme"
          title="合约主题"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot="{ row }">
            {{ row.theme }}
          </template>
        </ux-table-column>
        <ux-table-column
          field=" contractor"
          title="签约人"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot="{ row }">
            {{ row.contractor }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="department"
          title="签约人所属部门"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot="{ row }">
            {{ row.department }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="status"
          title="合约状态"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot="{ row }">
            {{ row.status }}
          </template>
        </ux-table-column>
      </ux-grid>
    </el-form>
  </div>
</template>
<script>
import OperationIcon from "@/components/OperationIcon";
import baseUI from "@/views/components/baseUI";
import {
  getTsmContractInformation,
  saveTsmContactInformation
} from "@/api/induction/EmployeeInfo.js";
export default {
  props: ["rowData", "contractDisabled"],
  extends: baseUI,
  components: {
    OperationIcon
  },
  data() {
    return {
      //合同信息表格
      contractForm: { dataList: [] }
    };
  },
  mounted() {
    this.getFormData();
  },
  methods: {
    //获取表格表单数据
    getFormData() {
      getTsmContractInformation(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.contractForm.dataList = res.data;
        }
      });
    }
  }
};
</script>
<style lang="scss" scoped>
.contractData {
  width: 100%;
  height: 100%;
  background: #ffffff;
  margin-left: 10px;
  padding: 10px;
  overflow: auto;
}
</style>