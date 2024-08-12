package com.yangge.generator;

import com.yangge.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 动静结合
 */
public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        // 数据模型
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setLoop(false);

        doGenerate(mainTemplateConfig);
    }

    public static void doGenerate(Object model) throws TemplateException, IOException {
        // 1. 静态文件生成
        String projectPath = System.getProperty("user.dir");
        // 输入路径
        String inputPath = projectPath + File.separator + "yangge-generator-demo-projects" + File.separator + "acm-template";
        // 输出路径
        String outputPath = projectPath;
        // 复制
        StaticGenerator.copyFileByHutool(inputPath, outputPath);
        // 1. 复制静态文件
        StaticGenerator.copyFileByHutool(inputPath, outputPath);
        // 2. 复制动态文件
        // 指定模板文件所在的路径
        String dynamicInputPath = projectPath + File.separator + "yangge-generator-basic" + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        // String dynamicOutputPath = projectPath + File.separator + "acm-template/src/com/yangge/acm/MainTemplate.java";
        String dynamicOutputPath = outputPath + File.separator + "acm-template/src/com/yangge/acm/MainTemplate.java";


        DynamicGenerator.doGenerate(dynamicInputPath, dynamicOutputPath, model);
    }
}
