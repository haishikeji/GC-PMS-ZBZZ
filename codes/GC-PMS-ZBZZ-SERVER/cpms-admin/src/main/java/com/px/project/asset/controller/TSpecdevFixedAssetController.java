package com.px.project.asset.controller;

 import com.alibaba.fastjson2.JSON;
 import com.px.common.annotation.Log;
 import com.px.common.core.controller.BaseController;
 import com.px.common.core.domain.AjaxResult;
 import com.px.common.core.domain.entity.SysDictData;
 import com.px.common.core.page.TableDataInfo;
 import com.px.common.enums.BusinessType;
 import com.px.common.utils.file.ExcelUtils;
import com.px.common.utils.poi.ExcelUtil;
import com.px.project.asset.domain.TSpecdevFixedAsset;
import com.px.project.asset.service.ITSpecdevFixedAssetService;
 import com.px.system.service.ISysDictTypeService;
 import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 固定资产Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/sems/fixedAsset")
public class TSpecdevFixedAssetController extends BaseController
{
    @Autowired
    private ITSpecdevFixedAssetService tSpecdevFixedAssetService;

    @Autowired
    private ISysDictTypeService iSysDictTypeService;
    /**
     * 查询固定资产列表
     */
    @PreAuthorize("@ss.hasPermi('sems:fixedAsset:list')")
    @GetMapping("/list")
    public TableDataInfo list(TSpecdevFixedAsset tSpecdevFixedAsset)
    {
        startPage();
        List<TSpecdevFixedAsset> list = tSpecdevFixedAssetService.selectTSpecdevFixedAssetList(tSpecdevFixedAsset);
        return getDataTable(list);
    }

    /**
     * 导出固定资产列表
     */
    @PreAuthorize("@ss.hasPermi('sems:fixedAsset:export')")
    @Log(title = "固定资产", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSpecdevFixedAsset tSpecdevFixedAsset)
    {
        List<TSpecdevFixedAsset> list = tSpecdevFixedAssetService.selectTSpecdevFixedAssetList(tSpecdevFixedAsset);
        ExcelUtil<TSpecdevFixedAsset> util = new ExcelUtil<TSpecdevFixedAsset>(TSpecdevFixedAsset.class);
        util.exportExcel(response, list, "asset");
    }

    /**
     * 获取固定资产详细信息
     */
    @PreAuthorize("@ss.hasPermi('sems:fixedAsset:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tSpecdevFixedAssetService.selectTSpecdevFixedAssetById(id));
    }

    /**
     * 新增固定资产
     */
    @PreAuthorize("@ss.hasPermi('sems:fixedAsset:add')")
    @Log(title = "固定资产", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSpecdevFixedAsset tSpecdevFixedAsset)
    {
        return toAjax(tSpecdevFixedAssetService.insertTSpecdevFixedAsset(tSpecdevFixedAsset));
    }

    /**
     * 修改固定资产
     */
    @PreAuthorize("@ss.hasPermi('sems:fixedAsset:edit')")
    @Log(title = "固定资产", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSpecdevFixedAsset tSpecdevFixedAsset)
    {
        return toAjax(tSpecdevFixedAssetService.updateTSpecdevFixedAsset(tSpecdevFixedAsset));
    }

