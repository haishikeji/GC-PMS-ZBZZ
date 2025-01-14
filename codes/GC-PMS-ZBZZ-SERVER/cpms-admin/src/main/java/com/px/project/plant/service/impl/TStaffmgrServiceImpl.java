package com.px.project.plant.service.impl;

import com.px.common.core.domain.entity.SysUser;
import com.px.project.plant.domain.TStaffmgr;
import com.px.project.plant.mapper.TStaffmgrMapper;
import com.px.project.plant.service.ITStaffmgrService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 人员管理Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class TStaffmgrServiceImpl implements ITStaffmgrService
{
    @Resource
    private TStaffmgrMapper tStaffmgrMapper;

    @Override
    public List<TStaffmgr> selectMentorList(TStaffmgr tStaffmgr) {
        return tStaffmgrMapper.selectMentorList(tStaffmgr);
    }

    /**
     * 查询人员管理
     *
     * @param staffid 人员管理员工编号
     * @return 人员管理
     */
    @Override
    public TStaffmgr selectTStaffmgrByStaffId(String staffid) { return tStaffmgrMapper.selectTStaffmgrByStaffId(staffid); }

    /**
     * 查询人员管理
     *
     * @param id 人员管理ID
     * @return 人员管理
     */
    @Override
    public TStaffmgr selectTStaffmgrById(Long id)
    {
        return tStaffmgrMapper.selectTStaffmgrById(id);
    }

    /**
     * 查询人员管理列表
     *
     * @param tStaffmgr 人员管理
     * @return 人员管理
     */
    @Override
    public List<TStaffmgr> selectTStaffmgrList(TStaffmgr tStaffmgr)
    {
        return tStaffmgrMapper.selectTStaffmgrList(tStaffmgr);
    }

    /**
     * 定时任务查询人员管理列表
     *
     * @param tStaffmgr 人员管理
     * @return 人员管理集合
     */
    @Override
    public List<TStaffmgr> selectList(TStaffmgr tStaffmgr) {
        return tStaffmgrMapper.selectList(tStaffmgr);
    }

    @Override
    public List<TStaffmgr> selectTaskList(TStaffmgr tStaffmgr) {
        return tStaffmgrMapper.selectTaskList(tStaffmgr);
    }

    /**
     * 培训查询人员管理列表
     *
     * @param tStaffmgr 人员管理
     * @return 人员管理集合
     */
    @Override
    public List<TStaffmgr> selectRecordList(TStaffmgr tStaffmgr) {
        return tStaffmgrMapper.selectRecordList(tStaffmgr);
    }

    /**
     * 新增人员管理
     *
     * @param tStaffmgr 人员管理
     * @return 结果
     */
    @Override
    public int insertTStaffmgr(TStaffmgr tStaffmgr)
    {
        return tStaffmgrMapper.insertTStaffmgr(tStaffmgr);
    }

    /**
     * 修改人员管理
     *
     * @param tStaffmgr 人员管理
     * @return 结果
     */
    @Override
    public int updateTStaffmgr(TStaffmgr tStaffmgr)
    {
        return tStaffmgrMapper.updateTStaffmgr(tStaffmgr);
    }

    /**
     * 批量删除人员管理
     *
     * @param ids 需要删除的人员管理ID
     * @return 结果
     */
    @Override
    public int deleteTStaffmgrByIds(Long[] ids)
    {
        return tStaffmgrMapper.deleteTStaffmgrByIds(ids);
    }

    @Override
    public int deleteLeftTStaffmgrByIds(Long ids) {
        return tStaffmgrMapper.deleteLeftTStaffmgrByIds(ids);
    }
    /**
     * 删除人员管理信息
     *
     * @param id 人员管理ID
     * @return 结果
     */
    @Override
    public int deleteTStaffmgrById(Long id)
    {
        return tStaffmgrMapper.deleteTStaffmgrById(id);
    }



    @Override
    public List<TStaffmgr> selectLeftTStaffmgrList(TStaffmgr tStaffmgr) {
        return tStaffmgrMapper.selectLeftTStaffmgrList(tStaffmgr);
    }

    @Override
    public int reLeftTStaffmgrByIds(Long id) {
        return tStaffmgrMapper.reLeftTStaffmgrByIds(id);
    }

    @Override
    public List<TStaffmgr> selectTStaffmgrByPost(SysUser sysUser) {
        return tStaffmgrMapper.selectTStaffmgrByPost(sysUser);
    }
    @Override
    public List<TStaffmgr> selectStaffByTeam(TStaffmgr tStaffmgr) {
        return tStaffmgrMapper.selectStaffByTeam(tStaffmgr);
    }

    @Override
    public List<TStaffmgr> selectTMentorStaffmgrByPost(SysUser sysUser) {
        return tStaffmgrMapper.selectTMentorStaffmgrByPost(sysUser);
    }

}
