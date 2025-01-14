package com.px.project.sems.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 特种设备电梯台账对象 t_specdev_dt
 *
 * @author 品讯科技
 * @date 2024-08
 */

public class TSpecdevDt extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识ID
     */
    private Long id;

    /**
     * 装置名称
     */
    @Excel(name = "装置")
    private String plantCode;

    /**
     * 型号
     */
    @Excel(name = "型号")
    private String model;

    /**
     * 使用地点
     */
    @Excel(name = "使用场所")
    private String location;

    /**
     * 下次年检时间
     */
    @JsonFormat(pattern = "yyyy-MM", timezone = "GMT+8")
    @Excel(name = "下次检验日期", width = 30, dateFormat = "yyyy-MM")
    private Date nextWarnDate;

    /**
     * 使用登记证编号 车牌号
     */
    @Excel(name = "使用登记证编号 车牌号")
    private String plateNo;

    /**
     * 出厂资料
     */
    @Excel(name = "出厂资料")
    private String factoryInfo;

    /**
     * 发动机号/底盘号
     */
    @Excel(name = "发动机号/底盘号")
    private String engineNo;

    /** 防爆级别*/
    @Excel(name = "防爆级别")
    private String exGrade;

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


    @Excel(name = "检验状态")
    private String checkStatus;


    /**
     * 单元
     */
//    @Excel(name = "单元")
    private String unit;

    /**
     * 档案号
     */
    //@Excel(name = "档案号")
    private String docno;

    /**
     * 使用部门
     */
    //@Excel(name = "使用部门")
    private String useDept;

    /**
     * 装置维修组
     */
   // @Excel(name = "装置维修组")
    private String plantMaint;

    /**
     * 设备名称
     */
   // @Excel(name = "名称")
    private String devname;

    /**
     * 注册代码
     */
   // @Excel(name = "注册编号")
    private String regno;

    /**
     * 审核状态
     */
    //@Excel(name = "审核状态", dictType = "spec_approve_status")
    private Long approveStatus;

    /**
     * 额定载重
     */
   // @Excel(name = "额定载重量(kg)")
    private String capacity;

    /**
     * 层站数
     */
   // @Excel(name = "层站数")
    private String floor;

    /**
     * 维保承包商
     */
   // @Excel(name = "维保承包商")
    private String maintContractor;

    /**
     * 初检日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@Excel(name = "初检日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date firstWarnDate;

    /**
     * 检验日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    ///@Excel(name = "检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date warnDate;

    /**
     * 检验单位
     */
   // @Excel(name = "检验单位")
    private String checkUnit;

    /**
     * 报告编号
     */
  //  @Excel(name = "报告编号")
    private String reportNo;


    /**
     * 定期检验结论
     */
   // @Excel(name = "定期检验结论")
    private String perTestConclusion;
    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 设备位号
     */
    //@Excel(name = "设备位号")
    private String devno;

    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /**
     * 提交日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    //@Excel(name = "投用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitdate;

    /**
     * 状态 0 ：正常 ；-1：删除
     */
    private Long delFlag;

    /**
     * 创建人
     */
    private Long createrCode;

    /**
     * 创建时间
     */
    private Date createrDate;

    /**
     * 修改人
     */
    private Long updaterCode;

    /**
     * 修改时间
     */
    private Date updaterDate;

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
    private Long warnCycle;

    /**
     * 预警标识
     */
    //@Excel(name = "预警标识")
    private Long warnFlag;

    /**
     * 检测策略
     */
    //@Excel(name = "检测策略")
    private String checkStrategy;

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

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getExGrade() {
        return exGrade;
    }

    public void setExGrade(String exGrade) {
        this.exGrade = exGrade;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    public String getFactoryInfo() {
        return factoryInfo;
    }

    public void setFactoryInfo(String factoryInfo) {
        this.factoryInfo = factoryInfo;
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

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Date getCreatedate() {
        return createdate;
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

    public void setCreaterDate(Date createrDate) {
        this.createrDate = createrDate;
    }

    public Date getCreaterDate() {
        return createrDate;
    }

    public void setUpdaterCode(Long updaterCode) {
        this.updaterCode = updaterCode;
    }

    public Long getUpdaterCode() {
        return updaterCode;
    }

    public void setUpdaterDate(Date updaterDate) {
        this.updaterDate = updaterDate;
    }

    public Date getUpdaterDate() {
        return updaterDate;
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

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFloor() {
        return floor;
    }

    public void setCreateUnit(String createUnit) {
        this.createUnit = createUnit;
    }

    public String getCreateUnit() {
        return createUnit;
    }

    public void setMaintContractor(String maintContractor) {
        this.maintContractor = maintContractor;
    }

    public String getMaintContractor() {
        return maintContractor;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setCheckStrategy(String checkStrategy) {
        this.checkStrategy = checkStrategy;
    }

    public String getCheckStrategy() {
        return checkStrategy;
    }

    public void setFirstWarnDate(Date firstWarnDate) {
        this.firstWarnDate = firstWarnDate;
    }

    public Date getFirstWarnDate() {
        return firstWarnDate;
    }

    public void setCheckUnit(String checkUnit) {
        this.checkUnit = checkUnit;
    }

    public String getCheckUnit() {
        return checkUnit;
    }

    public void setDocno(String docno) {
        this.docno = docno;
    }

    public String getDocno() {
        return docno;
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

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public String getReportNo() {
        return reportNo;
    }

    public String getUseDept() {
        return useDept;
    }

    public void setUseDept(String useDept) {
        this.useDept = useDept;
    }

    public String getPlantMaint() {
        return plantMaint;
    }

    public void setPlantMaint(String plantMaint) {
        this.plantMaint = plantMaint;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("plantCode", getPlantCode())
                .append("unit", getUnit())
                .append("devname", getDevname())
                .append("devno", getDevno())
                .append("createdate", getCreatedate())
                .append("submitdate", getSubmitdate())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("createrCode", getCreaterCode())
                .append("createrDate", getCreaterDate())
                .append("updaterCode", getUpdaterCode())
                .append("updaterDate", getUpdaterDate())
                .append("deptId", getDeptId())
                .append("remarks", getRemarks())
                .append("approveStatus", getApproveStatus())
                .append("regno", getRegno())
                .append("useno", getUseno())
                .append("warnDate", getWarnDate())
                .append("warnCycle", getWarnCycle())
                .append("nextWarnDate", getNextWarnDate())
                .append("warnFlag", getWarnFlag())
                .append("model", getModel())
                .append("capacity", getCapacity())
                .append("floor", getFloor())
                .append("createUnit", getCreateUnit())
                .append("maintContractor", getMaintContractor())
                .append("location", getLocation())
                .append("checkStrategy", getCheckStrategy())
                .append("firstWarnDate", getFirstWarnDate())
                .append("checkUnit", getCheckUnit())
                .append("docno", getDocno())
                .append("approveTime", getApproveTime())
                .append("changeTime", getChangeTime())
                .append("reportNo", getReportNo())
                .toString();
    }
}
