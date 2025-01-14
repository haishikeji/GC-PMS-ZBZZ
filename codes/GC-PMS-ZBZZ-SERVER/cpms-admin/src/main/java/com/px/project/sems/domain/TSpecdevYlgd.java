package com.px.project.sems.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 特种设备压力管道台账对象 t_specdev_ylgd
 *
 * @author 品讯科技
 * @date 2024-08
 */

public class TSpecdevYlgd extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识ID */
    private Long id;

    /** 装置名称 */
    @Excel(name = "装置")
    private String plantCode;

    /** 管道名称 */
    @Excel(name = "管道名称")
    private String devname;

    /** P&ID号 */
    @Excel(name = "P&ID号")
    private String pidNo;

    /** 管道编号 */
    @Excel(name = "管道编号")
    private String devno;

    /** ISO图纸 */
    @Excel(name = "ISO图纸")
    private  String isoPicUrl;

    /** 下次定期检验日期 */
    @JsonFormat(pattern = "yyyy-MM" , timezone = "GMT+8")
    @Excel(name = "下次检验日期", width = 30, dateFormat = "yyyy-MM")
    private Date nextWarnDate;

    /** 定期检验日期 */
    @JsonFormat(pattern = "yyyy-MM" , timezone = "GMT+8")
    @Excel(name = "最近一次检验日期", width = 30, dateFormat = "yyyy-MM")
    private Date warnDate;

    /** 起点 */
    @Excel(name = "起点")
    private String starting;

    /** 终点 */
    @Excel(name = "终点")
    private String ending;

    /** 安全阀 */
    @Excel(name = "安全阀")
    private String reliefValve;

    /** 管件数量 */
    @Excel(name = "管件数量")
    private Long fitNumber;

    /** 焊口数量 */
    @Excel(name = "对接焊缝数量")
    private String weldNumber;

    /** 管道级别 */
    @Excel(name = "管道级别")
    private String grade;

    /** 设计单位 */
    @Excel(name = "设计单位")
    private String designer;

    /** 安装单位 */
    @Excel(name = "安装单位")
    private String installer;

    /** 安装年月 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    @Excel(name = "安装年月", width = 30, dateFormat = "yyyy-MM")
    private Date installDate;

    /** 投用年月 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    @Excel(name = "投用年月", width = 30, dateFormat = "yyyy-MM")
    private Date submitdate;

    /** 直径 */
    @Excel(name = "公称直径(DN)")
    private String dia;

    /** 厚度等级 */
    @Excel(name = "壁厚(mm)")
    private String scheduleNo;

    /** 长度 */
    @Excel(name = "管道长度(m)")
    private String length;

    /** 材质 */
    @Excel(name = "材质")
    private String material;

    /** 保温 */
    @Excel(name = "保温")
    private String heatPreservation;

    /** 厚度 */
    @Excel(name = "厚度")
    private String thickness;

    /** 设计压力 */
    @Excel(name = "设计压力(MPa)")
    private String desPressure;

    /** 工作压力 */
    @Excel(name = "工作压力(MPa)")
    private String optPressure;

    /** 设计温度 */
    @Excel(name = "设计温度(℃)")
    private String desTemp;

    /** 工作温度 */
    @Excel(name = "工作温度(℃)")
    private String optTemp;

    /** 介质 */
    @Excel(name = "介质")
    private String medium;

    /** 年度检验结论 */
    @Excel(name = "检验结论")
    private String checkConclusion;


    /** 检验单位 */
    @Excel(name = "检验机构名称")
    private String checkUnit;

    /** 报告编号 */
    @Excel(name = "报告编号")
    private String reportNo;

    /** 状态 */
    @Excel(name = "状态", dictType = "spec_dev_status")
    private Long status;


    @Excel(name = "检验状态")
    private String checkStatus;

    /** 单元 */
    //@Excel(name = "单元")
    private String unit;

    /** PM维修组 */
   // @Excel(name = "装置维修组")
    private String plantMaint;

    /** 装置维修工程师 */
   // @Excel(name = "装置维修工程师")
    private String engineer;

    /** 审核状态 */
    //@Excel(name = "审核状态", dictType = "spec_approve_status")
    private Long approveStatus;

    /** 注册代码 */
    //@Excel(name = "工业管道代码")
    private String regno;

    /** 使用证编号 */
   // @Excel(name = "使用登记证编号")
    private String useno;

    /** 敷设方式 */
   // @Excel(name = "敷设方式")
    private String layingMethod;

    /** 绝热层代码 */
   // @Excel(name = "绝热层代码")
    private String adiabatic;

    /** 防腐层代码 */
