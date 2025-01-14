package com.px.project.asset.controller;

 import com.alibaba.fastjson2.JSON;
 import com.px.common.annotation.Log;
 import com.px.common.config.RuoYiConfig;
 import com.px.common.core.controller.BaseController;
 import com.px.common.core.domain.AjaxResult;
 import com.px.common.core.page.TableDataInfo;
 import com.px.common.enums.BusinessType;
 import com.px.common.utils.StringUtils;
import com.px.common.utils.file.ExcelUtils;
import com.px.common.utils.file.FileUploadUtils;
 import com.px.project.asset.domain.TSpecdevAsset;
import com.px.project.asset.service.ITSpecdevAssetService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

 import javax.servlet.http.HttpServletResponse;
 import java.io.IOException;
 import java.util.ArrayList;
import java.util.List;

/**
 * 资产与运营Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/sems/asset")
public class TSpecdevAssetController extends BaseController {
    @Autowired
    private ITSpecdevAssetService tSpecdevAssetService;

    /**
     * 查询资产与运营列表
     */
    @PreAuthorize("@ss.hasPermi('sems:asset:list')")
    @GetMapping("/list")
    public TableDataInfo list(TSpecdevAsset tSpecdevAsset) {
        startPage();
        List<TSpecdevAsset> list = tSpecdevAssetService.selectTSpecdevAssetList(tSpecdevAsset);
        return getDataTable(list);
    }

    /**
     * 导出资产与运营列表
     */
    @PreAuthorize("@ss.hasPermi('sems:asset:export')")
    @Log(title = "资产与运营", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSpecdevAsset tSpecdevAsset) throws IOException {
        List<TSpecdevAsset> list = tSpecdevAssetService.selectTSpecdevAssetList(tSpecdevAsset);
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);
        // 创建标题样式
        CellStyle cellStyle = wb.createCellStyle();
        // 内容垂直居中，四周加边框线，设置单元格前景色为灰色
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        // 设置字体，加粗，字体颜色
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        cellStyle.setFont(headerFont);

        // 正文格式
        CellStyle style2 = wb.createCellStyle();
        // 内容垂直居中，四周加边框线，自动换行
        style2.setAlignment(HorizontalAlignment.CENTER);
        style2.setVerticalAlignment(VerticalAlignment.CENTER);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setBorderTop(BorderStyle.THIN);
        style2.setWrapText(true);
        // 设置正文字体，字体颜色
        Font headerFont2 = wb.createFont();
        headerFont2.setFontName("Arial");
        headerFont2.setFontHeightInPoints((short) 10);
        headerFont2.setColor(IndexedColors.BLACK.getIndex());
        style2.setFont(headerFont2);
        //创建sheet页
        Sheet sheet = wb.createSheet("sheet1");
        //设置列的宽度，第一个参数为列的序号，从0开始，第二参数为列宽，单位1/256个字节
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(20, 20 * 256);
        // 创建表格第一行并设置相应标题
        Row row0 = sheet.createRow(0);
        row0.createCell(0).setCellValue("Type");
        row0.createCell(1).setCellValue("内容");
        row0.createCell(2).setCellValue("PID图号/管线号");
        row0.createCell(3).setCellValue("分界具体位置及描述");
        row0.createCell(4).setCellValue("According to");
        row0.createCell(5).setCellValue("资产");
        row0.createCell(6).setCellValue("DCS操作");
        row0.createCell(7).setCellValue("现场操作");
        row0.createCell(9).setCellValue("巡检");
        row0.createCell(11).setCellValue("维修");
        row0.createCell(13).setCellValue("维修协调");
        row0.createCell(15).setCellValue("技术管理");
        row0.createCell(17).setCellValue("资产分界点信息");
        row0.createCell(20).setCellValue("备注");
        row0.createCell(8);
        row0.createCell(10);
        row0.createCell(12);
        row0.createCell(14);
        row0.createCell(16);
        row0.createCell(18);
        row0.createCell(19);
        // 设置单元格样式
        setStyle(row0, cellStyle);

        // 创建第二行标题
        Row row1 = sheet.createRow(1);
        row1.createCell(0);
        row1.createCell(1);
        row1.createCell(2);
        row1.createCell(3);
        row1.createCell(4);
        row1.createCell(5).setCellValue("N.A");
        row1.createCell(6).setCellValue("N.A");
        row1.createCell(7).setCellValue("8小时");
        row1.createCell(8).setCellValue("8小时外");
        row1.createCell(9).setCellValue("8小时");
        row1.createCell(10).setCellValue("8小时外");
        row1.createCell(11).setCellValue("8小时");
        row1.createCell(12).setCellValue("8小时外");
        row1.createCell(13).setCellValue("8小时");
        row1.createCell(14).setCellValue("8小时外");
        row1.createCell(15).setCellValue("8小时");
        row1.createCell(16).setCellValue("8小时外");
        row1.createCell(17).setCellValue("位置");
        row1.createCell(18).setCellValue("管径");
        row1.createCell(19).setCellValue("照片");
        row1.createCell(20);
        // 设置单元格样式
        setStyle(row1, cellStyle);
        // 合并单元格  参数（1：起始行，2：结束行，3：起始列，4：结束列）
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 3));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 4, 4));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 8));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 9, 10));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 11, 12));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 13, 14));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 15, 16));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 17, 19));
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 20, 20));

        //填充数据
        int rowIndex = 2;
        int columnIndex = 1;
        for (TSpecdevAsset t : list
        ) {
            Row row = sheet.createRow(rowIndex);// 创建行
            //填充数据
            row.createCell(0).setCellValue(t.getType());
            row.createCell(1).setCellValue(t.getContent());
            row.createCell(2).setCellValue(t.getPidNo());
            row.createCell(3).setCellValue(t.getPositionDescribe());
            row.createCell(4).setCellValue(t.getAccordingTo());
            row.createCell(5).setCellValue(t.getAsset());
            row.createCell(6).setCellValue(t.getDcsOperation());
            row.createCell(7).setCellValue(t.getInSceneOperation());
            row.createCell(8).setCellValue(t.getOutSceneOperation());
            row.createCell(9).setCellValue(t.getInInspection());
            row.createCell(10).setCellValue(t.getOutInspection());
            row.createCell(11).setCellValue(t.getInRepair());
            row.createCell(12).setCellValue(t.getOutRepair());
            row.createCell(13).setCellValue(t.getInMaintenanceCoordination());
            row.createCell(14).setCellValue(t.getOutMaintenanceCoordination());
            row.createCell(15).setCellValue(t.getInTechnicalManagement());
            row.createCell(16).setCellValue(t.getOutTechnicalManagement());
            row.createCell(17).setCellValue(t.getPosition());
            row.createCell(18).setCellValue(t.getPipeDiameter());
            row.createCell(19).setCellValue(StringUtils.isNotEmpty(t.getPicUrl())?"Y":"N.A");
            row.createCell(20).setCellValue(t.getRemarks());
            setStyle(row, style2);
            rowIndex++;
        }

        // 生成文件返回下载地址
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        wb.write(response.getOutputStream());
        wb.close();
    }

    /**
     * 设置单元格格式
     *
     * @param row   行
     * @param style 单元格样式
     */
    private void setStyle(Row row, CellStyle style){
        for (int i = 0; i <= 20; i++) {
            row.getCell(i).setCellStyle(style);
        }
    }

    /**
     * 获取资产与运营详细信息
     */
    @PreAuthorize("@ss.hasPermi('sems:asset:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tSpecdevAssetService.selectTSpecdevAssetById(id));
    }

    /**
     * 新增资产与运营
     */
    @PreAuthorize("@ss.hasPermi('sems:asset:add')")
    @Log(title = "资产与运营", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSpecdevAsset tSpecdevAsset) {
        return toAjax(tSpecdevAssetService.insertTSpecdevAsset(tSpecdevAsset));
    }

    /**
     * 修改资产与运营
     */
    @PreAuthorize("@ss.hasPermi('sems:asset:edit')")
    @Log(title = "资产与运营", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSpecdevAsset tSpecdevAsset) {
        return toAjax(tSpecdevAssetService.updateTSpecdevAsset(tSpecdevAsset));
    }

    /**
     * 删除资产与运营
     */
    @PreAuthorize("@ss.hasPermi('sems:asset:remove')")
    @Log(title = "资产与运营", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tSpecdevAssetService.deleteTSpecdevAssetByIds(ids));
    }

    @Log(title = "文件上传", businessType = BusinessType.UPDATE)
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String avatar = FileUploadUtils.upload(RuoYiConfig.getFilePath("/specAsset"), file);
            return AjaxResult.success(avatar);
        }
        return AjaxResult.error("上传异常，请联系管理员");
    }

    @Log(title = "资产与运营批量导入更新", businessType = BusinessType.INSERT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) throws IOException {
        //获取操作人员ID
        Long userId = getUserId();
        //报错行数统计
        List<Integer> failRow = new ArrayList<>();
        Workbook workbook = ExcelUtils.getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<TSpecdevAsset> list = new ArrayList<>();
        int rowNum = sheet.getPhysicalNumberOfRows();
        int failNumber = 0;
        for (int i = 3; i < rowNum; i++) {
            try {
                logger.info("读取行数：" + i);
                Row row = sheet.getRow(i);
                int cellNum = row.getLastCellNum();
                TSpecdevAsset entity = new TSpecdevAsset();
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    String cellValue = ExcelUtils.getCellValue(cell);
                    logger.info("cellValue:" + cellValue);
                    if (j == 0) {
                        //type
                        entity.setType(cellValue);
                    } else if (j == 1) {
                        //内容
                        entity.setContent(cellValue);
                    } else if (j == 2) {
                        //PID图号/管线号
                        entity.setPidNo(cellValue);
                    } else if (j == 3) {
                        //是分解具体位置及描述
                        entity.setPositionDescribe(cellValue);
                    } else if (j == 4) {
                        //According to
                        entity.setAccordingTo(cellValue);
                    } else if (j == 5) {
                        //资产
                        entity.setAsset(cellValue);
                    } else if (j == 6) {
                        //DCS操作
                        entity.setDcsOperation(cellValue);
                    } else if (j == 7) {
                        //现场操作8小时
                        entity.setInSceneOperation(cellValue);
                    } else if (j == 8) {
                        //现场操作8小时外
                        entity.setOutSceneOperation(cellValue);
                    } else if (j == 9) {
                        //巡检8小时
                        entity.setInInspection(cellValue);
                    } else if (j == 10) {
                        //巡检8小时外
                        entity.setOutInspection(cellValue);
                    } else if (j == 11) {
                        //维修8小时
                        entity.setInRepair(cellValue);
                    } else if (j == 12) {
                        //维修8小时外
                        entity.setOutRepair(cellValue);
                    } else if (j == 13) {
                        //维修协调8小时
                        entity.setInMaintenanceCoordination(cellValue);
                    } else if (j == 14) {
                        //维修协调8小时外
                        entity.setOutMaintenanceCoordination(cellValue);
                    } else if (j == 15) {
                        //技术管理8小时
                        entity.setInTechnicalManagement(cellValue);
                    } else if (j == 16) {
                        //技术管理8小时外
                        entity.setOutTechnicalManagement(cellValue);
                    } else if (j == 17) {
                        //位置
                        entity.setPosition(cellValue);
                    } else if (j == 18) {
                        //管径
                        entity.setPipeDiameter(cellValue);
                    } else if (j == 19) {
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
        for (TSpecdevAsset t : list
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
