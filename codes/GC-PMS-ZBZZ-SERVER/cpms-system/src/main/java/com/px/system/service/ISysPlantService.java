package com.px.system.service;


import com.px.system.domain.SysPlant;

import java.util.List;

/**
 * 装置管理Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface ISysPlantService
{
    /**
     * 查询装置管理
     *
     * @param plantId 装置管理ID
     * @return 装置管理
     */
    public SysPlant selectSysPlantById(Long plantId);

    /**
     * 查询装置管理列表
     *
     * @param sysPlant 装置管理
     * @return 装置管理集合
     */
    public List<SysPlant> selectSysPlantList(SysPlant sysPlant);

    /**
     * 新增装置管理
     *
     * @param sysPlant 装置管理
     * @return 结果
     */
    public int insertSysPlant(SysPlant sysPlant);

    /**
     * 修改装置管理
     *
     * @param sysPlant 装置管理
     * @return 结果
     */
    public int updateSysPlant(SysPlant sysPlant);

    /**
     * 批量删除装置管理
     *
     * @param plantIds 需要删除的装置管理ID
     * @return 结果
     */
    public int deleteSysPlantByIds(Long[] plantIds);

    /**
     * 删除装置管理信息
     *
     * @param plantId 装置管理ID
     * @return 结果
     */
    public int deleteSysPlantById(Long plantId);

    List<SysPlant> selectSysPlantByPlants(List plantIds);

    List<SysPlant> selectSysMyPlantList(SysPlant sysPlant);

    List<SysPlant> selectSysMyPlantList3(SysPlant sysPlant);

    List<SysPlant> selectSysPlantByDeptId(Long deptId);
}
