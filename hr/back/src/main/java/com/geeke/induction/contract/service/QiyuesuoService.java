package com.geeke.induction.contract.service;

import com.geeke.config.exception.CommonJsonException;
import com.geeke.induction.contract.entity.InductionBatchContract;
import com.geeke.induction.contract.entity.TsmContract;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.sys.entity.DictItem;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.qiyuesuo.sdk.v2.SdkClient;
import com.qiyuesuo.sdk.v2.bean.*;
import com.qiyuesuo.sdk.v2.json.JSONUtils;
import com.qiyuesuo.sdk.v2.request.*;
import com.qiyuesuo.sdk.v2.response.ContractPageResult;
import com.qiyuesuo.sdk.v2.response.DocumentAddResult;
import com.qiyuesuo.sdk.v2.response.SdkResponse;
import com.qiyuesuo.sdk.v2.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("qiyuesuoService")
public class QiyuesuoService {

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${qiyuesuo.serverUrl}")
    private String serverUrl;
    @Value("${qiyuesuo.accessKey}")
    private String accessKey;
    @Value("${qiyuesuo.accessSecret}")
    private String accessSecret;
    @Value("${qiyuesuo.categoryId}")
    private String categoryId;

    /**
     * 创建合同草稿
     * @param theme 合约主题
     * @param name 入职人员名称
     * @param phoneNumber 入职人员联系方式
     * @return
     */
    public Contract createContract(String theme, String name, String phoneNumber) {
        SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
        // 合同基本参数
        Contract contract = new Contract();
        contract.setSubject(theme);
        contract.setCategory(new Category(Long.valueOf(categoryId)));
        contract.setSend(false);
        // 个人
        Signatory signatory1 = new Signatory();
        signatory1.setTenantName(name);
        signatory1.setTenantType("PERSONAL");
        signatory1.setReceiver(new User(name, phoneNumber, "MOBILE"));
        signatory1.setSerialNo(1);
        // 对接方
        Signatory signatory2 = new Signatory();
        signatory2.setTenantName("成都新致云服测试公司");
        signatory2.setTenantType("COMPANY");
        // TODO 公司人员信息，暂时固定
        signatory2.setReceiver(new User("罗一秀", "13708206115", "MOBILE"));
        signatory2.setSerialNo(2);
        Action action = new Action("COMPANY", 1);
        signatory2.addAction(action);
        // 设置签署方
        contract.addSignatory(signatory1);
        contract.addSignatory(signatory2);
        // 发起合同
        ContractDraftRequest request = new ContractDraftRequest(contract);
        String response = sdkClient.service(request);
        SdkResponse<Contract> responseObj = JSONUtils.toQysResponse(response, Contract.class);
        // 返回结果
        if(responseObj.getCode() != 0) {
            logger.info("创建合同草稿请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "请求失败，" + responseObj.getMessage()));
        }
        Contract result = responseObj.getResult();
        System.out.println("创建合同成功 = " + result);
        return result;
    }

    /**
     * 用模板添加合同文档
     * @param contractId 合同ID
     * @param title 文档标题
     * @param template 模板参数
     * @param templateId 模板ID
     * @return
     */
    public DocumentAddResult createTemplate(Long contractId, String title, List<TemplateParam> template, Long templateId) {
        SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
        // 添加合同文档
        DocumentAddByTemplateRequest request = new DocumentAddByTemplateRequest(contractId, templateId, template, title);
        String response = sdkClient.service(request);
        SdkResponse<DocumentAddResult> responseObj = JSONUtils.toQysResponse(response, DocumentAddResult.class);
        if(responseObj.getCode() != 0) {
            logger.info("生成文档请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "生成文档请求失败，" + responseObj.getMessage()));
        }
        DocumentAddResult result = responseObj.getResult();
        return result;
    }

    /**
     * 发起合约
     * @param contractId
     * @return
     */
    public void start(Long contractId) {
        SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
        // 发起合同
        ContractSendRequest request = new ContractSendRequest(contractId, new ArrayList<>());
        String response = sdkClient.service(request);
        SdkResponse<Object> responseObj = JSONUtils.toQysResponse(response);
        if(responseObj.getCode() != 0) {
            logger.info("发起合约请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "发起合约错误，" + responseObj.getMessage()));
        }
    }

    /**
     * 查看报告
     * @param contractId
     * @return
     */
    public String report(Long contractId) {
        SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
        // 合同页面
        ContractPageRequest request = new ContractPageRequest(contractId,
                new User("13708206115", "MOBILE"), "");
        String response = sdkClient.service(request);
        SdkResponse<ContractPageResult> responseObj = JSONUtils.toQysResponse(response, ContractPageResult.class);
        if(responseObj.getCode() != 0) {
            logger.info("报告文档请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "报告文档请求失败，" +  responseObj.getMessage()));
        }
        ContractPageResult result = responseObj.getResult();
        return result.getPageUrl();
    }

    /**
     * 预览合同文档
     * @param contractId
     * @return
     */
    public String viewContract(Long contractId) {
        SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
        ContractViewPageRequest request=new ContractViewPageRequest(contractId);
        String response = sdkClient.service(request);
        SdkResponse<ContractPageResult> responseObj = JSONUtils.toQysResponse(response, ContractPageResult.class);
        if(responseObj.getCode() != 0) {
            logger.info("请求失败，错误码:{}，错误信息:{}", responseObj.getCode(), responseObj.getMessage());
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "预览文档请求失败，" +  responseObj.getMessage()));
        }
        ContractPageResult result = responseObj.getResult();
        return result.getPageUrl();
    }

    /**
     * 下载合同文档
     * @param contractFileId
     * @return
     */
    public void uploadContract(Long contractFileId, HttpServletResponse response) throws Exception {
        // 初始化sdkClient
        SdkClient sdkClient = new SdkClient(serverUrl, accessKey, accessSecret);
        // 下载合同文档
        DocumentDownloadRequest request = new DocumentDownloadRequest(contractFileId);
        OutputStream outputStream = null;
        outputStream = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment; filename= 入职.pdf");
        sdkClient.download(request, outputStream);
        IOUtils.safeClose(outputStream);
        logger.info("下载合同文档成功");
    }

}
