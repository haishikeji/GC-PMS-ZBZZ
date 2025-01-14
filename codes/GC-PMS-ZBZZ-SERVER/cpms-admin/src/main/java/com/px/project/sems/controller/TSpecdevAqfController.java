package com.px.project.sems.controller;

import com.px.common.annotation.Log;
import com.px.common.core.controller.BaseController;
import com.px.common.core.domain.AjaxResult;
import com.px.common.core.page.TableDataInfo;
import com.px.common.enums.BusinessType;
import com.px.common.utils.DateUtils;
import com.px.common.utils.StringUtils;
import com.px.common.utils.poi.ExcelUtil;
import com.px.project.sems.domain.TSpecdevAqf;
import com.px.project.sems.service.ITSpecdevAqfService;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 特种设备安全阀台账Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/sems/aqf")
public class TSpecdevAqfController extends BaseController
{
    @Autowired
    private ITSpecdevAqfService tSpecdevAqfService;

    /**
     * 查询特种设备安全阀台账列表
     */
    @PreAuthorize("@ss.hasPermi('sems:specAqf:list')")
    @GetMapping("/list")
    public TableDataInfo list(TSpecdevAqf tSpecdevAqf)
    {
        startPage();
        List<TSpecdevAqf> list = tSpecdevAqfService.selectTSpecdevAqfList(tSpecdevAqf);
        for (TSpecdevAqf specdevAqf : list) {
            if (specdevAqf.getNextWarnDate()!= null) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date d1 = specdevAqf.getNextWarnDate();
                    Date d2 = format.parse(DateUtils.getDate());
                    DateTime dt1 = new DateTime(d1);
                    DateTime dt2 = new DateTime(d2);
                    int days = Days.daysBetween(dt2, dt1).getDays();
                    System.out.println(days);
                    if  (days > 60) {
                        specdevAqf.setCheckStatus("正常");
                    }else{
                        specdevAqf.setCheckStatus("待报检");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出特种设备安全阀台账列表
     */
    @PreAuthorize("@ss.hasPermi('sems:specAqf:export')")
    @Log(title = "特种设备安全阀台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TSpecdevAqf tSpecdevAqf)
    {
        List<TSpecdevAqf> list = tSpecdevAqfService.selectTSpecdevAqfList(tSpecdevAqf);
        for (TSpecdevAqf specdevAqf : list) {
            if (StringUtils.isNotEmpty(specdevAqf.getIsInspection())){
                specdevAqf.setIsInspection(specdevAqf.getIsInspection().equals("1")?"是":"否");
            }
            if (StringUtils.isNotEmpty(specdevAqf.getNeedScaffold())){
                specdevAqf.setNeedScaffold(specdevAqf.getNeedScaffold().equals("1")?"是":"否");
            }
            if (StringUtils.isNotEmpty(specdevAqf.getKeepWarm())){
                specdevAqf.setKeepWarm(specdevAqf.getKeepWarm().equals("1")?"是":"否");
            }
            if (StringUtils.isNotEmpty(specdevAqf.getCrane())){
                specdevAqf.setCrane(specdevAqf.getCrane().equals("1")?"是":"否");
            }
            if (StringUtils.isNotEmpty(specdevAqf.getIsPid())){
                specdevAqf.setIsPid(specdevAqf.getIsPid().equals("1")?"是":"否");
            }
            if (specdevAqf.getNextWarnDate()!= null) {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date d1 = specdevAqf.getNextWarnDate();
                    Date d2 = format.parse(DateUtils.getDate());
                    DateTime dt1 = new DateTime(d1);
                    DateTime dt2 = new DateTime(d2);
                    int days = Days.daysBetween(dt2, dt1).getDays();
                    System.out.println(days);
                    if  (days > 60) {
                        specdevAqf.setCheckStatus("正常");
                    }else{
                        specdevAqf.setCheckStatus("待报检");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ExcelUtil<TSpecdevAqf> util = new ExcelUtil<TSpecdevAqf>(TSpecdevAqf.class);
        util.exportExcel(response, list, "aqf");
    }

    /**
     * 获取特种设备安全阀台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('sems:specAqf:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tSpecdevAqfService.selectTSpecdevAqfById(id));
    }

    /**
     * 新增特种设备安全阀台账
     */
    @PreAuthorize("@ss.hasPermi('sems:specAqf:add')")
    @Log(title = "特种设备安全阀台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TSpecdevAqf tSpecdevAqf)
    {
        return toAjax(tSpecdevAqfService.insertTSpecdevAqf(tSpecdevAqf));
    }

    /**
     * 修改特种设备安全阀台账
     */
    @PreAuthorize("@ss.hasPermi('sems:specAqf:edit')")
    @Log(title = "特种设备安全阀台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TSpecdevAqf tSpecdevAqf)
    {
        return toAjax(tSpecdevAqfService.updateTSpecdevAqf(tSpecdevAqf));
    }

    /**
     * 删除特种设备安全阀台账
     */
    @PreAuthorize("@ss.hasPermi('sems:specAqf:remove')")
    @Log(title = "特种设备安全阀台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tSpecdevAqfService.deleteTSpecdevAqfByIds(ids));
    }
}
