package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gk.study.entity.Resou;
import com.gk.study.mapper.ResourceMapper;
import com.gk.study.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resou> implements ResourceService {
    @Autowired
    ResourceMapper mapper;

    @Override
    public List<Resou> getResourceList() {
        return mapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<Resou> getResourceListByTid(String tid) {
        QueryWrapper<Resou> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tid",tid);
        queryWrapper.orderBy(true, false, "create_time");
        return mapper.selectList(queryWrapper);
    }

    @Override
    public void createResource(Resou resource) {
        System.out.println(resource);
        resource.setCreateTime(String.valueOf(System.currentTimeMillis()));
        mapper.insert(resource);
    }

    @Override
    public void deleteResource(String id) {
        mapper.deleteById(id);
    }

    @Override
    public void updateResource(Resou Resource) {
        mapper.updateById(Resource);
    }
}
