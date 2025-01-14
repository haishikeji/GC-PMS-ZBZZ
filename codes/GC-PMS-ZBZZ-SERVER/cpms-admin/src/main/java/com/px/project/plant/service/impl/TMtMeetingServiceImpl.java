package com.px.project.plant.service.impl;

import java.util.List;

import com.px.project.plant.domain.*;
import com.px.project.plant.mapper.*;
import org.springframework.stereotype.Service;
import com.px.project.plant.service.ITMtMeetingService;

import javax.annotation.Resource;

/**
 * 装置会议Service业务层处理
 *
 * @author 品讯科技
 * @date 2024-08
 */
@Service
public class TMtMeetingServiceImpl implements ITMtMeetingService {
    @Resource
    private TMtMeetingMapper tMtMeetingMapper;
    @Resource
    private TMtAgendaMapper tMtAgendaMapper;
    @Resource
    private TMtPersonMapper tMtPersonMapper;
    @Resource
    private TMtContentMapper tMtContentMapper;

    /**
     * 查询装置会议
     *
     * @param id 装置会议ID
     * @return 装置会议
     */
    @Override
    public TMtMeeting selectTMtMeetingById(Long id) {
        return tMtMeetingMapper.selectTMtMeetingById(id);
    }

    /**
     * 查询装置会议列表
     *
     * @param tMtMeeting 装置会议
     * @return 装置会议
     */
    @Override
    public List<TMtMeeting> selectTMtMeetingList(TMtMeeting tMtMeeting) {
        return tMtMeetingMapper.selectTMtMeetingList(tMtMeeting);
    }

    /**
     * 新增装置会议
     *
     * @param tMtMeeting 装置会议
     * @return 结果
     */
    @Override
    public int insertTMtMeeting(TMtMeeting tMtMeeting) {
        tMtMeetingMapper.insertTMtMeeting(tMtMeeting);
        if (1 == tMtMeeting.getMtType()) {
            List<TMtAgenda> tMtAgendaList = tMtAgendaMapper.selectTMtAgendaList(tMtMeeting.gettMtAgenda());
            for (TMtAgenda item : tMtAgendaList) {
                item.setIsHis(1L);
                item.setMeetingId(String.valueOf(tMtMeeting.getId()));
                tMtAgendaMapper.updateTMtAgenda(item);
                if (!"Finished".equals(item.getStatus())) {
                    item.setIsHis(0L);
                    item.setMeetingId(null);
                    item.setId(item.getId());
                    tMtAgendaMapper.insertTMtAgenda(item);
                }
            }
            TMtPerson person = new TMtPerson();
            person.setIsCc(1L);
            List<TMtPerson> tMtPersonList = tMtPersonMapper.selectTMtPersonList(person);
            for (TMtPerson item : tMtPersonList) {
                item.setIsHis(1L);
                item.setMeetingId(tMtMeeting.getId());
                tMtPersonMapper.updateTMtPerson(item);
                item.setIsHis(0L);
                item.setIsCc(1L);
                item.setMeetingId(null);
                item.setId(null);
                tMtPersonMapper.insertTMtPerson(item);
            }
        } else {
            TMtContent content = new TMtContent();
            content.setIsHis(0);
            for (TMtContent item : tMtContentMapper.selectTMtContentList(content)) {
                item.setIsHis(1);
                item.setMeetingId(String.valueOf(tMtMeeting.getId()));
                tMtContentMapper.updateTMtContent(item);
            }
            List<TMtPerson> tMtPersonList = tMtPersonMapper.selectTMtPersonList(new TMtPerson());
            for (TMtPerson item : tMtPersonList) {
                item.setIsHis(1L);
                item.setMeetingId(tMtMeeting.getId());
                tMtPersonMapper.updateTMtPerson(item);
            }
        }
        return 1;
    }

    /**
     * 修改装置会议
     *
     * @param tMtMeeting 装置会议
     * @return 结果
     */
    @Override
    public int updateTMtMeeting(TMtMeeting tMtMeeting) {
        return tMtMeetingMapper.updateTMtMeeting(tMtMeeting);
    }

    /**
     * 批量删除装置会议
     *
     * @param ids 需要删除的装置会议ID
     * @return 结果
     */
    @Override
    public int deleteTMtMeetingByIds(Long[] ids) {
        return tMtMeetingMapper.deleteTMtMeetingByIds(ids);
    }

    /**
     * 删除装置会议信息
     *
     * @param id 装置会议ID
     * @return 结果
     */
    @Override
    public int deleteTMtMeetingById(Long id) {
        return tMtMeetingMapper.deleteTMtMeetingById(id);
    }
}
