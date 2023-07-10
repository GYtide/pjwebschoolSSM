package com.gk.study.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gk.study.entity.VisitData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.gk.study.mapper.OpLogMapper;
import com.gk.study.mapper.ThingMapper;
import com.gk.study.mapper.UserMapper;
import com.gk.study.service.OverViewService;
import com.gk.study.entity.OpLog;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Service
public class OverViewServiceImpl implements OverViewService {

    @Autowired
    OpLogMapper opLogMapper;
    @Override
    public Map<String, Integer> getWebVisitData() {
      Map<String, Integer> result = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        try {
            Date end = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(end);
            calendar.add(Calendar.DAY_OF_MONTH, -7);
            Date start = calendar.getTime();

            QueryWrapper<OpLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.between("reTimeNew", dateFormat.format(start), dateFormat.format(end));

            List<OpLog> opLogs = opLogMapper.selectList(queryWrapper);

            Map<String, Integer> countMap = new HashMap<>();
            for (OpLog opLog : opLogs) {
                Date dateTime = dateFormat.parse(opLog.getReTime());
                calendar.setTime(dateTime);
                //将分钟、秒、毫秒字段清零，只保留年月日时字段
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                String key = dateFormat.format(calendar.getTime());
                countMap.put(key, countMap.getOrDefault(key, 0) + 1);
            }

            for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
                result.put(entry.getKey(), entry.getValue());
            }

        } catch (ParseException e) {
            // 记录错误信息
            System.err.println("Error while parsing date: " + e.getMessage());
        } catch (Exception e) {
            // 记录其他错误信息
            System.err.println("Error occurred: " + e.getMessage());
        }

        return result;
    }
}
