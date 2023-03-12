package com.hero.herocat;

import com.hero.servlet.HeroRequest;
import com.hero.servlet.HeroResponse;
import com.hero.servlet.HeroServlet;

/**
 * HeroCat中对Servlet规范的默认实现
 */
public class DefaultHeroServlet extends HeroServlet {
    @Override
    public void doHeroGet(HeroRequest request, HeroResponse response) throws
            Exception {
        // http://localhost:8080/XXXservlet?param=wth
        String uri = request.getUri();
        String name = uri.substring(0, uri.indexOf("?"));
        response.write("404 - no this servlet : " + name,"1");
    }
    @Override
    public void doHeroPost(HeroRequest request, HeroResponse response) throws
            Exception {
        doHeroGet(request, response);
    }
}

