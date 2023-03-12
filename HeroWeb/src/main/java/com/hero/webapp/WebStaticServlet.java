package com.hero.webapp;

import com.hero.servlet.HeroRequest;
import com.hero.servlet.HeroResponse;
import com.hero.servlet.HeroServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * WebStaticServlet
 * 可读取web下的静态资源
 */
public class WebStaticServlet extends HeroServlet {
    //@Override
    public void doHeroGet(HeroRequest request, HeroResponse response) throws Exception {
        // http://localhost:8080/webstaticservlet/geeker.html
        String uri = request.getUri();
        String name = uri.substring(uri.lastIndexOf("/") + 1, uri.length());
        response.write(name,"2");


        //FileInputStream fis = new FileInputStream("web/WEB-INF/web/"+name);
        //byte[] bytes= new byte[1024];
        //int len = 0 ;

        //向浏览器 回写数据
        /*OutputStream out = socket.getOutputStream();
        out.write("HTTP/1.1 200 OK\r\n".getBytes());
        out.write("Content-Type:text/html\r\n".getBytes());
        out.write("\r\n".getBytes());
        while((len = fis.read(bytes))!=-1){
            out.write(bytes,0,len);
        }*/
        //fis.close();
        //out.close();
    }
    //@Override
    public void doHeroPost(HeroRequest request, HeroResponse response) throws Exception {
        doHeroGet(request, response);
    }

    //@Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //aaa/bbb/twoservlet/xxx.html
        //servletName = uri.substring(uri.lastIndexOf("/",uri.lastIndexOf("/")-1)+1,uri.lastIndexOf("/"));

        // http://localhost:8080/webstaticservlet/geeker.html
        String uri = req.getRequestURI();
        String name = uri.substring(uri.lastIndexOf("/") + 1, uri.length());

        //重定向
        //方法一：
        //1.设置响应状态码 302
        //resp.setStatus(302);
        //2．设置响应义 Location
        //resp.setHeader( "Location", "/resp1.2");

        //方法二：设置重定向的简化方式（一个方法即可）    推荐！
        //req.getContextPath：获取虚拟目录(项目访问路径)
        resp.sendRedirect(req.getContextPath()+name);
    }

    //@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}

