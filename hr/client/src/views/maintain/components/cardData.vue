<template>
  <div class="cardData">
    <el-divider class="dc-el-divider_ElDivider">卡片信息</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addCard" v-if="!cardDisabled"
        >新增</el-button
      >
      <el-button
        icon="el-icon-folder-checked"
        @click="saveTable(cardForm)"
        v-if="!cardDisabled"
        >保存</el-button
      >
    </div>

    <el-form
      ref="cardForm"
      :model="cardForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="cardForm.dataList"
        ref="tsmFamilyMemberInfoTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="accountNumber"
          title="账号"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.accountNumber'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '账号不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.accountNumber"
                :maxLength="255"
                placeholder="请输入账号"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.accountNumber }}
          </template>
        </ux-table-column>

        <ux-table-column
          field="accountType"
          title="类型"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            <span style="color: #f56c6c">*</span>
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.accountType'"
              label-width="0px"
              :rules="[
                { required: true, message: '类型不能为空', trigger: 'blur' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.accountType"
                :maxLength="255"
                placeholder="请输入类型"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.accountType }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="bankAddress"
          title="开户行"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.bankAddress'"
              label-width="0px"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.bankAddress"
                :maxLength="255"
                placeholder="请输入开户行"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.bankAddress }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="bankCity"
          title="开户城市"
          tree-node
          resizable
          min-width="120px"
          align="center"
          header-align="center"
          show-overflow
          :edit-render="{ autofocus: '.el-input__inner' }"
          class="dc-ux-table-column_SingleInput"
        >
          <template v-slot:header="{ column }">
            {{ column.title }}
          </template>
          <template v-slot:edit="{ row, rowIndex }">
            <el-form-item
              :prop="'dataList.' + rowIndex + '.bankCity'"
              label-width="0px"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.bankCity"
                :maxLength="255"
                placeholder="请输入开户城市"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.bankCity }}
          </template>
        </ux-table-column>
        <ux-table-column
          title="操作"
          tree-node
          resizable
          v-if="!cardDisabled"
          width="80px"
          min-width="80px"
          fixed="right"
          align="center"
          header-align="center"
          class="dc-ux-table-column_ElTableOptColumn"
        >
          <template v-slot:header="scope">
            <span>操作</span>
          </template>
          <template slot-scope="scope">
            <OperationIcon
              v-on:click="onDeleteChild(cardForm.dataList, scope)"
              type="danger"
              content="删除"
              placement="top"
              icon-name="el-icon-delete"
              class="dc-OperationIcon_IconButton"
            ></OperationIcon>
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
  getCard,
  saveTsmCard,
  deleteCard
} from "@/api/induction/EmployeeInfo.js";
export default {
  props: ["rowData", "cardDisabled"],
  extends: baseUI,
  components: {
    OperationIcon
  },
  data() {
    return {
      //家庭情况表格
      cardForm: { dataList: [] },
      inductionId: ""
    };
  },
  mounted() {
    this.getFormData();
  },
  methods: {
    //获取表格表单数据
    getFormData() {
      getCard(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.cardForm.dataList = res.data;
        }
      });
    },
    addCard() {
      this.cardForm.dataList.push({
        bankCity: "",
        accountNumber: "",
        accountType: "",
        bankAddress: ""
      });
    },
    //删除某条数据
    onDeleteChild(tableData, scope) {
      if (scope.row.id) {
        this.$confirm("确定要删除该条数据吗？", "确认", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            deleteCard(scope.row).then(res => {
              if (res.code === "100") {
                this.getFormData();
                this.showMessage({ msg: "删除数据成功", type: "success" });
              } else {
                this.showMessage({ type: "warning", msg: "网络错误请重试" });
              }
            });
          })
          .catch(() => {});
      } else {
        tableData.splice(scope.rowIndex, 1);
      }
    },
    //保存表格
    saveTable(formData) {
      if (formData.dataList.length > 0) {
        formData.dataList.map(item => {
          delete item._XID;
          return item;
        });

        let isValid = true;
        formData.dataList.forEach(item => {
          if (!item.accountNumber || !item.accountType) {
            isValid = false;
          }
        });
        if (isValid) {
          saveTsmCard(formData.dataList, this.rowData.empolyno).then(res => {
            if (res.code === "100") {
              this.getFormData();
              this.showMessage({ msg: "数据保存成功", type: "success" });
            } else {
              this.showMessage({ type: "warning", msg: "网络错误请重试" });
            }
          });
        } else {
          this.showMessage({
            type: "warning",
            msg: "存在未填写的数据,请完善！"
          });
        }
      } else {
        this.showMessage({ type: "warning", msg: "请填写相关数据！" });
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.cardData {
  width: 100%;
  height: 100%;
  background: #ffffff;
  margin-left: 10px;
  padding: 10px;
  overflow: auto;
}
</style>