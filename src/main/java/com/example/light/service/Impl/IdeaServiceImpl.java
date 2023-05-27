package com.example.light.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.light.entity.Device;
import com.example.light.entity.Idea;
import com.example.light.mapper.DeviceMapper;
import com.example.light.mapper.IdeaMapper;
import com.example.light.service.DeviceService;
import com.example.light.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class IdeaServiceImpl extends ServiceImpl<IdeaMapper, Idea> implements IdeaService {

    @Autowired
    private IdeaMapper ideaMapper;

    @Override
    public Idea queryIdeaById(Integer id) {
        QueryWrapper<Idea> wrapper = new QueryWrapper<>();
        wrapper.eq("idea_id",id);
        return ideaMapper.selectOne(wrapper);
    }

    @Override
    public List<Idea> queryIdeaList(){
        //return this.list();

        return ideaMapper.selectList(null);
    }
}
