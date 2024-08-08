<template>
  <el-dialog :title='dialogProps.title' :visible.sync='dialogProps.visible' width='640px' :close-on-click-modal='false' :lock-scroll='true' v-loading='loading'>
    <div slot='title' class='dialog-header'>{{ dialogProps.title }}</div>
    <data-permission ref='dataPermission' v-model='dataPermissions'></data-permission>
    <el-row :gutter='15'>
      <el-col :span='24'>
        <div class='permission-container'>
          <el-input
            style="margin-bottom: 10px"
            placeholder="输入关键字进行搜索"
            v-model="searchText">
          </el-input>
          <div class="tree-container">
            <el-tree ref='permissionTree'
                     :data='permissionTreeNodes'
                     :filter-node-method="searchNode"
                     show-checkbox
                     :props='{children: "children", label: "name"}'
                     :highlight-current='true'
                     :default-expand-all='true'
                     node-key='id'
                     :default-checked-keys='permissionKeys'
                     :expand-on-click-node='false'>
            <span class='custom-tree-node' slot-scope='{ node, data }'>
              <span>{{ node.label }}</span>
              <span v-if='canDataPermission(data, node)'>
                <el-button type='text' @click='() => onDataPermission(data)'>数据权限</el-button>
              </span>
            </span>
            </el-tree>
          </div>
        </div>
      </el-col>
    </el-row>
    <span slot='footer' class='dialog-footer'>
      <el-button type='primary' :plain='true' @click='doSavePermission()'>确 定</el-button>
      <el-button :plain='true' @click='handleDialogClose()'>取 消</el-button>
    </span>
  </el-dialog>
</template>

<script>
import { treePermission, listResourcePermissionByRoleId, listRouterPermissionByRoleId, savePermission, listDataPermissionByRoleId } from '@/api/admin/common/permission'
import DataPermission from './dataPermistion'
import BaseUI from '@/views/components/baseUI'
export default {
  extends: BaseUI,
  name: 'role-permission',
  components: {
    DataPermission
  },
  data() {
    return {
      loading: false,
      searchText: '',
      permissionTreeNodes: [],  // 可用的路由和资源树结构
      permissionKeys: [],     // 已分配权限资源Id列表
      dataPermissions: [],    // 数据权限列表
      role: {
        id: 0,
        name: ''
      },
      dialogProps: {
        visible: false,
        title: ''
      }
    }
  },
  watch: {
    searchText(val) {
      this.$refs.permissionTree.filter(val);
    }
  },
  methods: {
    searchNode(value, data, node) {
      if (!value) return true
      let parent = node.parent
      let labels = [node.label]
      let level = 1
      while (level < node.level) {
        labels = [...labels, parent.label]
        parent = parent.parent
        level++
      }
      return labels.some(label => label.indexOf(value) !== -1)
    },
    handleDialogClose() {
      this.dialogProps.visible = false
    },
    async componentInit(role) {
      this.loading = true
      this.role = role
      this.dialogProps.title = '权限设置 - ' + this.role.name
      this.permissionKeys = []
      this.dataPermissions = []
      try {
        let [resourceRespData, 
             routerRespData,
             treePermissionRespData, 
             listDataPermissionRespData] = await Promise.all([
          listResourcePermissionByRoleId(role.id),
          listRouterPermissionByRoleId(role.id),
          treePermission(),
          listDataPermissionByRoleId(role.id)
        ])
        if(resourceRespData.code == 100 
          && routerRespData.code == 100 
          && treePermissionRespData.code == 100 
          && listDataPermissionRespData.code == 100) {
          this.permissionTreeNodes = treePermissionRespData.data
          let routerPermission = this.getRouterPemissionLeaf(
              {id: -1, children: this.permissionTreeNodes, type: 'router'}, 
              routerRespData.data
            )
          this.permissionKeys = [...resourceRespData.data, ...routerPermission]
          this.dataPermissions = listDataPermissionRespData.data
        } else {
          this.showMessage(resourceRespData.code != 100 ? resourceRespData : 
            (routerRespData.code != 100 ? routerRespData :
            (treePermissionRespData.code != 100 ? treePermissionRespData : listDataPermissionRespData)))
        }

        this.loading = false
      } catch(error) {
        this.outputError(error)
      }
    },
    doSavePermission() {
      this.loading = true
      let routerIds = []
      let resourceIds = []
      this.$refs.permissionTree.getCheckedNodes().forEach(element => {
        if(element.type === 'router') {
          routerIds.push(element.id)
        }
        if(element.type === 'resource') {
          resourceIds.push(element.id)
        }
      })
      this.$refs.permissionTree.getHalfCheckedNodes().forEach(element => {
        if(element.type === 'router') {
          routerIds.push(element.id)
        }
        if(element.type === 'resource') {
          resourceIds.push(element.id)
        }
      })
      let permission = {
        routerIds: routerIds,
        resourceIds: resourceIds,
        dataPermissions: this.dataPermissions.filter(item => {
            return routerIds.indexOf(item.routerId) >= 0
          })
      }
      savePermission(this.role.id, permission).then(responseData => {
        if(responseData.code == 100) {
          this.dialogProps.visible = false
        } else {
          this.showMessage(responseData)
        }
        this.loading = false
      }).catch(error => {
        this.outputError(error)
      })
    },
    isRouterLeaf(data) {
      if(!(data.children instanceof Array) || data.children.length <= 0) {
        return true
      }
      for(let idx = 0; idx < data.children.length; idx++) {
        let item = data.children[idx]
        if(item.type == 'router') {
          return false
        }
      }
      return true
    },
    canDataPermission(data, node) {
      if(data.type == 'router' && this.isRouterLeaf(data)) {
        // 全选 或半选状态
        if(this.isHalfOrChecked(node)) {
          // 元数据配置文件存在
          try {
            let metas = require('@/views/' + data.code + '/metadata').metadata
            return true
          }catch (e) {
            console.warn('没有找到元数据描述文件：', '@/views/' + data.code + '/metadata')
          }
        }
      }
      return false
    },
    isHalfOrChecked(node) {
      if(node.checked) {
        return true
      }
      if(!(node.childNodes instanceof Array) || node.childNodes.length <= 0) {
        return false
      }

      for(let childNod of node.childNodes) {
        if(this.isHalfOrChecked(childNod)) {
          return true
        }
      }

      return false
    },
    onDataPermission(data) {
      if(data.code) {
        this.$refs.dataPermission.$emit('openDataPermission', data.code, this.role.id, data.id)
      }
    },
    /**
     * 获取不含资源末级路由，且由已经赋权限的路由
     * @param {*} node          路由资源权限树的节点
     * @param {*} routerIds     赋予权限的路由id列表
     */
    getRouterPemissionLeaf(node, routerIds) {
      if(node.type !== 'router') {
        return [];
      } else if(node.type === 'router' && (!node.children || node.children.length <= 0)) {
        if(routerIds.indexOf(node.id) >=0) {
          return [node.id]
        } else {
          return []
        }
      }
      let arr = []
      node.children.forEach((item) => {
        arr.push.apply(arr, this.getRouterPemissionLeaf(item, routerIds))
      })
      return arr
    }
  },
  mounted: function() {
    this.$nextTick(() => {
      this.$on('openSetPermissionDialog', function(role) {
        this.dialogProps.visible = true
        this.searchText = ''
        this.componentInit(role);
      })
    })
  }
}
</script>
<style lang='scss' scoped>
  .permission-container {
    .tree-container {
      height: 360px;
      overflow-y: auto;
    }
  }
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 14px;
    padding-right: 8px;
  }
</style>


