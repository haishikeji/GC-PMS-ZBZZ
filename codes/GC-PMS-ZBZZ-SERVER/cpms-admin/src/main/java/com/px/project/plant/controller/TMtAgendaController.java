package com.px.project.plant.controller;

import com.px.common.annotation.Log;
import com.px.common.core.controller.BaseController;
import com.px.common.core.domain.AjaxResult;
import com.px.common.core.page.TableDataInfo;
import com.px.common.enums.BusinessType;
import com.px.common.utils.poi.ExcelUtil;
import com.px.project.plant.domain.TMtAgenda;
import com.px.project.plant.service.ITMtAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 装置会议议程Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/plant/agenda")
public class TMtAgendaController extends BaseController {
    @Autowired
    private ITMtAgendaService tMtAgendaService;

    /**
     * 查询装置会议议程列表
     */
    @PreAuthorize("@ss.hasPermi('plant:agenda:list')")
    @GetMapping("/list")
    public TableDataInfo list(TMtAgenda tMtAgenda) {
        startPage();
        List<TMtAgenda> tMtAgendaList = tMtAgendaService.selectTMtAgendaList(tMtAgenda);
        return getDataTable(tMtAgendaList);
    }

    @PreAuthorize("@ss.hasPermi('plant:agenda:list')")
    @GetMapping("/list2")
    public TableDataInfo list2(TMtAgenda tMtAgenda) {
        startPage();
        List<TMtAgenda> tMtAgendaList = tMtAgendaService.selectAgendaList(tMtAgenda);
        return getDataTable(tMtAgendaList);
    }

    /**
     * 导出装置会议议程列表
     */
    @PreAuthorize("@ss.hasPermi('plant:agenda:export')")
    @Log(title = "装置会议议程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TMtAgenda tMtAgenda) {
        List<TMtAgenda> list = tMtAgendaService.selectTMtAgendaList(tMtAgenda);
        ExcelUtil<TMtAgenda> util = new ExcelUtil<TMtAgenda>(TMtAgenda.class);
        util.exportExcel(response, list, "装置会议议程数据");
    }

    /**
     * 获取装置会议议程详细信息
     */
    @PreAuthorize("@ss.hasPermi('plant:agenda:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tMtAgendaService.selectTMtAgendaById(id));
    }

    /**
     * 新增装置会议议程
     */
    @PreAuthorize("@ss.hasPermi('plant:agenda:add')")
    @Log(title = "装置会议议程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TMtAgenda tMtAgenda) {
        return toAjax(tMtAgendaService.insertTMtAgenda(tMtAgenda));
    }

    /**
     * 修改装置会议议程
     */
    @PreAuthorize("@ss.hasPermi('plant:agenda:edit')")
    @Log(title = "装置会议议程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TMtAgenda tMtAgenda) {
        return toAjax(tMtAgendaService.updateTMtAgenda(tMtAgenda));
    }

    /**
     * 删除装置会议议程
     */
    @PreAuthorize("@ss.hasPermi('plant:agenda:remove')")
    @Log(title = "装置会议议程", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tMtAgendaService.deleteTMtAgendaByIds(ids));
    }
}