//    @Excel(name = "防腐层代码")
    private String antiCorrosion;

    /** 绝热层厚度 */
    //@Excel(name = "绝热层厚度")
    private String adiabaticThickness;

    /** 是否涉危化品 */
   // @Excel(name = "是否涉危化品")
    private String isDanger;


    /** 安全状况等级 */
   // @Excel(name = "安全状况等级")
    private String safeClass;

    /** 年度检查日期 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
   // @Excel(name = "年度检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yearWarnDate;

    /** 下次年度检查日期 */
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
 //   @Excel(name = "下次年度检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date yearNextWarnDate;

    /** 年度检查报告编号 */
   // @Excel(name = "年度检查报告编号")
    private String yearReportNo;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 状态 1 ：正常 ；0：删除 */
    private Long delFlag;

    /** 创建人 */
    private Long createrCode;

    /** 创建时间 */
    private Date createdate;

    /** 修改人 */
    private Long updaterCode;

    /** 修改时间 */
    private Date updatedate;

    /** 部门编号 */
    private Long deptId;

    /** 检测周期 */
    //@Excel(name = "检测周期")
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


    private String fuzzy;

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(String fuzzy) {
        this.fuzzy = fuzzy;
    }
    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getPidNo() {
        return pidNo;
    }

    public void setPidNo(String pidNo) {
        this.pidNo = pidNo;
    }

    public String getIsoPicUrl() {
        return isoPicUrl;
    }

    public void setIsoPicUrl(String isoPicUrl) {
        this.isoPicUrl = isoPicUrl;
    }

    public String getReliefValve() {
        return reliefValve;
    }

    public void setReliefValve(String reliefValve) {
        this.reliefValve = reliefValve;
    }

    public Long getFitNumber() {
        return fitNumber;
    }

    public void setFitNumber(Long fitNumber) {
        this.fitNumber = fitNumber;
    }

    public String getHeatPreservation() {
        return heatPreservation;
    }

    public void setHeatPreservation(String heatPreservation) {
        this.heatPreservation = heatPreservation;
    }

    public Long getHiFlag() {
        return hiFlag;
    }

    public void setHiFlag(Long hiFlag) {
        this.hiFlag = hiFlag;
    }

    private Long isRepeat;

    public Long getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(Long isRepeat) {
        this.isRepeat = isRepeat;
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

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public Date getYearWarnDate() {
        return yearWarnDate;
    }

    public void setYearWarnDate(Date yearWarnDate) {
        this.yearWarnDate = yearWarnDate;
    }

    public Date getYearNextWarnDate() {
        return yearNextWarnDate;
    }

    public void setYearNextWarnDate(Date yearNextWarnDate) {
        this.yearNextWarnDate = yearNextWarnDate;
    }

    public String getYearReportNo() {
        return yearReportNo;
    }

    public void setYearReportNo(String yearReportNo) {
        this.yearReportNo = yearReportNo;
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
    public void setCreaterCode(Long createrCode)
    {
        this.createrCode = createrCode;
    }

    public Long getCreaterCode()
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
    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getGrade()
    {
        return grade;
    }
    public void setDesigner(String designer)
    {
        this.designer = designer;
    }

    public String getDesigner()
    {
        return designer;
    }
    public void setInstaller(String installer)
    {
        this.installer = installer;
    }

    public String getInstaller()
    {
        return installer;
    }
    public void setMaterial(String material)
    {
        this.material = material;
    }

    public String getMaterial()
    {
        return material;
    }
    public void setDia(String dia)
    {
        this.dia = dia;
    }

    public String getDia()
    {
        return dia;
    }
    public void setScheduleNo(String scheduleNo)
    {
        this.scheduleNo = scheduleNo;
    }

    public String getScheduleNo()
    {
        return scheduleNo;
    }
    public void setLength(String length)
    {
        this.length = length;
    }

    public String getLength()
    {
        return length;
    }
    public void setStarting(String starting)
    {
        this.starting = starting;
    }

    public String getStarting()
    {
        return starting;
    }
    public void setEnding(String ending)
    {
        this.ending = ending;
    }

    public String getEnding()
    {
        return ending;
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
    public void setMedium(String medium)
    {
        this.medium = medium;
    }

    public String getMedium()
    {
        return medium;
    }

    public String getCheckConclusion() {
        return checkConclusion;
    }

    public void setCheckConclusion(String checkConclusion) {
        this.checkConclusion = checkConclusion;
    }

    public void setCheckUnit(String checkUnit)
    {
        this.checkUnit = checkUnit;
    }

    public String getCheckUnit()
    {
        return checkUnit;
    }
    public void setSafeClass(String safeClass)
    {
        this.safeClass = safeClass;
    }

    public String getSafeClass()
    {
        return safeClass;
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
    public void setEngineer(String engineer)
    {
        this.engineer = engineer;
    }

    public String getEngineer()
    {
        return engineer;
    }
    public void setWeldNumber(String weldNumber)
    {
        this.weldNumber = weldNumber;
    }

    public String getWeldNumber()
    {
        return weldNumber;
    }
    public void setLayingMethod(String layingMethod)
    {
        this.layingMethod = layingMethod;
    }

    public String getLayingMethod()
    {
        return layingMethod;
    }
    public void setAdiabatic(String adiabatic)
    {
        this.adiabatic = adiabatic;
    }

    public String getAdiabatic()
    {
        return adiabatic;
    }
    public void setAntiCorrosion(String antiCorrosion)
    {
        this.antiCorrosion = antiCorrosion;
    }

    public String getAntiCorrosion()
    {
        return antiCorrosion;
    }
    public void setAdiabaticThickness(String adiabaticThickness)
    {
        this.adiabaticThickness = adiabaticThickness;
    }

    public String getAdiabaticThickness()
    {
        return adiabaticThickness;
    }

    public String getIsDanger() {
        return isDanger;
    }

    public void setIsDanger(String isDanger) {
        this.isDanger = isDanger;
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
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
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
            .append("grade", getGrade())
            .append("designer", getDesigner())
            .append("installer", getInstaller())
            .append("material", getMaterial())
            .append("dia", getDia())
            .append("scheduleNo", getScheduleNo())
            .append("length", getLength())
            .append("starting", getStarting())
            .append("ending", getEnding())
            .append("desPressure", getDesPressure())
            .append("desTemp", getDesTemp())
            .append("optPressure", getOptPressure())
            .append("optTemp", getOptTemp())
            .append("medium", getMedium())
            .append("checkConclusion", getCheckConclusion())
            .append("checkUnit", getCheckUnit())
            .append("safeClass", getSafeClass())
            .append("reportNo", getReportNo())
            .append("approveTime", getApproveTime())
            .append("changeTime", getChangeTime())
            .append("plantMaint", getPlantMaint())
            .append("engineer", getEngineer())
            .append("weldNumber", getWeldNumber())
            .append("layingMethod", getLayingMethod())
            .append("adiabatic", getAdiabatic())
            .append("antiCorrosion", getAntiCorrosion())
            .append("adiabaticThickness", getAdiabaticThickness())
            .append("isDanger", getIsDanger())
             .append("yearReportNo", getYearReportNo())
            .toString();
    }
}
