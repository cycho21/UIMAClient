package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;
import kr.ac.uos.ai.annotator.taskarchiver.TaskUnpacker;
import kr.ac.uos.ai.annotator.view.ConsolePanel;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver implements Runnable {

    private String queueName;
    private ActiveMQConnectionFactory factory;
    private Connection connection;
    private Session session;
    private Queue queue;
    private MessageConsumer consumer;
    private Message message;
    private TextMessage tMsg;
    private TaskUnpacker taskUnpacker;
    private RequestAnalyst requestAnalyst;
    private ConsolePanel consolePanel;
    private String serverIP;
    private TaskUnpacker unPacker;
    private Sender sdr;

    public Receiver() {
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    private void consume() {
        try {
            message = consumer.receive();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                consume();
                if (message.getObjectProperty("msgType").equals("uploadSeq")) {
                    TextMessage msg = (TextMessage) message;
                    if (msg != null) {
                        if (msg.getText().equals("completed")) {
                            consolePanel.printTextAndNewLine(" Completed");
                        }
                    }
                } else {
                    BytesMessage tMsg = (BytesMessage) message;
                    byte[] bytes = new byte[(int) tMsg.getBodyLength()];
                    tMsg.readBytes(bytes);

                    makeFile(bytes, tMsg);

                    for (int i = 0; i <= bytes.length; i++) {
                        if (i == bytes.length) {
                            sdr.sendMessage("uploadSeq", "completed");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Receiver Run Error");
            e.printStackTrace();
        }
    }

    public void init() {
        taskUnpacker = new TaskUnpacker();
        taskUnpacker.setSender(sdr);
        requestAnalyst = new RequestAnalyst();
        requestAnalyst.init();
        if (serverIP == null) {
        } else {
            factory = new ActiveMQConnectionFactory("tcp://" + serverIP + ":61616");
            try {
                connection = factory.createConnection();
                connection.start();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                queue = session.createQueue(queueName);
                consumer = session.createConsumer(queue);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }


    public void makeFile(byte[] bytes, BytesMessage tMsg) {
        try {
            taskUnpacker.makeFileFromByteArray(System.getProperty("user.dir") + "\\lib\\" + tMsg.getObjectProperty("FileName"), bytes);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public TextMessage gettMsg() {
        return tMsg;
    }

    public void settMsg(TextMessage tMsg) {
        this.tMsg = tMsg;
    }

    public void setConsolePanel(ConsolePanel consolePanel) {
        this.consolePanel = consolePanel;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public void setUnPacker(TaskUnpacker unPacker) {
        this.unPacker = unPacker;
    }

    public void destory() {
        try {
            consumer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void setRequestAnalyst(RequestAnalyst requestAnalyst) {
        this.requestAnalyst = requestAnalyst;
    }

    public void setSender(Sender sdr) {
        this.sdr = sdr;
    }
}