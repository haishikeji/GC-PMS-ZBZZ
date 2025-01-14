package com.px.project.plant.mapper;

import com.px.project.plant.domain.TStaffmgrRecord;

import java.util.List;

/**
 * 人员操作记录Mapper接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface TStaffmgrRecordMapper 
{
    /**
     * 查询人员操作记录
     * 
     * @param recordId 人员操作记录主键
     * @return 人员操作记录
     */
    public TStaffmgrRecord selectTStaffmgrRecordByRecordId(Long recordId);

    /**
     * 查询人员操作记录列表
     * 
     * @param tStaffmgrRecord 人员操作记录
     * @return 人员操作记录集合
     */
    public List<TStaffmgrRecord> selectTStaffmgrRecordList(TStaffmgrRecord tStaffmgrRecord);

    /**
     * 新增人员操作记录
     * 
     * @param tStaffmgrRecord 人员操作记录
     * @return 结果
     */
    public int insertTStaffmgrRecord(TStaffmgrRecord tStaffmgrRecord);

    /**
     * 修改人员操作记录
     * 
     * @param tStaffmgrRecord 人员操作记录
     * @return 结果
     */
    public int updateTStaffmgrRecord(TStaffmgrRecord tStaffmgrRecord);

    /**
     * 删除人员操作记录
     * 
     * @param recordId 人员操作记录主键
     * @return 结果
     */
    public int deleteTStaffmgrRecordByRecordId(Long recordId);

    /**
     * 批量删除人员操作记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTStaffmgrRecordByRecordIds(Long[] recordIds);
}
