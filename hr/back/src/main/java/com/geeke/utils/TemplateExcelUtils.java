package com.geeke.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.geeke.accumulation.entity.vo.BillDetailsVo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class TemplateExcelUtils {

    /**
     * 根据模板导出数据
     * @param fileName
     * @param sourcePath resource/template文件夹下路径
     * @param beanParams
     * @param response
     * @throws Exception
     */
    public static void downLoadExcel(String fileName, String sourcePath, Map<String, Object> beanParams, List<BillDetailsVo> list, HttpServletResponse response)
            throws Exception {
        try{
            OutputStream os = getOutputStream(fileName,response);
            //读取模板
            InputStream is = TemplateExcelUtils.class.getClassLoader().getResourceAsStream("template/"+sourcePath);
            ExcelWriter excelWriter = EasyExcel.write(os).withTemplate(is).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            excelWriter.fill(beanParams, writeSheet);
            excelWriter.fill(list, writeSheet);
            excelWriter.finish();
//            //向模板中写入内容
//            Workbook workbook = transformer.transformXLS(is, beanParams);
//            //写入成功后转化为输出流
//            workbook.write(os);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 导出文件时为Writer生成OutputStream.
     * @param fileName 文件名
     * @param response response
     * @return ""
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) throws Exception {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Type", "application/octet-stream;charset=utf-8");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e) {
            throw new Exception("导出excel表格失败!", e);
        }
    }
}