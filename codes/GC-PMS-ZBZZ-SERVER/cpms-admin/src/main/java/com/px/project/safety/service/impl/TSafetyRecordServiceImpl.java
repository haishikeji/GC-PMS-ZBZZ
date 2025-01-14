package com.px.project.safety.service.impl;

import com.px.project.safety.domain.TSafetyRecord;
import com.px.project.safety.mapper.TSafetyRecordMapper;
import com.px.project.safety.service.ITSafetyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 洗眼器检查记录Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class TSafetyRecordServiceImpl implements ITSafetyRecordService
{
    @Autowired
    private TSafetyRecordMapper tSafetyRecordMapper;

    /**
     * 查询洗眼器检查记录
     *
     * @param id 洗眼器检查记录主键
     * @return 洗眼器检查记录
     */
    @Override
    public TSafetyRecord selectTSafetyRecordById(Long id)
    {
        return tSafetyRecordMapper.selectTSafetyRecordById(id);
    }

    /**
     * 查询洗眼器检查记录列表
     *
     * @param tSafetyRecord 洗眼器检查记录
     * @return 洗眼器检查记录
     */
    @Override
    public List<TSafetyRecord> selectTSafetyRecordList(TSafetyRecord tSafetyRecord)
    {
        return tSafetyRecordMapper.selectTSafetyRecordList(tSafetyRecord);
    }

    /**
     * 新增洗眼器检查记录
     *
     * @param tSafetyRecord 洗眼器检查记录
     * @return 结果
     */
    @Override
    public int insertTSafetyRecord(TSafetyRecord tSafetyRecord)
    {
        return tSafetyRecordMapper.insertTSafetyRecord(tSafetyRecord);
    }

    /**
     * 修改洗眼器检查记录
     *
     * @param tSafetyRecord 洗眼器检查记录
     * @return 结果
     */
    @Override
    public int updateTSafetyRecord(TSafetyRecord tSafetyRecord)
    {
        return tSafetyRecordMapper.updateTSafetyRecord(tSafetyRecord);
    }

    /**
     * 批量删除洗眼器检查记录
     *
     * @param ids 需要删除的洗眼器检查记录主键
     * @return 结果
     */
    @Override
    public int deleteTSafetyRecordByIds(Long[] ids)
    {
        return tSafetyRecordMapper.deleteTSafetyRecordByIds(ids);
    }

    /**
     * 删除洗眼器检查记录信息
     *
     * @param id 洗眼器检查记录主键
     * @return 结果
     */
    @Override
    public int deleteTSafetyRecordById(Long id)
    {
        return tSafetyRecordMapper.deleteTSafetyRecordById(id);
    }
}
