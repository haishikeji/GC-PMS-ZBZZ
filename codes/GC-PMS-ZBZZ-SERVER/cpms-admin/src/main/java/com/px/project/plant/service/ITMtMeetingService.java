package com.px.project.plant.service;

import java.util.List;
import com.px.project.plant.domain.TMtMeeting;

/**
 * 装置会议Service接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface ITMtMeetingService 
{
    /**
     * 查询装置会议
     * 
     * @param id 装置会议ID
     * @return 装置会议
     */
    public TMtMeeting selectTMtMeetingById(Long id);

    /**
     * 查询装置会议列表
     * 
     * @param tMtMeeting 装置会议
     * @return 装置会议集合
     */
    public List<TMtMeeting> selectTMtMeetingList(TMtMeeting tMtMeeting);

    /**
     * 新增装置会议
     * 
     * @param tMtMeeting 装置会议
     * @return 结果
     */
    public int insertTMtMeeting(TMtMeeting tMtMeeting);

    /**
     * 修改装置会议
     * 
     * @param tMtMeeting 装置会议
     * @return 结果
     */
    public int updateTMtMeeting(TMtMeeting tMtMeeting);

    /**
     * 批量删除装置会议
     * 
     * @param ids 需要删除的装置会议ID
     * @return 结果
     */
    public int deleteTMtMeetingByIds(Long[] ids);

    /**
     * 删除装置会议信息
     * 
     * @param id 装置会议ID
     * @return 结果
     */
    public int deleteTMtMeetingById(Long id);
}
