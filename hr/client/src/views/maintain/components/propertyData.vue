<template>
  <div class="propertyData">
    <el-divider class="dc-el-divider_ElDivider">资产信息</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addFamily" v-if="!propertyDisabled"
        >新增</el-button
      >
      <el-button
        icon="el-icon-folder-checked"
        @click="saveTable(propertyForm)"
        v-if="!propertyDisabled"
        >保存</el-button
      >
    </div>

    <el-form
      ref="propertyForm"
      :model="propertyForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="propertyForm.dataList"
        ref="tsmFamilyMemberInfoTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="assetName"
          title="资产名称"
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
              :prop="'dataList.' + rowIndex + '.assetName'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '资产名称不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.assetName"
                :maxLength="255"
                placeholder="请输入资产名称"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.assetName }}
          </template>
        </ux-table-column>

        <ux-table-column
          field="employers"
          title="资产单位"
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
              :prop="'dataList.' + rowIndex + '.employers'"
              label-width="0px"
              :rules="[
                { required: true, message: '资产单位不能为空', trigger: 'blur' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.employers"
                :maxLength="255"
                placeholder="请输入资产单位"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.employers }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="assetNumber"
          title="资产编号"
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
              :prop="'dataList.' + rowIndex + '.assetNumber'"
              label-width="0px"
              :rules="[
                { required: true, message: '资产编号不能为空', trigger: 'blur' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.assetNumber"
                :maxLength="255"
                placeholder="请输入资产编号"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.assetNumber }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="collectionTime"
          title="领用时间"
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
              :prop="'dataList.' + rowIndex + '.collectionTime'"
              label-width="0px"
              :rules="[
                { required: true, message: '领用时间不能为空', trigger: 'blur' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.collectionTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.collectionTime }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="returnTime"
          title="归还时间"
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
              :prop="'dataList.' + rowIndex + '.returnTime'"
              label-width="0px"
              :rules="[
                { required: true, message: '归还时间不能为空', trigger: 'blur' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-date-picker
                v-model="row.returnTime"
                :style="{ width: '100%' }"
                placeholder="请选择"
                type="date"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                class="dc-el-date-picker_PickerTimeDate"
              ></el-date-picker>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.returnTime }}
          </template>
        </ux-table-column>
        <ux-table-column
          title="操作"
          tree-node
          v-if="!propertyDisabled"
          resizable
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
              v-on:click="onDeleteChild(propertyForm.dataList, scope)"
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
  getAssetInformation,
  saveTsmAssetInformation,
  deleteAssetInformation
} from "@/api/induction/EmployeeInfo.js";
export default {
  props: ["rowData", "propertyDisabled"],
  extends: baseUI,
  components: {
    OperationIcon
  },
  data() {
    return {
      //资产情况表格
      propertyForm: { dataList: [] },
      inductionId: ""
    };
  },
  mounted() {
    this.getFormData();
  },
  methods: {
    //获取表格表单数据
    getFormData() {
      getAssetInformation(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.propertyForm.dataList = res.data;
        }
      });
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
          if (
            !item.assetName ||
            !item.employers ||
            !item.returnTime ||
            !item.collectionTime ||
            !item.assetNumber
          ) {
            isValid = false;
          }
        });
        if (isValid) {
          saveTsmAssetInformation(
            formData.dataList,
            this.rowData.empolyno
          ).then(res => {
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
    },
    //新增资产情况
    addFamily() {
      this.propertyForm.dataList.push({
        assetName: "",
        employers: "",
        assetNumber: "",
        collectionTime: "",
        returnTime: ""
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
            deleteAssetInformation(scope.row).then(res => {
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
    }
  }
};
</script>
<style lang="scss" scoped>
.propertyData {
  width: 100%;
  height: 100%;
  background: #ffffff;
  margin-left: 10px;
  padding: 10px;
  overflow: auto;
}
</style>