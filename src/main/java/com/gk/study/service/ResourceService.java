package com.gk.study.service;

import com.gk.study.entity.Resou;

import java.util.List;
public interface ResourceService {
        List<Resou> getResourceList();
        List<Resou> getResourceListByTid(String Tid);
        void createResource(Resou Resource);
        void deleteResource(String id);
        void updateResource(Resou Resource);
}
