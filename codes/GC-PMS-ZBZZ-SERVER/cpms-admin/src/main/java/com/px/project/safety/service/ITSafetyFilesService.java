package com.px.project.safety.service;


import com.px.project.safety.domain.TSafetyFiles;

import java.util.List;

/**
 * 安全设备设施检查附件Service接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface ITSafetyFilesService 
{
    /**
     * 查询安全设备设施检查附件
     * 
     * @param id 安全设备设施检查附件主键
     * @return 安全设备设施检查附件
     */
    public TSafetyFiles selectTSafetyFilesById(Long id);

    /**
     * 查询安全设备设施检查附件列表
     * 
     * @param tSafetyFiles 安全设备设施检查附件
     * @return 安全设备设施检查附件集合
     */
    public List<TSafetyFiles> selectTSafetyFilesList(TSafetyFiles tSafetyFiles);

    /**
     * 新增安全设备设施检查附件
     * 
     * @param tSafetyFiles 安全设备设施检查附件
     * @return 结果
     */
    public int insertTSafetyFiles(TSafetyFiles tSafetyFiles);

    /**
     * 修改安全设备设施检查附件
     * 
     * @param tSafetyFiles 安全设备设施检查附件
     * @return 结果
     */
    public int updateTSafetyFiles(TSafetyFiles tSafetyFiles);

    /**
     * 批量删除安全设备设施检查附件
     * 
     * @param ids 需要删除的安全设备设施检查附件主键集合
     * @return 结果
     */
    public int deleteTSafetyFilesByIds(Long[] ids);

    /**
     * 删除安全设备设施检查附件信息
     * 
     * @param id 安全设备设施检查附件主键
     * @return 结果
     */
    public int deleteTSafetyFilesById(Long id);
}
