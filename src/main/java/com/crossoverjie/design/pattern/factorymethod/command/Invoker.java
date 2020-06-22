package com.crossoverjie.design.pattern.factorymethod.command;

/**
 * @Author: wangjikui
 * @Description:
 * @Date: 15:46 2018/7/17
 */
public class Invoker {

    private Command command;

    public Invoker(Command command) {
        super();
        this.command = command;
    }

    public void action() {
        command.execute();
    }
}
