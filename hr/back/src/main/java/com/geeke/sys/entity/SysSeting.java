package com.geeke.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.geeke.common.persistence.DataEntity;
import com.geeke.sys.entity.SysFile;
import com.google.common.collect.Lists;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
 * 系统设置Entity
 * @author
 * @version
 */

@ApiModel(description = "系统设置Entity")
public class SysSeting extends DataEntity<SysSeting> {

    @ApiModelProperty(hidden = true)
    private static final long serialVersionUID = 1332267278623703084L;

    @ApiModelProperty("sys_name")
    private String sysName; // sys_name

    @ApiModelProperty("sys_abbrname")
    private String sysAbbrname; // sys_abbrname

    @ApiModelProperty("登录背景色")
    private String loginBgcolor; // 登录背景色

    @ApiModelProperty("登陆布局")
    private String loginLayout; // 登陆布局

    @ApiModelProperty("项目标志")
    private SysFile projectLogo; // 项目标志

    @ApiModelProperty("登录标志")
    private SysFile loginLogo; // 登录标志

    @ApiModelProperty("系统标志")
    private SysFile sysLogo; // 系统标志

    @ApiModelProperty("系统图标")
    private SysFile favicon; // 系统图标

    @ApiModelProperty("登录背景图")
    private SysFile loginBg; // 登录背景图

    @ApiModelProperty("登录配图")
    private SysFile loginGraph; // 登录配图

    @ApiModelProperty("项目标志")
    private Integer projectLogoIndex; // 项目标志
    @ApiModelProperty("登录标志")
    private Integer loginLogoIndex; // 登录标志
    @ApiModelProperty("系统标志")
    private Integer sysLogoIndex; // 系统标志
    @ApiModelProperty("系统图标")
    private Integer faviconIndex; // 系统图标
    @ApiModelProperty("登录背景图")
    private Integer loginBgIndex; // 登录背景图
    @ApiModelProperty("登录配图")
    private Integer loginGraphIndex; // 登录配图

    // 构造方法
    public SysSeting() {
        super();
    }

    public SysSeting(String id) {
        super(id);
    }

    // 生成get和set方法

    @Length(min = 0, max = 255, message = "sys_name长度必须介于 0 和 255 之间")
    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    @Length(min = 0, max = 128, message = "sys_abbrname长度必须介于 0 和 128 之间")
    public String getSysAbbrname() {
        return sysAbbrname;
    }

    public void setSysAbbrname(String sysAbbrname) {
        this.sysAbbrname = sysAbbrname;
    }

    @Length(min = 0, max = 20, message = "登录背景色长度必须介于 0 和 20 之间")
    public String getLoginBgcolor() {
        return loginBgcolor;
    }

    public void setLoginBgcolor(String loginBgcolor) {
        this.loginBgcolor = loginBgcolor;
    }

    @Length(min = 0, max = 20, message = "登陆布局长度必须介于 0 和 20 之间")
    public String getLoginLayout() {
        return loginLayout;
    }

    public void setLoginLayout(String loginLayout) {
        this.loginLayout = loginLayout;
    }

    public SysFile getProjectLogo() {
        return projectLogo;
    }

    public void setProjectLogo(SysFile projectLogo) {
        this.projectLogo = projectLogo;
    }

    public SysFile getLoginLogo() {
        return loginLogo;
    }

    public void setLoginLogo(SysFile loginLogo) {
        this.loginLogo = loginLogo;
    }

    public SysFile getSysLogo() {
        return sysLogo;
    }

    public void setSysLogo(SysFile sysLogo) {
        this.sysLogo = sysLogo;
    }

    public SysFile getFavicon() {
        return favicon;
    }

    public void setFavicon(SysFile favicon) {
        this.favicon = favicon;
    }

    public SysFile getLoginBg() {
        return loginBg;
    }

    public void setLoginBg(SysFile loginBg) {
        this.loginBg = loginBg;
    }

    public SysFile getLoginGraph() {
        return loginGraph;
    }

    public void setLoginGraph(SysFile loginGraph) {
        this.loginGraph = loginGraph;
    }

    public Integer getProjectLogoIndex() {
        return projectLogoIndex;
    }

    public void setProjectLogoIndex(Integer projectLogoIndex) {
        this.projectLogoIndex = projectLogoIndex;
    }

    public Integer getLoginLogoIndex() {
        return loginLogoIndex;
    }

    public void setLoginLogoIndex(Integer loginLogoIndex) {
        this.loginLogoIndex = loginLogoIndex;
    }

    public Integer getSysLogoIndex() {
        return sysLogoIndex;
    }

    public void setSysLogoIndex(Integer sysLogoIndex) {
        this.sysLogoIndex = sysLogoIndex;
    }

    public Integer getFaviconIndex() {
        return faviconIndex;
    }

    public void setFaviconIndex(Integer faviconIndex) {
        this.faviconIndex = faviconIndex;
    }

    public Integer getLoginBgIndex() {
        return loginBgIndex;
    }

    public void setLoginBgIndex(Integer loginBgIndex) {
        this.loginBgIndex = loginBgIndex;
    }

    public Integer getLoginGraphIndex() {
        return loginGraphIndex;
    }

    public void setLoginGraphIndex(Integer loginGraphIndex) {
        this.loginGraphIndex = loginGraphIndex;
    }

    /**
     * 获取实体对应表名
     */
    @Override
    @JsonIgnore
    public String getBusTableName() {
        return "sys_seting";
    }

    /**
     * 获取实体对应Id
     */
    @Override
    @JsonIgnore
    public String getBusTableId() {
        return "1332267278623703084";
    }

    /**
     * 获取实体表中是否存在del_flag字段
     */
    @JsonIgnore
    public boolean getBusTableHasDelFlag() {
        return true;
    }
}
