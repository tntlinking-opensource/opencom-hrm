<template>
  <div>
    <div  style="text-align:right" >
      <OperationIcon type='primary' content='全部显示' placement='top-start' icon-name='el-icon-check' @click='onShowAllColumnView()'></OperationIcon>
      <OperationIcon type='primary' content='默认配置' placement='top-start' icon-name='el-icon-refresh' @click='onResetColumnView()'></OperationIcon>
    </div>
    <div  style="text-align:left" >
      <div v-for="(column, index) in vModel" :key='column.order'>
        <el-checkbox v-if="index !== 'version'" v-model='column.visible' :label='column.title' @change='onColumnViewChange' style="text-align:left"></el-checkbox>
      </div>
    </div>
  </div>
</template>

<script>
import OperationIcon from '@/components/OperationIcon'
export default {
  name: 'ViewColumnsSelect',
  components: {
    OperationIcon
  },
  data() {
    return {
      vModel: null
    }
  },
  props: {
    //接受外部v-model传入的值
    value: {
      type: Array
    }
  },
  watch:{
    value: {
      handler(val, oldVal) {
        this.vModel = val
      },
      deep: true
    }
  },
  methods: {
    async init() {

    },
    onResetColumnView(){
      this.$emit('show-default-column')
    },
    onShowAllColumnView(){
      this.$emit('input', this.vModel);
      this.$emit('show-all-column', this.vModel)
    },
    onColumnViewChange() {
      this.$emit('input', this.vModel);
      this.$emit('save-column-view', this.vModel)
    },
    onMouseEnter(e) {
      e.target.style.color = '#409EFF'
    },
    onMouseLeave(e) {
      e.target.style.color = '#909399'
    }
  },
  mounted() {
    this.vModel=this.value
    this.init()
  }
}
</script>

