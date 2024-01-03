package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.light.entity.Device;

import java.util.List;


public interface DeviceService extends IService<Device> {

    public Device queryDeviceById(Device device);

    public List<Device> queryDeviceList();

    public List<Device> deviceListByDeviceCoord(String deviceCoord);

    public void insertDevice(Device device);

    public void updateDeviceHearttime(Device device);

    public List<Device> deviceListByIsOnline(String deviceCoord);

    public void updateOnlineStatus(Integer deviceId ,int status);

    public List<Device> deviceListByIsNotOnline(String deviceCoord);

    Device queryDeviceBySerial(String deviceSerial);
}
