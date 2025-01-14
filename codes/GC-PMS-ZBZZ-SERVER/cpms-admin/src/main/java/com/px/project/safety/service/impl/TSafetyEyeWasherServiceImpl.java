package com.px.project.safety.service.impl;

import com.px.project.safety.domain.TSafetyEyeWasher;
import com.px.project.safety.mapper.TSafetyEyeWasherMapper;
import com.px.project.safety.service.ITSafetyEyeWasherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 洗眼器Service业务层处理
 * 
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class TSafetyEyeWasherServiceImpl implements ITSafetyEyeWasherService
{
    @Autowired
    private TSafetyEyeWasherMapper tSafetyEyeWasherMapper;

    /**
     * 查询洗眼器
     * 
     * @param id 洗眼器主键
     * @return 洗眼器
     */
    @Override
    public TSafetyEyeWasher selectTSafetyEyeWasherById(Long id)
    {
        return tSafetyEyeWasherMapper.selectTSafetyEyeWasherById(id);
    }

    /**
     * 查询洗眼器列表
     * 
     * @param tSafetyEyeWasher 洗眼器
     * @return 洗眼器
     */
    @Override
    public List<TSafetyEyeWasher> selectTSafetyEyeWasherList(TSafetyEyeWasher tSafetyEyeWasher)
    {
        return tSafetyEyeWasherMapper.selectTSafetyEyeWasherList(tSafetyEyeWasher);
    }

    /**
     * 新增洗眼器
     * 
     * @param tSafetyEyeWasher 洗眼器
     * @return 结果
     */
    @Override
    public int insertTSafetyEyeWasher(TSafetyEyeWasher tSafetyEyeWasher)
    {
        return tSafetyEyeWasherMapper.insertTSafetyEyeWasher(tSafetyEyeWasher);
    }

    /**
     * 修改洗眼器
     * 
     * @param tSafetyEyeWasher 洗眼器
     * @return 结果
     */
    @Override
    public int updateTSafetyEyeWasher(TSafetyEyeWasher tSafetyEyeWasher)
    {
        return tSafetyEyeWasherMapper.updateTSafetyEyeWasher(tSafetyEyeWasher);
    }

    /**
     * 批量删除洗眼器
     * 
     * @param ids 需要删除的洗眼器主键
     * @return 结果
     */
    @Override
    public int deleteTSafetyEyeWasherByIds(Long[] ids)
    {
        return tSafetyEyeWasherMapper.deleteTSafetyEyeWasherByIds(ids);
    }

    /**
     * 删除洗眼器信息
     * 
     * @param id 洗眼器主键
     * @return 结果
     */
    @Override
    public int deleteTSafetyEyeWasherById(Long id)
    {
        return tSafetyEyeWasherMapper.deleteTSafetyEyeWasherById(id);
    }
}
