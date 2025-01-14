package com.px.project.process.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.px.project.process.mapper.TValveNoncMapper;
import com.px.project.process.domain.TValveNonc;
import com.px.project.process.service.ITValveNoncService;

/**
 * 阀门常开/常关状态记录Service业务层处理
 * 
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class TValveNoncServiceImpl implements ITValveNoncService 
{
    @Autowired
    private TValveNoncMapper tValveNoncMapper;

    /**
     * 查询阀门常开/常关状态记录
     * 
     * @param id 阀门常开/常关状态记录主键
     * @return 阀门常开/常关状态记录
     */
    @Override
    public TValveNonc selectTValveNoncById(Long id)
    {
        return tValveNoncMapper.selectTValveNoncById(id);
    }

    /**
     * 查询阀门常开/常关状态记录列表
     * 
     * @param tValveNonc 阀门常开/常关状态记录
     * @return 阀门常开/常关状态记录
     */
    @Override
    public List<TValveNonc> selectTValveNoncList(TValveNonc tValveNonc)
    {
        return tValveNoncMapper.selectTValveNoncList(tValveNonc);
    }

    /**
     * 新增阀门常开/常关状态记录
     * 
     * @param tValveNonc 阀门常开/常关状态记录
     * @return 结果
     */
    @Override
    public int insertTValveNonc(TValveNonc tValveNonc)
    {
        return tValveNoncMapper.insertTValveNonc(tValveNonc);
    }

    /**
     * 修改阀门常开/常关状态记录
     * 
     * @param tValveNonc 阀门常开/常关状态记录
     * @return 结果
     */
    @Override
    public int updateTValveNonc(TValveNonc tValveNonc)
    {
        return tValveNoncMapper.updateTValveNonc(tValveNonc);
    }

    /**
     * 批量删除阀门常开/常关状态记录
     * 
     * @param ids 需要删除的阀门常开/常关状态记录主键
     * @return 结果
     */
    @Override
    public int deleteTValveNoncByIds(Long[] ids)
    {
        return tValveNoncMapper.deleteTValveNoncByIds(ids);
    }

    /**
     * 删除阀门常开/常关状态记录信息
     * 
     * @param id 阀门常开/常关状态记录主键
     * @return 结果
     */
    @Override
    public int deleteTValveNoncById(Long id)
    {
        return tValveNoncMapper.deleteTValveNoncById(id);
    }
}
