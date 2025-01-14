package com.px.project.sems.service;

import com.px.project.sems.domain.TSpecdevDt;

import java.util.List;

/**
 * 特种设备电梯台账Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface ITSpecdevDtService {
    /**
     * 查询特种设备电梯台账
     *
     * @param id 特种设备电梯台账ID
     * @return 特种设备电梯台账
     */
    public TSpecdevDt selectTSpecdevDtById(Long id);

    /**
     * 查询特种设备电梯台账列表
     *
     * @param tSpecdevDt 特种设备电梯台账
     * @return 特种设备电梯台账集合
     */
    public List<TSpecdevDt> selectTSpecdevDtList(TSpecdevDt tSpecdevDt);

    List<TSpecdevDt> selectTSpecdevDtListByYear(TSpecdevDt tSpecdevDt);

    /**
     * 新增特种设备电梯台账
     *
     * @param tSpecdevDt 特种设备电梯台账
     * @return 结果
     */
    public int insertTSpecdevDt(TSpecdevDt tSpecdevDt);

    /**
     * 修改特种设备电梯台账
     *
     * @param tSpecdevDt 特种设备电梯台账
     * @return 结果
     */
    public int updateTSpecdevDt(TSpecdevDt tSpecdevDt);

    /**
     * 批量删除特种设备电梯台账
     *
     * @param ids 需要删除的特种设备电梯台账ID
     * @return 结果
     */
    public int deleteTSpecdevDtByIds(Long[] ids);

    /**
     * 删除特种设备电梯台账信息
     *
     * @param id 特种设备电梯台账ID
     * @return 结果
     */
    public int deleteTSpecdevDtById(Long id);


    void duplicateTSpecdevDt();
}
