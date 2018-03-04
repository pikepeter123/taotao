package com.taotao.activemq;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * Created by 31364 on 2018/3/4.
 * description:
 */
public class TestSpringActiveMq {

    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
    }

    @Test
    public void testQueueProducer() throws Exception {
        JmsTemplate template = applicationContext.getBean(JmsTemplate.class);
        final Queue queue = applicationContext.getBean(Queue.class);
        template.send(queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage("使用spring和activeMq整合发送消息2");
                return message;
            }
        });
    }

}
