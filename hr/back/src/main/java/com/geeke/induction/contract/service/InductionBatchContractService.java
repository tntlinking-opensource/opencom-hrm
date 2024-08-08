package com.geeke.induction.contract.service;

import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.sequence.service.SequenceService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.induction.contract.dao.InductionBatchContractDao;
import com.geeke.induction.contract.dao.TsmContractDao;
import com.geeke.induction.contract.entity.InductionBatchContract;
import com.geeke.induction.contract.entity.TsmContract;
import com.geeke.induction.dto.ContractDemandDto;
import com.geeke.induction.entity.TsmContractInformation;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.entity.TsmInductionInfo;
import com.geeke.induction.enums.OperationEnum;
import com.geeke.sys.entity.DictItem;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.wf.service.BizInProcService;
import com.google.common.collect.Lists;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qiyuesuo.sdk.v2.bean.*;
import com.qiyuesuo.sdk.v2.json.JSONUtils;
import com.qiyuesuo.sdk.v2.request.*;
import com.qiyuesuo.sdk.v2.response.ContractPageResult;
import com.qiyuesuo.sdk.v2.response.DocumentAddResult;
import com.qiyuesuo.sdk.v2.response.SdkResponse;
import com.qiyuesuo.sdk.v2.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qiyuesuo.sdk.v2.SdkClient;

import javax.servlet.http.HttpServletResponse;

/**
 * 合约发起Service
 * @author
 * @version
 */

@Service("inductionBatchContractService")
@Transactional(readOnly = true)
public class InductionBatchContractService extends BizInProcService<InductionBatchContractDao, InductionBatchContract> {

    @Value("${qiyuesuo.templateId}")
    private String templateId;

    @Autowired
    private QiyuesuoService qiyuesuoService;

    @Autowired
    SequenceService sequenceService;

    @Autowired
    private TsmContractDao tsmContractDao;

    @Autowired
    private TsmContractService tsmContractService;

    /**
     * 同步合约信息
     * @param contractDemandDto 合约信息、岗位信息等
     * @param type 'edit'：更新，'add'：新增
     * @return
     */
    public boolean saveOrUpdate(ContractDemandDto contractDemandDto, OperationEnum type) {
        InductionBatchContract contract = cloneEntity(contractDemandDto, null); // 类型转换
        TsmContractInformation information = contractDemandDto.getTsmContractInformation();
        DictItem contractStatus = new DictItem();
        contractStatus.setValue("0");
        DictItem signStatus = new DictItem();
        signStatus.setValue("0");
        if (type == OperationEnum.EDIT) { // 更新操作
            // 获取要修改的记录(根据informationID、（签署状态） != 6(已作废))
            PageRequest pageRequest;
            List<Parameter> params = Lists.newArrayList();
            params.add(new Parameter("contract_information_id", "=", information.getId()));
            params.add(new Parameter("sign_status", "!=", "6"));
            pageRequest = new PageRequest(params);
            List<InductionBatchContract> contractList = dao.listAll(pageRequest);
            if (null == contractList || contractList.size() <= 0) {
                contract.preInsert();
                String sn = sequenceService.generate(SessionUtils.getUser().getCompanyId(), "HTBH", contract);
                contract.setCode(sn);
                contract.setContractStatus(contractStatus); // 合约状态
                contract.setSignStatus(signStatus); // 签署状态
                dao.insert(contract);
            } else { // 修改可能存在多条数据
                List<InductionBatchContract> inductionList = new ArrayList<>();
                for (int i = 0; i < contractList.size(); i++) {
                    InductionBatchContract newContract = new InductionBatchContract();
                    newContract = cloneEntity(contractDemandDto, contractList.get(i)); // 类型转换
                    newContract.preUpdate();
                    newContract.setId(contractList.get(i).getId());
                    inductionList.add(newContract);
                }
                dao.bulkUpdate(inductionList);
            }
        } else { // 新增操作
            contract.preInsert();
            // 新增时, 处理自动编号字段
            String sn = sequenceService.generate(SessionUtils.getUser().getCompanyId(), "HTBH", contract);
            contract.setCode(sn);
            contract.setContractStatus(contractStatus); // 合约状态
            contract.setSignStatus(signStatus); // 签署状态
            dao.insert(contract);
        }
        return true;
    }

