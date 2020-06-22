package com.crossoverjie.design.pattern.factorymethod.command;

/**
 * @Author: wangjikui
 * @Description:
 * @Date: 15:46 2018/7/17
 */
public class Client {

    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);
        Invoker invoker = new Invoker(command);
        invoker.action();
    }
}
