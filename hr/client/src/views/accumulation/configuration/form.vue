<template>
  <van-row class="dm-container">
    <el-dialog v-on:close="onDialogClose" v-loading="loading" width="50%" :fullscreen="false" title="缴费城市维护"
      :visible.sync="visible" :close-on-click-modal="false" class="dc-el-dialog_ElDialog" v-if="visible">
      <el-button class="dc-el-button_Button" @click="addCity" icon="el-icon-plus"
        style="margin-top: 0;margin-bottom: 10px;">新增</el-button>
      <el-row>
        <ux-grid :data="dataList" ref="tsmQualificationTable" height="400px" border
          :edit-config="{ trigger: 'click', mode: 'cell' }" class="dc-ux-grid_EditTable">
          <ux-table-column field="payCityName" title="城市名称" tree-node resizable min-width="120px" align="center"
            header-align="center" show-overflow :edit-render="{ autofocus: '.el-input__inner' }"
            class="dc-ux-table-column_SingleInput">
            <template v-slot:header="{ column }">
              <span style="color: #f56c6c">*</span>
              {{ column.title }}
            </template>
            <template v-slot:edit="{ row, rowIndex }">
              <el-input v-model="row.payCityName" :maxLength="128" placeholder="请输入城市名称" clearable
                autocomplete="new-password" class="dc-el-input_SingleInput"></el-input>
            </template>
            <template v-slot="{ row }">
              {{ row.payCityName }}
            </template>
          </ux-table-column>
          <ux-table-column field="socialAvgAmount" title="社会平均工资" tree-node resizable min-width="120px" align="center"
            header-align="center" show-overflow :edit-render="{ autofocus: '.el-input__inner' }"
            class="dc-ux-table-column_SingleInput">
            <template v-slot:header="{ column }">
              <span style="color: #f56c6c">*</span>
              {{ column.title }}
            </template>
            <template v-slot:edit="{ row, rowIndex }">
              <number-input v-model="row.socialAvgAmount" :precision="2" :min="0" placeholder="请输入社会平均工资" clearable
                autocomplete="new-password" class="dc-el-input_SingleInput"></number-input>
            </template>
            <template v-slot="{ row }">
              {{ row.socialAvgAmount | getFloat }}
            </template>
          </ux-table-column>

          <ux-table-column title="操作" tree-node resizable width="120px" min-width="80px" fixed="right" align="center"
            header-align="center" class="dc-ux-table-column_ElTableOptColumn">
            <template v-slot:header="scope">
              <span>操作</span>
            </template>
            <template slot-scope="scope">
              <OperationIcon v-on:click="
                saveData(scope)
                " type="primary" content="保存" placement="top" icon-name="el-icon-folder-checked"
                class="dc-OperationIcon_IconButton"></OperationIcon>
              <OperationIcon v-on:click="
                onDeleteChild(dataList, scope)
                " type="danger" content="删除" placement="top" icon-name="el-icon-delete"
                class="dc-OperationIcon_IconButton"></OperationIcon>
            </template>
          </ux-table-column>
        </ux-grid>
        <el-pagination :total="tableDataTotal" :page-size="search.limit" background :current-page="tableDataPage"
          :page-sizes="[5, 10, 20, 30, 40, 50, 100]" layout="total, sizes, prev, pager, next, jumper"
          v-on:size-change="onSizeChange" v-on:current-change="onCurrentChange"
          class="dc-el-pagination_ElPagination"></el-pagination>
      </el-row>
      <span slot="footer" class="dialog-footer">
        <el-button v-on:click="onDialogClose">返回</el-button>
      </span>
    </el-dialog>
  </van-row>
</template>
<script>
import {
  validatenull
} from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */

/** 根据用户界面配置import组件 结束 */

import BaseUI from '@/views/components/baseUI'
import OperationIcon from '@/components/OperationIcon'
import { savePayCity, listPayCityPage, deletePayCity } from "@/api/socialSecurityFund/payCity";
export default {
  extends: BaseUI,
  name: 'configuration-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */
    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      dataList: [],
      // 对话框属性变量
      visible: false,
      // 分页属性
      tableDataPage: 1,
      tableDataTotal: 0,
      search: {
        // 查询条件   业务表设置的筛选条件
        params: [],
        offset: 0,
        limit: 10,
        columnName: '', // 排序字段名
        order: '' // 排序
      },
      copyList: []
    }
  },
  filters: {
    getFloat(val) {
      if (val) {
        return Number(val).toFixed(2)
      }
      if (val === 0) {
        return '0.00'
      }
      return null
    }
  },
  methods: {
    onDialogClose() {
      this.visible = false
    },
    onSizeChange(val) {
      this.tableDataPage = 1
      this.search.limit = val;
      this.search.offset = (this.tableDataPage - 1) * val
      this.getList()
    },
    onCurrentChange(val) {
      this.search.offset = (val - 1) * this.search.limit
      this.tableDataPage = val
      this.getList()
    },
    //新增缴费城市
    addCity() {
      this.dataList.push({
        payCityName: "",
        socialAvgAmount: "",
      });
    },
    onDeleteChild(tableData, scope) {
      if (scope.row.id) {
        this.$confirm("确定要删除该条数据吗？", "确认", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          let obj = JSON.parse(JSON.stringify(scope.row))
          delete obj._XID
          delete obj.companyId
          deletePayCity(obj).then(res => {
            if (res.code == 100) {
              this.visible = false
              this.showMessage({
                type: 'success',
                msg: '删除数据成功'
              })
              this.$emit('save-finished')
              this.$emit('editCity')
            } else {
              this.showMessage(res)
            }
          }).catch(error => {
            this.outputError(error)
          })
        })
      } else {
        tableData.splice(scope.rowIndex, 1);
      }
    },
    saveData(scope) {
      let obj = JSON.parse(JSON.stringify(scope.row))
      if (!obj.payCityName || !obj.socialAvgAmount) {
        this.$message.warning('请填写完整的数据')
      } else {
        let canValid = true
        this.copyList.forEach(item => {
          if (item.payCityName === scope.row.payCityName) {
            canValid = false
          }
        })
        if (!canValid && !scope.row['id']) {
          this.$message.warning('该缴费城市已经存在')
        } else {
          delete obj._XID
          savePayCity(obj).then(res => {
            if (res.code == 100) {
              this.visible = false
              this.showMessage({
                type: 'success',
                msg: '保存成功'
              })
              this.$emit('save-finished')
              this.$emit('editCity')
            } else {
              this.showMessage(res)
            }
          }).catch(error => {
            this.outputError(error)
          })
        }
      }
    },
    getCity() {
      listPayCityPage(this.search).then(responseData => {
        if (responseData.code == 100) {
          this.tableDataTotal = responseData.data.total
          this.dataList = responseData.data.rows ? responseData.data.rows : []
          this.copyList = JSON.parse(JSON.stringify(this.dataList))
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    }
  },
  mounted: function () {
    this.$nextTick(() => {
      this.$on('openAddDialog', function () {
        this.visible = true
        this.getCity()
      })
    })
  }
}
</script>
