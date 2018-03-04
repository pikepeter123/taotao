package com.taotao.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * Created by 31364 on 2018/3/4.
 * description: 测试消息队列
 */
public class TestActiveMq {

    @Test
    public void testQueueProducer() throws Exception {
//        创建一个连接工厂，指定服务的ip和端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
//        使用工厂对象创建connection对象
        Connection connection = connectionFactory.createConnection();
//        调用start方法开启连接
        connection.start();
//        通过connection对象创建一个session对象
//        第一个参数是是否开启事务，只有第一个参数是false时，第二个参数才有意义
//        第二个参数时应答机制，手动应答和自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        使用session创建一个destination对象,目的地有两种，queue和topic
        Queue queue = session.createQueue("testQueue");
//        使用session对象创建生产者producer
        MessageProducer producer = session.createProducer(queue);
//        使用producer发送消息
        TextMessage message = new ActiveMQTextMessage();
        message.setText("测试一下发送消息到queue");
        producer.send(message);
//        关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testQueueConsumer() throws Exception {
//        创建一个连接工厂对象
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
//        创建一个连接
        Connection connection = connectionFactory.createConnection();
//        开启连接
        connection.start();
//        使用连接获取session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        创建一个destination对象
        Queue queue = session.createQueue("testQueue");
//        创建一个消费者
        MessageConsumer consumer = session.createConsumer(queue);
//        接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
//                打印消息
                TextMessage textMessage = (TextMessage) message;
                try {
                    String text = textMessage.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
//        关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicProducer() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("testTopic");
        MessageProducer producer = session.createProducer(topic);
        ActiveMQTextMessage message = new ActiveMQTextMessage();
        message.setText("测试一下topic2");
        producer.send(message);
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumer() throws Exception {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic("testTopic");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                String text = null;
                try {
                    text = textMessage.getText();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                System.out.println(text);
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();
    }
}
