package com.example.simpleplugin.toolWindow;

import com.example.simpleplugin.service.MyProjectService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBPanel;
import com.intellij.ui.content.Content;
import com.intellij.util.ui.components.JBComponent;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyToolWindow {

    private Project project;

    public MyToolWindow(Project project, ToolWindow toolWindow) {
        this.project = project;
    }

    public JComponent getContent(Project project) {
        JBPanel panel = new JBPanel();
        JBLabel label = new JBLabel("randomLabel");
        panel.add(label);
        JButton button = new JButton("shuffle");
        panel.add(button);
        final MyProjectService service = project.getService(MyProjectService.class);
        button.addActionListener(e -> label.setText(service.getRandomNumber() + ""));
        return panel;
    }

}
