package com.px.project.sems.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.px.project.sems.mapper.TSpecdevDzsbMapper;
import com.px.project.sems.domain.TSpecdevDzsb;
import com.px.project.sems.service.ITSpecdevDzsbService;

/**
 * 特种设备吊装设备台账Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service("tSpecdevDzsbService")
public class TSpecdevDzsbServiceImpl implements ITSpecdevDzsbService
{
    @Autowired
    private TSpecdevDzsbMapper tSpecdevDzsbMapper;

    /**
     * 查询特种设备吊装设备台账
     *
     * @param id 特种设备吊装设备台账ID
     * @return 特种设备吊装设备台账
     */
    @Override
    public TSpecdevDzsb selectTSpecdevDzsbById(Long id)
    {
        return tSpecdevDzsbMapper.selectTSpecdevDzsbById(id);
    }

    /**
     * 查询特种设备吊装设备台账列表
     *
     * @param tSpecdevDzsb 特种设备吊装设备台账
     * @return 特种设备吊装设备台账
     */
    @Override
    public List<TSpecdevDzsb> selectTSpecdevDzsbList(TSpecdevDzsb tSpecdevDzsb)
    {
        return tSpecdevDzsbMapper.selectTSpecdevDzsbList(tSpecdevDzsb);
    }

    @Override
    public List<TSpecdevDzsb> selectTSpecdevDzsbListByYear(TSpecdevDzsb tSpecdevDzsb) {
        return tSpecdevDzsbMapper.selectTSpecdevDzsbListByYear(tSpecdevDzsb);
    }

    /**
     * 新增特种设备吊装设备台账
     *
     * @param tSpecdevDzsb 特种设备吊装设备台账
     * @return 结果
     */
    @Override
    public int insertTSpecdevDzsb(TSpecdevDzsb tSpecdevDzsb)
    {
        return tSpecdevDzsbMapper.insertTSpecdevDzsb(tSpecdevDzsb);
    }

    /**
     * 修改特种设备吊装设备台账
     *
     * @param tSpecdevDzsb 特种设备吊装设备台账
     * @return 结果
     */
    @Override
    public int updateTSpecdevDzsb(TSpecdevDzsb tSpecdevDzsb)
    {
        return tSpecdevDzsbMapper.updateTSpecdevDzsb(tSpecdevDzsb);
    }

    /**
     * 批量删除特种设备吊装设备台账
     *
     * @param ids 需要删除的特种设备吊装设备台账ID
     * @return 结果
     */
    @Override
    public int deleteTSpecdevDzsbByIds(Long[] ids)
    {
        return tSpecdevDzsbMapper.deleteTSpecdevDzsbByIds(ids);
    }

    /**
     * 删除特种设备吊装设备台账信息
     *
     * @param id 特种设备吊装设备台账ID
     * @return 结果
     */
    @Override
    public int deleteTSpecdevDzsbById(Long id)
    {
        return tSpecdevDzsbMapper.deleteTSpecdevDzsbById(id);
    }


    @Override
    public void duplicateTSpecdevDzsb() {
        tSpecdevDzsbMapper.duplicateTSpecdevDzsb();
    }
}
