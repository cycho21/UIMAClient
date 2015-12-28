package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.bean.protocol.Protocol;
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

    public void sendMessage(byte[] msg, String fileName, Protocol protocol) {
        try {
            BytesMessage message = session.createBytesMessage();
            message.writeBytes(msg);
            message.setObjectProperty("msgType", protocol.getMsgType());
            message.setObjectProperty("developer", protocol.getJob().getDeveloper());
            message.setObjectProperty("jobName", protocol.getJob().getJobName());
            message.setObjectProperty("modifiedDate", protocol.getJob().getModifiedDate());
            message.setObjectProperty("version", protocol.getJob().getVersion());
            message.setObjectProperty("fileName", fileName);
            message.setObjectProperty("fileSize", protocol.getJob().getJobSize());
            producer.send(message);
            consolePanel.printText("File Upload to main...     " + fileName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String getJobList){
        TextMessage message;
        try {
            message = session.createTextMessage();
            message.setObjectProperty("msgType", getJobList);
            consolePanel.printText("Request Job List to server...     ");
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Protocol protocol) {
        TextMessage message;
        try {
            message = session.createTextMessage();
            message.setObjectProperty("msgType", protocol.getMsgType());
            message.setObjectProperty("jobName", protocol.getJob().getJobName());
            message.setObjectProperty("jobSize", protocol.getJob().getJobSize());
            message.setObjectProperty("version", protocol.getJob().getVersion());
            message.setObjectProperty("modifiedDate", protocol.getJob().getModifiedDate());
            message.setObjectProperty("developer", protocol.getJob().getDeveloper());
            message.setObjectProperty("fileName", protocol.getJob().getFileName());
            consolePanel.printText("Add job request to server...     ");
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage2(Protocol protocol) {
        TextMessage message;
        try {
            message = session.createTextMessage();
            message.setObjectProperty("msgType", protocol.getMsgType());
            message.setObjectProperty("jobName", protocol.getJob().getJobName());
            message.setObjectProperty("jobSize", protocol.getJob().getJobSize());
            message.setObjectProperty("version", protocol.getJob().getVersion());
            message.setObjectProperty("modifiedDate", protocol.getJob().getModifiedDate());
            message.setObjectProperty("developer", protocol.getJob().getDeveloper());
            message.setObjectProperty("fileName", protocol.getJob().getFileName());
            message.setObjectProperty("jobFileName", protocol.getJob().getFileName());
            consolePanel.printText("Request executing job to server...     ");
            producer.send(message);
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