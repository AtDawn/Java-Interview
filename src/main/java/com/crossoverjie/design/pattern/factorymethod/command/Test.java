package com.crossoverjie.design.pattern.factorymethod.command;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

/**
 * @Author: wangjikui
 * @Description:
 * @Date: 20:25 2018/7/17
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(DateUtils.addHours(new Date(),1));
    }
}
