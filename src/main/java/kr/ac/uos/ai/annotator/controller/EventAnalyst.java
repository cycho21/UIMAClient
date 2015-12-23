package kr.ac.uos.ai.annotator.controller;

import kr.ac.uos.ai.annotator.activemq.Broadcaster;
import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.bean.protocol.Protocol;
import kr.ac.uos.ai.annotator.taskarchiver.TaskPacker;
import kr.ac.uos.ai.annotator.view.ConsolePanel;
import kr.ac.uos.ai.annotator.view.CustomFrame;

import javax.swing.*;
import java.util.Locale;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-21 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class EventAnalyst {

    private String devName;
    private ConsolePanel consolePanel;
    private CustomFrame customFrame;
    private CustomChooser customChooser;
    private String filePath;
    private String fileName;
    private String jobName;
    private String comboBoxChose;
    private TaskPacker tp;
    private Sender sdr;
    private Broadcaster broadCaster;

    public EventAnalyst(CustomFrame customFrame, ConsolePanel consolePanel) {
        this.customFrame = customFrame;
        this.consolePanel = consolePanel;
        this.comboBoxChose = "upload";
        this.filePath = null;
        this.fileName = null;
        JFileChooser.setDefaultLocale(Locale.US);
        customChooser = new CustomChooser();
    }

    public void importFile() {
        if (customChooser.showOpenDialog(customFrame) == JFileChooser.APPROVE_OPTION) {
                filePath = customChooser.getSelectedFile().toString();
                fileName = customChooser.getSelectedFile().getName().toString();
                consolePanel.printTextAndNewLine("Input File Select : " + filePath);
        }
    }

    public void firstCombo(String actionCommand) {
        /*
            "upload", "getJobList", "requestJob", "sendJob"
         */
        switch (actionCommand) {
            case "upload":
                this.comboBoxChose = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;
            case "getJobList":
                this.comboBoxChose = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;
            case "requestJob":
                this.comboBoxChose = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;
            case "sendJob":
                JOptionPane jOptionPane = new JOptionPane();
                jobName = jOptionPane.showInputDialog(null, "Input jobName", "UIMA Management Ver. 0.0.1",
                        JOptionPane.INFORMATION_MESSAGE);
                this.comboBoxChose = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;
            case "test":
                this.comboBoxChose = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;
            default:
                break;
        }

    }

    public void execute() {
        switch (comboBoxChose) {
            case "upload" :
                Protocol protocol = new Protocol();
                byte[] tempByte = tp.file2Byte(filePath);
                protocol.makeProtocol(fileName, String.valueOf(tempByte.length), "1.0.0", devName);
                protocol.setMsgType("upload");
                broadCaster.sendMessage(tempByte, fileName, protocol);
                break;
            case "sendJob" :
                Protocol sendProtocol = new Protocol();
                sendProtocol.makeProtocol(jobName, null, "1.0.0", devName);
                sendProtocol.setMsgType("sendJob");
                sdr.sendMessage(sendProtocol);
                break;
            case "test" :
                broadCaster.sendMessageTest("testtesttest");
                break;
            default:
                break;
        }
    }

    public void setPacker(TaskPacker packer) {
        this.tp = packer;
    }

    public void setSender(Sender sdr) {
        this.sdr = sdr;
    }

    public void setBroadCaster(Broadcaster broadCaster) {
        this.broadCaster = broadCaster;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }
}
