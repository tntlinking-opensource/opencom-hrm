<template>
  <div v-loading='loading' class="query-tag-container">
    <div class="top">
      <div class="query-tags" :style="styleObject">
        <span class="query-tags-title">一键筛选：</span>
        <draggable
          style="flex: 1; float: left;"
          v-if="queryTags.length > 0"
          v-model="queryTags"
          group="tagSite"
          animation="300"
          dragClass="dragClass"
          ghostClass="ghostClass"
          chosenClass="chosenClass" 
          @add="onToQueryTag"
          @sort="onSort">
            <el-tag
              :key="index"
              ref="queryTagsRef"
              v-for="(tag, index) in queryTags"
              :type="tagIndex === tag.id ? '' : 'info'"
              :effect="tagIndex === tag.id ? 'plain' : 'plain'"
              :closable="isEdit"
              :disable-transitions="false"
              :class="!isEdit && !isMore ? 'tag-class' : ''"
              @click="clickTag(tag)"
              @close="removeTag(tag)">
              {{tag.name}}
            </el-tag>
        </draggable>

      </div>
      <div class="label-opt">
        <!-- <el-button v-if="!isEdit && moreQueryTags.length > 0"  plain @click="onMoreClick">{{ isMore ? '收起' : '更多' }}</el-button> -->
        <el-tooltip effect="light" content="查看更多" placement="top" class="more-icon">
          <span>
            <i v-if="!isEdit && moreQueryTags.length > 0" class="el-icon-d-arrow-right" @click="onMoreClick"></i>
          </span>
        </el-tooltip>
        <el-button v-if="!isEdit" plain @click="onOperateClick">调整</el-button> 
        <el-button v-if="isQueryConditionPanel" plain @click="onSaveClick">保存</el-button>
        <el-button v-if="isEdit"  plain @click="onCancelClick">取消</el-button>
      </div>
    </div>
    <div class="more-tags" v-if="isEdit || isMore" :style="styleObject">

      <draggable
        v-if="moreQueryTags.length > 0"
        v-model="moreQueryTags"
        group="tagSite"
        animation="300"
        dragClass="dragMoreClass"
        ghostClass="ghostMoreClass"
        chosenClass="chosenMoreClass"
        @add="onToMoreQueryTag">
          <el-tag 
            :key="index" 
            v-for="(tag, index) in moreQueryTags" 
            :type="tagIndex === tag.id ? '' : 'info'"
            :effect="tagIndex === tag.id ? 'plain' : 'plain'"
            :closable="isEdit"
            :disable-transitions="false" 
            @click="clickTag(tag)"
            @close="removeTag(tag)">
            {{tag.name}}
          </el-tag>
      </draggable>

    </div>

    <el-dialog :visible.sync='dialogVisible' width='350px' @open='onDialogOpen()'>
      <div slot='title'>
        {{ editObject && isEdit ? "更新查询标签" : "输入查询标签" }}
      </div>
      <el-form :model='nameModel' ref='nameForm' label-width='80px' label-position='right' :rules='formRules'>
        <el-form-item label="查询标签" prop='name'>
          <el-input v-model='nameModel.name'></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" plain @click="onDoSaveCondition">确 定</el-button>
        <el-button @click="dialogVisible = false">取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import BaseUI from './baseUI'
