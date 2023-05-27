package com.example.light.service;

/**
 *  @title 新闻发布
 *  @Desc
 *  @author <a href="mailto:avaos.wei@gmail.com">avaos.wei</a>
 *  @Date 2020-03-27 16:52
 *
 */
public interface NewsProducerService {

    /**
     * @title 发布消息
     * @desc  描述
     * @param msg: 待发布的内容
     * @return
     * @author <a href="mailto:avaos.wei@gmail.com">avaos.wei</a>
     * @date 2020-03-27 16:52
     */
    void publish(String msg);

    public void testPublish(byte[] payload);

    void publishBytes(byte[] payload);

}
