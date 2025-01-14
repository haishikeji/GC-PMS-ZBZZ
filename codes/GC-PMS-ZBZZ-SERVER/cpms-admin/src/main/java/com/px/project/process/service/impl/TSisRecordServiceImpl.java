package com.px.project.process.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.px.project.process.mapper.TSisRecordMapper;
import com.px.project.process.domain.TSisRecord;
import com.px.project.process.service.ITSisRecordService;

/**
 * sis RecordService业务层处理
 * 
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class TSisRecordServiceImpl implements ITSisRecordService 
{
    @Autowired
    private TSisRecordMapper tSisRecordMapper;

    /**
     * 查询sis Record
     * 
     * @param id sis Record主键
     * @return sis Record
     */
    @Override
    public TSisRecord selectTSisRecordById(Long id)
    {
        return tSisRecordMapper.selectTSisRecordById(id);
    }

    /**
     * 查询sis Record列表
     * 
     * @param tSisRecord sis Record
     * @return sis Record
     */
    @Override
    public List<TSisRecord> selectTSisRecordList(TSisRecord tSisRecord)
    {
        return tSisRecordMapper.selectTSisRecordList(tSisRecord);
    }

    /**
     * 新增sis Record
     * 
     * @param tSisRecord sis Record
     * @return 结果
     */
    @Override
    public int insertTSisRecord(TSisRecord tSisRecord)
    {
        return tSisRecordMapper.insertTSisRecord(tSisRecord);
    }

    /**
     * 修改sis Record
     * 
     * @param tSisRecord sis Record
     * @return 结果
     */
    @Override
    public int updateTSisRecord(TSisRecord tSisRecord)
    {
        return tSisRecordMapper.updateTSisRecord(tSisRecord);
    }

    /**
     * 批量删除sis Record
     * 
     * @param ids 需要删除的sis Record主键
     * @return 结果
     */
    @Override
    public int deleteTSisRecordByIds(Long[] ids)
    {
        return tSisRecordMapper.deleteTSisRecordByIds(ids);
    }

    /**
     * 删除sis Record信息
     * 
     * @param id sis Record主键
     * @return 结果
     */
    @Override
    public int deleteTSisRecordById(Long id)
    {
        return tSisRecordMapper.deleteTSisRecordById(id);
    }
}
