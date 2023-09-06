package com.example.light.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.example.light.annotation.LogAnnotation;
import com.example.light.common.ExcelUtil;
import com.example.light.common.ResultMapUtil;
import com.example.light.entity.SysLogs;
import com.example.light.service.SyslogsService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;


@Controller
public class logsController {

    @Autowired
    private SyslogsService syslogsService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/logsLight")
    public Object logsLight(){

        return "/logs/logsLight";

    }

//    @LogAnnotation
//    @ApiOperation(value = "查看所有日志")
    @RequestMapping("/logsList")
    @ResponseBody
    public Object logsList(){

        List<SysLogs> logList = syslogsService.queryLogList();

        return ResultMapUtil.getHashMapList(logList);
    }

    @RequestMapping("/logListByUserName/{userName}")
    @ResponseBody
    public Object logListByUserName(@PathVariable(name = "userName",required = true)String userName){

        List<SysLogs> logList = syslogsService.queryLogByUserName(userName);

        return ResultMapUtil.getHashMapList(logList);
    }

    @RequestMapping("/logListByModule/{module}")
    @ResponseBody
    public Object logListByModule(@PathVariable(name = "module",required = true)String module){

        List<SysLogs> logList = syslogsService.queryLogByModule(module);

        return ResultMapUtil.getHashMapList(logList);
    }

    @RequestMapping("/logListByUpTime/{upTime}")
    @ResponseBody
    public Object logListByUpTime(@PathVariable(name = "upTime",required = true)String upTime){

        List<SysLogs> logList = syslogsService.queryLogByUpTime(upTime);

        return ResultMapUtil.getHashMapList(logList);
    }


//    public void logExport( HttpServletResponse response) {
////        sql = getAndCheckSql(sql);
//        String sql = "select * from user";
//        String fileName = "logs";
//
//        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
//
//        if (!CollectionUtils.isEmpty(list)) {
//            Map<String, Object> map = list.get(0);
//
//            String[] headers = new String[map.size()];
//            int i = 0;
//            for (String key : map.keySet()) {
//                headers[i++] = key;
//            }
//
//            List<Object[]> datas = new ArrayList<>(list.size());
//            for (Map<String, Object> m : list) {
//                Object[] objects = new Object[headers.length];
//                for (int j = 0; j < headers.length; j++) {
//                    objects[j] = m.get(headers[j]);
//                }
//
//                datas.add(objects);
//            }
//
//            ExcelUtil.excelExport(
//                    fileName == null || fileName.trim().length() <= 0 ? DigestUtils.md5Hex(sql) : fileName, headers,
//                    datas, response);
//        }
//    }

    @RequestMapping("/logExport")
    @ResponseBody
    public void logExport( HttpServletResponse response) throws IOException {
//        String fileName = "F:\\操作日志.xls";
        System.out.println(response.toString());
        setExcelRespProp(response, "操作日志");
        List<SysLogs> logList = syslogsService.queryLogList();
//        EasyExcel.write(fileName, SysLogs.class)
//                .sheet(0)
//                .doWrite(logList);

        EasyExcel.write(response.getOutputStream(),SysLogs.class)
                .sheet("操作日志")
                .doWrite(logList);

    }

    private void setExcelRespProp(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }



}
