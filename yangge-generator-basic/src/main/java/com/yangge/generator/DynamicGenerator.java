package com.yangge.generator;

import com.yangge.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 代码生成器
 */
public class DynamicGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        // 指定模板文件所在的路径
        String projectPath = System.getProperty("user.dir") + File.separator + "yangge-generator-basic";
        String inputPath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        String outputPath = projectPath + File.separator + "MainTemplate.java";

        // 数据模型
        MainTemplateConfig mainTemplacteConfig = new MainTemplateConfig();
        mainTemplacteConfig.setLoop(true);

        doGenerate(inputPath, outputPath ,mainTemplacteConfig);
    }

    /**
     * 生成文件代码
     *
     * @param inputPath
     * @param outputPath
     * @param model
     * @throws IOException
     * @throws TemplateException
     */
    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        // 创建出Configuration对象，参数为FreeMarker版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);

        // 指定模板文件所在的路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);

        // 设置模块文件使用的字符集
        configuration.setDefaultEncoding("UTF-8");
        configuration.setNumberFormat("0.######");

        // 创建模板对象，加载指定模板
        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);

        FileWriter out = new FileWriter(outputPath);

        // 调用生成
        template.process(model, out);

        // 生成完文件后要关闭
        out.close();
    }
}
