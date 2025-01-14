package com.px.project.file.controller;

import com.px.common.annotation.Log;
import com.px.common.config.RuoYiConfig;
import com.px.common.core.controller.BaseController;
import com.px.common.core.domain.AjaxResult;
import com.px.common.enums.BusinessType;
import com.px.common.utils.StringUtils;
import com.px.common.utils.file.FileUploadUtils;
import com.px.project.file.domain.TFile;
import com.px.project.file.service.ITFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 附件Controller
 *
 * @author 品讯科技
 * @date 2024-08
 */
@RestController
@RequestMapping("/file/file")
public class TFileController extends BaseController {
    @Autowired
    private ITFileService tFileService;

    /**
     * 查询附件列表
     */
    @PreAuthorize("@ss.hasPermi('file:file:list')")
    @GetMapping("/list")
    public AjaxResult list(TFile tFile) {
        if (StringUtils.isEmpty(tFile.getLinkName())) {
            return AjaxResult.success(new ArrayList<TFile>());
        }
        List<TFile> list = tFileService.selectTFileList(tFile);
        return AjaxResult.success(list);
    }


    /**
     * 获取附件详细信息
     */
    @PreAuthorize("@ss.hasPermi('file:file:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(tFileService.selectTFileById(id));
    }

    /**
     * 新增附件
     */
    @PreAuthorize("@ss.hasPermi('file:file:add')")
    @Log(title = "附件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TFile tFile) {
        return toAjax(tFileService.insertTFile(tFile));
    }

    /**
     * 修改附件
     */
    @PreAuthorize("@ss.hasPermi('file:file:edit')")
    @Log(title = "附件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TFile tFile) {
        return toAjax(tFileService.updateTFile(tFile));
    }

    /**
     * 删除附件
     */
    @PreAuthorize("@ss.hasPermi('file:file:remove')")
    @Log(title = "附件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(tFileService.deleteTFileByIds(ids));
    }


    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(@RequestParam("file") MultipartFile file,
                                 @RequestParam(value = "linkId", required = false) Long linkId,
                                 @RequestParam(value = "linkName", required = false) String linkName,
                                 @RequestParam(value = "pId", required = false) Long pId) throws IOException {
        if (!file.isEmpty()) {
            String avatar = FileUploadUtils.upload(RuoYiConfig.getUploadPath(), file);
            TFile tFile = new TFile();
            tFile.setFileName(file.getOriginalFilename());
            tFile.setFileUrl(avatar);
            switch (Objects.requireNonNull(file.getOriginalFilename()).substring(
                    file.getOriginalFilename().lastIndexOf(".") + 1)) {
                case "jpg":
                case "png":
                case "gif":
                case "bmp":
                case "jpeg":
                case "jpe":
                    tFile.setType("6");
                    break;
                case "xls":
                case "xlsx":
                    tFile.setType("1");
                    break;
                case "ppt":
                case "pptx":
                    tFile.setType("2");
                    break;
                case "doc":
                case "docx":
                    tFile.setType("3");
                    break;
                case "pdf":
                    tFile.setType("4");
                    break;
                case "txt":
                    tFile.setType("5");
                    break;
                default:
                    tFile.setType("7");
                    break;
            }
            tFile.setpId(pId);
            tFile.setFileSize(String.valueOf(new BigDecimal(file.getSize()).
                    divide(BigDecimal.valueOf(1024), 2, RoundingMode.HALF_DOWN)));
            tFile.setUploader(getNickname());
            tFile.setUploadDate(new Date());
            tFile.setLinkId(linkId);
            tFile.setLinkName(linkName);
            tFileService.insertTFile(tFile);
            return AjaxResult.success(avatar);
        }
        return AjaxResult.error("上传异常，请联系管理员");
    }
}
