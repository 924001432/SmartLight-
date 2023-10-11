package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Stock;
import com.example.light.entity.SysLogs;
import com.example.light.entity.User;
import com.example.light.mapper.StockMapper;
import com.example.light.mapper.SyslogsMapper;
import com.example.light.service.StockService;
import com.example.light.service.SyslogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock> implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public List<Stock> queryStockList(){
        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.orderBy(true,false,"stock_intime");

        return stockMapper.selectList(wrapper);

    }

    @Override
    public Integer stockAdd(Stock stock){

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String update_time = sf.format(date);

        stock.setStockIntime(update_time);
        stock.setStockStatus(0);


        return stockMapper.insert(stock);
    }

    @Override
    public Integer stockEdit(Stock stock){

        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.eq("stock_id",stock.getStockId());

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String update_time = sf.format(date);

        stock.setStockUpdatetime(update_time);

        return stockMapper.update(stock,wrapper);
    }

    @Override
    public Integer stockOut(Integer stockId, Integer stockOperator){

        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.eq("stock_id",stockId);

        Stock stock = stockMapper.selectOne(wrapper);
        stock.setStockStatus(1);

        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String update_time = sf.format(date);

        stock.setStockOuttime(update_time);

        return stockMapper.update(stock,wrapper);

    }

    @Override
    public Integer stockDelete(Integer stockId){


        return stockMapper.deleteById(stockId);
    }

    @Override
    public List<Stock> queryStockBystockStatus(Integer stockStatus){
        //未出库的按照出库时间来查询
        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.eq("stock_status",stockStatus).orderBy(true,true,"stock_intime");



        return stockMapper.selectList(wrapper);
    }

    @Override
    public List<Stock> queryStockBydeviceSerial(Integer stockStatus,String deviceSerial){
        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.eq("stock_status",stockStatus)
                .eq("device_serial",deviceSerial)
                .orderBy(true,true,"stock_intime");

        return stockMapper.selectList(wrapper);
    }

    @Override
    public Stock queryStockBystockId(Integer stockId){
        QueryWrapper<Stock> wrapper = new QueryWrapper<>();
        wrapper.eq("stock_id",stockId);

        return stockMapper.selectOne(wrapper);
    }




}