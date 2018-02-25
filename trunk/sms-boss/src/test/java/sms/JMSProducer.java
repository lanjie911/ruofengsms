package sms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProducer {
	// 默认连接用户名
	private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
	// 默认连接密码
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
	// 默认的连接地址
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	// 发送的消息数量
	private static final int SENNUM = 10;

	public static void main(String[] args) {
//		recevier();
		productor();
	}
	
	public static void productor(){
		ConnectionFactory factory; 			// 连接工厂
		Connection connection = null; 		// 连接
		Session session; 					// 会话，接收或者发送消息的线程
		Destination destination; 			// 消息的目的地
		MessageProducer messageProducer; 	// 消息生产者
		// 实例化连接工厂
		factory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
		// 通过连接工厂获取connection
		try {
			connection = factory.createConnection();
			connection.start(); // 启动连接
			// 创建session
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			// 创建消息队列
			// destination = session.createQueue("FirstQueue");

			// 创建主题
			destination = session.createTopic("topic1");
			// 创建消息发布者
			messageProducer = session.createProducer(destination);
			// 发送消息
			sendMessage(session, messageProducer);
			session.commit();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	

	/**
	 * 发送消息
	 * 
	 * @param session
	 * @param mp
	 * @throws JMSException
	 */
	public static void sendMessage(Session session, MessageProducer mp) throws JMSException {
		for (int i = 0; i < 1; i++) {
			TextMessage message = session.createTextMessage("ActiveMq 发布的消息" + i);
			System.out.println("发布消息：" + "ActiveMq 发布的消息" + i);
			mp.send(message);
		}
	}
	
	public static void recevier(){
		ConnectionFactory factory; 			// 连接工厂
		Connection connection = null; 		// 连接
		Session session; 					// 会话，接收或者发送消息的线程
		Destination destination; 			// 消息的目的地
		// 实例化连接工厂
		factory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKEURL);
		try {
			
			connection = factory.createConnection();
			connection.start(); // 启动连接
			// 创建session
			session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			destination = session.createTopic("topic1");
			MessageConsumer consumer = session.createConsumer(destination);

			consumer.setMessageListener(new MessageListener(){

				@Override
				public void onMessage(Message message) {
					
					try {
						System.out.println("=======>>>>>Message:"+ message.toString());
						message.acknowledge();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			});
			
			
			/*TextMessage message = (TextMessage) consumer.receive();
			 if (null != message) {
				System.out.println("=====>>>>Receiver " + message.getText());
				session.commit();
			} else {
				System.out.println("=======没有记录=======");
			}*/
			
			consumer.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
