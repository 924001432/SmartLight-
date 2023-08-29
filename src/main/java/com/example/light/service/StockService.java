package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.light.entity.Stock;
import com.example.light.entity.SysLogs;

import java.util.List;


/**
 * 用户表的service接口
 */
public interface StockService extends IService<Stock> {
    /**
     * 根据用户名查询用户对象
     */

    public List<Stock> queryStockList();

    public Integer stockAdd(Stock stock);

    public Integer stockEdit(Stock stock);

    public Integer stockOut(Integer stockId);

    public Integer stockDelete(Integer stockId);

    public List<Stock> queryStockBystockStatus(Integer stockStatus);

    public List<Stock> queryStockBydeviceSerial(Integer stockStatus,String deviceSerial);

    public Stock queryStockBystockId(Integer stockId);


}
