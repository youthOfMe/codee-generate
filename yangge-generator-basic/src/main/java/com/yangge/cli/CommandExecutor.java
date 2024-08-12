package com.yangge.cli;

import com.yangge.cli.command.ConfigCommand;
import com.yangge.cli.command.GenerateCommand;
import com.yangge.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "yangge", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {

    private final CommandLine commandLine;

    {
        commandLine = new CommandLine(this)
                .addSubcommand(GenerateCommand.class)
                .addSubcommand(ListCommand.class)
                .addSubcommand(ConfigCommand.class);
    }

    @Override
    public void run() {
        // 不输入子命令时给出友好的提示
        System.out.println("请输入具体命令，或者输入 --help 查看命令提示");
    }


    public Integer doExecute(String[] args) {
        return commandLine.execute(args);
    }

}
