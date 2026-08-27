package org.wangyao.testjenkins02.code.thread;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 *
 * @author wangyao
 * @Date 2026/2/13 20:55
 *
 */
@Slf4j
public class day1  {

    private static final long EXPIRE_HOURS = 2;
    private static final long EXPIRE_MS = EXPIRE_HOURS * 10 * 60 * 1000;
    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMddHHmmss");

    public static void main(String[] args) throws ParseException {
        String dirName = "data_20260512103349";
        String timeStr = dirName.substring("date_".length());
        // 解析时间
        Date dirTime = SDF.parse(timeStr);
        long now = System.currentTimeMillis();
        long diff = now - dirTime.getTime();
        log.info("dirTime: {}, now: {}, diff: {}，EXPIRE_MS:{}", dirTime.getTime(), now, diff,  EXPIRE_MS);
    }
}
