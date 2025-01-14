package com.px.project.plant.controller;

import com.px.common.annotation.Log;
import com.px.common.config.RuoYiConfig;
import com.px.common.core.controller.BaseController;
import com.px.common.core.domain.AjaxResult;
import com.px.common.core.page.TableDataInfo;
import com.px.common.enums.BusinessType;
import com.px.common.utils.DateUtils;
import com.px.common.utils.StringUtils;
import com.px.common.utils.file.FileUploadUtils;
import com.px.common.utils.poi.ExcelUtil;
import com.px.project.file.domain.TFile;
import com.px.project.file.service.ITFileService;
import com.px.project.plant.domain.TMtAgenda;
import com.px.project.plant.domain.TMtContent;
import com.px.project.plant.domain.TMtMeeting;
import com.px.project.plant.domain.TMtPerson;
import com.px.project.plant.service.ITMtAgendaService;
import com.px.project.plant.service.ITMtContentService;
import com.px.project.plant.service.ITMtMeetingService;
import com.px.project.plant.service.ITMtPersonService;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.Rows;
import com.deepoove.poi.data.Tables;
import com.deepoove.poi.data.Texts;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 装置会议Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/plant/meeting")
public class TMtMeetingController extends BaseController {
    @Autowired
    private ITMtMeetingService tMtMeetingService;
    @Autowired
    private ITMtAgendaService tMtAgendaService;
    @Autowired
    private ITMtPersonService tMtPersonService;
    @Autowired
    private ITMtContentService itMtContentService;
    @Autowired
    private ITFileService tFileService;

    /**
     * 查询装置会议列表
     */
    @PreAuthorize("@ss.hasPermi('plant:meeting:list')")
    @GetMapping("/list")
    public TableDataInfo list(TMtMeeting tMtMeeting) {
        startPage();
        List<TMtMeeting> list = tMtMeetingService.selectTMtMeetingList(tMtMeeting);
        return getDataTable(list);
    }

    /**
     * 导出装置会议列表
     */
    @PreAuthorize("@ss.hasPermi('plant:meeting:export')")
    @Log(title = "装置会议", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response,TMtMeeting tMtMeeting) {
        List<TMtMeeting> list = tMtMeetingService.selectTMtMeetingList(tMtMeeting);
        ExcelUtil<TMtMeeting> util = new ExcelUtil<TMtMeeting>(TMtMeeting.class);
        util.exportExcel(response, list, "meeting");
    }

    /**
     * 获取装置会议详细信息
     */
    @PreAuthorize("@ss.hasPermi('plant:meeting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tMtMeetingService.selectTMtMeetingById(id));
    }

    /**
     * 新增装置会议
     */
    @PreAuthorize("@ss.hasPermi('plant:meeting:add')")
    @Log(title = "装置会议", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TMtMeeting tMtMeeting) {
        return toAjax(tMtMeetingService.insertTMtMeeting(tMtMeeting));
    }

    /**
     * 修改装置会议
     */
    @PreAuthorize("@ss.hasPermi('plant:meeting:edit')")
    @Log(title = "装置会议", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TMtMeeting tMtMeeting) {
        return toAjax(tMtMeetingService.updateTMtMeeting(tMtMeeting));
    }

