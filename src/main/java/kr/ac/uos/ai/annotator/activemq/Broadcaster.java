package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.bean.protocol.Protocol;
import kr.ac.uos.ai.annotator.view.ConsolePanel;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-23 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class Broadcaster {

    private String consumerID;
    private String serverIP;
    private String topicName;
    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Topic topic;
    private MessageProducer producer;
    private ConsolePanel consolePanel;

    public Broadcaster(String topicName, String serverIP) {
        this.topicName = topicName;
        this.serverIP = serverIP;
    }

    public void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory("tcp://" + serverIP + ":61616");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            topic = session.createTopic(topicName);
            producer = session.createProducer(topic);
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
            consolePanel.printText("File Uploading...     " + fileName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageTest(String message){
        TextMessage txtMsg;
        try {
            txtMsg = session.createTextMessage();
            txtMsg.setText(message);
            txtMsg.setObjectProperty("msgType", "TEST");
            producer.send(txtMsg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public String getConsumerID() {
        return consumerID;
    }

    public void setConsumerID(String consumerID) {
        this.consumerID = consumerID;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public ConsolePanel getConsolePanel() {
        return consolePanel;
    }

    public void setConsolePanel(ConsolePanel consolePanel) {
        this.consolePanel = consolePanel;
    }
}
