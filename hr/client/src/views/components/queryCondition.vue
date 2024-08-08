<template>
  <div v-loading='loading' class="query-condition">
    <div style="padding: 5px 0">
      <query-condition-panel
        ref='conditionPanel'
        v-model='vModel'
        :tableId='tableId'
        :schemeId='schemeId'
        :curNode='curNode'
        :exclude='exclude'
        :routerId='routerId'></query-condition-panel>
    </div>

    <!-- <div style="text-align: right">
      <el-button type="primary" plain @click="onSaveCondition">保存</el-button>
    </div> -->
  </div>
</template>

<script>
import BaseUI from './baseUI'
import queryConditionPanel from './queryConditionPanel'
export default {
  extends: BaseUI,
  name: 'QueryCondition',
  components: {
    queryConditionPanel
  },
  data() {
    return {
      vModel: null
    }
  },
  props: {
    value: {
      type: Object,
      default: () => {return {}}
    },
    tableId: {
      type: String
    },
    schemeId: {
      type: String
    },
    // 不作为条件的字段
    exclude: {
      type: String,
      default: () => {return null}
    },
    curNode: {
      type: Object,
      default: () => {return {}}
    },
    routerId: {
      type: String
    },
    editObject: {
      type: Object
    }
  },
  watch:{
    vModel(val, oldVal) {
      if(val != oldVal){
        this.$emit('input', this.vModel)
      }
    },
    value(val, oldVal) {
      if(val != oldVal){
        this.vModel = val
      }
    }
  },
  methods: {
    init() {

    }
  },
  mounted(){
    this.vModel = this.value
    this.$nextTick(() => {
      this.init()
    })
  }
}
</script>
<style lang="scss" scoped>
  .query-condition {

  }
  .condition-aside {
    .el-row {
      margin-bottom: 5px;
    }
  }
</style>
