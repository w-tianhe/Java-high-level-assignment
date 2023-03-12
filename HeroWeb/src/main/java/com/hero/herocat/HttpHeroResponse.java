package com.hero.herocat;

import com.hero.servlet.HeroResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;
import io.netty.util.internal.StringUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * HeroCat中对Servlet规范的默认实现
 */
public class HttpHeroResponse implements HeroResponse {
    private HttpRequest request;
    private HttpServletResponse resp;
    private ChannelHandlerContext context;
    public HttpHeroResponse(HttpRequest request, ChannelHandlerContext
            context) {
        this.request = request;
        this.context = context;
    }
    @Override
    public void write(String content,String flag) throws Exception {
        // 处理content为空的情况
        if (StringUtil.isNullOrEmpty(content)) {
            return;
        }
        if (flag.equals("1")) {
            // 创建响应对象
            FullHttpResponse response = new
                    DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    // 根据响应体内容大小为response对象分配存储空间
                    Unpooled.wrappedBuffer(content.getBytes("UTF-8")));
            // 获取响应头
            HttpHeaders headers = response.headers();
            // 设置响应体类型
            headers.set(HttpHeaderNames.CONTENT_TYPE, "text/json");
            // 设置响应体长度
            headers.set(HttpHeaderNames.CONTENT_LENGTH,
                    response.content().readableBytes());
            // 设置缓存过期时间
            headers.set(HttpHeaderNames.EXPIRES, 0);
            // 若HTTP请求是长连接，则响应也使用长连接
            if (HttpUtil.isKeepAlive(request)) {
                headers.set(HttpHeaderNames.CONNECTION,
                        HttpHeaderValues.KEEP_ALIVE);
            }
            // 将响应写入到Channel
            context.writeAndFlush(response);
        } else if (flag.equals("2")) {
            String path = "web/WEB-INF/web/" + "geeker.html";
            RandomAccessFile file = new RandomAccessFile(new File(path), "r");
            FullHttpResponse resp = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
            context.writeAndFlush(resp);
            HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());
            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            //将HttpResponse    写到客户端
            context.write(response);
            //将index.html写到客户端
            if (context.pipeline().get(SslHandler.class) == null) {
                context.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
            } else {
                context.write(new ChunkedNioFile(file.getChannel()));
            }
            //写LastHttpContent并冲刷至客户端
            ChannelFuture future = context.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
            if(file != null) {
                try {
                    file.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

}

