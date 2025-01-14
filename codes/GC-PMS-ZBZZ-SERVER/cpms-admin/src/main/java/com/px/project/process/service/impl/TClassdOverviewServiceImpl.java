package com.px.project.process.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.px.project.process.mapper.TClassdOverviewMapper;
import com.px.project.process.domain.TClassdOverview;
import com.px.project.process.service.ITClassdOverviewService;

/**
 * Class D overview listService业务层处理
 * 
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class TClassdOverviewServiceImpl implements ITClassdOverviewService 
{
    @Autowired
    private TClassdOverviewMapper tClassdOverviewMapper;

    /**
     * 查询Class D overview list
     * 
     * @param id Class D overview list主键
     * @return Class D overview list
     */
    @Override
    public TClassdOverview selectTClassdOverviewById(Long id)
    {
        return tClassdOverviewMapper.selectTClassdOverviewById(id);
    }

    /**
     * 查询Class D overview list列表
     * 
     * @param tClassdOverview Class D overview list
     * @return Class D overview list
     */
    @Override
    public List<TClassdOverview> selectTClassdOverviewList(TClassdOverview tClassdOverview)
    {
        return tClassdOverviewMapper.selectTClassdOverviewList(tClassdOverview);
    }

    /**
     * 新增Class D overview list
     * 
     * @param tClassdOverview Class D overview list
     * @return 结果
     */
    @Override
    public int insertTClassdOverview(TClassdOverview tClassdOverview)
    {
        return tClassdOverviewMapper.insertTClassdOverview(tClassdOverview);
    }

    /**
     * 修改Class D overview list
     * 
     * @param tClassdOverview Class D overview list
     * @return 结果
     */
    @Override
    public int updateTClassdOverview(TClassdOverview tClassdOverview)
    {
        return tClassdOverviewMapper.updateTClassdOverview(tClassdOverview);
    }

    /**
     * 批量删除Class D overview list
     * 
     * @param ids 需要删除的Class D overview list主键
     * @return 结果
     */
    @Override
    public int deleteTClassdOverviewByIds(Long[] ids)
    {
        return tClassdOverviewMapper.deleteTClassdOverviewByIds(ids);
    }

    /**
     * 删除Class D overview list信息
     * 
     * @param id Class D overview list主键
     * @return 结果
     */
    @Override
    public int deleteTClassdOverviewById(Long id)
    {
        return tClassdOverviewMapper.deleteTClassdOverviewById(id);
    }
}
