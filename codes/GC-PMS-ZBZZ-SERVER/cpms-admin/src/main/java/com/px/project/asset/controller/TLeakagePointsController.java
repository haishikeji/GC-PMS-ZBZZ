package com.px.project.asset.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSON;
import com.px.common.core.domain.entity.SysDictData;
import com.px.common.utils.DateUtils;
import com.px.common.utils.file.ExcelUtils;
import com.px.project.asset.service.ITLeakagePointsService;
import com.px.system.service.ISysDictTypeService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.px.common.annotation.Log;
import com.px.common.core.controller.BaseController;
import com.px.common.core.domain.AjaxResult;
import com.px.common.enums.BusinessType;
import com.px.project.asset.domain.TLeakagePoints;
import com.px.common.utils.poi.ExcelUtil;
import com.px.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 漏点清单Controller
 * 
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/asset/points")
public class TLeakagePointsController extends BaseController
{
    @Autowired
    private ITLeakagePointsService tLeakagePointsService;

    @Autowired
    private ISysDictTypeService sysDictTypeService;

    /**
     * 查询漏点清单列表
     */
    @PreAuthorize("@ss.hasPermi('asset:points:list')")
    @GetMapping("/list")
    public TableDataInfo list(TLeakagePoints tLeakagePoints)
    {
        startPage();
        List<TLeakagePoints> list = tLeakagePointsService.selectTLeakagePointsList(tLeakagePoints);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('asset:points:query')")
    @GetMapping("/lastPointNum")
    public AjaxResult lastPointNum()
    {
        return AjaxResult.success(tLeakagePointsService.selectMaxPointNo());
    }



    /**
     * 导出漏点清单列表
     */
    @PreAuthorize("@ss.hasPermi('asset:points:export')")
    @Log(title = "漏点清单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TLeakagePoints tLeakagePoints)
    {
        List<TLeakagePoints> list = tLeakagePointsService.selectTLeakagePointsList(tLeakagePoints);
        ExcelUtil<TLeakagePoints> util = new ExcelUtil<TLeakagePoints>(TLeakagePoints.class);
        util.exportExcel(response, list, "漏点清单数据");
    }

    /**
     * 获取漏点清单详细信息
     */
    @PreAuthorize("@ss.hasPermi('asset:points:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tLeakagePointsService.selectTLeakagePointsById(id));
    }

    /**
     * 新增漏点清单
     */
    @PreAuthorize("@ss.hasPermi('asset:points:add')")
    @Log(title = "漏点清单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TLeakagePoints tLeakagePoints)
    {
        return toAjax(tLeakagePointsService.insertTLeakagePoints(tLeakagePoints));
    }

    /**
     * 修改漏点清单
     */
    @PreAuthorize("@ss.hasPermi('asset:points:edit')")
    @Log(title = "漏点清单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TLeakagePoints tLeakagePoints)
    {
        return toAjax(tLeakagePointsService.updateTLeakagePoints(tLeakagePoints));
    }

    /**
     * 删除漏点清单
     */
    @PreAuthorize("@ss.hasPermi('asset:points:remove')")
    @Log(title = "漏点清单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tLeakagePointsService.deleteTLeakagePointsByIds(ids));
    }


    @Log(title = "漏点清单批量导入", businessType = BusinessType.INSERT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) throws IOException {
        //获取操作人员ID
        Long userId = getUserId();

        List<SysDictData> dictData = sysDictTypeService.selectDictDataByType("leakage_result");
        logger.info(JSON.toJSONString(dictData));
        //报错行数统计
        List<Integer> failRow = new ArrayList<>();
        Workbook workbook = ExcelUtils.getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<TLeakagePoints> list = new ArrayList<>();
        int rowNum = sheet.getPhysicalNumberOfRows();
        int failNumber = 0;
        for (int i = 2; i < rowNum; i++) {
            try {
                logger.info("读取行数：" + i);
                Row row = sheet.getRow(i);
                int cellNum = row.getLastCellNum();
                TLeakagePoints entity = new TLeakagePoints();
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    String cellValue = ExcelUtils.getCellValue(cell);
                    logger.info("cellValue:" + cellValue);
                    if (j == 0) {
                        entity.setPointNo(cellValue);
                    } else if (j == 1) {
                        entity.setLeakagePosition(cellValue);
                    } else if (j == 2) {
                        entity.setLeakageMedium(cellValue);
                    } else if (j == 3) {
                        entity.setDiscoveryTime(DateUtils.parseDate(cellValue));
                    } else if (j == 4) {
                        entity.setDiscoveryName(cellValue);
                    } else if (j == 5) {
                        entity.setLeakageAmount(cellValue);
                    } else if (j == 6) {
                        entity.setSapNo(cellValue);
                    } else if (j == 7) {
                        entity.setLeakageEliminationTime(DateUtils.parseDate(cellValue));
                    } else if (j == 8) {
                        entity.setLeakageEliminationFunc(cellValue);
                    } else if (j == 9) {
                        entity.setListing(cellValue);
                    } else if (j == 10) {
                        entity.setPressurePlugging(cellValue);
                    } else if (j == 11) {
                        for (SysDictData data : dictData) {
                            if (data.getDictLabel().equals(cellValue)){
                                entity.setLeakageEliminationResult(data.getDictValue());
                            }
                        }
                    } else if (j == 12) {
                        entity.setNextCheckDate(DateUtils.parseDate(cellValue));
                    } else if (j == 13) {
                        entity.setRemarks(cellValue);
                    }
                }
                entity.setCreaterCode(String.valueOf(userId));
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
        for (TLeakagePoints t : list
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
        logger.info("successNumber:" + successNumber);
        logger.info("failNumber:" + failNumber);
        logger.info("failRow:" + failRow);
        return AjaxResult.success(String.valueOf(successNumber), failRow);
    }
}
