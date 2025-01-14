package com.px.project.safety.mapper;


import com.px.project.safety.domain.TSafetyFiredoor;

import java.util.List;

/**
 * 防火门Mapper接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface TSafetyFiredoorMapper 
{
    /**
     * 查询防火门
     * 
     * @param id 防火门主键
     * @return 防火门
     */
    public TSafetyFiredoor selectTSafetyFiredoorById(Long id);

    /**
     * 查询防火门列表
     * 
     * @param tSafetyFiredoor 防火门
     * @return 防火门集合
     */
    public List<TSafetyFiredoor> selectTSafetyFiredoorList(TSafetyFiredoor tSafetyFiredoor);

    /**
     * 新增防火门
     * 
     * @param tSafetyFiredoor 防火门
     * @return 结果
     */
    public int insertTSafetyFiredoor(TSafetyFiredoor tSafetyFiredoor);

    /**
     * 修改防火门
     * 
     * @param tSafetyFiredoor 防火门
     * @return 结果
     */
    public int updateTSafetyFiredoor(TSafetyFiredoor tSafetyFiredoor);

    /**
     * 删除防火门
     * 
     * @param id 防火门主键
     * @return 结果
     */
    public int deleteTSafetyFiredoorById(Long id);

    /**
     * 批量删除防火门
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTSafetyFiredoorByIds(Long[] ids);
}
