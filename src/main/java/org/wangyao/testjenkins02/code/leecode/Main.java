package org.wangyao.testjenkins02.code.leecode;


import java.io.*;
import java.nio.Buffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

import java.util.HashMap;
class Main {
    public static void main(String[] args) {
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime now = LocalDateTime.now(zoneId);

        LocalDateTime tomorrowZero = LocalDate.now(zoneId).plusDays(1).atStartOfDay(zoneId).toLocalDateTime();

        Duration duration = Duration.between(now, tomorrowZero);

        System.out.printf(String.valueOf(duration.getSeconds()));

    }

    public static Integer towerNoToInt(String towerNo) {
        StringBuilder num = new StringBuilder();

        for (char c : towerNo.toCharArray()) {
            if (Character.isDigit(c)) {
                num.append(c);
            } else {
                break;
            }
        }

        return Integer.parseInt(num.toString());
    }
}