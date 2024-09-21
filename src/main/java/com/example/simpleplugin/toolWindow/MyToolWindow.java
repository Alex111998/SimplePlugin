package com.example.simpleplugin.toolWindow;

import com.example.simpleplugin.service.CallService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.util.ui.UIUtil;
import javafx.application.Platform;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import javax.swing.*;
import java.net.URL;
import java.util.TimerTask;

import static com.example.simpleplugin.constants.Constant.WEBVIEW_KEY;

public class MyToolWindow {
    private Project project;

    public MyToolWindow(Project project, ToolWindow toolWindow) {
        this.project = project;
    }

    public JComponent getContent(Project project) {
        JFXPanel jfxPanel = new JFXPanel();
        Platform.runLater(() -> {
            WebView webView = new WebView();
            URL url = this.getClass().getResource("/webview/chatbox.html");
            webView.getEngine().load(url.toExternalForm());
            webView.getEngine().setJavaScriptEnabled(true);
            // 设置命名空间并暴露对象开发页面
            webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
                Platform.runLater(() -> {
                    if (newValue == Worker.State.SUCCEEDED) {
                        boolean isDark = UIUtil.isUnderDarcula();
                        webView.getEngine().executeScript("changeTheme(" + isDark + ")");

                        // 使用定时任务将CallService放进window，不然会失效
                        java.util.Timer timer = new java.util.Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Platform.runLater(() -> {
                                    JSObject window = (JSObject) webView.getEngine().executeScript("window");
                                    window.setMember("call", new CallService());
                                });
                            }
                        }, 0, 5000); // 每 5 秒执行一次
                    }
                });

            });
            project.putUserData(WEBVIEW_KEY, webView);

            Scene scene = new Scene(webView, Color.TRANSPARENT);
            jfxPanel.setScene(scene);
        });
        return jfxPanel;
    }

}
