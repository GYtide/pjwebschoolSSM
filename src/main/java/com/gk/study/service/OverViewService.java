package com.gk.study.service;

import com.gk.study.entity.OpLog;
import  com.gk.study.entity.Thing;
import com.gk.study.entity.VisitData;
import org.apache.ibatis.annotations.Param;
import com.gk.study.entity.VisitData;

import java.util.List;
import java.util.Map;

public interface OverViewService {
    Map<String, Integer> getWebVisitData();
}
