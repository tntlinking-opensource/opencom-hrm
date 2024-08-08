package com.geeke.wf.persistence;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.admin.entity.User;
import com.geeke.camundaex.entity.ActNode;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.DictItem;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.camunda.bpm.engine.history.HistoricProcessInstance;


/**
 * 数据Entity类
 * @author lys
 * @version 2020-4-12

 */
@ApiModel(description = "数据Entity类")
public abstract class WfDataEntity<T> extends DataEntity<T> {

	@ApiModelProperty(hidden = true)
	private static final long serialVersionUID = 5201960367259755174L;
	@ApiModelProperty("流程实例Id")
	protected String procInst;		//流程实例Id
	@ApiModelProperty("流程实例状态")
	protected DictItem procStatus;	// 流程实例状态
	
	@ApiModelProperty("审批备注   无需保存到数据库中")
	protected String taskComement;		// 审批备注   无需保存到数据库中
	
	@ApiModelProperty("可选节节点")
	private List<ActNode> actNodes;	//可选节节点
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	@JsonIgnore
	public void preInsert(){
		super.preInsert();
		setProcStatusByValue(PROC_FLAG_APPROVE);
	}
	
	public WfDataEntity() {
		super();
	}
	
	public WfDataEntity(String id) {
		super(id);
	}
	
	
	public String getProcInst() {
		return procInst;
	}

	public void setProcInst(String procInst) {
		this.procInst = procInst;
	}

	public DictItem getProcStatus() {
		return procStatus;
	}

	public void setProcStatus(DictItem procStatus) {
		this.procStatus = procStatus;
	}

	public String getTaskComement() {
		return taskComement;
	}

	public void setTaskComement(String taskComement) {
		this.taskComement = taskComement;
	}


	public List<ActNode> getActNodes() {
		return actNodes;
	}

	public void setActNodes(List<ActNode> actNodes) {
		this.actNodes = actNodes;
	}

	/**
	 * 设置审批状态
	 * @param state
	 */
	@JsonIgnore
	public void setProcStatusByValue(String state) {
		DictItem di = new DictItem();
		di.setValue(state);
		if (PROC_FLAG_DRAFT.equals(state)) {
            di.setId("8047");
        }
        if (PROC_FLAG_APPROVE.equals(state)) {
            di.setId("8045");
        }
        if (PROC_FLAG_COMPLETED.equals(state)) {
            di.setId("8044");
        }
        if (PROC_FLAG_TERMINATED.equals(state)) {
            di.setId("8046");
        }
		setProcStatus(di);
	}

	/**
	 * 是否审批结束
	 * @return
	 */
	public boolean isApproved() {
		return procStatus != null && PROC_FLAG_COMPLETED.equals(procStatus.getValue());
	}
	
	/**
	 * 流程状态（DRAFT：草稿；ACTIVE：审批中；COMPLETED：已结束；TERMINATED：已终止；） 
	 */
	@ApiModelProperty("流程状态（DRAFT：草稿；ACTIVE：审批中；COMPLETED：已结束；TERMINATED：已终止；）")
	public static final String PROC_FLAG_DRAFT = "DRAFT";
	@ApiModelProperty(hidden = true)
	public static final String PROC_FLAG_APPROVE = HistoricProcessInstance.STATE_ACTIVE;
	@ApiModelProperty(hidden = true)
	public static final String PROC_FLAG_COMPLETED = HistoricProcessInstance.STATE_COMPLETED;
	@ApiModelProperty(hidden = true)
	public static final String PROC_FLAG_TERMINATED = "TERMINATED";
}
