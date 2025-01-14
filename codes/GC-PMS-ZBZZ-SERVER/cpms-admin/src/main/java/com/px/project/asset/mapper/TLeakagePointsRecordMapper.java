package com.px.project.asset.mapper;

import java.util.List;
import com.px.project.asset.domain.TLeakagePointsRecord;

/**
 * 漏点巡检记录 Mapper接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface TLeakagePointsRecordMapper 
{
    /**
     * 查询漏点巡检记录 
     * 
     * @param id 漏点巡检记录 主键
     * @return 漏点巡检记录 
     */
    public TLeakagePointsRecord selectTLeakagePointsRecordById(Long id);

    /**
     * 查询漏点巡检记录 列表
     * 
     * @param tLeakagePointsRecord 漏点巡检记录 
     * @return 漏点巡检记录 集合
     */
    public List<TLeakagePointsRecord> selectTLeakagePointsRecordList(TLeakagePointsRecord tLeakagePointsRecord);

    /**
     * 新增漏点巡检记录 
     * 
     * @param tLeakagePointsRecord 漏点巡检记录 
     * @return 结果
     */
    public int insertTLeakagePointsRecord(TLeakagePointsRecord tLeakagePointsRecord);

    /**
     * 修改漏点巡检记录 
     * 
     * @param tLeakagePointsRecord 漏点巡检记录 
     * @return 结果
     */
    public int updateTLeakagePointsRecord(TLeakagePointsRecord tLeakagePointsRecord);

    /**
     * 删除漏点巡检记录 
     * 
     * @param id 漏点巡检记录 主键
     * @return 结果
     */
    public int deleteTLeakagePointsRecordById(Long id);

    /**
     * 批量删除漏点巡检记录 
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTLeakagePointsRecordByIds(Long[] ids);
}
