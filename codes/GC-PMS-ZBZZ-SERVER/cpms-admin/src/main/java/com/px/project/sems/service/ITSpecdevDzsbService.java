package com.px.project.sems.service;

import com.px.project.sems.domain.TSpecdevDzsb;

import java.util.List;

/**
 * 特种设备吊装设备台账Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface ITSpecdevDzsbService {
    /**
     * 查询特种设备吊装设备台账
     *
     * @param id 特种设备吊装设备台账ID
     * @return 特种设备吊装设备台账
     */
    public TSpecdevDzsb selectTSpecdevDzsbById(Long id);

    /**
     * 查询特种设备吊装设备台账列表
     *
     * @param tSpecdevDzsb 特种设备吊装设备台账
     * @return 特种设备吊装设备台账集合
     */
    public List<TSpecdevDzsb> selectTSpecdevDzsbList(TSpecdevDzsb tSpecdevDzsb);

    List<TSpecdevDzsb> selectTSpecdevDzsbListByYear(TSpecdevDzsb tSpecdevDzsb);

    /**
     * 新增特种设备吊装设备台账
     *
     * @param tSpecdevDzsb 特种设备吊装设备台账
     * @return 结果
     */
    public int insertTSpecdevDzsb(TSpecdevDzsb tSpecdevDzsb);

    /**
     * 修改特种设备吊装设备台账
     *
     * @param tSpecdevDzsb 特种设备吊装设备台账
     * @return 结果
     */
    public int updateTSpecdevDzsb(TSpecdevDzsb tSpecdevDzsb);

    /**
     * 批量删除特种设备吊装设备台账
     *
     * @param ids 需要删除的特种设备吊装设备台账ID
     * @return 结果
     */
    public int deleteTSpecdevDzsbByIds(Long[] ids);

    /**
     * 删除特种设备吊装设备台账信息
     *
     * @param id 特种设备吊装设备台账ID
     * @return 结果
     */
    public int deleteTSpecdevDzsbById(Long id);


    void duplicateTSpecdevDzsb();
}
