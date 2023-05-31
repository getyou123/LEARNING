package org.example;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;

public class DateLearn {
    /**
     * 获取某一天的变化的天数的日期
     *
     * @param dateStr    移动日期的起点
     * @param dayStep    移动几天 exam: -1 表示前一天 20200509 -1 输出为20200508
     * @param formatType
     * @return 指定日期类型的移动后的日期
     */
    public static String addDays(String dateStr, int dayStep, String formatType) {
        SimpleDateFormat stringDateFormat = new SimpleDateFormat(formatType);
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = stringDateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayStep);
        date = calendar.getTime();
        return stringDateFormat.format(date);
    }


    public static void main(String[] args) throws ParseException {
        // date的两个构造函数
        {
            Date date = new Date();
            System.out.println("当前时间戳：" + date.getTime());
            System.out.println("当前时间：" + date);

            // 基于指定的时间戳
            Date date1 = new Date(1677571316596L);
            System.out.println("时间戳构造的时间：" + date1);

            // toString 默认的格式 dow mon dd hh:mm:ss zzz yyyy
        }

        //SimpleDateFormat 实现日期格式和字符串的转化
        {
            //date->string
            Date date = new Date();//最新的时间
            SimpleDateFormat stringToDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//获取Format对象
            System.out.println(stringToDateFormat.format(date));

            //string->data
            try {
                Date date1 = stringToDateFormat.parse("2018-12-22 15:34:32");//提取然后转化成需要的Date类型
                System.out.println(date1.toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // 非单纯日期
            Date d = new Date();
            SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 SSS毫秒  E Z");
            //把Date日期转成字符串，按照指定的格式转
            System.out.print("特殊的日期格式：");
            String str = sf.format(d);
            System.out.println(str);

            String str1 = "2022年06月06日 16时03分14秒 545毫秒  星期四 +0800";
            Date d1 = sf.parse(str1);
            System.out.print("特殊的日期格式：");
            System.out.println(d1);

        }

        //日期增加或者移动
        {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String resDate = addDays(simpleDateFormat.format(date), -1, "yyyyMMdd");//昨天的日期
            System.out.println(resDate);
        }

        //获取时间戳
        {
            Long timeStamp = System.currentTimeMillis();//获取当前的时间戳
            System.out.println("毫秒时间戳：" + timeStamp);// 毫秒时间戳

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(Long.parseLong(String.valueOf(timeStamp)));//时间戳转化成Date
            System.out.println("date对象对应的时间戳：" + date.getTime());
            String sd = sdf.format(date);
            System.out.println(sd);
        }

        //Calendar作为新的获取日期的工具类
        {
            // 获取Calendar对象
            Calendar calendar = Calendar.getInstance();
            System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
            System.out.println(calendar.get(Calendar.DAY_OF_YEAR));

            // 后移动
            calendar.add(Calendar.DATE, 1);
            System.out.println(calendar.get(Calendar.DAY_OF_YEAR));

            // 前移动
            calendar.add(Calendar.DATE, -10);
            System.out.println(calendar.get(Calendar.DAY_OF_YEAR));

            // 转为date
            System.out.println(calendar.getTime());

            // 重置为某个date
            calendar.setTime(new Date());
            System.out.println(calendar.getTime());

        }

        //jdk8以及之后的api
        {
            System.out.println("jdk8以及之后的api:");
            LocalDate localDate = LocalDate.now();
            LocalTime localTime = LocalTime.now();
            LocalDateTime localDateTime = LocalDateTime.now();

            System.out.println(localDate);
            System.out.println(localTime);
            System.out.println(localDateTime);

            // 获取指定的日期LocalDate
            LocalDate localDate1 = LocalDate.of(2022, 12, 1);
            System.out.println(localDate1);

            // 获取指定的时间
            LocalTime localTime1 = LocalTime.of(23, 13);
            System.out.println(localTime1);

            // 获取指定的datetime
            LocalDateTime localDateTime1 = LocalDateTime.of(2022, 12, 31, 11, 30, 00);
            System.out.println(localDateTime1);

            // 日期前移，日期后移
            System.out.println(localDate1.plusDays(2));
            System.out.println(localDate1.minusDays(2));

        }

        {
            System.out.println("jdk1.8之后的时间戳");
            Instant instant = Instant.now();
            System.out.println(instant);

            // 时区转化
            OffsetDateTime atOffset = instant.atOffset(ZoneOffset.ofHours(8));
            System.out.println(atOffset);

            // 时间戳->instant
            Instant instant1 = Instant.ofEpochMilli(1677575957929L);
            System.out.println(instant1);

            // instant->时间戳
            long l = atOffset.toInstant().toEpochMilli();
            System.out.println(l);
            System.out.println(instant.toEpochMilli());
        }

        // 字符串格式转化
        {
            System.out.println("字符串格式化");
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:MM:ss");
            String string = dateTimeFormatter.format(LocalDateTime.now());
            System.out.println(string);

            LocalDate parsed = LocalDate.parse(string, dateTimeFormatter);
            System.out.println(parsed);

        }

    }


    @Test
    public void test0001() throws ParseException {
        LocalDate startDate = LocalDate.of(2023, 5, 15);
        System.out.println(startDate);

        // 目前欠账总数
        BigDecimal currentOwn = new BigDecimal("521475.46");

        // 执行利率
        BigDecimal rate = new BigDecimal("0.05138");

        // 需要对齐为 521475.46 还了 976,443.38 86075308050.17653


        for (int i = 0; i <= 330; i++) { // 还需要还钱330期
            LocalDate newDate = startDate.plusMonths(i);
            System.out.println(i + 30 + "\t" + newDate + "\t" + currentOwn + "");
        }

    }

    public static void test0002() {

    }
}
