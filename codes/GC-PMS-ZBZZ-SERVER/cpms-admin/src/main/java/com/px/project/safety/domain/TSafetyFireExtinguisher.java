package com.px.project.safety.domain;

import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 灭火器对象 t_safety_fire_extinguisher
 *
 * @author 品讯科技
 * @date 2024-08
 */
public class TSafetyFireExtinguisher extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 装置名称 */
    @Excel(name = "装置名称")
    private String plantName;

    /** 位号 */
    @Excel(name = "位号")
    private String devNo;

    /** 位置 */
    @Excel(name = "位置")
    private String location;

    /** 数量 */
    @Excel(name = "数量")
    private String devNum;

    /** 灭火器类型 */
    @Excel(name = "灭火器类型")
    private String extinguisherType;

    /** 检查人 */
    @Excel(name = "检查人")
    private String inspector;

    /** 充装日期 */
    @Excel(name = "充装日期")
    private String fillingDate;

    /** 检查日期 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectdate;

    /** 状态 */
    @Excel(name = "状态",readConverterExp = "1=ok,0=not ok")
    private String status;

    /** 措施 */
    @Excel(name = "措施")
    private String measure;

    /** 检查状况 */
    @Excel(name = "检查状况")
    private String inspectstatus;

    /** 附件id */
    private String fileIds;

    /** 删除标识 */
    private Integer delFlag;

    /** 创建人 */
    private String createrCode;

    /** 创建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createdate;

    /** 更新人 */
    private String updaterCode;

    /** 更新日期 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updatedate;

    /** 部门编号 */
    private Long deptId;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setPlantName(String plantName)
    {
        this.plantName = plantName;
    }

    public String getPlantName()
    {
        return plantName;
    }
    public void setDevNo(String devNo)
    {
        this.devNo = devNo;
    }

    public String getDevNo()
    {
        return devNo;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLocation()
    {
        return location;
    }
    public void setDevNum(String devNum)
    {
        this.devNum = devNum;
    }

    public String getDevNum()
    {
        return devNum;
    }
    public void setExtinguisherType(String extinguisherType)
    {
        this.extinguisherType = extinguisherType;
    }

    public String getExtinguisherType()
    {
        return extinguisherType;
    }
    public void setInspector(String inspector)
    {
        this.inspector = inspector;
    }

    public String getInspector()
    {
        return inspector;
    }
    public void setFillingDate(String fillingDate)
    {
        this.fillingDate = fillingDate;
    }

    public String getFillingDate()
    {
        return fillingDate;
    }
    public void setInspectdate(Date inspectdate)
    {
        this.inspectdate = inspectdate;
    }

    public Date getInspectdate()
    {
        return inspectdate;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setMeasure(String measure)
    {
        this.measure = measure;
    }

    public String getMeasure()
    {
        return measure;
    }
    public void setInspectstatus(String inspectstatus)
    {
        this.inspectstatus = inspectstatus;
    }

    public String getInspectstatus()
    {
        return inspectstatus;
    }
    public void setFileIds(String fileIds)
    {
        this.fileIds = fileIds;
    }

    public String getFileIds()
    {
        return fileIds;
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
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("plantName", getPlantName())
            .append("devNo", getDevNo())
            .append("location", getLocation())
            .append("devNum", getDevNum())
            .append("extinguisherType", getExtinguisherType())
            .append("inspector", getInspector())
            .append("fillingDate", getFillingDate())
            .append("inspectdate", getInspectdate())
            .append("status", getStatus())
            .append("measure", getMeasure())
            .append("inspectstatus", getInspectstatus())
            .append("fileIds", getFileIds())
            .append("delFlag", getDelFlag())
            .append("createrCode", getCreaterCode())
            .append("createdate", getCreatedate())
            .append("updaterCode", getUpdaterCode())
            .append("updatedate", getUpdatedate())
            .append("deptId", getDeptId())
            .append("remarks", getRemarks())
            .toString();
    }
}
