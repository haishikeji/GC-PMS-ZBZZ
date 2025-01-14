package com.px.project.process.service.impl;

import java.util.List;

import com.px.project.process.domain.TSifVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.px.project.process.mapper.TSifOverviewMapper;
import com.px.project.process.domain.TSifOverview;
import com.px.project.process.service.ITSifOverviewService;

/**
 * SIF Overview ListService业务层处理
 * 
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class TSifOverviewServiceImpl implements ITSifOverviewService 
{
    @Autowired
    private TSifOverviewMapper tSifOverviewMapper;

    /**
     * 查询SIF Overview List
     * 
     * @param id SIF Overview List主键
     * @return SIF Overview List
     */
    @Override
    public TSifOverview selectTSifOverviewById(Long id)
    {
        return tSifOverviewMapper.selectTSifOverviewById(id);
    }

    /**
     * 查询SIF Overview List列表
     * 
     * @param tSifOverview SIF Overview List
     * @return SIF Overview List
     */
    @Override
    public List<TSifVo> selectTSifOverviewList(TSifOverview tSifOverview)
    {
        return tSifOverviewMapper.selectTSifOverviewList(tSifOverview);
    }

    /**
     * 新增SIF Overview List
     * 
     * @param tSifOverview SIF Overview List
     * @return 结果
     */
    @Override
    public int insertTSifOverview(TSifOverview tSifOverview)
    {
        return tSifOverviewMapper.insertTSifOverview(tSifOverview);
    }

    /**
     * 修改SIF Overview List
     * 
     * @param tSifOverview SIF Overview List
     * @return 结果
     */
    @Override
    public int updateTSifOverview(TSifOverview tSifOverview)
    {
        return tSifOverviewMapper.updateTSifOverview(tSifOverview);
    }

    /**
     * 批量删除SIF Overview List
     * 
     * @param ids 需要删除的SIF Overview List主键
     * @return 结果
     */
    @Override
    public int deleteTSifOverviewByIds(Long[] ids)
    {
        return tSifOverviewMapper.deleteTSifOverviewByIds(ids);
    }

    /**
     * 删除SIF Overview List信息
     * 
     * @param id SIF Overview List主键
     * @return 结果
     */
    @Override
    public int deleteTSifOverviewById(Long id)
    {
        return tSifOverviewMapper.deleteTSifOverviewById(id);
    }
}
