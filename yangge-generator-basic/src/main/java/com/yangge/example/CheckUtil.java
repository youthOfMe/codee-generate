package com.yangge.example;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import picocli.CommandLine;

import java.lang.reflect.Field;
import java.util.*;

public class CheckUtil {

    public static final ArrayList<String> argsList  = new ArrayList<>();

    public static String[] checkParam(String[] args) {


        if (args.length > 0 && (args[0].equals("--help") || args[0].equals("-h") || args[0].equals("-V"))) {
            return args;
        }

        Field[] fields = ReflectUtil.getFields(Login.class);
        List<CommandLine.Option> annotationList = new ArrayList<>();
        // 把所有命令填到hashSet中优化效率
        Set<String> orderSet = new HashSet<>();
        // 遍历所有字段获取到注解数组
        for (Field field : fields) {
            CommandLine.Option annotation = field.getAnnotation(CommandLine.Option.class);
            annotationList.add(annotation);
            orderSet.add(annotation.names()[0]);
        }

        for (int i = 0; i < annotationList.size(); i++) {
            CommandLine.Option option = annotationList.get(i);
            if (option.required()) {
                if (option.interactive()) {
                    // 遍历args数组看看是否有
                    boolean isHas = false;
                    int index = 0;
                    for (int j = 0; j < args.length; j++) {
                        if (args[j].equals(option.names()[0])) {
                            isHas = true;
                            index = j + 1;
                            break;
                        }
                    }
                    if (!isHas) {
                        argsList.add(option.names()[0]);
                    } else {
                        argsList.add(option.names()[0]);
                        // 判断一下args还有值吗
                        if (index < args.length) {
                            // 遍历一遍是不是命令值
                            boolean isOrder = orderSet.contains(args[index]);
                            if (!isOrder) {
                                argsList.add(args[index]);
                            }
                        }
                    }
                } else {
                    // 遍历args数组看看是否有
                    boolean isHas = false;
                    int index = 0;
                    for (int j = 0; j < args.length; j++) {
                        if (args[j].equals(option.names()[0])) {
                            isHas = true;
                            index = j + 1;
                            break;
                        }
                    }
                    if (isHas) {
                        argsList.add(option.names()[0]);
                        // 判断一下args还有值吗
                        if (args.length == index) {
                            throw new RuntimeException("必须要有" + option.names()[0]);
                        }
                        boolean isOrder = orderSet.contains(args[index]);
                        if (isOrder) {
                            throw new RuntimeException("必须要有" + option.names()[0]);
                        }
                        argsList.add(args[index]);
                    } else {
                        throw new RuntimeException("必须要有" + option.names()[0]);
                    }
                }
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
