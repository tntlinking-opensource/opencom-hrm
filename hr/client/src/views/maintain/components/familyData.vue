<template>
  <div class="familyData">
    <el-divider class="dc-el-divider_ElDivider">家庭情况</el-divider>
    <div style="margin-bottom:10px">
      <el-button icon="el-icon-plus" @click="addFamily" v-if="!familyDisabled"
        >新增</el-button
      >
      <el-button
        icon="el-icon-folder-checked"
        v-if="!familyDisabled"
        @click="saveTable(familyForm)"
        >保存</el-button
      >
    </div>

    <el-form
      ref="familyForm"
      :model="familyForm"
      label-width="110px"
      class="dc-el-form_ElEditForm"
    >
      <ux-grid
        :data="familyForm.dataList"
        ref="tsmFamilyMemberInfoTable"
        height="200px"
        border
        :edit-config="{ trigger: 'click', mode: 'cell' }"
        class="dc-ux-grid_EditTable"
      >
        <ux-table-column
          field="familyName"
          title="亲属姓名"
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
              :prop="'dataList.' + rowIndex + '.familyName'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '亲属姓名不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.familyName"
                :maxLength="255"
                placeholder="请输入亲属姓名"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.familyName }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="kinship"
          title="家属关系"
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
              :prop="'dataList.' + rowIndex + '.kinship'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '家属关系不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-select
                v-model="row.kinship"
                :style="{ width: '100%' }"
                placeholder="请输入家属关系"
                value-key="id"
                filterable
                class="dc-el-select_SelectInput"
              >
                <el-option
                  v-for="(item, index) in shipOptions"
                  :key="index"
                  :label="item.name"
                  :value="item"
                ></el-option>
              </el-select>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.kinship.name }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="career"
          title="职业"
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
              :prop="'dataList.' + rowIndex + '.career'"
              label-width="0px"
              :rules="[
                { required: true, message: '职业不能为空', trigger: 'blur' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.career"
                :maxLength="255"
                placeholder="请输入职业"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.career }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="contactAddress"
          title="联系地址"
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
              :prop="'dataList.' + rowIndex + '.contactAddress'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '联系地址不能为空',
                  trigger: 'blur'
                }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.contactAddress"
                :maxLength="255"
                placeholder="请输入联系地址"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.contactAddress }}
          </template>
        </ux-table-column>
        <ux-table-column
          field="contactType"
          title="联系方式"
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
              :prop="'dataList.' + rowIndex + '.contactType'"
              label-width="0px"
              :rules="[
                {
                  required: true,
                  message: '联系方式不能为空',
                  trigger: 'blur'
                },
                { pattern: phoneNumberPattern, message: '请输入正确的联系方式' }
              ]"
              class="dc-el-form-item_SingleInput"
            >
              <el-input
                v-model="row.contactType"
                :maxLength="255"
                placeholder="请输入联系方式"
                clearable
                autocomplete="new-password"
                class="dc-el-input_SingleInput"
              ></el-input>
            </el-form-item>
          </template>
          <template v-slot="{ row }">
            {{ row.contactType }}
          </template>
        </ux-table-column>
        <ux-table-column
          title="操作"
          tree-node
          resizable
          v-if="!familyDisabled"
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
              v-on:click="onDeleteChild(familyForm.dataList, scope)"
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
import {
  getFamilyMemberInfo,
  saveTsmFamilyMemberInfo
} from "@/api/induction/EmployeeInfo.js";
import OperationIcon from "@/components/OperationIcon";
import { deleteTsmFamilyMemberInfo } from "@/api/induction/tsmFamilyMemberInfo.js";
import { getDictTypeById } from "@/api/sys/dictType";
import baseUI from "@/views/components/baseUI";
export default {
  extends: baseUI,
  props: ["rowData", "familyDisabled"],
  components: {
    OperationIcon
  },
  data() {
    return {
      //家庭情况表格
      familyForm: { dataList: [] },
      shipOptions: [],
      phoneNumberPattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
    };
  },
  mounted() {
    this.initOptions();
  },
  methods: {
    //获取表格表单数据
    getFormData() {
      getFamilyMemberInfo(this.rowData.empolyno).then(res => {
        if (res.code === "100") {
          this.familyForm.dataList = res.data;
        }
      });
    },

    getShipOptions() {
      getDictTypeById("1529905266810732545").then(res => {
        this.shipOptions = res.data.dictItemList;
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
        let phoneValid = true;
        formData.dataList.forEach(item => {
          if (
            !item.familyName ||
            !item.kinship ||
            !item.career ||
            !item.contactAddress ||
            !item.contactType
          ) {
            isValid = false;
          }
          if (!this.phoneNumberPattern.test(item.contactType)) {
            phoneValid = false;
          }
        });
        if (!phoneValid) {
          this.showMessage({
            type: "warning",
            msg: "请输入正确的联系方式！"
          });
        } else {
          if (isValid) {
            saveTsmFamilyMemberInfo(
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
        }
      } else {
        this.showMessage({ type: "warning", msg: "请填写相关数据！" });
      }
    },
    //新增家庭成员
    addFamily() {
      this.familyForm.dataList.push({
        familyName: "",
        kinship: "",
        career: "",
        contactAddress: "",
        contactType: ""
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
            deleteTsmFamilyMemberInfo(scope.row).then(res => {
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

    initOptions() {
      this.getFormData();
      this.getShipOptions();
    }
  }
};
</script>
<style lang="scss" scoped>
.familyData {
  width: 100%;
  height: 100%;
  background: #ffffff;
  margin-left: 10px;
  padding: 10px;
  overflow: auto;
}
</style>