package com.example.light.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.light.annotation.LogAnnotation;
import com.example.light.common.ResultMapUtil;
import com.example.light.entity.Area;
import com.example.light.entity.User;
import com.example.light.service.AreaService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class areaLightController {

    @Autowired
    private AreaService areaService;


    @RequestMapping("/areaLight")
    public Object areaLight(){
        return "/area/areaLight";
    }

    @RequestMapping("/areaAddPage/{areaId}")
    public Object areaAddPage(@PathVariable(name = "areaId",required = true)Integer areaId, Model model){
        Area area = areaService.getById(areaId);
        model.addAttribute("obj",area);

        return "/area/areaAddPage";
    }

//    @LogAnnotation
//    @ApiOperation(value = "新增区域")
    @RequestMapping("/areaAdd")
    @ResponseBody
    public Object areaAdd(Area area) {
        System.out.println("here");

        try{
            int i = areaService.areaAdd(area);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }


    }

    @RequestMapping("/areaEditPage/{areaId}")
    public Object areaEditPage(@PathVariable(name = "areaId", required = true) Integer areaId, Model model) {
        Area area = areaService.getById(areaId);
        model.addAttribute("obj", area);

        return "/area/areaEditPage";
    }


//    @LogAnnotation // 标记日志注解，用于记录日志信息
//    @ApiOperation(value = "编辑区域") // 接口文档的注解，表示该方法是用来编辑区域的
    @RequestMapping("/areaEdit") // 定义请求的URL路径
    @ResponseBody // 返回结果会被转换为JSON格式

    public Object areaEdit(Area area) {

        System.out.println(area.toString());

        try {
            int i = areaService.areaEdit(area); // 调用具体的区域编辑逻辑
            return ResultMapUtil.getHashMapSave(i); // 返回编辑结果
        } catch (Exception e) {
            return ResultMapUtil.getHashMapException(e); // 返回异常信息
        }
    }


    @RequestMapping("/testAddPage")
    public Object testAddPage(){
        return "/area/testAddPage";
    }

    @RequestMapping("/testZTree")
    public Object testZTree(){
        return "/zTree/testZTree";
    }


    /**
     * 根据用户负责区域查询区域
     * @return
     */
    @RequestMapping("/areaListByareaId/{areaId}")
    @ResponseBody
    public Object areaListByareaId(@PathVariable(name = "areaId",required = true)Integer areaId) {

        Area area = areaService.getById(areaId);
//        System.out.println(area.toString());
        List<Area> list = new ArrayList<>();
        list.add(area);


        return list;
    }

//    @LogAnnotation
//    @ApiOperation(value = "获取所有区域")
    @RequestMapping("/area/all")
    @ResponseBody
    public JSONArray areaAll() {
        List<Area> areaAll = areaService.areaList();
        JSONArray array = new JSONArray();
        setPermissionsTree(0, areaAll, array);

        return array;
    }

    /**
     * 根据用户负责区域构造区域目录
     * @return
     */
    @RequestMapping("/areaListByuserArea/{userArea}")
    @ResponseBody
    public JSONArray areaListByuserArea(@PathVariable(name = "userArea",required = true)Integer userArea) {

        List<Area> areaAll = areaService.areaList();

        JSONArray array = new JSONArray();
        setPermissionsTree(userArea, areaAll, array);

//        System.out.println(array);

        return array;
    }

    private void setPermissionsTree(Integer pId, List<Area> permissionsAll, JSONArray array) {
        for (Area per : permissionsAll) {

            if (per.getParentId().equals(pId)) {
                String string = JSONObject.toJSONString(per);
                JSONObject parent = (JSONObject) JSONObject.parse(string);
                array.add(parent);

                if (permissionsAll.stream().filter(p -> p.getParentId().equals(per.getAreaId())).findAny() != null) {
                    JSONArray child = new JSONArray();
                    parent.put("child", child);
                    setPermissionsTree(per.getAreaId(), permissionsAll, child);
                }
            }
        }
    }

    @RequestMapping("/area/parents/{areaLevel}")
    @ResponseBody
    public List<Area> parentMenu(@PathVariable(name = "areaLevel",required = true)Integer areaLevel) {

        return areaService.parentList(areaLevel-1);

    }




    @RequestMapping("/areaList")
    @ResponseBody
    public Object areaList() {

        return areaService.areaList();

    }
}
