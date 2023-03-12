package com.hero.servlet;

import javax.servlet.http.HttpServlet;

/**
 * 定义Servlet规范
 */
public abstract class HeroServlet {
    //处理Http的get请求
    public abstract void doHeroGet(HeroRequest request, HeroResponse response)
            throws Exception;
    //处理Http的post请求
    public abstract void doHeroPost(HeroRequest request, HeroResponse response)
            throws Exception;
}
