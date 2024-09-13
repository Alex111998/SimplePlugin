package com.example.simpleplugin.listeners;

import com.intellij.openapi.application.ApplicationActivationListener;
import com.intellij.openapi.wm.IdeFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyApplicationActivationListener implements ApplicationActivationListener {

    private final Logger logger = LoggerFactory.getLogger(MyApplicationActivationListener.class);

    public void applicationActivated(IdeFrame ideFrame) {
        logger.warn("Don't forget to remove all non-needed sample code files with their " +
                "corresponding registration entries in `plugin.xml`.");
    }
}