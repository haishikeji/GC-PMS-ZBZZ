package com.px.project.process.service;

import java.util.List;
import com.px.project.process.domain.TMoc;

/**
 * MOC清单Service接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface ITMocService 
{
    /**
     * 查询MOC清单
     * 
     * @param id MOC清单主键
     * @return MOC清单
     */
    public TMoc selectTMocById(Long id);

    public TMoc selectTMocByMocNo(String mocNo);

    /**
     * 查询MOC清单列表
     * 
     * @param tMoc MOC清单
     * @return MOC清单集合
     */
    public List<TMoc> selectTMocList(TMoc tMoc);

    /**
     * 新增MOC清单
     * 
     * @param tMoc MOC清单
     * @return 结果
     */
    public int insertTMoc(TMoc tMoc);

    /**
     * 修改MOC清单
     * 
     * @param tMoc MOC清单
     * @return 结果
     */
    public int updateTMoc(TMoc tMoc);

    /**
     * 批量删除MOC清单
     * 
     * @param ids 需要删除的MOC清单主键集合
     * @return 结果
     */
    public int deleteTMocByIds(Long[] ids);

    /**
     * 删除MOC清单信息
     * 
     * @param id MOC清单主键
     * @return 结果
     */
    public int deleteTMocById(Long id);
}
