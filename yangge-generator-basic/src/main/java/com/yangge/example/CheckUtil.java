package com.yangge.example;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import picocli.CommandLine;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CheckUtil {

    public static final ArrayList<String> argsList  = new ArrayList<>();

    public static String[] checkParam(String[] args) {


        if (args.length > 0 && (args[0].equals("--help") || args[0].equals("-h") || args[0].equals("-V"))) {
            return args;
        }

        Field[] fields = ReflectUtil.getFields(Login.class);
        List<CommandLine.Option> annotationList = new ArrayList<>();
        int argIndex = 1;
        for (Field field : fields) {
            CommandLine.Option annotation = field.getAnnotation(CommandLine.Option.class);
            annotationList.add(annotation);
        }
        for (int i = 0; i < fields.length; i++) {
            // 获取到当前filed的注解
            CommandLine.Option annotation = annotationList.get(i);
            // 获取到注解的属性
            boolean required = annotation.required();
            boolean interactived = annotation.interactive();

            // 处理非交互式
            if (!interactived && required) {
                // 处理其中没有它
                for (int j = 0; j < args.length; j++) {
                    if (args[j].equals(annotation.names()[0])) {
                        break;
                    }
                    if (j == argIndex - 1) {
                        throw new RuntimeException("必须传入-u和参数");
                    }
                }
                // 处理其中有它
                for (CommandLine.Option option : annotationList) {
                    if (argIndex > args.length - 1) {
                        throw new RuntimeException("必须传入-u和参数");
                    }
                    if (args[argIndex].equals(option.names()[0])) {
                        throw new RuntimeException("必须传入-u和参数");
                    }
                }

                argsList.add(annotation.names()[0]);
                argsList.add(args[argIndex]);
            }
            // 处理交互式
            if (required && interactived) {
                boolean isHave = checkArgs(annotation.names()[0], args);
                if (!isHave) {
                    argsList.add(annotation.names()[0]);
                } else {
                    // 处理argIndex越界问题
                    if (argIndex > args.length - 1 || argIndex > fields.length - 1) {
                        continue;
                    }
                    // 处理一单一双问题
                    if (i == fields.length - 1 && !args[argIndex].equals(annotation.names()[0])) {
                        argsList.add(args[argIndex]);
                        continue;
                    }
                    // 处理一单一单的情况
                    if (args[argIndex].equals(annotationList.get(i+1).names()[0])) {
                        continue;
                    }
                    argsList.add(args[argIndex]);

                }
            }

            // 初始值问题 -> 将argIndex设置为每次取到的field的下一个
            if (argIndex > args.length - 1 || argIndex > fields.length - 1) {
                continue;
            }
            if (annotationList.get(i + 1).names()[0].equals(args[argIndex + 1])) {
                argIndex += 2;
            } else {
                argIndex += 1;
            }
        }
        return ArrayUtil.toArray(argsList, String.class);
    }

    public static boolean checkArgs(String order, String[] args) {
        for (String arg : args) {
            if (arg.equals(order)) {
                argsList.add(order);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        checkParam(args);
    }
}
