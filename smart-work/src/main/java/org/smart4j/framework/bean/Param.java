package org.smart4j.framework.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.smart4j.framework.util.StringUtil;

/**
 * 请求参数
 *
 * @Version: 1.0
 * @Author: jiabin.zhang 张佳宾
 * @Email: jiabin.zhang@rograndec.com
 * @CreateDate 2019/6/2
 */
public class Param {

    private List<FormParam> formParamList;

    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    public List<FormParam> getFormParamList() {
        return formParamList;
    }

    public void setFormParamList(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public List<FileParam> getFileParamList() {
        return fileParamList;
    }

    public void setFileParamList(List<FileParam> fileParamList) {
        this.fileParamList = fileParamList;
    }

    public Map<String, Object> getFieldMap() {
        Map<String, Object> mapResult = new HashMap<>();
        if (CollectionUtils.isNotEmpty(formParamList)) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (mapResult.containsKey(fieldName)) {
                    fieldValue = mapResult.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                mapResult.put(fieldName, fieldValue);
            }
        }
        return mapResult;
    }

    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> mapResult = new HashMap<>();
        if (CollectionUtils.isNotEmpty(fileParamList)) {
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParams = mapResult.get(fieldName);
                if (null == fileParams) {
                    fileParams = new ArrayList<>();
                    mapResult.put(fieldName, fileParams);
                }
                fileParams.add(fileParam);
            }
        }
        return mapResult;
    }

    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    public boolean isEmpty() {
        if (CollectionUtils.isEmpty(formParamList) && CollectionUtils.isEmpty(fileParamList)) {
            return true;
        }
        return false;
    }
}
