package com.hero;

import com.hero.herocat.CustomServer;

public class HeroCat {
    public static void main(String[] args) throws Exception {
        // 动态servlet：  http://localhost:8080/userservlet?param=wth
        // 静态态servlet：  http://localhost:8080/webstaticservlet/geeker.html
        CustomServer server = new CustomServer("com.hero.webapp");
        server.start();
    }
}
