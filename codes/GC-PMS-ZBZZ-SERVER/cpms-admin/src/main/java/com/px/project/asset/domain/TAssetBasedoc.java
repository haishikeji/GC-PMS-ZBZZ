package com.px.project.asset.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;

/**
 * 资产管理基础文档清单对象 t_asset_basedoc
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public class TAssetBasedoc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 文档名称 */
    @Excel(name = "文档名称")
    private String docName;

    /** 状态 */
    @Excel(name = "状态")
    private String docStatus;

    /** 上传人 */
    @Excel(name = "上传人")
    private String createrCode;

    /** 上传日期 */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Excel(name = "上传日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdate;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 删除标识 */
    private Integer delFlag;

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

    private Long fileNum;

    public Long getFileNum() {
        return fileNum;
    }

    public void setFileNum(Long fileNum) {
        this.fileNum = fileNum;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setDocName(String docName) 
    {
        this.docName = docName;
    }

    public String getDocName() 
    {
        return docName;
    }
    public void setDocStatus(String docStatus) 
    {
        this.docStatus = docStatus;
    }

    public String getDocStatus() 
    {
        return docStatus;
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
            .append("type", getType())
            .append("docName", getDocName())
            .append("docStatus", getDocStatus())
            .append("createrCode", getCreaterCode())
            .append("createdate", getCreatedate())
            .append("remarks", getRemarks())
            .append("delFlag", getDelFlag())
            .append("updaterCode", getUpdaterCode())
            .append("updatedate", getUpdatedate())
            .append("deptId", getDeptId())
            .toString();
    }
}
