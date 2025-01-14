package com.px.project.sems.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 特种设备锅炉台账对象 t_specdev_gl
 *
 * @author 品讯科技
 * @date 2024-08
 */

public class TSpecdevGl extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识ID */
    private Long id;

    /** 装置名称 */
    @Excel(name = "装置")
    private String plantCode;

    /**容器编号*/
    @Excel(name = "容器编号")
    private String containerNo;

    /** 注册代码 */
    @Excel(name = "注册编号")
    private String regno;

    /** 使用证编号 */
    @Excel(name = "使用证编号")
    private String useno;

    /** 建档情况 */
    @Excel(name = "建档情况")
    private String filedSituation;

    /** 出厂资料 */
    @Excel(name = "出厂资料")
    private String factoryInfo;

    /** 内部检验 */
    @Excel(name = "下次内部检验定期检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date internalInspection;

    /** 外部检验 */
    @Excel(name = "下次外部检验定期检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date externalInspection;

    /** 试压 */
    @Excel(name = "下次试压定期检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date pressureTest;

    /** 能效测试 */
    @Excel(name = "下次能效测试定期检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date energyEfficiencyTest;

    /** 设备名称 */
    @Excel(name = "容器名称")
    private String devname;

    /** 出厂编号 */
    @Excel(name = "出厂编号")
    private String factoryNo;

    /** 介质 */
    @Excel(name = "介质")
    private String medium;

    /** 设计单位 */
    @Excel(name = "设计单位")
    private String designUnit;


    /** 制造单位 */
    @Excel(name = "制造单位")
    private String createUnit;


    @Excel(name = "检验状态")
    private String checkStatus;

    /** 单元 */
   // @Excel(name = "单元")
    private String unit;

    /** 状态 */
    @Excel(name = "状态", dictType = "spec_dev_status")
    private Long status;

    /** 审核状态 */
//    @Excel(name = "审核状态", dictType = "spec_approve_status")
    private Long approveStatus;

    /** 装置维修组 */
   // @Excel(name = "装置维修组")
    private String plantMaint;

    /** 装置维修工程师 */
   // @Excel(name = "装置维修工程师")
    private String engineer;

    /** 设备位号 */
   // @Excel(name = "位号")
    private String devno;


    /** 型号 */
    //@Excel(name = "型号")
    private String model;

    /** 燃烧方式 */
  //  @Excel(name = "燃烧方式")
    private String burnMode;

    /** 水处理方式 */
   // @Excel(name = "水处理方式")
    private String waterMode;

    /** 燃烧种类 */
  //  @Excel(name = "燃烧种类")
    private String burnKind;

    /** 额度出力 */
    //@Excel(name = "额度出力")
    private String ratedPower;

    /** 设计温度 */
   // @Excel(name = "设计温度(℃)")
    private String desTemp;

    /** 操作温度 */
    //@Excel(name = "操作温度(℃)")
    private String optTemp;

    /** 设计压力 */
    //@Excel(name = "设计压力(MPa)")
    private String desPressure;

    /** 操作压力 */
    //@Excel(name = "操作压力(MPa)")
    private String optPressure;

    /** 提交日期 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    //@Excel(name = "投用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitdate;

    /** 检验单位 */
    //@Excel(name = "检验单位")
    private String checkUnit;

    /** 本次内部检验日期 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    //@Excel(name = "本次内部检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date warnDate;

    /** 检查结论 */
 //   @Excel(name = "内部检验结论")
    private String checkConclusion;

    /** 报告编号 */
   // @Excel(name = "内部检验报告编号")
    private String reportNo;

    /** 下次内部检验日期 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    //@Excel(name = "下次内部检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextWarnDate;

    /** 本次外部检验日期 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    //@Excel(name = "本次外部检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outWarnDate;

    /** 外部检验结论 */
   // @Excel(name = "外部检验结论")
    private String outCheckConclusion;
    /** 外部检验编号 */
    //@Excel(name = "外部检验编号")
    private String outReportNo;
    /** 下次外部检验日期 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    //@Excel(name = "下次外部检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outNextWarnDate;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 状态 1 ：正常 ；0：删除 */
    private Long delFlag;

    /** 创建人 */
    private String createrCode;

    /** 创建时间 */
    private Date createdate;

    /** 修改人 */
    private Long updaterCode;

    /** 修改时间 */
    private Date updatedate;

    /** 部门编号 */
    private Long deptId;

    /** 检测周期 */
    private Long warnCycle;

    /** 预警标识 */
    //@Excel(name = "预警标识")
    private Long warnFlag;

    /** 最新申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    //@Excel(name = "最新申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approveTime;

    /** 状态修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    //@Excel(name = "状态修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date changeTime;



    /** 部门名称 */
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

    public String getContainerNo() {
        return containerNo;
    }

    public void setContainerNo(String containerNo) {
        this.containerNo = containerNo;
    }

    public String getFiledSituation() {
        return filedSituation;
    }

    public void setFiledSituation(String filedSituation) {
        this.filedSituation = filedSituation;
    }

    public String getFactoryInfo() {
        return factoryInfo;
    }

    public void setFactoryInfo(String factoryInfo) {
        this.factoryInfo = factoryInfo;
    }

    public Date getInternalInspection() {
        return internalInspection;
    }

    public void setInternalInspection(Date internalInspection) {
        this.internalInspection = internalInspection;
    }

    public Date getExternalInspection() {
        return externalInspection;
    }

    public void setExternalInspection(Date externalInspection) {
        this.externalInspection = externalInspection;
    }

    public Date getPressureTest() {
        return pressureTest;
    }

    public void setPressureTest(Date pressureTest) {
        this.pressureTest = pressureTest;
    }

    public Date getEnergyEfficiencyTest() {
        return energyEfficiencyTest;
    }

    public void setEnergyEfficiencyTest(Date energyEfficiencyTest) {
        this.energyEfficiencyTest = energyEfficiencyTest;
    }

    public String getFactoryNo() {
        return factoryNo;
    }

    public void setFactoryNo(String factoryNo) {
        this.factoryNo = factoryNo;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getDesignUnit() {
        return designUnit;
    }

    public void setDesignUnit(String designUnit) {
        this.designUnit = designUnit;
    }

    public Long getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(Long isRepeat) {
        this.isRepeat = isRepeat;
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

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setPlantCode(String plantCode)
    {
        this.plantCode = plantCode;
    }

    public String getPlantCode()
    {
        return plantCode;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setDevname(String devname)
    {
        this.devname = devname;
    }

    public String getDevname()
    {
        return devname;
    }
    public void setDevno(String devno)
    {
        this.devno = devno;
    }

    public String getDevno()
    {
        return devno;
    }
    public void setSubmitdate(Date submitdate)
    {
        this.submitdate = submitdate;
    }

    public Date getSubmitdate()
    {
        return submitdate;
    }
    public void setStatus(Long status)
    {
        this.status = status;
    }

    public Long getStatus()
    {
        return status;
    }
    public void setDelFlag(Long delFlag)
    {
        this.delFlag = delFlag;
    }

    public Long getDelFlag()
    {
        return delFlag;
    }
    public void setCreaterCode(String createrCode)
    {
        this.createrCode = createrCode;
    }

    public String getCreaterCode()
    {
        return createrCode;
    }
    public void setCreatedate(Date createdate)
    {
        this.createdate = createdate;
    }

    public Date getCreatedate()
    {
        return createdate;
    }
    public void setUpdaterCode(Long updaterCode)
    {
        this.updaterCode = updaterCode;
    }

    public Long getUpdaterCode()
    {
        return updaterCode;
    }
    public void setUpdatedate(Date updatedate)
    {
        this.updatedate = updatedate;
    }

    public Date getUpdatedate()
    {
        return updatedate;
    }
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }
    public void setApproveStatus(Long approveStatus)
    {
        this.approveStatus = approveStatus;
    }

    public Long getApproveStatus()
    {
        return approveStatus;
    }
    public void setRegno(String regno)
    {
        this.regno = regno;
    }

    public String getRegno()
    {
        return regno;
    }
    public void setUseno(String useno)
    {
        this.useno = useno;
    }

    public String getUseno()
    {
        return useno;
    }
    public void setWarnDate(Date warnDate)
    {
        this.warnDate = warnDate;
    }

    public Date getWarnDate()
    {
        return warnDate;
    }
    public void setWarnCycle(Long warnCycle)
    {
        this.warnCycle = warnCycle;
    }

    public Long getWarnCycle()
    {
        return warnCycle;
    }
    public void setNextWarnDate(Date nextWarnDate)
    {
        this.nextWarnDate = nextWarnDate;
    }

    public Date getNextWarnDate()
    {
        return nextWarnDate;
    }
    public void setWarnFlag(Long warnFlag)
    {
        this.warnFlag = warnFlag;
    }

    public Long getWarnFlag()
    {
        return warnFlag;
    }
    public void setCreateUnit(String createUnit)
    {
        this.createUnit = createUnit;
    }

    public String getCreateUnit()
    {
        return createUnit;
    }
    public void setBurnMode(String burnMode)
    {
        this.burnMode = burnMode;
    }

    public String getBurnMode()
    {
        return burnMode;
    }
    public void setBurnKind(String burnKind)
    {
        this.burnKind = burnKind;
    }

    public String getBurnKind()
    {
        return burnKind;
    }
    public void setRatedPower(String ratedPower)
    {
        this.ratedPower = ratedPower;
    }

    public String getRatedPower()
    {
        return ratedPower;
    }
    public void setDesPressure(String desPressure)
    {
        this.desPressure = desPressure;
    }

    public String getDesPressure()
    {
        return desPressure;
    }
    public void setDesTemp(String desTemp)
    {
        this.desTemp = desTemp;
    }

    public String getDesTemp()
    {
        return desTemp;
    }
    public void setOptPressure(String optPressure)
    {
        this.optPressure = optPressure;
    }

    public String getOptPressure()
    {
        return optPressure;
    }
    public void setOptTemp(String optTemp)
    {
        this.optTemp = optTemp;
    }

    public String getOptTemp()
    {
        return optTemp;
    }
    public void setCheckUnit(String checkUnit)
    {
        this.checkUnit = checkUnit;
    }

    public String getCheckUnit()
    {
        return checkUnit;
    }
    public void setCheckConclusion(String checkConclusion)
    {
        this.checkConclusion = checkConclusion;
    }

    public String getCheckConclusion()
    {
        return checkConclusion;
    }
    public void setModel(String model)
    {
        this.model = model;
    }

    public String getModel()
    {
        return model;
    }
    public void setWaterMode(String waterMode)
    {
        this.waterMode = waterMode;
    }

    public String getWaterMode()
    {
        return waterMode;
    }
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }

    public String getReportNo()
    {
        return reportNo;
    }
    public void setApproveTime(Date approveTime)
    {
        this.approveTime = approveTime;
    }

    public Date getApproveTime()
    {
        return approveTime;
    }
    public void setChangeTime(Date changeTime)
    {
        this.changeTime = changeTime;
    }

    public Date getChangeTime()
    {
        return changeTime;
    }
    public void setPlantMaint(String plantMaint)
    {
        this.plantMaint = plantMaint;
    }

    public String getPlantMaint()
    {
        return plantMaint;
    }
    public void setOutWarnDate(Date outWarnDate)
    {
        this.outWarnDate = outWarnDate;
    }

    public Date getOutWarnDate()
    {
        return outWarnDate;
    }
    public void setOutNextWarnDate(Date outNextWarnDate)
    {
        this.outNextWarnDate = outNextWarnDate;
    }

    public Date getOutNextWarnDate()
    {
        return outNextWarnDate;
    }
    public void setOutCheckConclusion(String outCheckConclusion)
    {
        this.outCheckConclusion = outCheckConclusion;
    }

    public String getOutCheckConclusion()
    {
        return outCheckConclusion;
    }
    public void setOutReportNo(String outReportNo)
    {
        this.outReportNo = outReportNo;
    }

    public String getOutReportNo()
    {
        return outReportNo;
    }
    public void setEngineer(String engineer)
    {
        this.engineer = engineer;
    }

    public String getEngineer()
    {
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
            .append("createUnit", getCreateUnit())
            .append("burnMode", getBurnMode())
            .append("burnKind", getBurnKind())
            .append("ratedPower", getRatedPower())
            .append("desPressure", getDesPressure())
            .append("desTemp", getDesTemp())
            .append("optPressure", getOptPressure())
            .append("optTemp", getOptTemp())
            .append("checkUnit", getCheckUnit())
            .append("checkConclusion", getCheckConclusion())
            .append("model", getModel())
            .append("waterMode", getWaterMode())
            .append("reportNo", getReportNo())
            .append("approveTime", getApproveTime())
            .append("changeTime", getChangeTime())
            .append("plantMaint", getPlantMaint())
            .append("outWarnDate", getOutWarnDate())
            .append("outNextWarnDate", getOutNextWarnDate())
            .append("outCheckConclusion", getOutCheckConclusion())
            .append("outReportNo", getOutReportNo())
            .append("engineer", getEngineer())
            .toString();
    }
}
