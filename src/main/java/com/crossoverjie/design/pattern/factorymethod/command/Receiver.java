package com.crossoverjie.design.pattern.factorymethod.command;

/**
 * @Author: wangjikui
 * @Description:
 * @Date: 15:45 2018/7/17
 */
public class Receiver {
    public Receiver() {
        super();
    }

    public void action() {
        System.out.println("接收者接到命令，开始行动");
    }
}
