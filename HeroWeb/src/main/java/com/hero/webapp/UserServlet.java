package com.hero.webapp;

import com.hero.servlet.HeroRequest;
import com.hero.servlet.HeroResponse;
import com.hero.servlet.HeroServlet;

/**
 * UserServlet
 */
public class UserServlet extends HeroServlet {
    @Override
    public void doHeroGet(HeroRequest request, HeroResponse response) throws Exception {
        // http://localhost:8080/userservlet?param=wth
        String uri = request.getUri();
        String name = uri.substring(0, uri.indexOf("?"));
        response.write("This servlet is : " + name,"1");
    }
    @Override
    public void doHeroPost(HeroRequest request, HeroResponse response) throws Exception {
        doHeroGet(request, response);
    }
}

