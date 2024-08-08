package com.yangge.example;

import picocli.CommandLine.Option;

import java.util.Arrays;
import java.util.concurrent.Callable;

public class Login implements Callable<Integer> {
    @Option(names = {"-u", "--user"}, description = "User name")
    String user;

    @Option(names = {"-p", "--password"}, arity = "0..1", description = "Passphrase", interactive = true, required = true)
    String password;

    @Option(names = {"-cp", "--checkPassword"}, arity = "0..1", description = "niubi", interactive = true, required = true)
    String checkPassword;

    public Integer call() throws Exception {
        System.out.println("password = " + password);
        System.out.println("checkPassword = " + checkPassword);
        return 0;
    }

    public static void main(String[] args) {
        String[] strings = CheckUtil.checkParam(new String[]{"-p", "-cp", "666"});
        System.out.println(Arrays.toString(strings));
        // new CommandLine(new Login()).execute(strings);
    }
}
