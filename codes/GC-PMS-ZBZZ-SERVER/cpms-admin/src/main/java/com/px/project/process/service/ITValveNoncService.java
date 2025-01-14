package com.px.project.process.service;

import java.util.List;
import com.px.project.process.domain.TValveNonc;

/**
 * 阀门常开/常关状态记录Service接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface ITValveNoncService 
{
    /**
     * 查询阀门常开/常关状态记录
     * 
     * @param id 阀门常开/常关状态记录主键
     * @return 阀门常开/常关状态记录
     */
    public TValveNonc selectTValveNoncById(Long id);

    /**
     * 查询阀门常开/常关状态记录列表
     * 
     * @param tValveNonc 阀门常开/常关状态记录
     * @return 阀门常开/常关状态记录集合
     */
    public List<TValveNonc> selectTValveNoncList(TValveNonc tValveNonc);

    /**
     * 新增阀门常开/常关状态记录
     * 
     * @param tValveNonc 阀门常开/常关状态记录
     * @return 结果
     */
    public int insertTValveNonc(TValveNonc tValveNonc);

    /**
     * 修改阀门常开/常关状态记录
     * 
     * @param tValveNonc 阀门常开/常关状态记录
     * @return 结果
     */
    public int updateTValveNonc(TValveNonc tValveNonc);

    /**
     * 批量删除阀门常开/常关状态记录
     * 
     * @param ids 需要删除的阀门常开/常关状态记录主键集合
     * @return 结果
     */
    public int deleteTValveNoncByIds(Long[] ids);

    /**
     * 删除阀门常开/常关状态记录信息
     * 
     * @param id 阀门常开/常关状态记录主键
     * @return 结果
     */
    public int deleteTValveNoncById(Long id);
}
