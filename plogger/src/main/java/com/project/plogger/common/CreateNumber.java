package com.project.plogger.common;

public class CreateNumber {
    
    public String generateAuthNumber() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            sb.append((int)(Math.random() * 10));
        }

        return sb.toString();
    }

}
