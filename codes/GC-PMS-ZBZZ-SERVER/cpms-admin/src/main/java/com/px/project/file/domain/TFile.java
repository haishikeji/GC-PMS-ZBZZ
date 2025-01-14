package com.px.project.file.domain;

import com.px.common.annotation.Excel;
import com.px.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 附件对象 t_file
 *
 * @author 品讯科技
 * @date 2024-08
 */
public class TFile extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 附件地址
     */
    @Excel(name = "附件地址")
    private String fileUrl;

    /**
     * 附件名称
     */
    @Excel(name = "附件名称")
    private String fileName;

    /**
     * 文件大小
     */
    @Excel(name = "文件大小")
    private String fileSize;

    /**
     * 类型
     */
    @Excel(name = "类型")
    private String type;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadDate;

    /**
     * 上传人
     */
    @Excel(name = "上传人")
    private String uploader;

    /**
     * 关联表id
     */
    @Excel(name = "关联表id")
    private Long linkId;

    /**
     * 关联表名
     */
    @Excel(name = "关联表名")
    private String linkName;

    /**
     * 删除标识
     */
    private String delFlag;

    /**
     * 上级id
     */
    private Long pId;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getUploader() {
        return uploader;
    }

    public void setLinkId(Long linkId) {
        this.linkId = linkId;
    }

    public Long getLinkId() {
        return linkId;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("fileUrl", getFileUrl())
                .append("fileName", getFileName())
                .append("uploadDate", getUploadDate())
                .append("uploader", getUploader())
                .append("remark", getRemark())
                .append("linkId", getLinkId())
                .append("linkName", getLinkName())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
