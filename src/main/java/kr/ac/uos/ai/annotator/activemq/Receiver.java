package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.AnnotatorRunningInfo;
import kr.ac.uos.ai.annotator.bean.JobList;
import kr.ac.uos.ai.annotator.bean.protocol.AnnotatorInfo;
import kr.ac.uos.ai.annotator.bean.protocol.Job;
import kr.ac.uos.ai.annotator.controller.EventAnalyst;
import kr.ac.uos.ai.annotator.taskarchiver.TaskUnpacker;
import kr.ac.uos.ai.annotator.view.ConsolePanel;
import kr.ac.uos.ai.annotator.view.JobListTree;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.HashMap;

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
    private ConsolePanel consolePanel;
    private String serverIP;
    private TaskUnpacker unPacker;
    private Sender sdr;
    private JobListTree tree;
    private EventAnalyst eventAnalyst;

    public Receiver() {
        AnnotatorRunningInfo.getInstance();
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
                        System.out.println(message);
                        if (msg.getObjectProperty("text").equals("completed")) {
                            if (msg.getObjectProperty("ip") != null) {
                                consolePanel.printTextAndNewLine("     ...Upload Annotator to main ");
                            } else {
                                consolePanel.printTextAndNewLine("     ...Completed");
                            }
                        }
                    }
                }

                if (message.getObjectProperty("msgType").equals("getJobList")){
                    if(AnnotatorRunningInfo.getTempAnnotatorList().containsKey(message.getObjectProperty("msgTxt"))) {
                    } else {
                        AnnotatorRunningInfo.getTempAnnotatorList().put(message.getObjectProperty("msgTxt").toString(), "true");
                    }
                }

                if (message.getObjectProperty("msgType").equals("anno")) {
                    consolePanel.printTextAndNewLine("     ..." + message.getObjectProperty("ip") + "'s Annotator is Starting...");
                }

                if (message.getObjectProperty("msgType").equals("getAnnotatorListCallBack")) {
                    consolePanel.printTextAndNewLine("     ... " + "Annotator Name : " + message.getObjectProperty("annotatorName").toString());
                    setAnnotatorRunningInfo(message);
                }

                if (message.getObjectProperty("msgType").equals("callBack")) {
                    consolePanel.printTextAndNewLine(message.getObjectProperty("text").toString());
                }

                if (message.getObjectProperty("msgType").equals("requestJob")) {
                    consolePanel.printTextAndNewLine("     ...Executed");
                }

                if (message.getObjectProperty("msgType").equals("getJobs")) {
                    if (message.getObjectProperty("type").equals("do")) {
                        Job tempJob = new Job();
                        tempJob.setJobSize(message.getObjectProperty("jobSize").toString());
                        tempJob.setDeveloper(message.getObjectProperty("developer").toString());
                        tempJob.setVersion(message.getObjectProperty("version").toString());
                        tempJob.setModifiedDate(message.getObjectProperty("modifiedDate").toString());
                        tempJob.setJobName(message.getObjectProperty("jobName").toString());
                        tempJob.setFileName(message.getObjectProperty("fileName").toString());
                        JobList.getJobList().put(String.valueOf(JobList.getJobList().size()), tempJob);
                    }

                    if (message.getObjectProperty("type").equals("end")) {
                        consolePanel.printTextAndNewLine("     ...Completed");
                        tree.repaintTree();
                    }
                }

                if (message.getObjectProperty("msgType").equals("jobList")) {
                    MapMessage mapMsg = (MapMessage) message;
                    HashMap<String, Job> jobMap = (HashMap) mapMsg.getObject("jobMap");
                    JobList.setJobList(jobMap);
                    tree.repaintTree();
                }
            }
        } catch (Exception e) {
            System.out.println("Receiver Run Error");
            e.printStackTrace();
        }
    }

    private void setAnnotatorRunningInfo(Message localMessage) {
        AnnotatorInfo annotatorInfo = new AnnotatorInfo();
        try {
            if (AnnotatorRunningInfo.getAnnotatorList().containsKey(localMessage.getObjectProperty("annotatorName").toString())) {
            } else {
                annotatorInfo.setName(localMessage.getObjectProperty("annotatorName").toString());
                annotatorInfo.setAuthor(localMessage.getObjectProperty("author").toString());
                annotatorInfo.setModifiedDate(localMessage.getObjectProperty("modifiedDate").toString());
                annotatorInfo.setFileName(localMessage.getObjectProperty("fileName").toString());
                annotatorInfo.setVersion(localMessage.getObjectProperty("version").toString());

                AnnotatorRunningInfo.getAnnotatorList().put(localMessage.getObjectProperty("annotatorName").toString(), annotatorInfo);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        taskUnpacker = new TaskUnpacker();
        taskUnpacker.setSender(sdr);
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

    public void setSender(Sender sdr) {
        this.sdr = sdr;
    }

    public void setEventAnalyst(EventAnalyst eventAnalyst) {
        this.eventAnalyst = eventAnalyst;
    }

    public void setTree(JobListTree tree) {
        this.tree = tree;
    }
}