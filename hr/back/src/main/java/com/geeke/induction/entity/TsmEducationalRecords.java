package com.geeke.induction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.sys.entity.DictItem;
import com.geeke.wf.persistence.WfDataEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * inductionEntity
 * @author
 * @version
 */
public class TsmEducationalRecords extends WfDataEntity<TsmEducationalRecords> {

	private static final long serialVersionUID = 1457254743943432654L;

	private String inductionId; // 员工ID

	private String graduatedSchool; // 毕业学校

	private String major; // 专业

	private DictItem highestEducation; // 最高学历

	private DictItem educationMethod; // 教育方式

	private String admissionTime; // 入学时间

	private String graduationTime; // 毕业时间

	private String diplomaNumber; // 毕业证编号

	private String degreeCertificateNumber; // 学位证编号

	// 构造方法
	public TsmEducationalRecords() {
		super();
	}

	public TsmEducationalRecords(String id) {
		super(id);
	}

	// 生成get和set方法

	@Length(min = 1, max = 20, message = "员工ID长度必须介于 1 和 20 之间")
	public String getInductionId() {
		return inductionId;
	}

	public void setInductionId(String inductionId) {
		this.inductionId = inductionId;
	}

	@Length(min = 1, max = 128, message = "毕业学校长度必须介于 1 和 128 之间")
	public String getGraduatedSchool() {
		return graduatedSchool;
	}

	public void setGraduatedSchool(String graduatedSchool) {
		this.graduatedSchool = graduatedSchool;
	}

	@Length(min = 1, max = 128, message = "专业长度必须介于 1 和 128 之间")
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@NotNull(message = "最高学历不能为空")
	public DictItem getHighestEducation() {
		return highestEducation;
	}

	public void setHighestEducation(DictItem highestEducation) {
		this.highestEducation = highestEducation;
	}

	@NotNull(message = "教育方式不能为空")
	public DictItem getEducationMethod() {
		return educationMethod;
	}

	public void setEducationMethod(DictItem educationMethod) {
		this.educationMethod = educationMethod;
	}

	@Length(min = 1, max = 64, message = "入学时间长度必须介于 1 和 64 之间")
	public String getAdmissionTime() {
		return admissionTime;
	}

	public void setAdmissionTime(String admissionTime) {
		this.admissionTime = admissionTime;
	}

	@Length(min = 1, max = 64, message = "毕业时间长度必须介于 1 和 64 之间")
	public String getGraduationTime() {
		return graduationTime;
	}

	public void setGraduationTime(String graduationTime) {
		this.graduationTime = graduationTime;
	}

	@Length(min = 1, max = 128, message = "毕业证编号长度必须介于 1 和 128 之间")
	public String getDiplomaNumber() {
		return diplomaNumber;
	}

	public void setDiplomaNumber(String diplomaNumber) {
		this.diplomaNumber = diplomaNumber;
	}

	@Length(min = 1, max = 128, message = "学位证编号长度必须介于 1 和 128 之间")
	public String getDegreeCertificateNumber() {
		return degreeCertificateNumber;
	}

	public void setDegreeCertificateNumber(String degreeCertificateNumber) {
		this.degreeCertificateNumber = degreeCertificateNumber;
	}

	/**
	 * 获取实体对应表名
	 */
	@Override
	@JsonIgnore
	public String getBusTableName() {
		return "tsm_educational_records";
	}

	/**
	 * 获取实体对应Id
	 */
	@Override
	@JsonIgnore
	public String getBusTableId() {
		return "1457254743943432654";
	}

	/**
	 * 获取实体对应描述
	 */
	@Override
	@JsonIgnore
	public String getBusTableComments() {
		return "教育经历";
	}

	/**
	 * 获取实体表中是否存在del_flag字段
	 */
	@JsonIgnore
	public boolean getBusTableHasDelFlag() {
		return true;
	}
}
