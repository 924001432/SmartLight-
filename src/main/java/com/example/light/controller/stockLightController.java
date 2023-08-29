package com.example.light.controller;

import com.example.light.common.ResultMapUtil;
import com.example.light.entity.Alarm;
import com.example.light.entity.Stock;
import com.example.light.entity.User;
import com.example.light.service.DeviceService;
import com.example.light.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class stockLightController {

    @Autowired
    private StockService stockService;

    @RequestMapping("/stockLight")
    public Object stockLight(){

        return "/stock/stockLight";

    }

    @RequestMapping("/stockAddPage")
    public Object stockAddPage(){

        return "/stock/stockAddPage";
    }

    @GetMapping("/stockAdd")
    @ResponseBody
    public Object stockAdd(Stock stock){

        try{
            int i = stockService.stockAdd(stock);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }

    }

    @RequestMapping("/stockEditPage/{stockId}")
    public Object stockEditPage(@PathVariable(name = "stockId",required = true)Integer stockId, Model model){


        Stock stock = stockService.queryStockBystockId(stockId);
        model.addAttribute("obj",stock);


        return "/stock/stockEditPage";
    }

    @RequestMapping("/stockEdit")
    @ResponseBody
    public Object stockEdit(Stock stock){
        //存在问题：页面转换数据时，替换原来的int型为string导致数据库存储出现问题
        System.out.println(stock.toString());

        try{
            int i = stockService.stockEdit(stock);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }

    }

    /**
     * 出库
     * @param stockId
     * @return
     */
    @RequestMapping("/stockOut/{stockId}")
    @ResponseBody
    public Object stockOut(@PathVariable(name = "stockId",required = true)Integer stockId){

        try{
            int i = stockService.stockOut(stockId);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }

    @RequestMapping("/stockDelete/{stockId}")
    @ResponseBody
    public Object stockDelete(@PathVariable(name = "stockId",required = true)Integer stockId){

        try{
            int i = stockService.stockDelete(stockId);
            return ResultMapUtil.getHashMapSave(i);
        } catch (Exception e){
            return ResultMapUtil.getHashMapException(e);
        }
    }



    @RequestMapping("/queryStockBystockStatus/{stockStatus}")
    @ResponseBody
    public List<Stock> queryStockBystockStatus(@PathVariable(name = "stockStatus",required = true)Integer stockStatus){



        return stockService.queryStockBystockStatus(stockStatus);

    }

    @RequestMapping("/stockListByDeviceSerial/{stockStatus}&{deviceSerial}")
    @ResponseBody
    public List<Stock> stockListByDeviceSerial(@PathVariable(name = "stockStatus",required = true)Integer stockStatus,
                                               @PathVariable(name = "deviceSerial",required = true)String deviceSerial){



        return stockService.queryStockBydeviceSerial(stockStatus,deviceSerial);

    }



}
