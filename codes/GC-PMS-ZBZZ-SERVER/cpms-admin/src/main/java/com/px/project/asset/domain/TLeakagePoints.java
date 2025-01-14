package com.px.project.asset.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;

/**
 * 漏点清单对象 t_leakage_points
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public class TLeakagePoints extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 漏点编号 */
    @Excel(name = "漏点编号")
    private String pointNo;

    /** 泄漏位置 */
    @Excel(name = "泄漏位置")
    private String leakagePosition;

    /** 泄漏介质 */
    @Excel(name = "泄漏介质")
    private String leakageMedium;

    /** 发现时间 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "发现时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date discoveryTime;

    /** 发现人员 */
    @Excel(name = "发现人员")
    private String discoveryName;

    /** 泄漏量 */
    @Excel(name = "泄漏量")
    private String leakageAmount;

    /** SAP通知单号 */
    @Excel(name = "SAP通知单号")
    private String sapNo;

    /** 消漏时间 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "消漏时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leakageEliminationTime;

    /** 消漏方法 */
    @Excel(name = "消漏方法")
    private String leakageEliminationFunc;

    /** 挂牌 */
    @Excel(name = "挂牌")
    private String listing;

    /** 带压堵漏 */
    @Excel(name = "带压堵漏")
    private String pressurePlugging;

    /** 消漏结果 */
    @Excel(name = "消漏结果")
    private String leakageEliminationResult;

    /** 下次检修日期 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "下次检修日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextCheckDate;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 删除标识 */
    private Integer delFlag;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createrCode;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "创建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updaterCode;

    /** 更新日期 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "更新日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatedate;

    /** 部门编号 */
    @Excel(name = "部门编号")
    private Long deptId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPointNo(String pointNo) 
    {
        this.pointNo = pointNo;
    }

    public String getPointNo() 
    {
        return pointNo;
    }
    public void setLeakagePosition(String leakagePosition) 
    {
        this.leakagePosition = leakagePosition;
    }

    public String getLeakagePosition() 
    {
        return leakagePosition;
    }
    public void setLeakageMedium(String leakageMedium) 
    {
        this.leakageMedium = leakageMedium;
    }

    public String getLeakageMedium() 
    {
        return leakageMedium;
    }
    public void setDiscoveryTime(Date discoveryTime) 
    {
        this.discoveryTime = discoveryTime;
    }

    public Date getDiscoveryTime() 
    {
        return discoveryTime;
    }
    public void setDiscoveryName(String discoveryName) 
    {
        this.discoveryName = discoveryName;
    }

    public String getDiscoveryName() 
    {
        return discoveryName;
    }
    public void setLeakageAmount(String leakageAmount) 
    {
        this.leakageAmount = leakageAmount;
    }

    public String getLeakageAmount() 
    {
        return leakageAmount;
    }
    public void setSapNo(String sapNo) 
    {
        this.sapNo = sapNo;
    }

    public String getSapNo() 
    {
        return sapNo;
    }
    public void setLeakageEliminationTime(Date leakageEliminationTime) 
    {
        this.leakageEliminationTime = leakageEliminationTime;
    }

    public Date getLeakageEliminationTime() 
    {
        return leakageEliminationTime;
    }
    public void setLeakageEliminationFunc(String leakageEliminationFunc) 
    {
        this.leakageEliminationFunc = leakageEliminationFunc;
    }

    public String getLeakageEliminationFunc() 
    {
        return leakageEliminationFunc;
    }
    public void setListing(String listing) 
    {
        this.listing = listing;
    }

    public String getListing() 
    {
        return listing;
    }
    public void setPressurePlugging(String pressurePlugging) 
    {
        this.pressurePlugging = pressurePlugging;
    }

    public String getPressurePlugging() 
    {
        return pressurePlugging;
    }
    public void setLeakageEliminationResult(String leakageEliminationResult) 
    {
        this.leakageEliminationResult = leakageEliminationResult;
    }

    public String getLeakageEliminationResult() 
    {
        return leakageEliminationResult;
    }
    public void setNextCheckDate(Date nextCheckDate) 
    {
        this.nextCheckDate = nextCheckDate;
    }

    public Date getNextCheckDate() 
    {
        return nextCheckDate;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
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
    public void setUpdaterCode(String updaterCode) 
    {
        this.updaterCode = updaterCode;
    }

    public String getUpdaterCode() 
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pointNo", getPointNo())
            .append("leakagePosition", getLeakagePosition())
            .append("leakageMedium", getLeakageMedium())
            .append("discoveryTime", getDiscoveryTime())
            .append("discoveryName", getDiscoveryName())
            .append("leakageAmount", getLeakageAmount())
            .append("sapNo", getSapNo())
            .append("leakageEliminationTime", getLeakageEliminationTime())
            .append("leakageEliminationFunc", getLeakageEliminationFunc())
            .append("listing", getListing())
            .append("pressurePlugging", getPressurePlugging())
            .append("leakageEliminationResult", getLeakageEliminationResult())
            .append("nextCheckDate", getNextCheckDate())
            .append("remarks", getRemarks())
            .append("delFlag", getDelFlag())
            .append("createrCode", getCreaterCode())
            .append("createdate", getCreatedate())
            .append("updaterCode", getUpdaterCode())
            .append("updatedate", getUpdatedate())
            .append("deptId", getDeptId())
            .toString();
    }
}
