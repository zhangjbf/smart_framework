package org.smart4j.framework.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.smart4j.framework.FrameworkConstant;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.util.FileUtil;
import org.smart4j.framework.util.StreamUtil;
import org.smart4j.framework.util.StringUtil;

/**
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/3
 */
public final class UploadHelper {

    private static ServletFileUpload servletFileUpload;

    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(
            new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int appUploadLimit = ConfigHelper.getAppUploadLimit();
        if (appUploadLimit != 0) {
            servletFileUpload.setFileSizeMax(appUploadLimit * 1024 * 1024);
        }
    }

    public static Boolean isMultiPart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    public static Param createParam(HttpServletRequest request) {
        List<FormParam> formParams = new ArrayList<>();
        List<FileParam> fileParams = new ArrayList<>();

        try {
            Map<String, List<FileItem>> fileItemListMapData = servletFileUpload.parseParameterMap(request);
            if (null != fileItemListMapData && fileItemListMapData.size() > 0) {
                for (Map.Entry<String, List<FileItem>> entry : fileItemListMapData.entrySet()) {
                    String fieldName = entry.getKey();
                    List<FileItem> fileItemList = entry.getValue();
                    if (CollectionUtils.isNotEmpty(fileItemList)) {
                        for (FileItem fileItem : fileItemList) {
                            if (fileItem.isFormField()) {
                                String fieldValue = fileItem.getString(FrameworkConstant.UTF_8);
                                formParams.add(new FormParam(fieldName, fieldValue));
                            } else {
                                String fileName = FileUtil.getRealFileName(
                                    new String(fileItem.getName().getBytes(), FrameworkConstant.UTF_8));
                                if (StringUtil.isNotEmpty(fileName)) {
                                    long size = fileItem.getSize();
                                    String contentType = fileItem.getContentType();
                                    InputStream inputStream = fileItem.getInputStream();
                                    fileParams.add(new FileParam(fieldName, fileName, size, contentType, inputStream));
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Param(formParams, fileParams);
    }

    public static void uploadFile(String basePath, FileParam fileParam) {
        try {
            if (null != fileParam) {
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uploadFile(String basePath, List<FileParam> fileParams) {
        if (CollectionUtils.isNotEmpty(fileParams)) {
            for (FileParam fileParam : fileParams) {
                uploadFile(basePath, fileParam);
            }
        }
    }
}