import { mapGetters } from 'vuex'
import { listDcConditionAll, saveDcCondition, bulkUpdateSort, deleteDcCondition } from '@/api/dc/dcCondition'
export default {
  extends: BaseUI,
  name: 'QueryTag',
  components: {

  },
  data() {
    const checkQueryTagName= (rule, value, callback) => {
      if (!value) {
        return callback(new Error('请输入查询标签名称!'))
      } else if (value.length > 5) {
        callback(new Error('限制不能超过5个字符!'))
		  } else {
        callback()
      }
    }
    return {
      queryTagValue: '',
      isEdit: false,
      isMore: false,
      limit: 6,
      allQueryTags: [],
      queryTags: [],
      moreQueryTags: [],
      tagIndex: '',
      editObject: null,
      dialogVisible: false,   // 查询标题框显示标志
      nameModel: {
        name: ''   // 查询标题名
      },

      formRules: {
        'name': [
            { validator: checkQueryTagName, trigger: 'blur' }
        ],
      }
    }
  },
  props: {
    value: {
      type: Object,
      default: () => {return {}}
    },
    routerId: {
      type: String
    },
    isQueryConditionPanel: {
      type: Boolean
    },
    moreParm: {
      type: Object
    }
  },
  watch:{
    value(val, old) {
      if (val && val != old) {
        this.editObject = val
      }
    }
  },
  computed: {
    ...mapGetters(['settings']),
    styleObject() {
      return {
        '--active-color': this.settings.theme
      }
    }
  },
  methods: {
    init() {
      listDcConditionAll({
        params: [{columnName: 'user_id', queryType: '=', value: currentUser.id},
          {columnName: 'router_id', queryType: '=', value: this.routerId}
        ]
      }).then(responseData => {
        if(responseData.code == 100) {
          this.allQueryTags = responseData.data
          this.handleTags()
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    },
    onToQueryTag() {
      const lastTag = this.queryTags.pop()
      this.moreQueryTags.unshift(lastTag)
    },
    onToMoreQueryTag() {
      const firstTag = this.moreQueryTags.shift()
      this.queryTags.push(firstTag)
    },
    onSort() {
      const userId = currentUser.id
      this.allQueryTags = [...this.queryTags, ...this.moreQueryTags]
      bulkUpdateSort(userId, this.routerId, this.allQueryTags).then(responseData => {
      }).catch(error => {})
    },
    handleTags() {
      this.queryTags = this.allQueryTags.slice(0, this.limit)
      this.moreQueryTags = this.allQueryTags.slice(this.limit)
    },
    clickTag(val) {
      if (val) {
        this.tagIndex = val.id
        this.editObject = val
        if (!this.isEdit) {
          this.$emit('queryTagClick', val)
          this.onSearch()
        } else {
          // this.$emit('queryTagClick', val, 'edit')
          this.$emit('queryTagClick', val)
        }
      }
    },
    onSearch() {
      this.$emit('search')
    },
    onSaveClick() {
      if(this.editObject && this.isEdit) {
        this.nameModel.name = this.editObject.name
      } else {
        this.nameModel.name = ''
      }
      this.dialogVisible = true
    },
    onMoreClick() {
      this.isEdit = false
      this.isMore = !this.isMore
    },
    onCancelClick() {
      this.isEdit = false
      this.isMore = false
      this.$emit('queryTagPanel', this.editObject)
    },
    onOperateClick() {
      this.isEdit = true
      this.$emit('queryTagPanel', this.editObject, 'edit')
    },
    removeTag(condition) {
      deleteDcCondition(condition).then(responseData => {
        if(responseData.code == 100) {
          if (this.tagIndex == this.editObject.id) {
             this.editObject = null
            this.$emit('queryTagPanel', null, 'delEditObject')
          }
          let arr = this.allQueryTags.filter(item => item.id != condition.id)
          this.allQueryTags = arr
          this.handleTags()
          this.showMessage({type: 'success', msg: '删除成功'})
        } else {
          this.showMessage(responseData)
        }
        this.resetLoad()
      }).catch(error => {
        this.outputError(error)
      })
    },
    onDialogOpen() {
      this.$nextTick(() => {
        this.$refs['nameForm'].clearValidate()
      })
    },
    // 点击保存对话框确定按钮
    onDoSaveCondition() {
        this.$refs['nameForm'].validate(valid => {
        if (valid) {
          this.setLoad()
  
          saveDcCondition({
            id: this.editObject && this.isEdit ? this.editObject.id : null,
            userId: currentUser.id,
            routerId: this.routerId,
            sort: 0,
            name: this.nameModel.name,
            conditions: JSON.stringify(this.moreParm)
          }).then(responseData => {
            if(responseData.code == 100) {
              this.tagIndex = responseData.data
              listDcConditionAll({
                params: [{columnName: 'user_id', queryType: '=', value: currentUser.id},
                  {columnName: 'router_id', queryType: '=', value: this.routerId}
                ]
              }).then(responseData => {
                if(responseData.code == 100) {
                  // 刷新查询标签
                  this.allQueryTags = responseData.data
                  this.handleTags()
                  this.editObject = this.allQueryTags[0]
                  this.$emit('input', this.editObject)
                  
                }
              })

              this.dialogVisible = false
              this.showMessage({type: 'success', msg: '保存成功'})
            } else {
              this.showMessage(responseData)
            }
            this.resetLoad()
          }).catch(error => {
            this.outputError(error)
          })

        } else {
          return false
        }
      });
    }
  },
  mounted(){
    this.$nextTick(() => {
      this.init()
    })
  }
}
</script>
<style lang="scss">
.query-tag-container {
  margin-bottom: 5px;
  .query-tags-title {
    width: 70px;
    display: inline-block;
    font-size: 12px;
    color: #555;
    float: left;
    margin-top: 5px;
  }
  .tag-class {
    // border-color: var(--active-color);
    // background-color: transparent;
    color: var(--active-color);
    i {
      color: #555;
      &:hover {
        color: #fff;
        background-color: #999;
      }
    }
    &:hover {
      color: var(--active-color);
    }
  }
  .top {
    overflow: hidden;
    // display: flex;
    // align-content: center;
    // margin-bottom: 5px;
  }
  .el-tag {
    cursor: pointer;
    margin: 0 5px 4px 5px;
    height: 38px;
    line-height: 37px;
    &.el-tag--mini {
      height: 28px;
      line-height: 27px;
    }
    &.el-tag--small {
      height: 32px;
      line-height: 31px;
    }
    &.el-tag--medium {
      height: 34px;
      line-height: 33px;
    }
    &:hover {
      color: var(--active-color);
    }
  }
  .query-tags {
    padding-right: 10px;
    // display: flex;
    // align-items: center;
  }
  .label-opt {
    float: right;
    position: relative;
    padding: 0 10px;
    display: flex;
    align-items: center;
    // display: flex;
    // align-items: center;
    &::before {
      position: absolute;
      content: '';
      left: 0;
      width: 1px;
      height: 20px;
      background-color: #dbdbdb;
      top: 50%;
      margin-top: -10px;
    }
    .more-icon {
      position: absolute;
      left: -30px;
      padding: 5px;
      color: #999;
      cursor: pointer;
    }
  }
  .query-tags, .more-tags {
    transition: all .5s;
  }
  .more-tags {
    /*border-top: 1px solid #f7f7f7;*/
    margin-left: 70px;
    .el-tag {
      cursor: pointer;
      margin: 0 5px 4px 5px;
    }
  }
}
</style>
