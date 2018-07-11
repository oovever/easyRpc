package com.OovEver.netty.client;

import org.junit.Test;

/**
 * @author OovEver
 * 2018/7/9 23:02
 */
public class TcpClientTest {
    @Test
    public void testGetResponse() {
        ClientRequest request = new ClientRequest();
        request.setContent("测试TCP长连接请求");
        Response response = TcpClient.send(request);
        System.out.println(response.getResult());
    }

}