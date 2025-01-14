package com.px.project.sems.controller;

import com.alibaba.fastjson2.JSON;
import com.px.common.annotation.Log;
import com.px.common.core.controller.BaseController;
import com.px.common.core.domain.AjaxResult;
import com.px.common.core.domain.entity.SysDept;
import com.px.common.core.domain.entity.SysDictData;
import com.px.common.core.page.TableDataInfo;
import com.px.common.enums.BusinessType;
import com.px.common.utils.DateUtils;
import com.px.common.utils.file.ExcelUtils;
import com.px.common.utils.poi.ExcelUtil;
import com.px.project.sems.domain.TSpecdevDzsb;
import com.px.project.sems.service.ITSpecdevDzsbService;
import com.px.system.service.ISysDeptService;
import com.px.system.service.ISysDictTypeService;
import org.apache.poi.ss.usermodel.*;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 特种设备吊装设备台账Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/sems/specDzsb")
public class TSpecdevDzsbController extends BaseController {
    @Autowired
    private ITSpecdevDzsbService tSpecdevDzsbService;
    @Autowired
    private ISysDeptService iSysDeptService;
    @Autowired
    private ISysDictTypeService iSysDictTypeService;

    /**
     * 查询特种设备吊装设备台账列表
     */
    @PreAuthorize("@ss.hasPermi('sems:specDzsb:list')")
    @GetMapping("/list")
    public TableDataInfo list(TSpecdevDzsb tSpecdevDzsb) {
        startPage();
        List<TSpecdevDzsb> list = tSpecdevDzsbService.selectTSpecdevDzsbList(tSpecdevDzsb);
        for (TSpecdevDzsb dev : list) {
            if (dev.getNextWarnDate() != null) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date d1 = dev.getNextWarnDate();
                    Date d2 = format.parse(DateUtils.getDate());
                    DateTime dt1 = new DateTime(d1);
                    DateTime dt2 = new DateTime(d2);
                    int days = Days.daysBetween(dt2, dt1).getDays();
                    if (days > 60) {
                        dev.setCheckStatus("正常");
                    } else {
                        dev.setCheckStatus("待报检");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出特种设备吊装设备台账列表
     */
    @PreAuthorize("@ss.hasPermi('sems:specDzsb:export')")
    @Log(title = "特种设备吊装设备台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSpecdevDzsb tSpecdevDzsb) {
        List<TSpecdevDzsb> list = tSpecdevDzsbService.selectTSpecdevDzsbList(tSpecdevDzsb);
        for (TSpecdevDzsb dev : list) {
            if (dev.getNextWarnDate() != null) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date d1 = dev.getNextWarnDate();
                    Date d2 = format.parse(DateUtils.getDate());
                    DateTime dt1 = new DateTime(d1);
                    DateTime dt2 = new DateTime(d2);
                    int days = Days.daysBetween(dt2, dt1).getDays();
                    if (days > 60) {
                        dev.setCheckStatus("正常");
                    } else {
                        dev.setCheckStatus("待报检");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ExcelUtil<TSpecdevDzsb> util = new ExcelUtil<TSpecdevDzsb>(TSpecdevDzsb.class);
        util.exportExcel(response, list, "specDzsb");
    }

    /**
     * 获取特种设备吊装设备台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('sems:specDzsb:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tSpecdevDzsbService.selectTSpecdevDzsbById(id));
    }

    /**
     * 新增特种设备吊装设备台账
     */
    @PreAuthorize("@ss.hasPermi('sems:specDzsb:add')")
    @Log(title = "特种设备吊装设备台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSpecdevDzsb tSpecdevDzsb) {

        return toAjax(tSpecdevDzsbService.insertTSpecdevDzsb(tSpecdevDzsb));
    }

    /**
     * 修改特种设备吊装设备台账
     */
    @PreAuthorize("@ss.hasPermi('sems:specDzsb:edit')")
    @Log(title = "特种设备吊装设备台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSpecdevDzsb tSpecdevDzsb) {
        /*TSpecdevDzsb old = tSpecdevDzsbService.selectTSpecdevDzsbById(tSpecdevDzsb.getId());
        if (!old.getReportNo().equals(tSpecdevDzsb.getReportNo())) {
            TSpecCheck tc = new TSpecCheck();
            tc.setDevType(3l);
            tc.setCheckUnit(tSpecdevDzsb.getCheckUnit());
            tc.setDevId(tSpecdevDzsb.getId());
            tc.setNextWarnDate(tSpecdevDzsb.getNextWarnDate());
            tc.setReportNo(tSpecdevDzsb.getReportNo());
            tc.setWarnDate(tSpecdevDzsb.getWarnDate());
            tSpecCheckService.insertTSpecCheck(tc);
        }*/
        return toAjax(tSpecdevDzsbService.updateTSpecdevDzsb(tSpecdevDzsb));
    }

    /**
     * 删除特种设备吊装设备台账
     */
    @PreAuthorize("@ss.hasPermi('sems:specDzsb:remove')")
    @Log(title = "特种设备吊装设备台账", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tSpecdevDzsbService.deleteTSpecdevDzsbByIds(ids));
    }

    /**
     * 去重
     */
    @PreAuthorize("@ss.hasPermi('sems:plant:remove')")
    @GetMapping("/duplicate")
    public AjaxResult duplicate() {
        tSpecdevDzsbService.duplicateTSpecdevDzsb();
        return AjaxResult.success();
    }

    /**
     * 批量导入
     */
    @PreAuthorize("@ss.hasPermi('sems:specDzsb:add')")
    @Log(title = "特种设备批量导入", businessType = BusinessType.INSERT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) throws IOException {
        //获取操作人员ID
        Long userId = getUserId();
        //报错行数统计
        List<Integer> failRow = new ArrayList<Integer>();
        Workbook workbook = ExcelUtils.getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<TSpecdevDzsb> list = new ArrayList<TSpecdevDzsb>();
        //字典查询
        List<SysDictData> status = iSysDictTypeService.selectDictDataByType("spec_dev_status");

        //部门查询
        List<SysDept> dept = iSysDeptService.selectDeptList(new SysDept());
        int rowNum = sheet.getPhysicalNumberOfRows();
        logger.info("rowNum:" + rowNum);
        int failNumber = 0;
        for (int i = 1; i < rowNum; i++) {
            try {
                logger.info("读取行数：" + i);
                Row row = sheet.getRow(i);
                int cellNum = row.getLastCellNum();
                logger.info("cellNum:" + cellNum);
                TSpecdevDzsb entity = new TSpecdevDzsb();
                for (int j = 0; j <= cellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    String cellValue = ExcelUtils.getCellValue(cell);
                    logger.info("cellValue:" + cellValue);
                    if (j == 0) {
                        //序号
                        entity.setDevType(cellValue);
                    } else if (j == 1) {
                        entity.setPlantCode(cellValue);//装置
                    } else if (j == 2) {
                        entity.setDevType2(cellValue);//装置维修组
                    } else if (j == 3) {
                        for (SysDictData data : status) {
                            if (data.getDictLabel().equals(cellValue)) {
                                entity.setStatus(Long.valueOf(data.getDictValue()));//状态
                                break;
                            }
                        }
                    } else if (j == 4) {
                        entity.setModel(cellValue);//装置维修工程师
                    } else if (j == 5) {
                        entity.setNextWarnDate(DateUtils.parseDate(cellValue));//设备名称
                    } else if (j == 6) {
                        entity.setFactoryInfo(cellValue);//设备位号
                    } else if (j == 7) {
                        entity.setPlateNo(cellValue);//档案号
                    } else if (j == 8) {
                        entity.setRegno(cellValue);//出厂编号
                    } else if (j == 9) {
                        entity.setEngineNo(cellValue);//设备型号
                    } else if (j == 10) {
                        entity.setExLevel(cellValue);//起吊重量
                    } else if (j == 11) {
                        entity.setCreateUnit(cellValue);//设备使用地点
                    } else if (j == 12) {
                        entity.setRemarks(cellValue);//设备注册编号
                    }

                }
                entity.setCreaterCode(userId);
                logger.info("entity:" + JSON.toJSONString(entity));
                list.add(entity);
            } catch (Exception e) {
                failNumber++;
                logger.error("e:" + JSON.toJSONString(e));
                failRow.add(i + 1);
            }
        }
        int successNumber = 0;
        int failNum = 0;
        for (TSpecdevDzsb t : list
        ) {
            failNum++;
            try {
                tSpecdevDzsbService.insertTSpecdevDzsb(t);
                successNumber++;
            } catch (Exception e) {
                failNumber++;
                logger.error("e:" + e);
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
