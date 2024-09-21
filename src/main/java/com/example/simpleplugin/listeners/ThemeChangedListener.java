package com.example.simpleplugin.listeners;

import com.intellij.openapi.editor.colors.EditorColorsListener;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.util.ui.UIUtil;
import javafx.application.Platform;
import javafx.scene.web.WebView;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.simpleplugin.constants.Constant.WEBVIEW_KEY;

public class ThemeChangedListener implements EditorColorsListener {

    private final Logger logger = LoggerFactory.getLogger(ThemeChangedListener.class);

    @Override
    public void globalSchemeChange(@Nullable EditorColorsScheme scheme) {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        for (Project project : projects) {
            WebView webView = project.getUserData(WEBVIEW_KEY);
            if (webView == null) {
                logger.error("WebView for {} is not exists!", project.getName());
            } else {
                Platform.runLater(() -> {
                    webView.getEngine().executeScript("changeTheme(" + UIUtil.isUnderDarcula() + ")");
                });
            }
        }
    }
}