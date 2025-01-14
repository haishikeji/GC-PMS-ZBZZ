package com.px.project.safety.mapper;


import com.px.project.safety.domain.TSafetyFireExtinguisher;

import java.util.List;

/**
 * 灭火器Mapper接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface TSafetyFireExtinguisherMapper 
{
    /**
     * 查询灭火器
     * 
     * @param id 灭火器主键
     * @return 灭火器
     */
    public TSafetyFireExtinguisher selectTSafetyFireExtinguisherById(Long id);

    /**
     * 查询灭火器列表
     * 
     * @param tSafetyFireExtinguisher 灭火器
     * @return 灭火器集合
     */
    public List<TSafetyFireExtinguisher> selectTSafetyFireExtinguisherList(TSafetyFireExtinguisher tSafetyFireExtinguisher);

    /**
     * 新增灭火器
     * 
     * @param tSafetyFireExtinguisher 灭火器
     * @return 结果
     */
    public int insertTSafetyFireExtinguisher(TSafetyFireExtinguisher tSafetyFireExtinguisher);

    /**
     * 修改灭火器
     * 
     * @param tSafetyFireExtinguisher 灭火器
     * @return 结果
     */
    public int updateTSafetyFireExtinguisher(TSafetyFireExtinguisher tSafetyFireExtinguisher);

    /**
     * 删除灭火器
     * 
     * @param id 灭火器主键
     * @return 结果
     */
    public int deleteTSafetyFireExtinguisherById(Long id);

    /**
     * 批量删除灭火器
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTSafetyFireExtinguisherByIds(Long[] ids);
}
