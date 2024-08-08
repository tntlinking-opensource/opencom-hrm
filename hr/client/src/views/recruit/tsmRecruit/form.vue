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
          <el-form-item
            prop="postName"
            label="招聘职位"
            :rules="[
              { required: true, message: '招聘职位不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.postName"
              :maxLength="64"
              placeholder="请输入招聘岗位"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="staffNum"
            label="招聘人数"
            :rules="[
              { required: true, message: '招聘人数不能为空', trigger: 'blur' },
              {
                validator: isNumber,
                trigger: ['blur', 'change']
              }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.staffNum"
              :maxLength="12"
              placeholder="请输入招聘人数"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="workCity"
            label="工作城市"
            :rules="[
              { required: true, message: '工作城市不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_SingleInput"
          >
            <el-input
              v-model="editFormData.workCity"
              :maxLength="32"
              placeholder="请输入工作城市"
              clearable
              class="dc-el-input_SingleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="deptId"
            label="归属部门"
            :rules="[
              { required: true, message: '归属部门不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.deptId"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              @change="changeDepart"
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in departmentOptions"
                :key="index"
                :label="item.label"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="recruitType"
            label="招聘类型"
            :rules="[
              { required: true, message: '招聘类型不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.recruitType"
              :style="{ width: '100%' }"
              placeholder="请选择招聘类型"
              clearable
              value-key="value"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in recruitTypeOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="recruitExperience"
            label="工作经验"
            :rules="[
              { required: true, message: '工作经验不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.recruitExperience"
              :style="{ width: '100%' }"
              placeholder="工作经验"
              clearable
              value-key="value"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in recruitExperienceOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="recruitStudy"
            label="选择学历"
            :rules="[
              { required: true, message: '选择学历不能为空', trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.recruitStudy"
              :style="{ width: '100%' }"
              placeholder="请选择选择学历"
              clearable
              value-key="valuw"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in recruitStudyOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="salaryHigh"
            label="薪资范围"
            :rules="[
              {
                required: true,
                message: '薪资范围不能为空',
                trigger: 'change'
              },
              { validator: this.checkSalary, trigger: 'change' }
            ]"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.salaryLow"
              :style="{ width: '46%' }"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in salaryHighOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
            --
            <el-select
              v-model="editFormData.salaryHigh"
              :style="{ width: '46%' }"
              clearable
              value-key="id"
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in salaryLowOptions"
                :key="index"
                :label="item.name"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            prop="postDescribe"
            label="岗位描述"
            :rules="[
              { required: true, message: '岗位描述不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_MutilpleInput"
          >
            <el-input
              v-model="editFormData.postDescribe"
              type="textarea"
              placeholder="请输入"
              rows="2"
              :maxLength="-1"
              class="dc-el-input_MutilpleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item
            prop="postDemand"
            label="任职资格"
            :rules="[
              { required: true, message: '任职资格不能为空', trigger: 'blur' }
            ]"
            class="dc-el-form-item_MutilpleInput"
          >
            <el-input
              v-model="editFormData.postDemand"
              type="textarea"
              placeholder="请输入"
              rows="2"
              :maxLength="-1"
              class="dc-el-input_MutilpleInput"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="firstInterviewer"
            label="初试面试官"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.firstInterviewer"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in userOptions"
                :key="index"
                :label="item.name + '+' + item.phone"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="secondInterviewer"
            label="复试面试官"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.secondInterviewer"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in userOptions"
                :key="index"
                :label="item.name + '+' + item.phone"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item
            prop="finalInterviewer"
            label="最终面试官"
            class="dc-el-form-item_SelectInput"
          >
            <el-select
              v-model="editFormData.finalInterviewer"
              :style="{ width: '100%' }"
              placeholder="请选择"
              clearable
              filterable
              class="dc-el-select_SelectInput"
            >
              <el-option
                v-for="(item, index) in userOptions"
                :key="index"
                :label="item.name + '+' + item.phone"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </el-row>
</template>
<script>
import BizForm from "@/views/wf/common/bizForm";
import { validatenull } from "@/utils/validate";
/** 根据用户界面配置import组件 结束 */
import { listDictItemAll } from "@/api/sys/dictItem.js";
import { listAllByCompany } from "@/api/org/department";
import { listUserAll } from "@/api/admin/user";

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
} from "@/api/recruit/tsmRecruit";
import OperationIcon from "@/components/OperationIcon";
export default {
  extends: BizForm,
  name: "tsmRecruit-form",
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
        title: ""
      },
      // 选项变量
      // 归属部门选项
      departmentOptions: [],
      // 招聘类型选项
      recruitTypeOptions: [],
      // 工作经验选项
      recruitExperienceOptions: [],
      // 选择学历选项
      recruitStudyOptions: [],
      // 薪资上限选项
      salaryHighOptions: [],
      // 薪资下限选项
      salaryLowOptions: [],
      // 初试面试官选项
      userOptions: [],
      /** 根据用户界面配置生成data数据 结束 */

      // 窗口操作类型 view/edit/add
      action: ""
    };
  },
  props: {
    // 权限
    permission: {
      type: Object
    }
  },
  computed: {},
  methods: {
    isNumber(rule, value, callback) {
      if (isNaN(Number(value))) {
        callback(new Error("招聘人数为数字"));
      } else {
        callback();
      }
    },
    checkSalary(rule, value, callback) {
      if (!this.editFormData.salaryLow) {
        callback(new Error("请先选择工资下限"));
      } else {
        if (Number(this.editFormData.salaryLow) > Number(value)) {
          callback(new Error("工资上限要高于工资下限"));
        } else {
          callback();
        }
      }
    },
    loadModelData(taskId) {
      this.setLoad();
      getTsmRecruitByTaskId(taskId)
        .then(responseData => {
          if (responseData.code == 100) {
            this.editFormData = {
              ...this.initEditData(),
              ...responseData.data
            };
            this.$emit("after-load-data", this.editFormData); // 表单数据对象传给父节点
          } else {
            this.showMessage(responseData);
          }
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    //获取部门
    getDepartment() {
      listAllByCompany({}).then(res => {
        this.departmentOptions = res.data || [];
      });
    },
    //获取人员
    getUser() {
      listUserAll({}).then(res => {
        this.userOptions = res.data || [];
      });
    },

    // 保存草稿，files附件列表, draftModel 草稿对象
    doSaveDraft(draftModel, files) {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          if (!draftModel) {
            // 不是从草稿打开创建，保存时更新原草稿
            draftModel = {
              procdefId: this.procDef.id,
              procdefName: this.procDef.name,
              name: this.procDef.name + "-" + this.editFormData.name
            };
          }
          this.setLoad();
          let formData = this.createWfData(this.editFormData, files);
          formData.append("strDraft", JSON.stringify(draftModel));
          saveDraftTsmRecruit(formData)
            .then(responseData => {
              this.handleResponse(responseData);
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        }
      });
    },
    // 创建一个新流程 process流程 actNodes是提交的用户任务节点及其审批用户
    doCreate(files, actNodes) {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.setLoad();
          this.editFormData.actNodes = actNodes; // 选择节点及审批用户
          let formData = this.createWfData(this.editFormData, files);
          createTsmRecruit(this.procDef.id, formData)
            .then(responseData => {
              this.handleResponse(responseData);
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        }
      });
    },
    // 重新发起申请 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doReApply(comment, files, actNodes) {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.setLoad();
          this.editFormData.actNodes = actNodes; // 选择节点及审批用户
          this.editFormData.taskComement = comment; // 意见

          let formData = this.createWfData(this.editFormData, files);
          reapplyTsmRecruit(this.task.id, formData)
            .then(responseData => {
              this.handleResponse(responseData);
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        }
      });
    },
    // 同意 comment 意见, files文件附件列表，actNodes提交节点和用户信息
    doAgree(comment, files, actNodes) {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.setLoad();
          this.editFormData.actNodes = actNodes; // 选择节点及审批用户
          this.editFormData.taskComement = comment; // 意见

          let formData = this.createWfData(this.editFormData, files);
          agreeTsmRecruit(this.task.id, formData)
            .then(responseData => {
              this.handleResponse(responseData);
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        }
      });
    },
    // 转派任务
    doForward(comment, files, actNodes) {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.setLoad();
          this.editFormData.actNodes = actNodes; // 选择节点及审批用户
          this.editFormData.taskComement = comment; // 意见

          let formData = this.createWfData(this.editFormData, files);
          forwardTsmRecruit(this.task.id, formData)
            .then(responseData => {
              this.handleResponse(responseData);
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        }
      });
    },
    // 委派任务
    doCommission(comment, files, actNodes) {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.setLoad();
          this.editFormData.actNodes = actNodes; // 选择节点及审批用户
          this.editFormData.taskComement = comment; // 意见

          let formData = this.createWfData(this.editFormData, files);
          commissionTsmRecruit(this.task.id, formData)
            .then(responseData => {
              this.handleResponse(responseData);
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        }
      });
    },
    // 拟办（办理委派过来的任务）
    doPropose(comment, files) {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.setLoad();
          this.editFormData.taskComement = comment; // 意见

          let formData = this.createWfData(this.editFormData, files);
          proposeTsmRecruit(this.task.id, formData)
            .then(responseData => {
              this.handleResponse(responseData);
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        }
      });
    },
    // 拒绝
    doReject(comment, files) {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.setLoad();
          this.editFormData.taskComement = comment; // 意见

          let formData = this.createWfData(this.editFormData, files);
          rejectTsmRecruit(this.task.id, formData)
            .then(responseData => {
              this.handleResponse(responseData);
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        }
      });
    },
    // 退回 comment 意见, actNodes退回节点和用户信息
    doBack(comment, files, actNodes) {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.setLoad();
          this.editFormData.actNodes = actNodes; // 选择节点及审批用户
          this.editFormData.taskComement = comment; // 意见

          let formData = this.createWfData(this.editFormData, files);
          backTsmRecruit(this.task.id, formData)
            .then(responseData => {
              this.handleResponse(responseData);
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        }
      });
    },
    changeDepart(val) {
      let obj = this.departmentOptions.find(item => item.id === val);
      this.editFormData.deptName = obj.label;
    },
    // 终止流程 comment 意见, actNodes退回节点和用户信息
    doTerminate(comment, files) {
      this.setLoad();
      this.editFormData.taskComement = comment; // 意见

      let formData = this.createWfData(this.editFormData, files);
      terminateTsmRecruit(this.task.id, formData)
        .then(responseData => {
          if (responseData.code == 100) {
            this.dialogProps.visible = false;
            this.$emit("save-finished");
          }
          this.showMessage(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    // 撤回任务
    doReverse(task) {
      this.setLoad();
      reverseTsmRecruit(task.id)
        .then(responseData => {
          this.handleResponse(responseData);
          this.resetLoad();
        })
        .catch(error => {
          this.outputError(error);
        });
    },
    doListActivity() {
      this.$refs["editForm"].validate(valid => {
        if (valid) {
          this.setLoad();
          listBackActivity(this.task.id)
            .then(responseData => {
              this.handleResponse(responseData);
              this.resetLoad();
            })
            .catch(error => {
              this.outputError(error);
            });
        }
      });
    },

    listRecruitTypeOptions() {
      let search_List = {
        params: []
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "dict_type_id",
          queryType: "=",
          value: "1476148957913825287"
        }
      ]);

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1220000000000000004"
      );

      listDictItemAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.recruitTypeOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },

    listRecruitExperienceOptions() {
      let search_List = {
        params: []
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "dict_type_id",
          queryType: "=",
          value: "1474869366897205488"
        }
      ]);

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1220000000000000004"
      );

      listDictItemAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.recruitExperienceOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },

    listRecruitStudyOptions() {
      let search_List = {
        params: []
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "dict_type_id",
          queryType: "=",
          value: "1474869366897205495"
        }
      ]);

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1220000000000000004"
      );

      listDictItemAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.recruitStudyOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },

    listSalaryHighOptions() {
      let search_List = {
        params: []
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "dict_type_id",
          queryType: "=",
          value: "1474869366897205502"
        }
      ]);

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1220000000000000004"
      );

      listDictItemAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.salaryHighOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },

    listSalaryLowOptions() {
      let search_List = {
        params: []
      };
      // filter条件
      search_List.params.push.apply(search_List.params, [
        {
          columnName: "dict_type_id",
          queryType: "=",
          value: "1474869366897205502"
        }
      ]);

      // 数据权限: 字典项sys_dict_item
      this.pushDataPermissions(
        search_List.params,
        this.$route.meta.routerId,
        "1220000000000000004"
      );

      listDictItemAll(search_List).then(responseData => {
        if (responseData.code == 100) {
          this.salaryLowOptions = responseData.data;
        } else {
          this.showMessage(responseData);
        }
      });
    },

    initOptions(This) {
      // 初始化自定义类型选择框选项变量

      this.listRecruitTypeOptions();

      this.listRecruitExperienceOptions();

      this.listRecruitStudyOptions();

      this.listSalaryHighOptions();

      this.listSalaryLowOptions();
      this.getDepartment();
      this.getUser();
    },
    initEditData(This) {
      return {
        postName: "", // 招聘职位
        staffNum: "", // 招聘人数
        workCity: "", // 工作城市
        deptName: null, // 归属部门
        deptId: "",
        recruitType: "",
        recruitExperience: "",
        recruitStudy: "",
        salaryHigh: "",
        salaryLow: "",
        postDescribe: "", // 岗位描述
        postDemand: "", // 任职资格
        firstInterviewer: null, // 初试面试官
        secondInterviewer: null, // 复试面试官
        finalInterviewer: null // 最终面试官
      };
    }
  },
  watch: {},
  mounted: function() {
    this.$nextTick(() => {
      this.dialogProps.visible = true;
      this.initOptions();
      this.tabIndex = "1";
      if (this.wfAction == "create") {
        this.$refs["editForm"].resetFields();
        // 从草稿创建
        if (this.bizData) {
          this.editFormData = {
            ...this.initEditData(),
            ...this.bizData
          };
        }
      } else if (this.wfAction == "approve") {
        // 重新申请
        let reApply =
          this.task.assignee == this.task.owner &&
          this.task.assignee == this.task.extInfo.applicantId &&
          this.task.taskDefinitionKey == this.task.extInfo.appActId;
        if (!reApply) {
          this.action = "view";
        }
        this.loadModelData(this.task.id);
      } else if (this.wfAction == "viewBiz" || this.wfAction == "proStart") {
        this.action = "view";
        this.editFormData = {
          ...this.initEditData(),
          ...this.bizData
        };
      } else if (this.wfAction == "viewTask") {
        this.action = "view";
        this.loadModelData(this.task.id);
      } else if (this.wfAction == "edit") {
        this.action = "edit";
        this.editFormData = {
          ...this.initEditData(),
          ...this.bizData
        };
      } else {
        console.err("=============== 业务审批单中，实现的：" + this.wfAction);
      }
    });
  }
};
</script>
