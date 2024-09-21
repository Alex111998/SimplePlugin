package com.example.simpleplugin.toolWindow;

import com.example.simpleplugin.service.MyProjectService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class MyToolWindowFactory implements ToolWindowFactory {
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        MyToolWindow myToolWindow = new MyToolWindow(project, toolWindow);
        Content content = ContentFactory.SERVICE.getInstance().createContent(myToolWindow.getContent(project), null, false);
        toolWindow.getContentManager().addContent(content);
    }
}
