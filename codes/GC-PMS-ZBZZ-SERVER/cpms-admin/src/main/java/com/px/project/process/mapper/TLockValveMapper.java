package com.px.project.process.mapper;

import java.util.List;
import com.px.project.process.domain.TLockValve;

/**
 * 阀门锁开锁关状态记录Mapper接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface TLockValveMapper 
{
    /**
     * 查询阀门锁开锁关状态记录
     * 
     * @param id 阀门锁开锁关状态记录主键
     * @return 阀门锁开锁关状态记录
     */
    public TLockValve selectTLockValveById(Long id);

    /**
     * 查询阀门锁开锁关状态记录列表
     * 
     * @param tLockValve 阀门锁开锁关状态记录
     * @return 阀门锁开锁关状态记录集合
     */
    public List<TLockValve> selectTLockValveList(TLockValve tLockValve);

    /**
     * 新增阀门锁开锁关状态记录
     * 
     * @param tLockValve 阀门锁开锁关状态记录
     * @return 结果
     */
    public int insertTLockValve(TLockValve tLockValve);

    /**
     * 修改阀门锁开锁关状态记录
     * 
     * @param tLockValve 阀门锁开锁关状态记录
     * @return 结果
     */
    public int updateTLockValve(TLockValve tLockValve);

    /**
     * 删除阀门锁开锁关状态记录
     * 
     * @param id 阀门锁开锁关状态记录主键
     * @return 结果
     */
    public int deleteTLockValveById(Long id);

    /**
     * 批量删除阀门锁开锁关状态记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTLockValveByIds(Long[] ids);
}
