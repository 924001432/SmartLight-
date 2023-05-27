package com.example.light.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.example.light.entity.Idea;

import java.util.List;


public interface IdeaService extends IService<Idea> {

    public Idea queryIdeaById(Integer id);

    public List<Idea> queryIdeaList();

    //public void insertIdea(Idea idea);
}