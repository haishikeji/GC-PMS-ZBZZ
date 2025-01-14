package com.px.project.asset.service;

import com.px.project.asset.domain.TSpecdevFixedAsset;

import java.util.List;

/**
 * 固定资产Service接口
 *
 * @author 品讯科技
 * @date 2024-08
 */
public interface ITSpecdevFixedAssetService
{
    /**
     * 查询固定资产
     *
     * @param id 固定资产ID
     * @return 固定资产
     */
    public TSpecdevFixedAsset selectTSpecdevFixedAssetById(Long id);

    /**
     * 查询固定资产列表
     *
     * @param tSpecdevFixedAsset 固定资产
     * @return 固定资产集合
     */
    public List<TSpecdevFixedAsset> selectTSpecdevFixedAssetList(TSpecdevFixedAsset tSpecdevFixedAsset);

    /**
     * 新增固定资产
     *
     * @param tSpecdevFixedAsset 固定资产
     * @return 结果
     */
    public int insertTSpecdevFixedAsset(TSpecdevFixedAsset tSpecdevFixedAsset);

    /**
     * 修改固定资产
     *
     * @param tSpecdevFixedAsset 固定资产
     * @return 结果
     */
    public int updateTSpecdevFixedAsset(TSpecdevFixedAsset tSpecdevFixedAsset);

    /**
     * 批量删除固定资产
     *
     * @param ids 需要删除的固定资产ID
     * @return 结果
     */
    public int deleteTSpecdevFixedAssetByIds(Long[] ids);

    /**
     * 删除固定资产信息
     *
     * @param id 固定资产ID
     * @return 结果
     */
    public int deleteTSpecdevFixedAssetById(Long id);
}
