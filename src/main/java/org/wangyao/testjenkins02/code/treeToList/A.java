package org.wangyao.testjenkins02.code.treeToList;

import cn.hutool.core.lang.func.Func;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class A {

    public static final String PATTERNYMDHMS = "yyyy-MM-dd HH:mm:ss";


    public static void main(String[] args) throws Exception {
        String[] aa = new  String[2];
        setYesterdayDateRange2(aa);
        System.out.println(JSON.toJSONString(aa));
    }

    public static void setYesterdayDateRange2(String[] dates) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERNYMDHMS);
        // 昨天 00:00:00
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        dates[0] = sdf.format(cal.getTime());

        // 昨天 23:59:59
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        dates[1] = sdf.format(cal.getTime());
    }

    /**
     * 获取【前一天（昨天）00:00:00  ~ 昨天 23:59:59】
     * dates[0] = 昨天0点，dates[1] = 昨天23:59:59
     */
    public static void setYesterdayDateRange(String[] dates) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERNYMDHMS);
        // 昨天 00:00:00
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        dates[0] = sdf.format(cal.getTime());

        // 昨天 23:59:59
        cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        dates[1] = sdf.format(cal.getTime());
    }



    // 当天开始和结束时间
    public static void setDefaultDateRange(String[] dates) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERNYMDHMS);
        if (dates[0] == null || dates[0].isEmpty()) {
            // 默认开始时间：一个月前 00:00:00
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, -1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            dates[0] = sdf.format(calendar.getTime());
        } else {
            // 前端传了开始日期，自动加 00:00:00
            dates[0] = appendStartTime(dates[0]);
        }
        if (dates[1] == null || dates[1].isEmpty()) {
            // 默认结束时间：当天 23:59:59
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, -1);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            dates[1] = sdf.format(calendar.getTime());
        } else {
            // 前端传了结束日期，自动加 23:59:59
            dates[1] = appendEndTime(dates[1]);
        }
    }

    /**
     * 开始日期追加 00:00:00
     */
    private static String appendStartTime(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return dateStr;
        }
        if (dateStr.length() > 10) {
            return dateStr;
        }
        return dateStr + " 00:00:00";
    }

    /**
     * 结束日期追加 23:59:59
     */
    private static String appendEndTime(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return dateStr;
        }
        if (dateStr.length() > 10) {
            return dateStr;
        }
        return dateStr + " 23:59:59";
    }


}