    public InductionBatchContract cloneEntity(ContractDemandDto contractDemandDto, InductionBatchContract contract) {
        TsmContractInformation information = contractDemandDto.getTsmContractInformation(); // 合约信息
        TsmInductionInfo info = contractDemandDto.getInfo(); // 个人信息补充
        TsmInductionBasic basic = contractDemandDto.getBasic();
        if (StringUtils.isNullOrEmpty(contract)) {
            contract = new InductionBatchContract(); // 发起合约
        }
        contract.setContractInformation(information); // 合约信息
        contract.setInduction(new TsmInductionBasic(information.getInductionId())); // 入职员工
        contract.setTheme(basic.getName() + "-入职合同"); // 合约主题，需要获取到入职人员名称
        contract.setEntryTime(information.getEntryTime()); // 入职时间
        contract.setProbationMonth(information.getProbationMonth()); // 试用期
        contract.setEmployeeLevel(information.getEmployeeLevel()); // 员工职级
        contract.setContractStartTime(information.getContractStartTime()); // 合同开始时间
        contract.setContractEndTime(information.getContractEndTime()); // 合同结束时间
        contract.setProbationEndTime(information.getProbationEndTime()); // 试用期结束时间
        contract.setTechPosition(information.getTechPosition()); // 员工职位
        contract.setBjob(information.getJobType()); // 从事工作
        contract.setEmployeeNumber(info.getEmployeeNumber()); // 工号
        contract.setContractSignUnit(information.getContractSignUnit()); // 合同签约单位
        contract.setLaborLegalmana(information.getCompanyLegalPerson()); // 合同签约方法人
        contract.setCompany(contractDemandDto.getCompany()); // 公司
        contract.setDepartment(contractDemandDto.getDepartment()); // 部门
        contract.setCompanyEmail(info.getCompanyEmail()); // 公司邮箱
        contract.setReportAddress(information.getReportAddress()); // 甲方住所地
        contract.setFullWages(information.getFullWages()); // 全额工资（元）
        contract.setProbationWages(information.getProbationWages()); // 试用期工资（元）
        contract.setBasicWages(information.getBasicWages()); // 基本工资（元）
        contract.setProbationBasicWages(information.getProbationBasicWages()); // 试用期基本工资（元）
        contract.setSecrecyReward(information.getSecrecyReward()); // 保密奖（元）
        contract.setSystemReward(information.getSystemReward()); // 制度奖（元）
        contract.setBasicPerformance(information.getBasicPerformance()); // 基本绩效（元）
        contract.setFloatingPerformance(information.getFloatingPerformance()); // 浮动绩效（元）
        contract.setProbationBasicPerformance(information.getProbationBasicPerformance()); // 试用期基本绩效（元）
        contract.setProbationFloatingPerformance(information.getProbationFloatingPerformance()); // 试用期浮动绩效（元）
        return contract;
    }

    /**
     * 批量生成合约
     * @param entitys
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<String> bulkGenerate(List<InductionBatchContract> entitys) {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < entitys.size(); i++) {
            InductionBatchContract generate = generate(entitys.get(i).getId());
            ids.add(generate.getId());
        }
        return ids;
    }

    /**
     * 生成合约
     * @param id
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public InductionBatchContract generate(String id) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        InductionBatchContract induction = super.get(id);
        TsmInductionBasic basic = induction.getInduction();
        if (StringUtils.isNullOrEmpty(basic)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "获取人员基本信息失败！"));
        }
        // 创建合同草稿（设置基本信息）
        Contract contract = qiyuesuoService.createContract(induction.getTheme(), basic.getName(), basic.getPhoneNumber());
        // 用模板添加合同文档
        DocumentAddResult template = createTemplate(induction, contract.getId());
        // 更新记录当前记录中合同生成时间、合约生成状态
        DictItem status = new DictItem();
        status.setValue("1");
        induction.setContractStatus(status);
        induction.setContractDate(format.format(new Date()));
        InductionBatchContract save = super.save(induction);
        // 获取契约锁返回的合同ID、模板ID，保存数据到合同表
        tsmContractService.saveTsmContract(save.getId(), contract, template);
        return save;
    }

    /**
     * 用模板添加合同文档
     * @param induction
     * @param contractId
     * @return
     */
    public DocumentAddResult createTemplate(InductionBatchContract induction, Long contractId) {
        String title = "入职合同";
        TsmInductionBasic base = induction.getInduction(); // 入职员工基本信息
        if (!StringUtils.isNullOrEmpty(base)) {
            title = base.getName() + "-入职合同";
        }
        // 添加合同文档
        List<TemplateParam> template = setTemplate(induction);
        DocumentAddResult result = qiyuesuoService.createTemplate(contractId, title, template, Long.valueOf(templateId));
        return result;
    }

