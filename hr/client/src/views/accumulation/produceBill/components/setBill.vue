<template>
    <el-dialog title="账单设置" :visible.sync="dialogVisible" width="500px">
        <div style='display: flex;align-items: center;'>
            <div style="width:135px">员工离职日期在当月</div><number-input v-model="day" style="width:80px" :maxLength="64"
                placeholder="账单日期设置" clearable :min="1" :max="31" class="dc-el-input_SingleInput"></number-input>
            <div style="width:200px"> 号之前不再进行计算社保公积金</div>
        </div>
        <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitMonth">保 存</el-button>
            <el-button @click="dialogVisible = false">返 回</el-button>
        </span>
    </el-dialog>
</template>
<script>

import { listAllBill, saveBillDetails } from "@/api/socialSecurityFund/socialSecurityFund";
import BaseUI from '@/views/components/baseUI'
export default {
    extends: BaseUI,
    data() {
        return {
            dialogVisible: false,
            day: '',
            billDetailsId: '',
            updateBy: '',
            updateDate: '',
            tableObj: {
                id: '',
                batchNo: 'bill_default_config',
                billYearMonth: '',
                updateBy: '',
                updateDate: ''
            }
        }
    },
    methods: {
        getList() {
            let search = {
                params: [
                    {
                        "columnName": "batch_no",
                        "queryType": "=",
                        "value": 'bill_default_config'
                    }
                ],
                offset: 0,
                limit: 10,
                columnName: '', // 排序字段名
                order: '' // 排序
            }
            listAllBill(search).then((responseData) => {
                if (responseData.code == 100) {
                    this.billDetailsId = responseData.data ? responseData.data[0].id : '',
                        this.updateBy = responseData.data ? responseData.data[0].updateBy : null,
                        this.updateDate = responseData.data ? responseData.data[0].updateDate : null,
                        this.day = responseData.data ? responseData.data[0].billYearMonth : '1'
                } else {
                    this.showMessage(responseData)
                }
            })

        },
        submitMonth() {
            this.tableObj.billYearMonth = this.day
            this.tableObj.id = this.billDetailsId
            this.tableObj.updateBy = this.updateBy
            this.tableObj.updateDate = this.updateDate
            saveBillDetails(this.tableObj).then(responseData => {
                if (responseData.code == 100) {
                    this.$message.success('账单日期设置成功')

                    this.dialogVisible = true
                } else {
                    this.showMessage(responseData)
                }
            })
                .catch(error => {
                    this.outputError(error)
                })
        },
        init() {
            this.dialogVisible = true
            this.getList()
        }
    }
}
</script>