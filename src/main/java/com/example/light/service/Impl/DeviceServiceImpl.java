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
    public List<Device> deviceListByDeviceCoord(String deviceCoord) {

        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("device_coord",deviceCoord);

        return deviceMapper.selectList(wrapper);
    }

    @Override
    public void insertDevice(Device device){

        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        //应该改成同时确定网络标签和设备标签两个条件
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

    @Override
    public void updateDeviceHearttime(Device device){

    }

    @Override
    public List<Device> deviceListByIsOnline(Integer deviceCoord) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("device_coord",deviceCoord);
        wrapper.eq("device_status",1);
        return deviceMapper.selectList(wrapper);
    }

    @Override
    public void updateOnlineStatus(Integer deviceId ,int status) {
        deviceMapper.updateOnlineStatus(deviceId,status);

    }

    @Override
    public List<Device> deviceListByIsNotOnline(Integer deviceCoord) {
        QueryWrapper<Device> wrapper = new QueryWrapper<>();
        wrapper.eq("device_coord",deviceCoord);
        wrapper.eq("device_status",0);
        return deviceMapper.selectList(wrapper);
    }
}
