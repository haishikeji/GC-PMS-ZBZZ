package com.px.project.sems.service;

import com.px.project.sems.domain.TSpecdevYlrq;

import java.util.List;

/**
 * 特种设备压力容器台账Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface ITSpecdevYlrqService
{
    /**
     * 查询特种设备压力容器台账
     *
     * @param id 特种设备压力容器台账ID
     * @return 特种设备压力容器台账
     */
    public TSpecdevYlrq selectTSpecdevYlrqById(Long id);

    /**
     * 查询特种设备压力容器台账列表
     *
     * @param tSpecdevYlrq 特种设备压力容器台账
     * @return 特种设备压力容器台账集合
     */
    public List<TSpecdevYlrq> selectTSpecdevYlrqList(TSpecdevYlrq tSpecdevYlrq);

    List<TSpecdevYlrq> selectTSpecdevYlrqListByYear(TSpecdevYlrq tSpecdevYlrq);
    /**
     * 新增特种设备压力容器台账
     *
     * @param tSpecdevYlrq 特种设备压力容器台账
     * @return 结果
     */
    public int insertTSpecdevYlrq(TSpecdevYlrq tSpecdevYlrq);

    /**
     * 修改特种设备压力容器台账
     *
     * @param tSpecdevYlrq 特种设备压力容器台账
     * @return 结果
     */
    public int updateTSpecdevYlrq(TSpecdevYlrq tSpecdevYlrq);

    /**
     * 批量删除特种设备压力容器台账
     *
     * @param ids 需要删除的特种设备压力容器台账ID
     * @return 结果
     */
    public int deleteTSpecdevYlrqByIds(Long[] ids);

    /**
     * 删除特种设备压力容器台账信息
     *
     * @param id 特种设备压力容器台账ID
     * @return 结果
     */
    public int deleteTSpecdevYlrqById(Long id);

    void duplicateTSpecdevYlrq();
}
