package com.px.project.safety.domain;

import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 洗眼器对象 t_safety_eye_washer
 *
 * @author 品讯科技
 * @date 2024-08
 */
public class TSafetyEyeWasher extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 编号
     */
    @Excel(name = "编号")
    private String washerNo;

    /**
     * 位置
     */
    @Excel(name = "位置")
    private String location;

    /**
     * ok/not ok
     */
    @Excel(name = "ok/not ok",readConverterExp = "1=ok,0=not ok")
    private String status;

    /**
     * 故障描述
     */
    @Excel(name = "故障描述")
    private String faultDesc;

    /**
     * 故障解决签名
     */
    @Excel(name = "故障解决签名")
    private String faultSloveSign;

    /**
     * 检查人
     */
    @Excel(name = "检查人")
    private String inspector;

    /**
     * 检查日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "检查日期" , width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectdate;

    /**
     * 检查状况
     */
   @Excel(name = "检查状况")
    private String inspectstatus;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 创建人
     */
   // @Excel(name = "创建人")
    private String createrCode;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
   // @Excel(name = "创建日期" , width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /**
     * 更新人
     */
   // @Excel(name = "更新人")
    private String updaterCode;

    /**
     * 更新日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    //@Excel(name = "更新日期" , width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedate;

    /**
     * 部门编号
     */
    private Long deptId;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    private String fileIds;

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setWasherNo(String washerNo) {
        this.washerNo = washerNo;
    }

    public String getWasherNo() {
        return washerNo;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setFaultDesc(String faultDesc) {
        this.faultDesc = faultDesc;
    }

    public String getFaultDesc() {
        return faultDesc;
    }

    public void setFaultSloveSign(String faultSloveSign) {
        this.faultSloveSign = faultSloveSign;
    }

    public String getFaultSloveSign() {
        return faultSloveSign;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getInspector() {
        return inspector;
    }

    public void setInspectdate(Date inspectdate) {
        this.inspectdate = inspectdate;
    }

    public Date getInspectdate() {
        return inspectdate;
    }

    public void setInspectstatus(String inspectstatus) {
        this.inspectstatus = inspectstatus;
    }

    public String getInspectstatus() {
        return inspectstatus;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setCreaterCode(String createrCode) {
        this.createrCode = createrCode;
    }

    public String getCreaterCode() {
        return createrCode;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setUpdaterCode(String updaterCode) {
        this.updaterCode = updaterCode;
    }

    public String getUpdaterCode() {
        return updaterCode;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id" , getId())
                .append("washerNo" , getWasherNo())
                .append("location" , getLocation())
                .append("status" , getStatus())
                .append("faultDesc" , getFaultDesc())
                .append("faultSloveSign" , getFaultSloveSign())
                .append("inspector" , getInspector())
                .append("inspectdate" , getInspectdate())
                .append("inspectstatus" , getInspectstatus())
                .append("delFlag" , getDelFlag())
                .append("createrCode" , getCreaterCode())
                .append("createdate" , getCreatedate())
                .append("updaterCode" , getUpdaterCode())
                .append("updatedate" , getUpdatedate())
                .append("deptId" , getDeptId())
                .append("remarks" , getRemarks())
                .toString();
    }
}
