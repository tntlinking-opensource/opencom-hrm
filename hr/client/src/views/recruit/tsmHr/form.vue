<template>
  <el-row class="dc-container" v-loading="loading">
    <el-form
      ref="editForm"
      :model="editFormData"
      label-width="100px"
      :disabled="action == 'view'"
      class="dc-el-form_ElEditForm"
    >
      <el-row>
        <el-col :span="6">
          <el-form-item prop="recruitCode" label="招聘编号" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.recruitCode"
              :maxLength="32"
              placeholder="请输入招聘编号"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="postName" label="招聘岗位" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.postName"
              :maxLength="64"
              placeholder="请输入招聘岗位"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="postDescribe" label="岗位描述" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.postDescribe"
              :maxLength="-1"
              placeholder="请输入岗位描述"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="postDemand" label="任职资格" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.postDemand"
              :maxLength="-1"
              placeholder="请输入任职资格"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="salaryLow.id" label="薪资下限" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="editFormData.salaryLow"
              :style="{ width: '100%' }"
              placeholder="请选择薪资下限"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in salaryLowOptions" :key="index" :label="item.name" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="salaryHigh.id" label="薪资上限" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="editFormData.salaryHigh"
              :style="{ width: '100%' }"
              placeholder="请选择薪资上限"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in salaryHighOptions" :key="index" :label="item.name" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="salarySpecial" label="特殊工资范围" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.salarySpecial"
              :maxLength="32"
              placeholder="请输入特殊工资范围"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="staffNum" label="招聘人数" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.staffNum"
              :maxLength="12"
              placeholder="请输入招聘人数"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="deptId" label="部门id" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.deptId"
              :maxLength="255"
              placeholder="请输入部门id"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="deptCode" label="事业部编号" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.deptCode"
              :maxLength="32"
              placeholder="请输入事业部编号"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="deptName" label="部门名称" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.deptName"
              :maxLength="32"
              placeholder="请输入部门名称"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="company" label="所属公司名称" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.company"
              :maxLength="32"
              placeholder="请输入所属公司名称"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="workCity" label="工作城市" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.workCity"
              :maxLength="32"
              placeholder="请输入工作城市"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="recruitType.id" label="招聘类型" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="editFormData.recruitType"
              :style="{ width: '100%' }"
              placeholder="请选择招聘类型"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in recruitTypeOptions" :key="index" :label="item.name" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="firstInterviewer" label="初试面试官" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.firstInterviewer"
              :maxLength="255"
              placeholder="请输入初试面试官"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="secondInterviewer" label="复试面试官" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.secondInterviewer"
              :maxLength="255"
              placeholder="请输入复试面试官"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="finalInterviewer" label="最终面试官" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.finalInterviewer"
              :maxLength="255"
              placeholder="请输入最终面试官"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="formNum" label="form_num" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.formNum"
              :maxLength="100"
              placeholder="请输入form_num"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="recruitStatus"
            label="招聘状态 0-未招聘 2-招聘中 3-招聘完成 4-招聘取消"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.recruitStatus"
              :maxLength="10"
              placeholder="请输入招聘状态 0-未招聘 2-招聘中 3-招聘完成 4-招聘取消"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="candidate" label="已招聘人数" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.candidate"
              :maxLength="100"
              placeholder="请输入已招聘人数"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="actStatus"
            label="审批状态。0-未发起，2-审批中，3-审批通过，4-退回"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.actStatus"
              :maxLength="2"
              placeholder="请输入审批状态。0-未发起，2-审批中，3-审批通过，4-退回"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="actName" label="流程名称" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.actName"
              :maxLength="100"
              placeholder="请输入流程名称"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="actKey" label="流程id" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.actKey"
              :maxLength="100"
              placeholder="请输入流程id"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="applyDate" label="申请时间" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.applyDate"
              :maxLength="-1"
              placeholder="请输入申请时间"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="tenantId" label="tenant_id" class="dc-el-form-item_SingleInput">
            <el-input
              v-model="editFormData.tenantId"
              :maxLength="100"
              placeholder="请输入tenant_id"
              clearable
              autocomplete="new-password"
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="recruitStudy.id" label="选择学历" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="editFormData.recruitStudy"
              :style="{ width: '100%' }"
              placeholder="请选择选择学历"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option v-for="(item, index) in recruitStudyOptions" :key="index" :label="item.name" :value="item"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="recruitExperience.id" label="选择经历" class="dc-el-form-item_SelectInput">
            <el-select
              v-model="editFormData.recruitExperience"
              :style="{ width: '100%' }"
              placeholder="请选择选择经历"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in recruitExperienceOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item prop="remarks" label="备注信息" class="dc-el-form-item_MutilpleInput">
            <el-input
              v-model="editFormData.remarks"
              type="textarea"
              placeholder="请输入备注信息"
              rows="2"
              :maxLength="255"
              class="dc-el-input_MutilpleInput"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-row>
