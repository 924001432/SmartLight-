package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Device;
import com.example.light.mapper.DeviceMapper;
import com.example.light.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Override
    public Device queryDeviceById(Device device) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("device_id",device.getDeviceId());
        return deviceMapper.selectOne(wrapper);
    }

    @Override
    public List<Device> queryDeviceList(){
        //return this.list();

        return deviceMapper.selectList(null);
    }

    @Override
    public void insertDevice(Device device){

        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("device_serial",device.getDeviceSerial());

        if(deviceMapper.selectOne(wrapper)!=null){
            System.out.println("update over");
            deviceMapper.update(device,wrapper);
        }else {
            System.out.println("insert over");
            deviceMapper.insert(device);
        }
        /*
            不为空则更新，空则插入
         */
    }
}
