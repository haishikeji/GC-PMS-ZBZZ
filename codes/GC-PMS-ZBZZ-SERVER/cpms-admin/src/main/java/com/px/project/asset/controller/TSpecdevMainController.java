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
import com.px.project.asset.domain.TSpecdevMain;
import com.px.project.file.domain.TFile;
import com.px.project.file.service.ITFileService;
import com.px.project.sems.service.ITSpecdevAqfService;
import com.px.project.asset.service.ITSpecdevFixedAssetService;
import com.px.project.asset.service.ITSpecdevMainService;
import com.px.system.service.ISysDictTypeService;
import org.apache.commons.collections4.CollectionUtils;
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
 * 设备总Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/sems/main")
public class TSpecdevMainController extends BaseController {
    @Autowired
    private ITSpecdevMainService tSpecdevMainService;

    @Autowired
    private ITSpecdevAqfService tSpecdevAqfService;

    @Autowired
    private ISysDictTypeService iSysDictTypeService;

    @Autowired
    private ITSpecdevFixedAssetService tSpecdevFixedAssetService;

    @Autowired
    private ITFileService tFileService;

    /**
     * 查询设备总列表
     */
    @PreAuthorize("@ss.hasPermi('sems:main:list')")
    @GetMapping("/list")
    public TableDataInfo list(TSpecdevMain tSpecdevMain) {
        startPage();
        List<TSpecdevMain> list = tSpecdevMainService.selectTSpecdevMainList(tSpecdevMain);
        for (TSpecdevMain specdevMain : list) {
            TFile tFile = new TFile();
            tFile.setLinkId(specdevMain.getId());
            tFile.setLinkName("specMain");
            List<TFile> tFiles = tFileService.selectTFileList(tFile);
            if (CollectionUtils.isNotEmpty(tFiles)){
                specdevMain.setCountNum(String.valueOf(tFiles.size()));
            }else{
                specdevMain.setCountNum("0");
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出设备总列表
     */
    @PreAuthorize("@ss.hasPermi('sems:main:export')")
    @Log(title = "设备总表格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response,TSpecdevMain tSpecdevMain) {
        List<TSpecdevMain> list = tSpecdevMainService.selectTSpecdevMainList(tSpecdevMain);
        List<SysDictData> devType = iSysDictTypeService.selectDictDataByType("dev_type");
        List<SysDictData> assetStatus = iSysDictTypeService.selectDictDataByType("spec_dev_status");
        int index = 1;
        for (TSpecdevMain specdevMain : list) {
            specdevMain.setIndex(index++);
        }
        ExcelUtil<TSpecdevMain> util = new ExcelUtil<TSpecdevMain>(TSpecdevMain.class);
        util.exportExcel(response,list, "main");
    }

    /**
     * 获取设备总详细信息
     */
    @PreAuthorize("@ss.hasPermi('sems:main:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tSpecdevMainService.selectTSpecdevMainById(id));
    }

    /**
     * 新增设备总
     */
    @PreAuthorize("@ss.hasPermi('sems:main:add')")
    @Log(title = "设备总表格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSpecdevMain tSpecdevMain) {
        tSpecdevMainService.insertTSpecdevMain(tSpecdevMain);
        /*switch (tSpecdevMain.getDevType2()) {
                case "安全阀":
                    TSpecdevAqf tSpecdevAqf = new TSpecdevAqf(tSpecdevMain);
                    tSpecdevAqfService.insertTSpecdevAqf(tSpecdevAqf);
                    break;
            }*/
        return AjaxResult.success();
    }

    /**
     * 修改设备总
     */
    @PreAuthorize("@ss.hasPermi('sems:main:edit')")
    @Log(title = "设备总表格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSpecdevMain tSpecdevMain) {
        tSpecdevMainService.updateTSpecdevMain(tSpecdevMain);//            switch (tSpecdevMain.getDevType2()) {
//                case "安全阀":
//                    TSpecdevAqf tSpecdevAqf = new TSpecdevAqf();
//                    tSpecdevAqf.setMainId(tSpecdevMain.getId());
//                    if (CollectionUtils.isNotEmpty(tSpecdevAqfService.selectTSpecdevAqfList(tSpecdevAqf))) {
//                        tSpecdevAqf = new TSpecdevAqf(tSpecdevMain);
//                        tSpecdevAqfService.updateTSpecdevAqf(tSpecdevAqf);
//                    } else {
//                        tSpecdevAqf = new TSpecdevAqf(tSpecdevMain);
//                        tSpecdevAqfService.insertTSpecdevAqf(tSpecdevAqf);
//                    }
//                    break;
//            }
        return AjaxResult.success();
    }

    /**
     * 删除设备总
     */
    @PreAuthorize("@ss.hasPermi('sems:main:remove')")
    @Log(title = "设备总表格", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            TSpecdevMain tSpecdevMain = tSpecdevMainService.selectTSpecdevMainById(id);
            tSpecdevMainService.deleteTSpecdevMainById(id);
            //                switch (tSpecdevMain.getDevType2()) {
//                    case "安全阀":
//                        tSpecdevAqfService.deleteTSpecdevAqfByMainId(id);
//                        break;
//                }
        }
        return AjaxResult.success();
    }

    @Log(title = "设备总表格批量导入更新", businessType = BusinessType.INSERT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) throws IOException {
        //获取操作人员ID
        Long userId = getUserId();
        //报错行数统计
        List<Integer> failRow = new ArrayList<>();
        Workbook workbook = ExcelUtils.getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<TSpecdevMain> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //字典查询
        List<SysDictData> devType = iSysDictTypeService.selectDictDataByType("dev_type");
        List<SysDictData> assetStatus = iSysDictTypeService.selectDictDataByType("spec_dev_status");
        int rowNum = sheet.getPhysicalNumberOfRows();
        int failNumber = 0;
        for (int i = 22; i < rowNum; i++) {
            try {
                logger.info("读取行数：" + i);
                Row row = sheet.getRow(i);
                int cellNum = row.getLastCellNum();
                TSpecdevMain entity = new TSpecdevMain();
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    String cellValue = ExcelUtils.getCellValue(cell);
                    logger.info("cellValue:" + cellValue);
                    if (j == 0) {
                        //中文名
                        entity.setDevName(cellValue);
                    } else if (j == 1) {
                        //英文名
                        entity.setDevEnname(cellValue);
                    } else if (j == 2) {
                        //位号
                        entity.setDevno(cellValue);
                    } else if (j == 3) {
                        //是否在pid上
                        entity.setIsPid(cellValue.equals("是") ? "1" : "0");
                    } else if (j == 4) {
                        //PID图号/其他图号
                        entity.setPidNo(cellValue);
                    } else if (j == 5) {
                        //安装位置
                        entity.setInstallPosition(cellValue);
                    } else if (j == 6) {
                        //功能位置
                        entity.setFuncPosition(cellValue);
                    } else if (j == 7) {
                        //资产号
                        entity.setAssetNo(cellValue);
                    } else if (j == 8) {
                        //安全相关
                        entity.setSecurityRelevant(cellValue);
                    } else if (j == 9) {
                        //安全相关来自
                        entity.setSecurityRelevantFrom(cellValue);
                    } else if (j == 10) {
                        //生产厂家
                        entity.setManufacturer(cellValue);
                    } else if (j == 11) {
                        //生产年份
                        entity.setProductionYear(cellValue);
                    } else if (j == 12) {
                        //型号
                        entity.setModel(cellValue);
                    } else if (j == 13) {
                        //主要规格参数
                        entity.setMainSpecifications(cellValue);
                    } else if (j == 14) {
                        //设备铭牌
                        entity.setDataPlate(cellValue);
                    } else if (j == 15) {
                        //维修风险
                        entity.setMaintenanceRisk(cellValue);
                    } else if (j == 16) {
                        //生产风险
                        entity.setProductionRisk(cellValue);
                    } else if (j == 17) {
                        //设备分级
                        entity.setDevGrade(cellValue);
                    } else if (j == 18) {
                        //设备来自
                        entity.setDevFrom(cellValue);
                    } else if (j == 19) {
                        //设备类型 （一级）
//                        for (SysDictData dictData : devType) {
//                            if (dictData.getDictLabel().equals(cellValue.trim())) {
//                                entity.setDevType1(dictData.getDictValue());
//                            }
//                        }
                        entity.setDevType1(cellValue);
                    } else if (j == 20) {
//                        //设备类型 （二级）
//                        List<SysDictData> devType2 = new ArrayList<>();
//                        switch (entity.getDevType1()) {
//                            case "1"://固定资产
//                            case "5"://电气
//                                break;
//                            case "2"://静设备
//                                devType2 = iSysDictTypeService.selectDictDataByType("dev_static");
//                                break;
//                            case "4"://仪表
//                                devType2 = iSysDictTypeService.selectDictDataByType("dev_meter");
//                                break;
//                            case "6"://动设备
//                                devType2 = iSysDictTypeService.selectDictDataByType("dev_act");
//                                break;
//                            case "3"://管线
//                                devType2 = iSysDictTypeService.selectDictDataByType("dev_tunnel");
//                                break;
//                        }
//                        for (SysDictData dictData : devType2) {
//                            if (dictData.getDictLabel().equals(cellValue.trim())) {
//                                entity.setDevType2(dictData.getDictValue());
//                            }
//                        }
                        entity.setDevType2(cellValue);
                    } else if (j == 21) {
                        //资产状态
                        for (SysDictData dictData : assetStatus) {
                            if (dictData.getDictLabel().equals(cellValue.trim())) {
                                entity.setAssetStatus(dictData.getDictValue());
                            }
                        }
                    } else if (j == 22) {
                        //备注
                        entity.setRemarks(cellValue);
                    } else if (j == 23) {
                        //
                        entity.setMainSpecifications(entity.getMainSpecifications() + " " + cellValue);
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
        for (TSpecdevMain t : list
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

    @PostMapping("/syncAllDev")
    public AjaxResult syncAllDev() {
        for (TSpecdevMain tSpecdevMain : tSpecdevMainService.selectTSpecdevMainList(new TSpecdevMain())) {
//            if (StringUtils.isNotEmpty(tSpecdevMain.getDevType2())) {
//                switch (tSpecdevMain.getDevType2()) {
//                    case "安全阀":
//                        TSpecdevAqf tSpecdevAqf = new TSpecdevAqf();
//                        tSpecdevAqf.setMainId(tSpecdevMain.getId());
//                        if (CollectionUtils.isEmpty(tSpecdevAqfService.selectTSpecdevAqfList(tSpecdevAqf))) {
//                            tSpecdevAqf = new TSpecdevAqf(tSpecdevMain);
//                            tSpecdevAqfService.insertTSpecdevAqf(tSpecdevAqf);
//                        }
//                        break;
//                    default:
//                        break;
//                }
//            }
        }
        return AjaxResult.success();
    }
}
