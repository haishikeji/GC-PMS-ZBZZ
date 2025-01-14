package com.px.project.web.controller.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.px.common.config.RuoYiConfig;
import com.px.common.constant.Constants;
import com.px.common.core.domain.AjaxResult;
import com.px.common.utils.StringUtils;
import com.px.common.utils.file.FileUploadUtils;
import com.px.common.utils.file.FileUtils;
import com.px.framework.config.ServerConfig;

/**
 * 通用请求处理
 * 
 * @author 品讯科技
 */
@RestController
@RequestMapping("/common")
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;

    private static final String FILE_DELIMETER = ",";

    /**
     * 导入下载模板
     */
    @RequestMapping("/template")
    @ResponseBody
    public void template(String type,HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String downloadname = "";
        String url = "";
        switch (type) {
            case "staffmgr":
                downloadname = "人员信息导入模板.xlsx";
                url = "static/template/plant/staffmgr.xlsx";
                break;
            case "specYlrq":
                downloadname = "压力容器导入模板.xlsx";
                url = "static/template/sems/specYlrq.xlsx";
                break;
            case "specYlgd":
                downloadname = "压力管道导入模板.xlsx";
                url = "static/template/sems/specYlgd.xlsx";
                break;
            case "specGl":
                downloadname = "锅炉导入模板.xlsx";
                url = "static/template/sems/specGl.xlsx";
                break;
            case "specDzsb":
                downloadname = "起重机械导入模板.xlsx";
                url = "static/template/sems/specDzsb.xlsx";
                break;
            case "specCc":
                downloadname = "场内机动车辆导入模板.xlsx";
                url = "static/template/sems/specCc.xlsx";
                break;
            case "specDt":
                downloadname = "电梯导入模板.xlsx";
                url = "static/template/sems/specDt.xlsx";
                break;
            case "specMain":
                downloadname = "设备总表格导入模板.xlsx";
                url = "static/template/asset/specMain.xlsx";
                break;
            case "specAsset":
                downloadname = "资产与运营清单导入模板.xlsx";
                url = "static/template/asset/specAsset.xlsx";
                break;
            case "fixedAsset":
                downloadname = "固定资产清单导入模板.xlsx";
                url = "static/template/asset/fixedAsset.xlsx";
                break;
            case "approval":
                downloadname = "批文清单导入模板.xlsx";
                url = "static/template/ehs/approval.xlsx";
                break;
            case "assetPoints":
                downloadname = "漏点清单导入模板.xlsx";
                url = "static/template/asset/assetPoints.xlsx";
                break;
            case "mocmoc":
                downloadname = "Moc清单导入模板.xlsx";
                url = "static/template/process/mocmoc.xlsx";
                break;
            case "processnonc":
                downloadname = "NONC清单导入模板.xlsx";
                url = "static/template/process/nonc.xlsx";
                break;
            case "processcsocsc":
                downloadname = "CSOCSC清单导入模板.xlsx";
                url = "static/template/process/csocsc.xlsx";
                break;
            case "processSifList":
                downloadname = "sif-overlist清单导入模板.xlsx";
                url = "static/template/process/sisList.xlsx";
                break;
            case "processvalve":
                downloadname = "LOLC清单导入模板.xlsx";
                url = "static/template/process/valve.xlsx";
                break;
        }
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(url);

        downloadFile(downloadname, is, request, response);
    }

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.checkAllowDownload(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, realFileName);
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 通用上传请求（多个）
     */
    @PostMapping("/uploads")
    public AjaxResult uploadFiles(List<MultipartFile> files) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = RuoYiConfig.getUploadPath();
            List<String> urls = new ArrayList<String>();
            List<String> fileNames = new ArrayList<String>();
            List<String> newFileNames = new ArrayList<String>();
            List<String> originalFilenames = new ArrayList<String>();
            for (MultipartFile file : files)
            {
                // 上传并返回新文件名称
                String fileName = FileUploadUtils.upload(filePath, file);
                String url = serverConfig.getUrl() + fileName;
                urls.add(url);
                fileNames.add(fileName);
                newFileNames.add(FileUtils.getName(fileName));
                originalFilenames.add(file.getOriginalFilename());
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("urls", StringUtils.join(urls, FILE_DELIMETER));
            ajax.put("fileNames", StringUtils.join(fileNames, FILE_DELIMETER));
            ajax.put("newFileNames", StringUtils.join(newFileNames, FILE_DELIMETER));
            ajax.put("originalFilenames", StringUtils.join(originalFilenames, FILE_DELIMETER));
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/download/resource")
    public void resourceDownload(String resource, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        try
        {
            if (!FileUtils.checkAllowDownload(resource))
            {
                throw new Exception(StringUtils.format("资源文件({})非法，不允许下载。 ", resource));
            }
            // 本地资源路径
            String localPath = RuoYiConfig.getProfile();
            // 数据库资源地址
            String downloadPath = localPath + StringUtils.substringAfter(resource, Constants.RESOURCE_PREFIX);
            // 下载名称
            String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            FileUtils.setAttachmentResponseHeader(response, downloadName);
            FileUtils.writeBytes(downloadPath, response.getOutputStream());
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    public static void downloadFile(String filename, InputStream is, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.reset();
        if (filename.endsWith(".doc") || filename.endsWith(".docx")) {
            response.setContentType("application/msword;charset=utf-8");
        } else if (filename.endsWith(".xls") || filename.endsWith(".xlsx")) {
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
        } else {
            response.setHeader("content-type", "application/octet-stream");
        }
        //下载文件的名称
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"), "iso-8859-1"));
        request.setCharacterEncoding("UTF-8");

        ServletOutputStream out = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