    public List<TemplateParam> setTemplate(InductionBatchContract induction) {
        List<TemplateParam> params = new ArrayList<>();
        TsmInductionBasic base = induction.getInduction(); // 入职员工基本信息
        if (!StringUtils.isNullOrEmpty(base)) {
            params.add(new TemplateParam("partb", base.getName())); // 乙方-姓名
            params.add(new TemplateParam("credentiano", base.getIdNumber())); // 乙方-身份证号
            params.add(new TemplateParam("address", base.getResidenceAddress())); // 乙方-居住地地址
            params.add(new TemplateParam("mobile", base.getPhoneNumber())); // 乙方-联系方式
            params.add(new TemplateParam("email", base.getEmail())); // 乙方-邮箱
        }
        params.add(new TemplateParam("workParta", induction.getContractSignUnit())); // 甲方
        params.add(new TemplateParam("workAddressa", induction.getReportAddress())); // 甲方-住所地
        params.add(new TemplateParam("startTime", induction.getContractStartTime())); // 合同开始时间
        params.add(new TemplateParam("endTime", induction.getContractEndTime())); // 合同结束时间
        params.add(new TemplateParam("testStartTime", induction.getContractStartTime())); // 试用期开始时间
        params.add(new TemplateParam("testEndTime", induction.getProbationEndTime())); // 试用期结束时间
        params.add(new TemplateParam("post", induction.getTechPosition().getName())); // 员工职位
        params.add(new TemplateParam("rank", induction.getEmployeeLevel())); // 员工职级
        params.add(new TemplateParam("workLegalmana", induction.getLaborLegalmana())); // 法定代表人
        params.add(new TemplateParam("bWages", induction.getBasicWages())); // 基本工资
        params.add(new TemplateParam("tryOutBasic", induction.getProbationBasicWages())); // 试用期基本工资
        params.add(new TemplateParam("instBonus", induction.getSystemReward())); // 制度奖
        params.add(new TemplateParam("secretBonus", induction.getSecrecyReward())); // 保密金
        params.add(new TemplateParam("basicPerf", induction.getBasicPerformance())); // 基本绩效
        params.add(new TemplateParam("testBasicPerf", induction.getProbationBasicPerformance())); // 试用期基本绩效
        params.add(new TemplateParam("floatPerf", induction.getFloatingPerformance())); // 浮动绩效
        params.add(new TemplateParam("testFloatPerf", induction.getProbationFloatingPerformance())); // 试用期浮动绩效
        return params;
    }

