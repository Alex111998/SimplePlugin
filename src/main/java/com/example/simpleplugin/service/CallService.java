package com.example.simpleplugin.service;

import com.intellij.openapi.project.ProjectManager;
import javafx.application.Platform;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static com.example.simpleplugin.constants.Constant.WEBVIEW_KEY;

public class CallService {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd_HHmmssSSS");

    public void open() {
        // 这里是被调用时要执行的 Java 代码逻辑
        try {
            Desktop.getDesktop().browse(new URI("https://www.baidu.com"));
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void request() {
        WebView webView = ProjectManager.getInstance().getOpenProjects()[0].getUserData(WEBVIEW_KEY);
        Platform.runLater(() -> {
            webView.getEngine().executeScript("createQuestion('" + getQuestion() + "');");
            try {
                Thread.sleep(1000L * 2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            webView.getEngine().executeScript("createAnswer('" + getAnswer() + "');");
        });
    }

    private String getQuestion() {
        return String.format("Question: %s", sdf.format(new Date()));
    }

    private String getAnswer() {
        String answer = String.format(JAVA_CODE, sdf.format(new Date()));
        return Base64.getEncoder().encodeToString(answer.getBytes());
    }

    private static final String JAVA_CODE = "public class Test_%s { " +
            "/**\n" +
            "     * Human\n" +
            "     */\n" +
            "    public static final String NAME = \"Human\";\n" +
            "public static void main(String[] args) {\n" +
            "        System.out.println(String.format(\"Name: xxx\", NAME));\n" +
            "    }" +
            "}";
}
