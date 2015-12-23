package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.view.ConsolePanel;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Sender {
    private ActiveMQConnectionFactory factory;
    private Connection connection;
    private Session session;
    private Queue queue;
    private MessageProducer producer;
    private ConsolePanel consolePanel;
    private String serverIP;

    public Sender() {
    }

    public void createQueue(String queueName) {
        try {
            queue = session.createQueue(queueName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        set();
    }

    private void set() {
        try {
            producer = session.createProducer(queue);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        if (serverIP == null) {
        } else {
            factory = new ActiveMQConnectionFactory("tcp://" + serverIP + ":61616");
            try {
                connection = factory.createConnection();
                connection.start();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(byte[] msg, String fileName) {
        try {
            BytesMessage message = session.createBytesMessage();
            message.writeBytes(msg);
            message.setObjectProperty("msgType", "upload");
            message.setObjectProperty("FileName", fileName);
            producer.send(message);
            consolePanel.printTextAndNewLine("File Name : " + fileName);
            message.clearBody();
            message.clearProperties();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String simpleMsgType, String process) {
        TextMessage message;
        try {
            message = session.createTextMessage();
            message.setObjectProperty("msgType", simpleMsgType);
            message.setText(process);
            producer.send(message);
            message.clearBody();
            message.clearProperties();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void destory() {
        try {
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void setConsolePanel(ConsolePanel consolePanel) {
        this.consolePanel = consolePanel;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }
}