package com.example.light.mapper;




import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.light.entity.Stock;
import com.example.light.entity.SysLogs;
import org.springframework.stereotype.Repository;

/**
 * 日志Mapper
 */
@Repository
public interface StockMapper extends BaseMapper<Stock> {
}