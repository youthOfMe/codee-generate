package com.yangge.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;

import java.io.*;

/**
 * 静态文件生成
 */
public class StaticGenerator {

    public static void main(String[] args) throws IOException {
        // 当前项目目录
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        // 输入路径
        String inputPath = projectPath + File.separator + "yangge-generator-demo-projects" + File.separator + "acm-template";
        // inputPath = projectPath + File.separator + "yangge-generator-demo-projects" + File.separator + "1.txt";
        // 输出路径
        String outputPath = projectPath;
        // copyFileByHutool(inputPath, outputPath);
        copyFile(inputPath, outputPath);
    }

    public static void copyFileByHutool(String inputPath, String outputPath) {
        FileUtil.copy(inputPath, outputPath, false);
    }

    public static void copyFile(String inputPath, String outputPath) throws IOException {
        String resourceName = new File(inputPath).getName();
        String parentPath = outputPath + File.separator + resourceName;
        File file = new File(parentPath);

        // 输入文件
        File inputFile = new File(inputPath);
        // 输出文件 -> 输出到根路径
        File outputFile = new File(parentPath);
        if (!file.exists() && inputFile.isDirectory()) {
            file.mkdirs();
        }
        copyFileByMe(inputFile, outputFile);
    }
    public static void copyFileByMe(File inputFile, File outputFile) throws IOException {
        // 处理输入/出文件不存在的情况
        if (!inputFile.exists()) {
            throw new IORuntimeException("输入文件不存在！");
        }

        if (inputFile.isDirectory()) { // 复制目录
            if (outputFile.exists() && !outputFile.isDirectory()) {
                // 处理目标不是一个目录的情况
                throw new IORuntimeException("inputPath为一个目录但是outputFile是一个文件");
            }
            // outputFile不能是inputFile的子目录
            if (FileUtil.isSub(inputFile, outputFile)) {
                throw new IORuntimeException("outputFile不能是inputFile的子文件");
            }


            File[] files = inputFile.listFiles();
            if (files == null || files.length == 0) {
                return;
            }

            if (!outputFile.exists()) {
                outputFile.mkdirs();
            }

            for (File file : files) {
                // 移动路径
                String movePath = outputFile.getPath() + File.separator + file.getName();
                if (file.isDirectory()) {
                    copyFileByMe(new File(file.getAbsolutePath()), new File(movePath));
                } else {
                    BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
                    BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(movePath));
                    byte[] bytes = new byte[1024];
                    int temp = 0;
                    while ((temp = in.read(bytes)) != -1) {
                        out.write(bytes, 0, temp);
                    }
                    out.close();
                    in.close();
                }
            }
        } else { // 复制文件
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(inputFile));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile));
            byte[] bytes = new byte[1024];
            int temp = 0;
            while ((temp = in.read(bytes)) != -1) {
                out.write(bytes, 0, temp);
            }
            out.close();
            in.close();
        }
    }
}
