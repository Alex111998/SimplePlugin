package com.example.simpleplugin.service;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class CallService {

    public void open() {
        // 这里是被调用时要执行的 Java 代码逻辑
        try {
            Desktop.getDesktop().browse(new URI("https://www.baidu.com"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
