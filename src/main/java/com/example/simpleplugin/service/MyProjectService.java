package com.example.simpleplugin.service;

import com.intellij.openapi.components.Service;

import java.util.Random;

import static java.lang.Math.random;

//@Service(Service.Level.PROJECT)
public class MyProjectService {

    public int getRandomNumber() {
        return new Random().nextInt(100) + 1;
    }
}
