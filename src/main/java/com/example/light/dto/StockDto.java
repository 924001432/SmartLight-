package com.example.light.dto;

import com.example.light.entity.Stock;

public class StockDto extends Stock {

    //构造方法
    public StockDto(Stock stock) {
        this.setStockId(stock.getStockId());
        this.setDeviceSerial(stock.getDeviceSerial());
        this.setDeviceType(stock.getDeviceType());
        this.setDeviceName(stock.getDeviceName());
        this.setDeviceModel(stock.getDeviceModel());
        this.setDeviceBrand(stock.getDeviceBrand());
        this.setStockBatch(stock.getStockBatch());
        this.setStockUser(stock.getStockUser());
        this.setDeviceProducetime(stock.getDeviceProducetime());
        this.setStockIntime(stock.getStockIntime());
        this.setStockOuttime(stock.getStockOuttime());
        this.setStockUpdatetime(stock.getStockUpdatetime());
        this.setStockStatus(stock.getStockStatus());
        this.setDeviceRepairnum(stock.getDeviceRepairnum());
        this.setStockOperator(stock.getStockOperator());
    }

    //操作用户名称
    private String stockOperatorName;

    //操作用户名称的getter方法，setter方法
    public String getStockOperatorName() {
        return stockOperatorName;
    }

    public void setStockOperatorName(String stockOperatorName) {
        this.stockOperatorName = stockOperatorName;
    }

}
