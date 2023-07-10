package com.gk.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gk.study.service.OverViewService;
import com.sun.management.OperatingSystemMXBean;
import com.gk.study.common.APIResponse;
import com.gk.study.common.ResponeCode;
import com.gk.study.entity.Order;
import com.gk.study.entity.Thing;
import com.gk.study.entity.VisitData;
import com.gk.study.mapper.OrderMapper;
import com.gk.study.mapper.OverviewMapper;
import com.gk.study.mapper.ThingMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/overview")
public class OverViewController {

    @Autowired
    ThingMapper thingMapper;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OverviewMapper overviewMapper;

    @Autowired
    OverViewService overViewService;

    private final static Logger logger = LoggerFactory.getLogger(OverViewController.class);

    @RequestMapping(value = "/sysInfo", method = RequestMethod.GET)
    public APIResponse sysInfo() {

        Map<String, String> map = new HashMap<>();
        map.put("sysName", "后台系统");
        map.put("versionName", "1.0");
        map.put("processor", "2");
        map.put("sysLan", "En");
        map.put("sysZone", "东八区");

        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        map.put("pf", osmxb.getArch());
        map.put("osName", osmxb.getName());
        map.put("cpuCount", String.valueOf(osmxb.getAvailableProcessors()));

        DecimalFormat df = new DecimalFormat("#,##0.0");

        //获取CPU
        double cpuLoad = osmxb.getSystemCpuLoad();
        //获取内存
        double totalvirtualMemory = osmxb.getTotalPhysicalMemorySize();
        double freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
        double value = freePhysicalMemorySize / totalvirtualMemory;
        double percentMemoryLoad = ((1 - value) * 100);
        map.put("cpuLoad", df.format(cpuLoad * 100));
        map.put("memory", df.format(totalvirtualMemory / 1024 / 1024 / 1024));
        map.put("usedMemory", df.format((totalvirtualMemory - freePhysicalMemorySize) / 1024 / 1024 / 1024));
        map.put("percentMemory", df.format(percentMemoryLoad));

        map.put("jvmVersion", System.getProperty("java.version"));

        return new APIResponse(ResponeCode.SUCCESS, "查询成功", map);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public APIResponse count() {
        // 网站流量
        Map<String,Integer> map = overViewService.getWebVisitData();
        System.out.println(map);
        return new APIResponse(ResponeCode.SUCCESS, "查询成功", map);
    }


    public static List<String> getSevenDate() {

        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i < 7; i++) {

            Date date = DateUtils.addDays(new Date(), -i);
            String formatDate = sdf.format(date);
            dateList.add(formatDate);
        }
        Collections.reverse(dateList);
        return dateList;
    }

}
