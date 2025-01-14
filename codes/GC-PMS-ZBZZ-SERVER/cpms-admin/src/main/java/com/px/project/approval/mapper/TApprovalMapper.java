package com.px.project.approval.mapper;

import com.px.project.approval.domain.TApproval;

import java.util.List;

/**
 * 批文清单Mapper接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface TApprovalMapper 
{
    /**
     * 查询批文清单
     * 
     * @param id 批文清单主键
     * @return 批文清单
     */
    public TApproval selectTApprovalById(Long id);

    /**
     * 查询批文清单列表
     * 
     * @param tApproval 批文清单
     * @return 批文清单集合
     */
    public List<TApproval> selectTApprovalList(TApproval tApproval);

    /**
     * 新增批文清单
     * 
     * @param tApproval 批文清单
     * @return 结果
     */
    public int insertTApproval(TApproval tApproval);

    /**
     * 修改批文清单
     * 
     * @param tApproval 批文清单
     * @return 结果
     */
    public int updateTApproval(TApproval tApproval);

    /**
     * 删除批文清单
     * 
     * @param id 批文清单主键
     * @return 结果
     */
    public int deleteTApprovalById(Long id);

    /**
     * 批量删除批文清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTApprovalByIds(Long[] ids);
}
