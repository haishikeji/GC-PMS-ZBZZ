package com.px.project.process.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;
import com.alibaba.fastjson2.JSON;
import com.px.common.utils.file.ExcelUtils;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.px.common.annotation.Log;
import com.px.common.core.controller.BaseController;
import com.px.common.core.domain.AjaxResult;
import com.px.common.enums.BusinessType;
import com.px.project.process.domain.TValveNonc;
import com.px.project.process.service.ITValveNoncService;
import com.px.common.utils.poi.ExcelUtil;
import com.px.common.core.page.TableDataInfo;

/**
 * 阀门常开/常关状态记录Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/process/nonc")
public class TValveNoncController extends BaseController
{
    @Autowired
    private ITValveNoncService tValveNoncService;

    /**
     * 查询阀门常开/常关状态记录列表
     */
    @PreAuthorize("@ss.hasPermi('process:nonc:list')")
    @GetMapping("/list")
    public TableDataInfo list(TValveNonc tValveNonc)
    {
        startPage();
        List<TValveNonc> list = tValveNoncService.selectTValveNoncList(tValveNonc);
        return getDataTable(list);
    }

    /**
     * 导出阀门常开/常关状态记录列表
     */
    @PreAuthorize("@ss.hasPermi('process:nonc:export')")
    @Log(title = "阀门常开/常关状态记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TValveNonc tValveNonc)
    {
        List<TValveNonc> list = tValveNoncService.selectTValveNoncList(tValveNonc);
        ExcelUtil<TValveNonc> util = new ExcelUtil<TValveNonc>(TValveNonc.class);
        util.exportExcel(response, list, "阀门常开常关状态记录数据");
    }

    /**
     * 导出阀门常开/常关状态记录列表
     */
    @PreAuthorize("@ss.hasPermi('process:nonc:export')")
    @Log(title = "阀门常开/常关状态记录", businessType = BusinessType.EXPORT)
    @PostMapping("/exportTmpl")
    public void exportTmpl(HttpServletResponse response, TValveNonc tValveNonc)
    {
        List<TValveNonc> list = tValveNoncService.selectTValveNoncList(tValveNonc);
        try {
            String tempUrl = "static/template/process/noncExport.xlsx"; // 模板文件
            InputStream is = null;
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(tempUrl);
            XSSFWorkbook wb1 = null;
            wb1 = new XSSFWorkbook(is);
            SXSSFWorkbook wb = new SXSSFWorkbook(wb1, 1000);

            Sheet sheet;
            if (wb instanceof SXSSFWorkbook) {
                SXSSFWorkbook sxssfWorkbook = (SXSSFWorkbook) wb;
                sheet = sxssfWorkbook.getXSSFWorkbook().getSheetAt(0);
            } else {
                sheet = wb.getSheetAt(0);
            }

            //填充数据
            int rowIndex = 4;
            int num = 1;

            Row originalRow = sheet.getRow(4);
            Cell originalcell = originalRow.getCell(0);
            // 获取单元格样式
            CellStyle originalStyle = originalcell.getCellStyle();

            for (TValveNonc t: list
            ) {
                Row row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue(num);
                row.createCell(1).setCellValue(t.getVtNo());
                row.createCell(2).setCellValue(t.getPidNo());
                row.createCell(3).setCellValue(t.getLocationAt());
                row.createCell(4).setCellValue(t.getMedium());
                row.createCell(5).setCellValue(t.getValveType());
                row.createCell(6).setCellValue(t.getValveSize());
                row.createCell(7).setCellValue(t.getResponsibility());
                row.createCell(8).setCellValue(t.getPidStatus());
                row.createCell(9).setCellValue(t.getRiskLevel());
                row.createCell(10).setCellValue(t.getRemarks());


                //渲染样式
                for (int i = 0; i < 11; i++) {
                    row.getCell(i).setCellStyle(originalStyle);
                }
                num++;
                rowIndex++;
            }

            // 生成文件返回下载地址
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            wb.write(response.getOutputStream());
            wb.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取阀门常开/常关状态记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('process:nonc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(tValveNoncService.selectTValveNoncById(id));
    }

    /**
     * 新增阀门常开/常关状态记录
     */
    @PreAuthorize("@ss.hasPermi('process:nonc:add')")
    @Log(title = "阀门常开/常关状态记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TValveNonc tValveNonc)
    {
        return toAjax(tValveNoncService.insertTValveNonc(tValveNonc));
    }

    /**
     * 修改阀门常开/常关状态记录
     */
    @PreAuthorize("@ss.hasPermi('process:nonc:edit')")
    @Log(title = "阀门常开/常关状态记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TValveNonc tValveNonc)
    {
        return toAjax(tValveNoncService.updateTValveNonc(tValveNonc));
    }

    /**
     * 删除阀门常开/常关状态记录
     */
    @PreAuthorize("@ss.hasPermi('process:nonc:remove')")
    @Log(title = "阀门常开/常关状态记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tValveNoncService.deleteTValveNoncByIds(ids));
    }


    @Log(title = "阀门常开/常关状态记录批量导入", businessType = BusinessType.INSERT)
    @PostMapping("/importData")
    public AjaxResult importData(@RequestParam("file") MultipartFile file) throws IOException {
        //获取操作人员ID
        Long userId = getUserId();
        //报错行数统计
        List<Integer> failRow = new ArrayList<>();
        Workbook workbook = ExcelUtils.getWorkBook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<TValveNonc> list = new ArrayList<>();
        int rowNum = sheet.getPhysicalNumberOfRows();
        int failNumber = 0;
        for (int i = 2; i < rowNum; i++) {
            try {
                logger.info("读取行数：" + i);
                Row row = sheet.getRow(i);
                int cellNum = row.getLastCellNum();
                TValveNonc entity = new TValveNonc();
                for (int j = 0; j < cellNum; j++) {
                    Cell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    String cellValue = ExcelUtils.getCellValue(cell);
                    logger.info("cellValue:" + cellValue);
                    if (j==0){
                        entity.setVtNo(cellValue);
                    }else if (j==1){
                        entity.setPidNo(cellValue);
                    }else if (j==2){
                        entity.setLocationAt(cellValue);
                    }else if (j==3){
                        entity.setMedium(cellValue);
                    }else if (j==4){
                        entity.setValveType(cellValue);
                    }else if (j==5){
                        entity.setValveSize(cellValue);
                    }else if (j==6){
                        entity.setResponsibility(cellValue);
                    }else if (j==7){
                        entity.setPidStatus(cellValue);
                    }else if (j==8){
                        entity.setRiskLevel(cellValue);
                    }else if (j==9){
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
        for (TValveNonc t : list
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
