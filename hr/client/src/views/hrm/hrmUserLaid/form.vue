<template>
  <el-row class="dc-container" v-loading="loading">
    <el-form
      ref="editForm"
      :model="editFormData"
      label-width="300px"
      :disabled="action == 'view'"
      class="dc-el-form_ElEditForm"
    >
      <el-row>
        <user-info :userId="editFormData.user.id"/>
        <el-divider>离职信息填写</el-divider>
        <el-col :span="12">
          <el-form-item
            prop="laidapplydate"
            label="离职申请日期"
            :rules="[{ required: true, message: '离职申请日期不能为空', trigger: 'change' }]"
            class="dc-el-form-item_PickerTimeDate"
          >
            <el-date-picker
              v-model="editFormData.laidapplydate"
              :style="{ width: '100%' }"
              placeholder="请选择"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="lastworkdate"
            label="最后工作日"
            :rules="[{ required: true, message: '最后工作日不能为空', trigger: 'change' }]"
            class="dc-el-form-item_PickerTimeDate"
          >
            <el-date-picker
              v-model="editFormData.lastworkdate"
              :style="{ width: '100%' }"
              placeholder="请选择"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="laiddealdate"
            label="离职办理日期"
            :rules="[{ required: true, message: '离职办理日期不能为空', trigger: 'change' }]"
            class="dc-el-form-item_PickerTimeDate"
          >
            <el-date-picker
              v-model="editFormData.laiddealdate"
              :style="{ width: '100%' }"
              placeholder="请选择"
              type="date"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              class="dc-el-date-picker_PickerTimeDate"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="handinguserid"
            label="交接人选择"
            :rules="[{ required: true, message: '交接人选择不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.handinguserid"
              :style="{ width: '100%' }"
              placeholder="请选择交接人"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in handinguseridOptions"
                :key="index"
                :label="item.name"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="laidtype"
            label="离职类型"
            :rules="[{ required: true, message: '离职类型不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.laidtype"
              :style="{ width: '100%' }"
              value-key="id"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(hrmLaidType, index) in hrmLaidTypes"
                :key="hrmLaidType.id"
                :label="hrmLaidType.name"
                :value="hrmLaidType"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="laidmaincause"
            label="离职主要原因"
            :rules="[{ required: true, message: '离职主要原因不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.laidmaincause"
              :style="{ width: '100%' }"
              placeholder="请选择离职主要原因"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in laidmaincauseOptions"
                :key="index"
                :label="item.value"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            prop="laidcause"
            label="离职原因"
            :rules="[{ required: true, message: '离职原因不能为空', trigger: 'blur' }]"
            class="dc-el-form-item_MutilpleInput"
          >
            <el-input
              v-model="editFormData.laidcause"
              type="textarea"
              placeholder="请输入"
              rows="3"
              :maxLength="-1"
              class="dc-el-input_MutilpleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-divider>离职员工调研</el-divider>
        <el-col :span="12">
          <el-form-item
            prop="research1"
            label="总体上，我认为在公司工作是件很好的事"
            :rules="[{ required: true, message: '总体上，我认为在公司工作是件很好的事不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research1"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research2"
            label="我的工作环境很好"
            :rules="[{ required: true, message: '我的工作环境很好不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research2"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research3"
            label="我一直被公平对待"
            :rules="[{ required: true, message: '我一直被公平对待不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research3"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research4"
            label="我知道对我的工作要求"
            :rules="[{ required: true, message: '我知道对我的工作要求不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research4"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research5"
            label="我有做好我的工作所需要的材料和设备"
            :rules="[{ required: true, message: '我有做好我的工作所需要的材料和设备不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research5"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research6"
            label="我总有机会做我最擅长做的事"
            :rules="[{ required: true, message: '我总有机会做我最擅长做的事不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research6"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research7"
            label="上级主管/经理能及时向我表示认可或肯定"
            :rules="[{ required: true, message: '上级主管/经理能及时向我表示认可或肯定不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research7"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research8"
            label="我觉得我的上级主管/经理关心我的个人情况"
            :rules="[{ required: true, message: '我觉得我的上级主管/经理关心我的个人情况不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research8"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research9"
            label="公司里有人鼓励我的发展"
            :rules="[{ required: true, message: '公司里有人鼓励我的发展不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research9"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research10"
            label="在工作中，我的意见能够得到重视"
            :rules="[{ required: true, message: '在工作中，我的意见能够得到重视不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research10"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research11"
            label="公司的使命/目标使我觉得我的工作重要"
            :rules="[{ required: true, message: '公司的使命/目标使我觉得我的工作重要不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research11"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research12"
            label="我的同事们致力于高质量的工作"
            :rules="[{ required: true, message: '我的同事们致力于高质量的工作不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research12"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research13"
            label="我在公司有关系很好的伙伴"
            :rules="[{ required: true, message: '我在公司有关系很好的伙伴不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research13"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research14"
            label="过去的三个月内，公司里有人谈及我的进步"
            :rules="[{ required: true, message: '过去的三个月内，公司里有人谈及我的进步不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research14"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item
            prop="research15"
            label="我在工作中有机会学习与成长"
            :rules="[{ required: true, message: '我在工作中有机会学习与成长不能为空', trigger: 'change' }]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.research15"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in dictItemOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
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
import { listUserAll } from '@/api/admin/user.js'
import { listHrmLaidTypeAll } from '@/api/hrm/hrmLaidType.js'
import { listHrmLaidValueAll } from '@/api/hrm/hrmLaidValue.js'
import { listDictItemAll } from '@/api/sys/dictItem.js'
/** 根据用户界面配置import组件 结束 */
import {
  getHrmUserLaidById,
  createHrmUserLaid,
  reapplyHrmUserLaid,
  getHrmUserLaidByTaskId,
  agreeHrmUserLaid,
  forwardHrmUserLaid,
  commissionHrmUserLaid,
  proposeHrmUserLaid,
  backHrmUserLaid,
  rejectHrmUserLaid,
  terminateHrmUserLaid,
  reverseHrmUserLaid,
  listBackActivity,
  saveDraftHrmUserLaid
} from '@/api/hrm/hrmUserLaid'
import OperationIcon from '@/components/OperationIcon'
import UserInfo from "./userinfo.vue";
export default {
  extends: BizForm,
  name: 'hrmUserLaid-form',
  components: {
    /** 根据用户界面配置组件 开始 */

    /** 根据用户界面配置组件 结束 */

    OperationIcon,
    UserInfo
  },
  data() {
    return {
      /** 根据用户界面配置生成data数据 开始 */
      editFormData: this.initEditData(),
      dialogProps: {
        visible: false,
        title: ''
      },

      // 交接人选择选项
      handinguseridOptions: [],
      // 离职类型选项
      hrmLaidTypes: [],
      // 离职主要原因选项
      laidmaincauseOptions: [],
      // 总体上，我认为在公司工作是件很好的事选项
      dictItemOptions: [],
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
      getHrmUserLaidByTaskId(taskId)
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
      saveDraftHrmUserLaid(formData)
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
          createHrmUserLaid(this.procDef.id, formData)
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
          reapplyHrmUserLaid(this.task.id, formData)
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
          agreeHrmUserLaid(this.task.id, formData)
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
          forwardHrmUserLaid(this.task.id, formData)
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
          commissionHrmUserLaid(this.task.id, formData)
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
          proposeHrmUserLaid(this.task.id, formData)
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
          rejectHrmUserLaid(this.task.id, formData)
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
          backHrmUserLaid(this.task.id, formData)
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
      terminateHrmUserLaid(this.task.id, formData)
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
      reverseHrmUserLaid(task.id)
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

    listHandinguseridOptions() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 用户sys_user
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1220000000000000002')

      listUserAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.handinguseridOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listHrmLaidTypes() {
      let search_List = {
        params: []
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 离职类型hrm_laid_type
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1457254743943433140')

      listHrmLaidTypeAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.hrmLaidTypes = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    listLaidmaincauseOptions() {
      let search_List = {
        params: []
      }
      if(!validatenull(this.editFormData.laidtype.id)){
        search_List.params.push(
          {
            columnName: 'hrm_laid_type_id',
            queryType: '=',
            value: this.editFormData.laidtype.id
          }
        )
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      // 数据权限: 离职类型列表hrm_laid_value
      this.pushDataPermissions(search_List.params, this.$route.meta.routerId, '1457254743943433187')

      listHrmLaidValueAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.laidmaincauseOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },
    listDictItemOptions() {
      let search_List = {
        params: [
          {
            columnName: 'dict_type_id',
            queryType: '=',
            value: '1507209113427910884'
          }
        ]
      }
      // filter条件
      search_List.params.push.apply(search_List.params, [])

      listDictItemAll(search_List).then((responseData) => {
        if (responseData.code == 100) {
          this.dictItemOptions = responseData.data
        } else {
          this.showMessage(responseData)
        }
      })
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listHandinguseridOptions()

      this.listHrmLaidTypes()

      this.listLaidmaincauseOptions()

      this.listDictItemOptions()

    },
    initEditData(This) {
      return {
        laidapplydate: '', // 离职申请日期
        lastworkdate: '', // 最后工作日
        laiddealdate: '', // 离职办理日期
        handinguserid:null,
        laidtype: {
          id: null,
          name: null
        },
        laidmaincause: {
          id: null,
          name: null,
          hrmLaidTypeId: null,
          value: null
        },
        laidcause: '', // 离职原因
        research1: null, // 总体上，我认为在公司工作是件很好的事
        research2: null, // 我的工作环境很好
        research3: null, // 我一直被公平对待
        research4: null, // 我知道对我的工作要求
        research5: null, // 我有做好我的工作所需要的材料和设备
        research6: null, // 我总有机会做我最擅长做的事
        research7: null, // 上级主管/经理能及时向我表示认可或肯定
        research8: null, // 我觉得我的上级主管/经理关心我的个人情况
        research9: null, // 公司里有人鼓励我的发展
        research10: null, // 在工作中，我的意见能够得到重视
        research11: null, // 公司的使命/目标使我觉得我的工作重要
        research12: null, // 我的同事们致力于高质量的工作
        research13: null, // 我在公司有关系很好的伙伴
        research14: null, // 过去的三个月内，公司里有人谈及我的进步
        research15: null, // 我在工作中有机会学习与成长
        user:{
          id: currentUser.id,
          name: currentUser.name
        }
      }
    }
  },
  watch: {
    'editFormData.laidtype.id': {
      handler: function (val, oldVal) {
        this.$nextTick(() => {
          this.listLaidmaincauseOptions()
        })
      },
      deep: true
    }
  },
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
