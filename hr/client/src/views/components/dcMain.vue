<template>
  <div  style="text-align:left" >
    list窗口的基础组件类，处理列头拖动排序、导出数据的列头数据，以及列表显示个性化配置。
  </div>
</template>
<script>
import Sortable from 'sortablejs/modular/sortable.core.esm.js'
import BaseUI from './baseUI'
import { listDcColumnViewAll, saveDcColumnView, deleteDcColumnView } from '@/api/common/dcColumnView'
import { validatenull, validateURL } from '../../utils/validate'
export default {
  extends: BaseUI,
  name: 'DcMain',
  data() {
    return {
      dcColumnView: null, // 查询列配置对象(后台存储对象)
      tableId: '0',       // 处理的主表id, 在子类中覆写
      initTableColumns: [], // 保存初始化的表格列配置
      tableColumns: [],   // 表格列配置,子列中覆盖值
      version: 0,   // 版本号,子列中覆盖值
      curentRow: {},
      isQueryConditionPanel: false,     // 搜素框是否查询条件面板
      isMoreQueryTag: false,     // 搜素框是否更多查询标签
      queryEditObject: null, // 当前点击的查询标签对象
      moreParm: {   // 更多条件值
          groupOne: [
            {logic: 'AND', column: null, queryType: '', value: null, operations: []},
            {logic: 'AND', column: null, queryType: '', value: null, operations: []},
            {logic: 'AND', column: null, queryType: '', value: null, operations: []}
          ],
          groupTwo: [
            {logic: 'AND', column: null, queryType: '', value: null, operations: []},
            {logic: 'AND', column: null, queryType: '', value: null, operations: []},
            {logic: 'AND', column: null, queryType: '', value: null, operations: []}
          ],
          groupLogic: 'OR'
      }
    }
  },
  methods: {
    // 自定义列
    saveColumn(tableColumns, table) {
      this.$set(tableColumns, 'version', this.version)
      // fullColumn: 全量表头列   tableColumn: 当前渲染中的表头列
      let { fullColumn } = this.$refs[table].getTableColumn()
      // 保存个性化列头排序和列宽
      this.dcColumnView.json = JSON.stringify(tableColumns)
      saveDcColumnView(this.dcColumnView).then(responseData => {
        if(responseData.code == 100) {
          this.dcColumnView.id = responseData.data
          // 加载列
          this.$refs[table].loadColumn(fullColumn)
        }
      })
    },
    // 显示所有列
    showAllColumn(tableColumns, table) {
      this.$set(tableColumns, 'version', this.version)
      // fullColumn: 全量表头列   tableColumn: 当前渲染中的表头列
      let { fullColumn } = this.$refs[table].getTableColumn()
      // 保存个性化列头排序和列宽
      for (const key in tableColumns) {
        if (key != 'version') {
          tableColumns[key].visible = true
        }
      }
      this.dcColumnView.json = JSON.stringify(tableColumns)
      saveDcColumnView(this.dcColumnView).then(responseData => {
        if(responseData.code == 100) {
          this.dcColumnView.id = responseData.data
          // 加载列
          this.$refs[table].loadColumn(fullColumn)
        }
      })
    },
    // 显示默认列
    showDefaultColumn(table) {
      // fullColumn: 全量表头列   tableColumn: 当前渲染中的表头列
      let { fullColumn } = this.$refs[table].getTableColumn()
      // 保存个性化列头排序和列宽
      deleteDcColumnView(this.dcColumnView).then(responseData => {
        if(responseData.code == 100) {
          this.tableColumns = JSON.parse(JSON.stringify(this.initTableColumns))
          this.refreshColumn(this.$refs[table])  // 重新刷新列
        }
      })
    },
    // 调整列宽
    onWidthChange(plxTable) {
      console.log('==============onWidthChange')
      let { fullColumn } = plxTable.getTableColumn()
      this.handleColumns(fullColumn)
    },
    columnDrop (plxTable) {
      this.pesonalColumns(plxTable)

      this.$nextTick(() => {
        // 关于sortable的配置https://www.cnblogs.com/xiangsj/p/6628003.html
        this.sortable = Sortable.create(plxTable.$el.querySelector('.body--wrapper .elx-table--header .elx-header--row'), {
          // 列表项中的拖动控制柄选择器 拖拽区域，默认为 .plx-header--row 的 子元素，
          // 下面（这个意思呢）是排除掉plx-header--column拖拽列中的固定列部分
          handle: '.elx-header--column:not(.col--fixed)',
          ghostClass: 'dragColumnColbg',
          chosenClass: 'dragColumnColbg',
          // 拖拽移动的时候
          onMove: (evt, origEvt) => {
            // 固定列不允许停靠
            if(evt.related._prevClass.indexOf("col--fixed") > -1) {
              return false
            }
          },
          // 拖拽结束
          onEnd: ({ item, newIndex, oldIndex }) => {
            if(newIndex == oldIndex) return
            // fullColumn: 全量表头列   tableColumn: 当前渲染中的表头列
            let { fullColumn, tableColumn } = plxTable.getTableColumn()
            // 转换真实索引
            let oldColumnIndex = plxTable.getColumnIndex(tableColumn[oldIndex])
            let newColumnIndex = plxTable.getColumnIndex(tableColumn[newIndex])
            // 移动到目标列
            let currRow = fullColumn.splice(oldColumnIndex, 1)[0]
            fullColumn.splice(newColumnIndex, 0, currRow)
            // 加载列
            plxTable.loadColumn(fullColumn)


            this.handleColumns(fullColumn)
          }
        })
      })
    },
    /* 导出excel的列表显示列头数组 */
    getHeads(){
      var heads = []
      for (let item in this.tableColumns) {
        if(this.tableColumns[item] && this.tableColumns[item].visible && this.tableColumns[item].field) {
          heads.push(this.tableColumns[item].title)
        }
      }
      return heads
    },
    /* 导出excel的列表显示列数据字段数组 */
    getFilterVal(){
      var filterVal = []
      for (let item in this.tableColumns) {
        if(this.tableColumns[item] && this.tableColumns[item].visible && this.tableColumns[item].field) {
          filterVal.push(this.tableColumns[item].field)
        }
      }
      return filterVal
    },
    onExport(tableData) {
      // 没有数据时，提示
      if(validatenull(tableData)) {
        this.$alert('没有导出数据！', '提示', {
          type: 'info'
        })
        return
      }
      this.loading = true
      import('@/vendor/Export2Excel').then(excel => {
        const data = this.formatJson(this.getFilterVal(), tableData)
        excel.export_json_to_excel({
          header: this.getHeads(),
          data,
          filename: 'export',
          autoWidth: true
        })
        this.loading = false
      }).catch(error => {
        console.log('-----------export excel error-------------')
        console.error(error)
        this.loading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return this.parsValue(v, j)
        }
      }))
    },
    parsValue(val, keys) {
      let entity = val
      let arr = keys.split('.')
      for (var i = 0; i < arr.length; i++) {
        entity = entity[arr[i]]
      }
      return entity;
    },
    handleColumns(fullColumn) {
      fullColumn.forEach((element, index) => {
        if(element.params && element.params.sortId) {
          this.tableColumns[element.params.sortId] = {
            ...this.tableColumns[element.params.sortId],
            visible: element.visible,
            order: index
          }
        }
      })

      // 保存个性化列头排序和列宽
      this.dcColumnView.json = JSON.stringify(this.tableColumns)
      saveDcColumnView(this.dcColumnView).then(responseData => {
        if(responseData.code == 100) {
          this.dcColumnView.id = responseData.data
        }
      })
    },
    pesonalColumns(plxTable) {
      if (this.tableId && this.tableId != '0') {
        this.setLoad()
        let search = {
          params: [
            { columnName: 'user_id', queryType: '=', value: currentUser.id },
            { columnName: 'router_id', queryType: '=', value: this.$route.meta.routerId }
          ],
          columnName: '',       // 排序字段名
          order: ''             // 排序
        }
        listDcColumnViewAll(search).then(responseData => {
          if(responseData.code == 100) {
            if(responseData.data && responseData.data.length > 0) {
              let personal = JSON.parse(responseData.data[0].json)
              // 个性化列的版本号与代码中的版本号一致
              if(personal.version == this.version) {
                this.dcColumnView = responseData.data[0]
                this.tableColumns = personal
                this.refreshColumn(plxTable)  // 重新刷新列
              } else {
                deleteDcColumnView(responseData.data[0])
              }
            } else {
              this.dcColumnView = { dcTable: {id: this.tableId}, user: {id: currentUser.id}, router: {id: this.$route.meta.routerId}, json: "[]"}
            }
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        }).catch(error => {
          this.outputError(error)
        })
      }
    },
    // 重新刷新列
    refreshColumn(plxTable) {
      this.$nextTick(() => {
        let { fullColumn } = plxTable.getTableColumn()
        let setings = this.tableColumns
        let arr = []
        for(let key in setings) {
          arr.push({...setings[key], sortId: key})
        }
        arr.sort(function(a, b){
          return a.order - b.order
        })

        let newColumns = []
        arr.forEach(item => {
          fullColumn.some( element => {
            if(element.params && element.params.sortId == item.sortId) {
              newColumns.push(element)
              return true
            }
          })
        })
        plxTable.loadColumn(newColumns)
      })
    },

    // 相应显示历史记录按钮
    onShowHistory(scope) {
      this.curentRow = {id: scope.row.id, name: scope.row.name}
    },

    // 查询标签联动查询面板
    onQueryTagPanel(val, type) {
      if (type === 'delEditObject') {
        this.queryEditObject = null
        return
      }
      this.isQueryConditionPanel = type === 'edit'
      this.queryEditObject = val
      this.isMoreQueryTag = !this.isMoreQueryTag
    },

    // 查询标签点击事件
    onQueryTagClick(val, type) {
      this.queryEditObject = val
      this.moreParm = JSON.parse(val.conditions)
      this.isQueryConditionPanel = true
    },
    // 查询面板显示
    onQueryConditionPanel(type) {
      if (type === 'query') {
        this.queryEditObject = null
      }
      // 查询面板和查询表单切换时清空
      if (this.isQueryConditionPanel) {
        this.$refs.conditionPanel.reset()
      } else {
        this.$refs.queryForm.resetFields()
      }
      this.isQueryConditionPanel = !this.isQueryConditionPanel
    },
    setTableSize() {
      if (this.$refs.table) {
        this.$refs.table.size = this.$store.state.settings.size
      }
      if (this.$refs.listTree) {
        this.$refs.listTree.size = this.$store.state.settings.size
      }
    }
  },
  mounted() {
    this.setTableSize()
    this.initTableColumns = JSON.parse(JSON.stringify(this.tableColumns))
  }
}
</script>

<style lang="scss" scoped>
  /deep/ .dragColumnColbg {
    background-color: #409eff!important;
  }
  /deep/ .elx-header--row .elx-header--column.col--fixed {
    cursor: no-drop;
  }
  /deep/ .plTableBox {
    .col--tree-node:not(:first-child) {
      .elx-cell--tree-node {
        padding-left: 0!important;
        .elx-tree--btn-wrapper {
          display: none;
        }
        .elx-tree-cell {
          padding-left: 0;
        }
      }
    }
    .elx-table--fixed-wrapper {
      .elx-table--fixed-right-wrapper {
        .elx-cell--tree-node {
          padding-left: 0!important;
          .elx-tree--btn-wrapper {
            display: none;
          }
          .elx-tree-cell {
            padding-left: 0;
          }
        }
      }
    }
}
</style>
