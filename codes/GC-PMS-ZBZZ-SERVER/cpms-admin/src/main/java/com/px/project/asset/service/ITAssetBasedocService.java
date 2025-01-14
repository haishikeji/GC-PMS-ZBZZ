package com.px.project.asset.service;

import java.util.List;
import com.px.project.asset.domain.TAssetBasedoc;

/**
 * 资产管理基础文档清单Service接口
 * 
 * @author 品讯科技
 * @date 2024-08
 */
public interface ITAssetBasedocService 
{
    /**
     * 查询资产管理基础文档清单
     * 
     * @param id 资产管理基础文档清单主键
     * @return 资产管理基础文档清单
     */
    public TAssetBasedoc selectTAssetBasedocById(Long id);

    /**
     * 查询资产管理基础文档清单列表
     * 
     * @param tAssetBasedoc 资产管理基础文档清单
     * @return 资产管理基础文档清单集合
     */
    public List<TAssetBasedoc> selectTAssetBasedocList(TAssetBasedoc tAssetBasedoc);

    /**
     * 新增资产管理基础文档清单
     * 
     * @param tAssetBasedoc 资产管理基础文档清单
     * @return 结果
     */
    public int insertTAssetBasedoc(TAssetBasedoc tAssetBasedoc);

    /**
     * 修改资产管理基础文档清单
     * 
     * @param tAssetBasedoc 资产管理基础文档清单
     * @return 结果
     */
    public int updateTAssetBasedoc(TAssetBasedoc tAssetBasedoc);

    /**
     * 批量删除资产管理基础文档清单
     * 
     * @param ids 需要删除的资产管理基础文档清单主键集合
     * @return 结果
     */
    public int deleteTAssetBasedocByIds(Long[] ids);

    /**
     * 删除资产管理基础文档清单信息
     * 
     * @param id 资产管理基础文档清单主键
     * @return 结果
     */
    public int deleteTAssetBasedocById(Long id);
}
