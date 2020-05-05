package com.springboot.vhrend.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


import java.util.Properties;

@Component
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha=new DefaultKaptcha();
        Properties properties=new Properties();
//        图片边框
        properties.put(Constants.KAPTCHA_BORDER,"no");
//        图片宽高
        properties.put(Constants.KAPTCHA_IMAGE_WIDTH,"100");
        properties.put(Constants.KAPTCHA_IMAGE_HEIGHT,"40");
        properties.put(Constants.KAPTCHA_SESSION_KEY,"code");
//        字体样式
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE,"30");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR,"blue");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES,"宋体,黑体,楷体");
//        字符数量间距
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH,"4");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE,"6");
//        噪音颜色实现
        properties.put(Constants.KAPTCHA_NOISE_COLOR,"35,37,38");
        properties.put(Constants.KAPTCHA_NOISE_IMPL,"com.google.code.kaptcha.impl.DefaultNoise");
//        写入设置
        Config config=new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
