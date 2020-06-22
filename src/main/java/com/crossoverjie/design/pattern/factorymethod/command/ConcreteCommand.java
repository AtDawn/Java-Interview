package com.crossoverjie.design.pattern.factorymethod.command;

/**
 * @Author: wangjikui
 * @Description:
 * @Date: 15:45 2018/7/17
 */
public class ConcreteCommand implements Command {

    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        super();
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        // TODO Auto-generated method stub
        receiver.action();
    }
}

