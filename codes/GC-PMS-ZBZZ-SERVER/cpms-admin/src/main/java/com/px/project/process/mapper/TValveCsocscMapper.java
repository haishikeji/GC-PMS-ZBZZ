package com.px.project.process.mapper;

import java.util.List;
import com.px.project.process.domain.TValveCsocsc;

/**
 * 阀门铅封开关状态记录Mapper接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface TValveCsocscMapper 
{
    /**
     * 查询阀门铅封开关状态记录
     * 
     * @param id 阀门铅封开关状态记录主键
     * @return 阀门铅封开关状态记录
     */
    public TValveCsocsc selectTValveCsocscById(Long id);

    /**
     * 查询阀门铅封开关状态记录列表
     * 
     * @param tValveCsocsc 阀门铅封开关状态记录
     * @return 阀门铅封开关状态记录集合
     */
    public List<TValveCsocsc> selectTValveCsocscList(TValveCsocsc tValveCsocsc);

    /**
     * 新增阀门铅封开关状态记录
     * 
     * @param tValveCsocsc 阀门铅封开关状态记录
     * @return 结果
     */
    public int insertTValveCsocsc(TValveCsocsc tValveCsocsc);

    /**
     * 修改阀门铅封开关状态记录
     * 
     * @param tValveCsocsc 阀门铅封开关状态记录
     * @return 结果
     */
    public int updateTValveCsocsc(TValveCsocsc tValveCsocsc);

    /**
     * 删除阀门铅封开关状态记录
     * 
     * @param id 阀门铅封开关状态记录主键
     * @return 结果
     */
    public int deleteTValveCsocscById(Long id);

    /**
     * 批量删除阀门铅封开关状态记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTValveCsocscByIds(Long[] ids);
}
