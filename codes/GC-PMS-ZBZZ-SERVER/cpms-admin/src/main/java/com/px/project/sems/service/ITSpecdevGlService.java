package com.px.project.sems.service;

import com.px.project.sems.domain.TSpecdevGl;

import java.util.List;

/**
 * 特种设备锅炉台账Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface ITSpecdevGlService
{
    /**
     * 查询特种设备锅炉台账
     *
     * @param id 特种设备锅炉台账ID
     * @return 特种设备锅炉台账
     */
    public TSpecdevGl selectTSpecdevGlById(Long id);

    /**
     * 查询特种设备锅炉台账列表
     *
     * @param tSpecdevGl 特种设备锅炉台账
     * @return 特种设备锅炉台账集合
     */
    public List<TSpecdevGl> selectTSpecdevGlList(TSpecdevGl tSpecdevGl);
    List<TSpecdevGl> selectTSpecdevGlListByYear(TSpecdevGl tSpecdevGl);
    /**
     * 新增特种设备锅炉台账
     *
     * @param tSpecdevGl 特种设备锅炉台账
     * @return 结果
     */
    public int insertTSpecdevGl(TSpecdevGl tSpecdevGl);

    /**
     * 修改特种设备锅炉台账
     *
     * @param tSpecdevGl 特种设备锅炉台账
     * @return 结果
     */
    public int updateTSpecdevGl(TSpecdevGl tSpecdevGl);

    /**
     * 批量删除特种设备锅炉台账
     *
     * @param ids 需要删除的特种设备锅炉台账ID
     * @return 结果
     */
    public int deleteTSpecdevGlByIds(Long[] ids);

    /**
     * 删除特种设备锅炉台账信息
     *
     * @param id 特种设备锅炉台账ID
     * @return 结果
     */
    public int deleteTSpecdevGlById(Long id);



    void duplicateTSpecdevGl();
}
