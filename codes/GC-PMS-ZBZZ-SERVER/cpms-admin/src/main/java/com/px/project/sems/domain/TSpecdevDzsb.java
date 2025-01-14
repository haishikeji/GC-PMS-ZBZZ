package com.px.project.sems.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 特种设备吊装设备台账对象 t_specdev_dzsb
 *
 * @author 品讯科技
 * @date 2024-08
 */

public class TSpecdevDzsb extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识ID
     */
    private Long id;

    /**
     * 设备类别
     */
    @Excel(name = "设备类别")
    private String devType;

    /**
     * 装置名称
     */
    @Excel(name = "装置")
    private String plantCode;

    /**
     * 设备类别2
     */
    @Excel(name = "设备类别2")
    private String devType2;

    /**
     * 型号
     */
    @Excel(name = "设备型号")
    private String model;

    /**
     * 下次年检时间
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    @Excel(name = "下次检验日期", width = 30, dateFormat = "yyyy-MM")
    private Date nextWarnDate;

    /**
     * 出场资料
     */
    @Excel(name = "出场资料")
    private String factoryInfo;

    /**
     * 使用登记证编号 车牌号
     */
    @Excel(name = "使用登记证编号 车牌号")
    private String plateNo;

    /**
     * 设备代码
     */
    @Excel(name = "设备代码")
    private String devCode;

    /**
     * 发动机号/底盘号
     */
    @Excel(name = "发动机号/底盘号")
    private String engineNo;

    /**
     * 防爆等级
     */
    @Excel(name = "防爆等级")
    private String exLevel;

    /**
     * 制造单位
     */
    @Excel(name = "制造单位")
    private String createUnit;

    /**
     * 状态
     */
    @Excel(name = "状态", dictType = "spec_dev_status")
    private Long status;

    /**
     * 检验状态
     */

    @Excel(name = "检验状态")
    private String checkStatus;

    /**
     * 单元
     */
    //@Excel(name = "单元")
    private String unit;

    /**
     * 装置维修组
     */
    //@Excel(name = "装置维修组")
    private String plantMaint;

    /**
     * 工程师
     */
    //@Excel(name = "装置维修工程师")
    private String engineer;

    /**
     * 审核状态
     */
    //@Excel(name = "审核状态", dictType = "spec_approve_status")
    private Long approveStatus;

    /**
     * 设备名称
     */
    //@Excel(name = "设备名称")
    private String devname;

    /**
     * 设备位号
     */
   // @Excel(name = "设备位号")
    private String devno;

    /**
     * 档案号
     */
    //@Excel(name = "档案号")
    private String docno;

    /**
     * 产品出厂编号
     */
    //@Excel(name = "产品出厂编号")
    private String factoryNo;

    /**
     * 起吊重量
     */
    //@Excel(name = "起吊重量(Ton)")
    private String capacity;

    /**
     * 设备使用地点
     */
    //@Excel(name = "使用地点")
    private String location;

    /**
     * 注册代码
     */
    //@Excel(name = "设备注册登记证编号")
    private String regno;

    /**
     * 提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@Excel(name = "投用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitdate;

    /**
     * 是否防爆
     */
    //@Excel(name = "是否防爆")
    private String isEx;

    /**
     * 检验单位
     */
    //@Excel(name = "检验单位")
    private String checkUnit;

    /**
     * 检验策略
     */
    //@Excel(name = "检验策略")
    private String checkStrategy;

    /**
     * 上次年检时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@Excel(name = "检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date warnDate;

    /**
     * 报告编号
     */
    //@Excel(name = "检验报告编号")
    private String reportNo;

    /**
     * 定期检验结论
     */
    //@Excel(name = "定期检验结论")
    private String perTestConclusion;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 状态 1 ：正常 ；0：删除
     */
    private Long delFlag;

    /**
     * 创建人
     */
    private Long createrCode;

    /**
     * 创建时间
     */
    private Date createdate;

    /**
     * 修改人
     */
    private Long updaterCode;

    /**
     * 修改时间
     */
    private Date updatedate;

    /**
     * 部门编号
     */
    //@Excel(name = "部门编号")
    private Long deptId;

    /**
     * 使用证编号
     */
    //@Excel(name = "使用证编号")
    private String useno;

    /**
     * 检测周期
     */
    //@Excel(name = "检测周期")
    private Long warnCycle;

    /**
     * 预警标识
     */
    //@Excel(name = "预警标识")
    private Long warnFlag;

    /**
     * 年度维保日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@Excel(name = "年度维保日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yearMaint;

    /**
     * 最新申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@Excel(name = "最新申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approveTime;

    /**
     * 状态修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@Excel(name = "状态修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date changeTime;

    /**
     * 部门名称
     */
    //@Excel(name = "部门名称")

    private String deptName;


    private Long devId;


    private Long hiType;


    private Long hiFlag;


    private String plantIds;


    private String unitIds;


    private String checkYear;

    private Long isRepeat;


    private String fuzzy;

    public String getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(String fuzzy) {
        this.fuzzy = fuzzy;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getDevType2() {
        return devType2;
    }

    public void setDevType2(String devType2) {
        this.devType2 = devType2;
    }

    public String getFactoryInfo() {
        return factoryInfo;
    }

    public void setFactoryInfo(String factoryInfo) {
        this.factoryInfo = factoryInfo;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getExLevel() {
        return exLevel;
    }

    public void setExLevel(String exLevel) {
        this.exLevel = exLevel;
    }

    public Long getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(Long isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getPerTestConclusion() {
        return perTestConclusion;
    }

    public void setPerTestConclusion(String perTestConclusion) {
        this.perTestConclusion = perTestConclusion;
    }

    public Long getHiFlag() {
        return hiFlag;
    }

    public void setHiFlag(Long hiFlag) {
        this.hiFlag = hiFlag;
    }

    public Long getDevId() {
        return devId;
    }

    public void setDevId(Long devId) {
        this.devId = devId;
    }

    public Long getHiType() {
        return hiType;
    }

    public void setHiType(Long hiType) {
        this.hiType = hiType;
    }

    public String getIsEx() {
        return isEx;
    }

    public void setIsEx(String isEx) {
        this.isEx = isEx;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setPlantCode(String plantCode) {
        this.plantCode = plantCode;
    }

    public String getPlantCode() {
        return plantCode;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setDevname(String devname) {
        this.devname = devname;
    }

    public String getDevname() {
        return devname;
    }

    public void setDevno(String devno) {
        this.devno = devno;
    }

    public String getDevno() {
        return devno;
    }

    public void setSubmitdate(Date submitdate) {
        this.submitdate = submitdate;
    }

    public Date getSubmitdate() {
        return submitdate;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getStatus() {
        return status;
    }

    public void setDelFlag(Long delFlag) {
        this.delFlag = delFlag;
    }

    public Long getDelFlag() {
        return delFlag;
    }

    public void setCreaterCode(Long createrCode) {
        this.createrCode = createrCode;
    }

    public Long getCreaterCode() {
        return createrCode;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setUpdaterCode(Long updaterCode) {
        this.updaterCode = updaterCode;
    }

    public Long getUpdaterCode() {
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

    public void setApproveStatus(Long approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Long getApproveStatus() {
        return approveStatus;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getRegno() {
        return regno;
    }

    public void setUseno(String useno) {
        this.useno = useno;
    }

    public String getUseno() {
        return useno;
    }

    public void setWarnDate(Date warnDate) {
        this.warnDate = warnDate;
    }

    public Date getWarnDate() {
        return warnDate;
    }

    public void setWarnCycle(Long warnCycle) {
        this.warnCycle = warnCycle;
    }

    public Long getWarnCycle() {
        return warnCycle;
    }

    public void setNextWarnDate(Date nextWarnDate) {
        this.nextWarnDate = nextWarnDate;
    }

    public Date getNextWarnDate() {
        return nextWarnDate;
    }

    public void setWarnFlag(Long warnFlag) {
        this.warnFlag = warnFlag;
    }

    public Long getWarnFlag() {
        return warnFlag;
    }

    public void setPlantMaint(String plantMaint) {
        this.plantMaint = plantMaint;
    }

    public String getPlantMaint() {
        return plantMaint;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getDocno() {
        return docno;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setCreateUnit(String createUnit) {
        this.createUnit = createUnit;
    }

    public String getCreateUnit() {
        return createUnit;
    }

    public void setCheckStrategy(String checkStrategy) {
        this.checkStrategy = checkStrategy;
    }

    public String getCheckStrategy() {
        return checkStrategy;
    }

    public void setYearMaint(Date yearMaint) {
        this.yearMaint = yearMaint;
    }

    public Date getYearMaint() {
        return yearMaint;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setCheckUnit(String checkUnit) {
        this.checkUnit = checkUnit;
    }

    public String getCheckUnit() {
        return checkUnit;
    }

    public void setEngineer(String engineer) {
        this.engineer = engineer;
    }

    public String getEngineer() {
        return engineer;
    }

    public String getPlantIds() {
        return plantIds;
    }

    public void setPlantIds(String plantIds) {
        this.plantIds = plantIds;
    }

    public String getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds;
    }

    public String getCheckYear() {
        return checkYear;
    }

    public void setCheckYear(String checkYear) {
        this.checkYear = checkYear;
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
    }

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("plantCode", getPlantCode())
                .append("unit", getUnit())
                .append("devname", getDevname())
                .append("devno", getDevno())
                .append("submitdate", getSubmitdate())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createrCode", getCreaterCode())
                .append("createdate", getCreatedate())
                .append("updaterCode", getUpdaterCode())
                .append("updatedate", getUpdatedate())
                .append("deptId", getDeptId())
                .append("remarks", getRemarks())
                .append("approveStatus", getApproveStatus())
                .append("regno", getRegno())
                .append("useno", getUseno())
                .append("warnDate", getWarnDate())
                .append("warnCycle", getWarnCycle())
                .append("nextWarnDate", getNextWarnDate())
                .append("warnFlag", getWarnFlag())
                .append("plantMaint", getPlantMaint())
                .append("docno", getDocno())
                .append("model", getModel())
                .append("capacity", getCapacity())
                .append("location", getLocation())
                .append("createUnit", getCreateUnit())
                .append("checkStrategy", getCheckStrategy())
                .append("yearMaint", getYearMaint())
                .append("reportNo", getReportNo())
                .append("approveTime", getApproveTime())
                .append("changeTime", getChangeTime())
                .append("checkUnit", getCheckUnit())
                .append("engineer", getEngineer())
                .toString();
    }
}