    /**
     * 删除固定资产
     */
    @PreAuthorize("@ss.hasPermi('sems:fixedAsset:remove')")
    @Log(title = "固定资产", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tSpecdevFixedAssetService.deleteTSpecdevFixedAssetByIds(ids));
    }

    @Log(title = "固定资产清单批量导入", businessType = BusinessType.INSERT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) throws IOException {
        //获取操作人员ID
        Long userId = getUserId();
        //报错行数统计
        List<Integer> failRow = new ArrayList<>();
        Workbook workbook = ExcelUtils.getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<TSpecdevFixedAsset> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //字典查询
        List<SysDictData> usingStatus = iSysDictTypeService.selectDictDataByType("spec_dev_status");
        int rowNum = sheet.getPhysicalNumberOfRows();
        int failNumber = 0;
        for (int i = 2; i < rowNum; i++) {
            try {
                logger.info("读取行数：" + i);
                Row row = sheet.getRow(i);
                int cellNum = row.getLastCellNum();
                TSpecdevFixedAsset entity = new TSpecdevFixedAsset();
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    String cellValue = ExcelUtils.getCellValue(cell);
                    logger.info("cellValue:" + cellValue);
                    if (j == 0) {
                        //资产类别
                        entity.setAssetType(cellValue);
                    } else if (j == 1) {
                        //资产编号
                        entity.setAssetNo(cellValue);
                    } else if (j == 2) {
                        //资产子编号
                        entity.setAssetSubNo(cellValue);
                    } else if (j == 3) {
                        //资产描述（中文）
                        entity.setDevName(cellValue);
                    } else if (j == 4) {
                        //资产描述（英文）
                        entity.setDevEnname(cellValue);
                    } else if (j == 5) {
                        //成本中心名称
                        entity.setCostCenter(cellValue);
                    } else if (j == 6) {
                        //成本中心代码
                        entity.setCostCenterCode(cellValue);
                    } else if (j == 7) {
                        //资产类别ap003xxx
                        entity.setAssetTypeAp(cellValue);
                    } else if (j == 8) {
                        //资本化日期
                        entity.setCapitalizedDate(sdf.parse(cellValue));
                    } else if (j == 9) {
                        //数量
                        entity.setQuantity(cellValue);
                    } else if (j == 10) {
                        //币种
                        entity.setCurrency(cellValue);
                    } else if (j == 11) {
                        //原值
                        entity.setCurrentApc(cellValue);
                    } else if (j == 12) {
                        //使用年限
                        entity.setServiceLife(cellValue);
                    } else if (j == 13) {
                        //管理人
                        entity.setAdministrator(cellValue);
                    } else if (j == 14) {
                        //位号
                        entity.setDevNo(cellValue);
                    } else if (j == 15) {
                        //资产详细描述
                        entity.setAssetDetail(cellValue);
                    } else if (j == 16) {
                        //工艺子单元
                        entity.setProcessSubunit(cellValue);
                    } else if (j == 17) {
                        //pid图号/其他图号
                        entity.setPidNo(cellValue);
                    } else if (j == 18) {
                        //安装位置
                        entity.setInstallPosition(cellValue);
                    } else if (j == 19) {
                        //创建原由
                        entity.setCreateReason(cellValue);
                    } else if (j == 20) {
                        //条形码位置
                        entity.setBarcodePosition(cellValue);
                    } else if (j == 21) {
                        //报废原由
                        entity.setScrapReason(cellValue);
                    } else if (j == 22) {
                        //报废提出日期
                        entity.setScrapPoseDate(sdf.parse(cellValue));
                    } else if (j == 23) {
                        //报废完成时间
                        entity.setScrapDoneDate(sdf.parse(cellValue));
                    } else if (j == 24) {
                        //报废人
                        entity.setScraper(cellValue);
                    } else if (j == 25) {
                        //在用状态
                        for (SysDictData dictData : usingStatus) {
                            if (dictData.getDictLabel().equals(cellValue.trim())) {
                                entity.setUsingStatus(dictData.getDictValue());
                            }
                        }
                    } else if (j == 26) {
                        //备注
                        entity.setRemarks(cellValue);
                    }
                }
                entity.setCreaterCode(userId);
                logger.info("entity:" + entity);
                list.add(entity);
            } catch (Exception e) {
                failNumber++;
                logger.info("e:" + JSON.toJSONString(e));
                failRow.add(i + 1);
            }
        }
        int successNumber = 0;
        int failNum = 0;
        for (TSpecdevFixedAsset t : list
        ) {
            failNum++;
            try {
                //根据使用证、注册编号、位号，进行数据更新
                add(t);
                successNumber++;
            } catch (Exception e) {
                failNumber++;
                logger.info("e:" + e);
                failRow.add(failNum + 1);
            }
        }
        logger.info("list:" + JSON.toJSONString(list));
        logger.info("successNumber:" + String.valueOf(successNumber));
        logger.info("failNumber:" + String.valueOf(failNumber));
        logger.info("failRow:" + String.valueOf(failRow));
        return AjaxResult.success(String.valueOf(successNumber), failRow);
    }
}
