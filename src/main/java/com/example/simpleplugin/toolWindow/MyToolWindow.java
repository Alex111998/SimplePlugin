package com.example.simpleplugin.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.net.URL;

public class MyToolWindow {

    private Project project;

    public MyToolWindow(Project project, ToolWindow toolWindow) {
        this.project = project;
    }

    public JComponent getContent(Project project) {
        JFXPanel jfxPanel = new JFXPanel();
        WebView webView = new WebView();
        URL url = this.getClass().getResource("/webview/chatbox.html");
        webView.getEngine().load(url.toExternalForm());

        // 设置命名空间并暴露对象开发页面
        webView.getEngine().setUserAgent("app");
        webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                webView.getEngine().executeScript("var app = { javaMethodToBeCalled: java.lang.invoke.MethodHandles.lookup().unreflect(com.example.simpleplugin.toolWindow.Test.javaMethodToBeCalled) }");
            }
        });

        Scene scene = new Scene(webView, Color.TRANSPARENT);
        jfxPanel.setScene(scene);
        return jfxPanel;
    }

}
