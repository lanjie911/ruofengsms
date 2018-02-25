package sms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQReceiver {
	
	private static final Logger logger = LoggerFactory.getLogger(MQReceiver.class);
	
	private ConnectionFactory connectionFactory = null;
	private Connection connection = null;
	private Session session = null;
	private MessageConsumer consumer = null;
	private Destination destination = null;

	public void init() {
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616"); // ActiveMQ默认使用的TCP连接端口是61616
		
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			connection.start();
			
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			/**
			 * 第一种方式：Queue
			 */
//			 destination = session.createQueue("send_sms_test");
			 
//			 consumer = session.createConsumer(destination);
			 
			//for(int i = 0; i< 10; i++){
				 /*TextMessage textMessage = (TextMessage) consumer.receive(5000);
				 if(textMessage != null){
	                 System.out.println("收到的消息:" + textMessage.getText());
	                 textMessage.acknowledge();
	             }else{
	            	 System.out.println("NO Message!");
	             }*/
			 //}
//			 for(int i = 0 ; i < 10; i++){
				 
				/* MessageConsumer a = session.createConsumer(destination); 
//					final int idx = i;
					a.setMessageListener(new MessageListener() {
						@Override
						public void onMessage(Message message) {
							System.out.println("====>>Consumer" + idx + message.toString());
							logger.info("====>>Consumer" + idx);
							logger.debug("===>>Message" + message.toString());
							
//							ProcessorSmsMqMessage.process((ActiveMQObjectMessage)message,"Consumer"+idx);
						}
					});*/
				 
				/* consumer = session.createConsumer(destination);
				 consumer.setMessageListener(new MessageListener(){
					@Override
					public void onMessage(Message message) {
						
						System.out.println("Message" + message.toString());
						
						if(null != message){
							try {
								message.acknowledge();		// 客户端收到回执，通知Borker清楚消息
							} catch (JMSException e) {
								e.printStackTrace();
							}
						} else {
							System.out.println("***********No Message***********");
						}
					}
				 });*/
//			 }
				 
//			consumer.close();
			 
//			 session.commit();
//			 session.close();
			 
             
			 
		/*	 consumer.setMessageListener(new MessageListener() {  
				    @Override  
				    public void onMessage(Message message) {  
				        try{  
				            ActiveMQMessage m = (ActiveMQMessage)message;  
				            System.out.println(m.toString()); 
				            System.out.println("=====>>>>Receiver " + m.getMessage());
				        }catch(Exception e){  
				            e.printStackTrace();  
				        }  
				    }  
				});  
				connection.start(); */
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		MQReceiver jms = new MQReceiver();
		//jms.init();
		jms.topicReceiver();
	}
	
	public void topicReceiver(){
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://127.0.0.1:61616"); // ActiveMQ默认使用的TCP连接端口是61616
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			connection.start();
			
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic("topic1");
			consumer = session.createConsumer(topic);
			 
			 /*TextMessage message = (TextMessage) consumer.receive();
			 if (null != message) {
					System.out.println("=====>>>>Receiver " + message.getText());
					session.commit();
				} else {
					System.out.println("=======没有记录=======");
				}
//			 message.acknowledge();
			 session.commit();*/
			 
			while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里谁定为500s
				TextMessage message = (TextMessage) consumer.receive();
				if (null != message) {
					System.out.println("=====>>>>MQReceiver1 " + message.getText());
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