</template>
<script>
import BizForm from '@/views/wf/common/bizForm'
import { validatenull } from '@/utils/validate'
/** 根据用户界面配置import组件 结束 */
import { listDictItemAll } from '@/api/sys/dictItem.js'
/** 根据用户界面配置import组件 结束 */
import {
  getTsmRecruitById,
  createTsmRecruit,
  reapplyTsmRecruit,
  getTsmRecruitByTaskId,
  agreeTsmRecruit,
  forwardTsmRecruit,
  commissionTsmRecruit,
  proposeTsmRecruit,
  backTsmRecruit,
  rejectTsmRecruit,
  terminateTsmRecruit,
  reverseTsmRecruit,
  listBackActivity,
  saveDraftTsmRecruit
} from '@/api/hrRecruit/tsmRecruit'
import OperationIcon from '@/components/OperationIcon'
export default {
  extends: BizForm,
  name: 'tsmRecruit-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      dialogProps: {
        visible: false,
        title: ''
      },

      // 选项变量
      // 薪资下限选项
      salaryLowOptions: [],
      // 薪资上限选项
      salaryHighOptions: [],
      // 招聘类型选项
      recruitTypeOptions: [],
      // 选择学历选项
      recruitStudyOptions: [],
      // 选择经历选项
      recruitExperienceOptions: [],
      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: ''
    }
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  computed: {},
  methods: {
    loadModelData(taskId) {
      this.setLoad()
      getTsmRecruitByTaskId(taskId)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.editFormData = {
              ...this.initEditData(),
              ...responseData.data
            }
            this.$emit('after-load-data', this.editFormData) // 表单数据对象传给父节点
          } else {
            this.showMessage(responseData)
          }
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 保存草稿，files附件列表, draftModel 草稿对象
    doSaveDraft(draftModel, files) {
      if (!draftModel) {
        // 不是从草稿打开创建，保存时更新原草稿
        draftModel = {
          procdefId: this.procDef.id,
          procdefName: this.procDef.name,
          name: this.procDef.name + '-' + this.editFormData.name
        }
      }
      this.setLoad()
      let formData = this.createWfData(this.editFormData, files)
      formData.append('strDraft', JSON.stringify(draftModel))
      saveDraftTsmRecruit(formData)
        .then((responseData) => {
          this.handleResponse(responseData)
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 创建一个新流程 process流程 actNodes是提交的用户任务节点及其审批用户
    doCreate(files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          let formData = this.createWfData(this.editFormData, files)
          createTsmRecruit(this.procDef.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 重新发起申请 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doReApply(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          reapplyTsmRecruit(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 同意 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doAgree(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          agreeTsmRecruit(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 转派任务
    doForward(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          forwardTsmRecruit(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 委派任务
    doCommission(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          commissionTsmRecruit(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 拟办（办理委派过来的任务）
    doPropose(comment, files) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          proposeTsmRecruit(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 拒绝
    doReject(comment, files) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          rejectTsmRecruit(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 退回 comment 意见, actNodes退回节点和用户信息
    doBack(comment, files, actNodes) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          this.editFormData.actNodes = actNodes // 选择节点及审批用户
          this.editFormData.taskComement = comment // 意见

          let formData = this.createWfData(this.editFormData, files)
          backTsmRecruit(this.task.id, formData)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },
    // 终止流程 comment 意见, actNodes退回节点和用户信息
    doTerminate(comment, files) {
      this.setLoad()
      this.editFormData.taskComement = comment // 意见

      let formData = this.createWfData(this.editFormData, files)
      terminateTsmRecruit(this.task.id, formData)
        .then((responseData) => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false
            this.$emit('save-finished')
          }
          this.showMessage(responseData)
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    // 撤回任务
    doReverse(task) {
      this.setLoad()
      reverseTsmRecruit(task.id)
        .then((responseData) => {
          this.handleResponse(responseData)
          this.resetLoad()
        })
        .catch((error) => {
          this.outputError(error)
        })
    },
    doListActivity() {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.setLoad()
          listBackActivity(this.task.id)
            .then((responseData) => {
              this.handleResponse(responseData)
              this.resetLoad()
            })
            .catch((error) => {
              this.outputError(error)
            })
        }
      })
    },

    listSalaryLowOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.salaryLowOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listSalaryHighOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.salaryHighOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listRecruitTypeOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.recruitTypeOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listRecruitStudyOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.recruitStudyOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listRecruitExperienceOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000004')

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.recruitExperienceOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listSalaryLowOptions()

      this.listSalaryHighOptions()

      this.listRecruitTypeOptions()

      this.listRecruitStudyOptions()

      this.listRecruitExperienceOptions()
    },
    initEditData(This) {
      return {
        recruitCode: '', // 招聘编号
        postName: '', // 招聘岗位
        postDescribe: '', // 岗位描述
        postDemand: '', // 任职资格
        salaryLow: {
          id: null,
          name: null
        },
        salaryHigh: {
          id: null,
          name: null
        },
        salarySpecial: '', // 特殊工资范围
        staffNum: '', // 招聘人数
        deptId: '', // 部门id
        deptCode: '', // 事业部编号
        deptName: '', // 部门名称
        company: '', // 所属公司名称
        workCity: '', // 工作城市
        recruitType: {
          id: null,
          name: null
        },
        firstInterviewer: '', // 初试面试官
        secondInterviewer: '', // 复试面试官
        finalInterviewer: '', // 最终面试官
        formNum: '', // form_num
        recruitStatus: '', // 招聘状态 0-未招聘 2-招聘中 3-招聘完成 4-招聘取消
        candidate: '', // 已招聘人数
        actStatus: '0', // 审批状态。0-未发起，2-审批中，3-审批通过，4-退回
        actName: '', // 流程名称
        actKey: '', // 流程id
        applyDate: '', // 申请时间
        tenantId: '', // tenant_id
        recruitStudy: {
          id: null,
          name: null
        },
        recruitExperience: {
          id: null,
          name: null
        },
        remarks: '' // 备注信息
      }
    }
  },
  watch: {},
  mounted: function () {
    this.$nextTick(() => {
      this.dialogProps.visible = true
      this.initOptions()
      this.tabIndex = '1'
      if (this.wfAction == 'create') {
        this.$refs['editForm'].resetFields()
        // 从草稿创建
        if (this.bizData) {
          this.editFormData = {
            ...this.initEditData(),
            ...this.bizData
          }
        }
      } else if (this.wfAction == 'approve') {
        // 重新申请
        let reApply =
          this.task.assignee == this.task.owner &&
          this.task.assignee == this.task.extInfo.applicantId &&
          this.task.taskDefinitionKey == this.task.extInfo.appActId
        if (!reApply) {
          this.action = 'view'
        }
        this.loadModelData(this.task.id)
      } else if (this.wfAction == 'viewBiz') {
        this.action = 'view'
        this.editFormData = {
          ...this.initEditData(),
          ...this.bizData
        }
      } else if (this.wfAction == 'viewTask') {
        this.action = 'view'
        this.loadModelData(this.task.id)
      } else {
        console.err('=============== 业务审批单中，实现的：' + this.wfAction)
      }
    })
  }
}
</script>