    /**
     * 删除装置会议
     */
    @PreAuthorize("@ss.hasPermi('plant:meeting:remove')")
    @Log(title = "装置会议", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tMtMeetingService.deleteTMtMeetingByIds(ids));
    }

    @GetMapping("/wordView/{id}")
    public AjaxResult wordView(@PathVariable Long id) throws IOException {
        //根据ID查询并生成
        TMtMeeting tMtMeeting = tMtMeetingService.selectTMtMeetingById(id);
        //根据ID查询并生成
        String url = PreView(tMtMeeting);
        List<String> list = new ArrayList<>();
        list.add(RuoYiConfig.getFilePath(url.substring(8)));
        if (tMtMeeting.getMtType() == 1) {

        } else if (tMtMeeting.getMtType() == 2) {
            TMtContent tMtContent = new TMtContent();
            tMtContent.setMeetingId(String.valueOf(id));
            for (TMtContent mtContent : itMtContentService.selectTMtContentList(tMtContent)) {
                TFile tFile = new TFile();
                tFile.setLinkName("meetContent");
                tFile.setLinkId(mtContent.getId());
                for (TFile file : tFileService.selectTFileList(tFile)) {
                    list.add(RuoYiConfig.getFilePath(file.getFileUrl().substring(8)));                  ;
                }
            }
        } else {
        }
        String zipName = "/upload/"+ UUID.randomUUID()+".zip";
        FileOutputStream fos = new FileOutputStream(RuoYiConfig.getFilePath(zipName));
        ZipOutputStream zos = new ZipOutputStream(fos);
        for (String  path: list) {
            // 创建一个输入流，读取文件数据
            FileInputStream fis = new FileInputStream(path);
            // 创建一个zip条目，指定文件名
            ZipEntry entry = new ZipEntry(path.substring(path.lastIndexOf("/")));
            // 将条目添加到zip文件中
            zos.putNextEntry(entry);
            // 将文件数据写入zip文件
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            // 关闭输入流
            fis.close();
            // 完成当前文件的压缩
            zos.closeEntry();
        }

        // 关闭输出流
        zos.close();
        return AjaxResult.success("/profile"+zipName);
    }
    /**
     * @param tMtMeeting 生成文件名
     * @return
     * @throws IOException
     */
    public String PreView(TMtMeeting tMtMeeting) throws IOException {
        return this.createMeetingWord(tMtMeeting);
    }

    public String createMeetingWord(TMtMeeting tMtMeeting) throws IOException {
        //生成word
        // 模板路径
        String templatePath;
        // 生成word的路径
        String fileDir = RuoYiConfig.getProfile() + "/" + "meeting";
        // 生成word的文件名称
        String fileName = tMtMeeting.getMeetingNo() + "号会议记录.docx";
        //渲染文本
        Map<String, Object> params = getWordData(tMtMeeting);
        if (tMtMeeting.getMtType() == 1) {
            templatePath = "static/word/meeting.docx";
        } else if (tMtMeeting.getMtType() == 2) {
            templatePath = "static/word/blankMeeting.docx";
        } else {
            return "";
        }
        return createWord(templatePath, fileDir, fileName, params, String.valueOf(tMtMeeting.getId()));
    }

    public Map<String, Object> getWordData(TMtMeeting tMtMeeting) {
        Map<String, Object> params = new HashMap<>();
        params.put("mtDate", Texts.of(DateUtils.parseDateToStr("yyyyMMdd", tMtMeeting.getMeetingDate())).fontSize(10).bold().create());
        params.put("mtVenue", Texts.of(String.valueOf(tMtMeeting.getVenue())).fontSize(10).bold().create());
        params.put("subject", Texts.of(tMtMeeting.getSubject()).fontSize(10).bold().create());
        params.put("mtNo", Texts.of(tMtMeeting.getMeetingNo()).fontSize(10).bold().create());
        params.put("mtRecorder", Texts.of(tMtMeeting.getRecorder()).fontSize(10).bold().create());
        params.put("mtPresided", Texts.of(tMtMeeting.getPresided()).fontSize(10).bold().create());
        params.put("participants", replaceTextInParagraph("participants", String.valueOf(tMtMeeting.getId())));
        if (tMtMeeting.getMtType()==1) {
            params.put("table1", replaceTextInParagraph("table1", String.valueOf(tMtMeeting.getId())));
            params.put("table2", replaceTextInParagraph("table2", String.valueOf(tMtMeeting.getId())));
            params.put("table3", replaceTextInParagraph("table3", String.valueOf(tMtMeeting.getId())));
            params.put("table4", replaceTextInParagraph("table4", String.valueOf(tMtMeeting.getId())));
            params.put("table5", replaceTextInParagraph("table5", String.valueOf(tMtMeeting.getId())));
            params.put("table6", replaceTextInParagraph("table6", String.valueOf(tMtMeeting.getId())));
            params.put("table7", replaceTextInParagraph("table7", String.valueOf(tMtMeeting.getId())));
            params.put("table8", replaceTextInParagraph("table8", String.valueOf(tMtMeeting.getId())));
            params.put("table9", replaceTextInParagraph("table9", String.valueOf(tMtMeeting.getId())));
            params.put("table10", replaceTextInParagraph("table10", String.valueOf(tMtMeeting.getId())));
            params.put("table11", replaceTextInParagraph("table11", String.valueOf(tMtMeeting.getId())));
            params.put("table12", replaceTextInParagraph("table12", String.valueOf(tMtMeeting.getId())));
            params.put("table13", replaceTextInParagraph("table13", String.valueOf(tMtMeeting.getId())));
            params.put("table14", replaceTextInParagraph("table14", String.valueOf(tMtMeeting.getId())));
        } else if (tMtMeeting.getMtType()==2) {
            params.put("content", replaceTextInParagraph(String.valueOf(tMtMeeting.getId())));
        }
        return params;
    }

    public String createWord(String templatePath, String fileDir, String fileName, Map<String, Object> paramMap, String meetingId) throws IOException {
        Assert.notNull(templatePath, "word模板文件路径不能为空");
        Assert.notNull(fileDir, "生成的文件存放地址不能为空");
        Assert.notNull(fileName, "生成的文件名不能为空");
        File dir = new File(fileDir);
        if (!dir.exists()) {
            logger.info("目录不存在，创建文件夹{}!", fileDir);
            dir.mkdirs();
        }
        fileName = fileName.replaceAll("/", "_"); //替换文件中敏感字段
        logger.info("目录文件{}!", fileName);
        String filePath = fileDir + "/" + fileName;
        logger.info("目录{}!", filePath);
        // 读取模板渲染参数
        InputStream is = getClass().getClassLoader().getResourceAsStream(templatePath);
        XWPFTemplate template = null;
        if (is != null) {
            template = XWPFTemplate.compile(is).render(paramMap);
        }
        try {
            // 将模板参数写入路径
            if (template != null) {
                template.writeToFile(filePath);
                template.close();
            }
        } catch (Exception e) {
            logger.error("生成word异常{}", e.getMessage());
            e.printStackTrace();
        }
        return FileUploadUtils.getPathFileName(RuoYiConfig.getFilePath("/" + "meeting"), fileName);
    }

    private Object replaceTextInParagraph(String placeholder, String meetingId) {
        String title = "";
        String agendaType = "";
        TMtAgenda tMtAgenda = new TMtAgenda();
        tMtAgenda.setMeetingId(String.valueOf(meetingId));
        List<TMtAgenda> list;
        switch (placeholder) {
            case "participants": {
                TMtPerson tMtPerson = new TMtPerson();
                tMtPerson.setMeetingId(Long.valueOf(meetingId));
                List<TMtPerson> tMtPersonList = tMtPersonService.selectTMtPersonList(tMtPerson);
                List<RowRenderData> rowDatas = new ArrayList<>();
                List<List<String>> names = new ArrayList<>();
                List<String> nameList = new ArrayList<>();
                for (int i = 0; i < tMtPersonList.size(); i++) {
                    TMtPerson person = tMtPersonList.get(i);
                    nameList.add(person.getName());
                    if (tMtPersonList.size() - 1 == i && nameList.size() != 2) {
                        nameList.add("");
                        names.add(nameList);
                        break;
                    }
                    if (nameList.size() == 2) {
                        names.add(nameList);
                        nameList = new ArrayList<>();
                    }
                }
                for (List<String> item : names) {
                    rowDatas.add(Rows.of(item.toArray(new String[0])).create());
                }
                return Tables.of(rowDatas.toArray(new RowRenderData[0])).width(16.98, new double[]{16.98 * 0.55, 16.98 * 0.55}).create();
            }
            case "table1": {
                title = "内容";
                agendaType = "1";
                tMtAgenda.setAgendaType(agendaType);
                list = tMtAgendaService.selectTMtAgendaList(tMtAgenda);
                list.add(new TMtAgenda());
                ArrayList<RowRenderData> rowDatas = new ArrayList<>();
                // 表头
                RowRenderData tableHead = Rows.of(title, "类型", "跟踪人", "回顾/截止时间", "状态").textFontFamily("微软雅黑").textFontSize(8).textBold().create();
                rowDatas.add(tableHead);
                for (TMtAgenda agenda : list) {
                    rowDatas.add(Rows.of(agenda.getAgendaContent(), agenda.getWorkType(), agenda.getResponsible(), DateUtils.dateTime(agenda.getDeadlineTime()), agenda.getStatus()).textFontSize(8).create());
                }
                return Tables.of(rowDatas.toArray(new RowRenderData[0])).width(16.98, new double[]{16.98 * 0.6, 16.98 * 0.1, 16.98 * 0.1, 16.98 * 0.2, 16.98 * 0.1}).create();
            }
            case "table2":
                title = "√EHS 信息分享（作业票，安全绩效，政府/公司检查信息等）";
                agendaType = "2";
                break;
            case "table3":
                title = "√质量议题";
                agendaType = "3";
                break;
            case "table4":
                title = "√行动项追踪系统状态";
                agendaType = "4";
                break;
            case "table14":
                title = "√生产计划";
                agendaType = "14";
                break;
            case "table5": {
                title = "√装置运行状态，负荷等";
                agendaType = "5";
                tMtAgenda.setAgendaType(agendaType);
                list = tMtAgendaService.selectTMtAgendaList(tMtAgenda);
                list.add(new TMtAgenda());
                ArrayList<RowRenderData> rowDatas = new ArrayList<>();
                // 表头
                RowRenderData tableHead = Rows.of(title, "", "", "", "").textFontFamily("微软雅黑").textFontSize(8).textBold().create();
                rowDatas.add(tableHead);
                for (TMtAgenda agenda : list) {
                    if (StringUtils.isEmpty(agenda.getPlanType()))
                        agenda.setPlanType("");
                    if (StringUtils.isEmpty(agenda.getAgendaContent()))
                        agenda.setAgendaContent("");
                    rowDatas.add(Rows.of(agenda.getPlanType() + agenda.getAgendaContent(), agenda.getWorkType(), agenda.getResponsible(), DateUtils.dateTime(agenda.getDeadlineTime()), agenda.getStatus()).textFontSize(8).create());
                }
                return Tables.of(rowDatas.toArray(new RowRenderData[0])).width(16.98, new double[]{16.98 * 0.6, 16.98 * 0.1, 16.98 * 0.1, 16.98 * 0.2, 16.98 * 0.1}).create();
            }
            case "table6":
                title = "√OpEx/KPIs 议题";
                agendaType = "6";
                break;
            case "table7":
                title = "√MOC议题";
                agendaType = "7";
                break;
            case "table8":
                title = "√CapEx/检修议题";
                agendaType = "8";
                break;
            case "table9":
                title = "√文档完整性";
                agendaType = "9";
                break;
            case "table10":
                title = "√其他/5S";
                agendaType = "10";
                break;
            case "table11":
                title = "√其他/LEAN";
                agendaType = "11";
                break;
            case "table12":
                title = "√静设备近期PM/检维修计划";
                agendaType = "12";
                break;
            case "table13":
                title = "√电气仪表近期PM/检维修计划";
                agendaType = "13";
                break;
        }
        tMtAgenda.setAgendaType(agendaType);
        list = tMtAgendaService.selectTMtAgendaList(tMtAgenda);
        list.add(new TMtAgenda());
        ArrayList<RowRenderData> rowDatas = new ArrayList<>();
        // 表头
        RowRenderData tableHead = Rows.of(title, "", "", "", "").textFontFamily("微软雅黑").textFontSize(8).textBold().create();
        rowDatas.add(tableHead);
        for (TMtAgenda agenda : list) {
            rowDatas.add(Rows.of(agenda.getAgendaContent(), agenda.getWorkType(), agenda.getResponsible(), DateUtils.dateTime(agenda.getDeadlineTime()), agenda.getStatus()).textFontSize(8).create());
        }
        return Tables.of(rowDatas.toArray(new RowRenderData[0])).width(16.98, new double[]{16.98 * 0.6, 16.98 * 0.1, 16.98 * 0.1, 16.98 * 0.2, 16.98 * 0.1}).create();
    }
    private Object replaceTextInParagraph(String meetingId) {
        TMtContent tMtContent = new TMtContent();
        tMtContent.setMeetingId(String.valueOf(meetingId));
        List<TMtContent> list = itMtContentService.selectTMtContentList(tMtContent);
        ArrayList<RowRenderData> rowDatas = new ArrayList<>();
        for (TMtContent agenda : list) {// 表头
            RowRenderData tableHead = Rows.of("内容", "类型", "跟踪人", "回顾/截止时间", "状态").textFontFamily("微软雅黑").textFontSize(8).textBold().create();
            rowDatas.add(tableHead);
            rowDatas.add(Rows.of(agenda.getAgendaContent(), agenda.getWorkType(), agenda.getResponsible(), DateUtils.dateTime(agenda.getDeadlineTime()), agenda.getStatus()).textFontSize(8).create());
        }
        return Tables.of(rowDatas.toArray(new RowRenderData[0])).width(16.98, new double[]{16.98 * 0.6, 16.98 * 0.1, 16.98 * 0.1, 16.98 * 0.2, 16.98 * 0.1}).create();
    }

}
