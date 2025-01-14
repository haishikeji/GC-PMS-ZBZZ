package com.px.project.shiftmgr.service.impl;

import com.px.project.shiftmgr.domain.TShiftLog;
import com.px.project.shiftmgr.mapper.TShiftLogMapper;
import com.px.project.shiftmgr.service.ITShiftLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮班日志Service业务层处理
 * 
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class TShiftLogServiceImpl implements ITShiftLogService 
{
    @Autowired
    private TShiftLogMapper tShiftLogMapper;

    /**
     * 查询轮班日志
     * 
     * @param id 轮班日志主键
     * @return 轮班日志
     */
    @Override
    public TShiftLog selectTShiftLogById(Long id)
    {
        return tShiftLogMapper.selectTShiftLogById(id);
    }

    /**
     * 查询轮班日志列表
     * 
     * @param tShiftLog 轮班日志
     * @return 轮班日志
     */
    @Override
    public List<TShiftLog> selectTShiftLogList(TShiftLog tShiftLog)
    {
        return tShiftLogMapper.selectTShiftLogList(tShiftLog);
    }

    /**
     * 新增轮班日志
     * 
     * @param tShiftLog 轮班日志
     * @return 结果
     */
    @Override
    public int insertTShiftLog(TShiftLog tShiftLog)
    {
        return tShiftLogMapper.insertTShiftLog(tShiftLog);
    }

    /**
     * 修改轮班日志
     * 
     * @param tShiftLog 轮班日志
     * @return 结果
     */
    @Override
    public int updateTShiftLog(TShiftLog tShiftLog)
    {
        return tShiftLogMapper.updateTShiftLog(tShiftLog);
    }

    /**
     * 批量删除轮班日志
     * 
     * @param ids 需要删除的轮班日志主键
     * @return 结果
     */
    @Override
    public int deleteTShiftLogByIds(Long[] ids)
    {
        return tShiftLogMapper.deleteTShiftLogByIds(ids);
    }

    /**
     * 删除轮班日志信息
     * 
     * @param id 轮班日志主键
     * @return 结果
     */
    @Override
    public int deleteTShiftLogById(Long id)
    {
        return tShiftLogMapper.deleteTShiftLogById(id);
    }
}
