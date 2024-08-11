package com.yangge.cli.pattern;

/**
 * 开启设备的命令
 */
public class TurnOnCommand implements Command {
    private Device device;

    public TurnOnCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.turnOn();
    }
}