    /**
     * 批量发起合约
     * @param entitys
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<String> bulkStart(List<InductionBatchContract> entitys) {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < entitys.size(); i++) {
            InductionBatchContract start = start(entitys.get(i).getId());
            ids.add(start.getId());
        }
        return ids;
    }

    /**
     * 发起合约
     * @param id
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public InductionBatchContract start(String id) {
        InductionBatchContract induction = super.get(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TsmContract byBatchContract = tsmContractDao.getByBatchContractId(id);
        if (StringUtils.isNullOrEmpty(byBatchContract)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "发起合约错误，请先执行生成合约操作！"));
        }
        // 契约锁发起合约
        qiyuesuoService.start(Long.valueOf(byBatchContract.getContractId()));
        // 更改合约表信息
        DictItem status = new DictItem();
        status.setValue("9");
        induction.setContractStatus(status);
        // 更改签署状态
        DictItem sign = new DictItem();
        sign.setValue("4"); // 待签字
        induction.setSignStatus(sign);
        induction.setContractStartDate(format.format(new Date())); // 合约发起时间
        InductionBatchContract save = super.save(induction);
        return save;
    }

    /**
     * 查看报告
     * @param id
     * @return
     */
    public String report(String id) {
        TsmContract byBatchContract = tsmContractDao.getByBatchContractId(id);
        if (StringUtils.isNullOrEmpty(byBatchContract)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "查看报告错误，请重新生成合约！"));
        }
        String pageUrl = qiyuesuoService.report(Long.valueOf(byBatchContract.getContractId()));
        return pageUrl;
    }

    /**
     * 预览合同文档
     * @param id
     * @return
     */
    public String viewContract(String id) {
        TsmContract tsmContract = tsmContractDao.getByBatchContractId(id);
        if (StringUtils.isNullOrEmpty(tsmContract)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "预览文档错误，请重新生成合约！"));
        }
        String pageUrl = qiyuesuoService.viewContract(Long.valueOf(tsmContract.getContractId()));
        return pageUrl;
    }

    /**
     * 下载合同文档
     * @param id
     * @return
     */
    public void uploadContract(String id, HttpServletResponse response) throws Exception {
        TsmContract tsmContract = tsmContractDao.getByBatchContractId(id);
        if (StringUtils.isNullOrEmpty(tsmContract)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "下载文档错误，请重新生成合约！"));
        }
        qiyuesuoService.uploadContract(Long.valueOf(tsmContract.getContractFileId()), response);
    }

    /**
     * 发起签署--创建流程
     * @param procDefId
     * @param entity
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public ActResult firstCreate(String procDefId, InductionBatchContract entity) {
        entity.preUpdate();
        ActResult actResult = wfUtils.create(procDefId, entity, null, this);
        if(actResult.getCode() == ActResult.EnumCodes.AutoSubmit || actResult.getCode() == ActResult.EnumCodes.Done) {
            this.doUpdate(entity);
        }
        return actResult;
    }

    /**
     * 批量发起签署--创建流程
     * @param procDefId
     * @param entitys
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<ActResult> bulkSignCreate(String procDefId, List<InductionBatchContract> entitys) {
        List<ActResult> list = new ArrayList<>();
        for (int i = 0; i < entitys.size(); i++) {
            entitys.get(i).preUpdate();
            ActResult actResult = wfUtils.create(procDefId, entitys.get(i), null, this);
            if(actResult.getCode() == ActResult.EnumCodes.AutoSubmit || actResult.getCode() == ActResult.EnumCodes.Done) {
                this.doUpdate(entitys.get(i));
            }
            list.add(actResult);
        }
        return list;
    }

    /**
     * 批量添加
     * @param entitys
     * @return
     */
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<String> bulkInsert(List<InductionBatchContract> entitys) {
        List<String> ids = Lists.newArrayList();
        DictItem contractStatus = new DictItem();
        contractStatus.setValue("0");
        DictItem signStatus = new DictItem();
        signStatus.setValue("0");
        for(InductionBatchContract entity: entitys) {
            // 设置自动编号
            String sn = sequenceService.generate(SessionUtils.getUser().getCompanyId(), "HTBH", entity);
            entity.setCode(sn);
            entity.setContractDate(""); // 合约生成时间
            entity.setContractStartDate(""); // 合约发起时间
            entity.setContractStatus(contractStatus); // 合约状态
            entity.setSignStatus(signStatus); // 签署状态
            entity.preInsert();
            ids.add(entity.getId());
        }
        dao.bulkInsert(entitys);
        return ids;
    }

    @Override
    protected Class<InductionBatchContract> getBizClass() {
        return InductionBatchContract.class;
    }
}
