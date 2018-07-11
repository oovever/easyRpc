package com.OovEver.netty.client;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author OovEver
 * 2018/7/9 22:31
 */
public class DefaultFuture {
    public static ConcurrentHashMap<Long, DefaultFuture> allDefaultFuture = new ConcurrentHashMap<Long, DefaultFuture>();
    final Lock lock = new ReentrantLock();
    public Condition condition=lock.newCondition();



    private Response response;


    public DefaultFuture(ClientRequest request) {
        allDefaultFuture.put(request.getId(), this);
    }

    /**
     * 主线程获取数据
     * 1.等待结果
     * @return 获取响应结果
     */
    public Response get() {
        lock.lock();
        try {
//            是否有响应结果
            while (!done()) {
                condition.await();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return this.response;
    }
    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    /**
     * 接受Response数据
     * @param response response数据
     */
    public static void recive(Response response) {
        DefaultFuture df = allDefaultFuture.get(response.getId());
        if (df != null) {
            Lock lock = df.lock;
            lock.lock();
            try {
                df.setResponse(response);
                df.condition.signal();
                allDefaultFuture.remove(df);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }
    }
    /**
     *
     * @return 返回是否有响应结果
     */
    private boolean done() {
        if (this.response != null) {
            return true;
        }
        return false;
    }
}
