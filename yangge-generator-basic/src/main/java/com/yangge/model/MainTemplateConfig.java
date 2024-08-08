package com.yangge.model;

import lombok.Data;

/**
 * 静态模板配置
 */
@Data
public class MainTemplateConfig {

    /**
     * 作者（字符串，填充值）
     */
    private String author = "洋哥";

    /**
     * 输出消息
     */
    private String outputText = "yzy";

    /**
     * 是否循环
     */
    private boolean loop = false;
}
