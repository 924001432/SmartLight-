package com.example.light.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.light.annotation.LogAnnotation;
import com.example.light.common.ResultMapUtil;
import com.example.light.entity.Area;
import com.example.light.service.AreaService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class areaLightController {

    @Autowired
    private AreaService areaService;


    @RequestMapping("/areaLight")
    public Object areaLight(){
        return "/area/areaLight";
    }

    @RequestMapping("/areaAddPage")
    public Object areaAddPage(){
        return "/area/areaAddPage";
    }

    @RequestMapping("/testAddPage")
    public Object testAddPage(){
        return "/area/testAddPage";
    }

    @RequestMapping("/testZTree")
    public Object testZTree(){
        return "/zTree/testZTree";
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


    @LogAnnotation
    @ApiOperation(value = "新增区域")
    @RequestMapping("/areaAdd")
    @ResponseBody
    public Object areaAdd(Area area) {

        try{
            int i = areaService.areaAdd(area);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }


    }

    @RequestMapping("/areaList")
    @ResponseBody
    public Object areaList() {

        return areaService.areaList();

    }
}
