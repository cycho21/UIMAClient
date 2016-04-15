package kr.ac.uos.ai.annotator.controller;

import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.bean.protocol.Protocol;
import kr.ac.uos.ai.annotator.taskarchiver.TaskPacker;
import kr.ac.uos.ai.annotator.view.ConsolePanel;
import kr.ac.uos.ai.annotator.view.CustomFrame;
import kr.ac.uos.ai.annotator.view.JobListTree;

import javax.swing.*;
import java.awt.*;
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
    private String jobFileName;
    private String annoFileName;
    private JobListTree tree;

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

    public void upLoad() {
        Protocol protocol = new Protocol();
        byte[] tempByte = tp.file2Byte(filePath);
        protocol.makeProtocol(fileName, String.valueOf(tempByte.length), "1.0.0", devName, fileName);
        protocol.setMsgType("upload");
        if(fileName.contains("jar")){
            sdr.sendMessage(tempByte, fileName, protocol);
        } else {
            sdr.sendMessage(tempByte, fileName, protocol);
        }
    }

    public void firstCombo(String actionCommand) {
        /*
            "upload", "getJobList", "requestJob", "sendJob"
         */
        switch (actionCommand) {

            case "getNodeInfo":
                break;

            case "SimpleProcess":
                customChooser.setting("input");
                if (customChooser.showOpenDialog(customFrame) == JFileChooser.APPROVE_OPTION) {
                    filePath = customChooser.getSelectedFile().toString();
                    fileName = customChooser.getSelectedFile().getName().toString();
                    consolePanel.printTextAndNewLine("Input File Select : " + filePath);
                }
                customChooser.setting("jar");
                if (customChooser.showOpenDialog(customFrame) == JFileChooser.APPROVE_OPTION) {
                    filePath = customChooser.getSelectedFile().toString();
                    fileName = customChooser.getSelectedFile().getName().toString();
                    consolePanel.printTextAndNewLine("Annotator File Select : " + filePath);
                }
                upLoad();

                this.comboBoxChose = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);

                break;

            case "upload":
                this.comboBoxChose = actionCommand;
                filePath = customChooser.getSelectedFile().toString();
                fileName = customChooser.getSelectedFile().getName().toString();

                upLoad();

                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;

            case "getJobList":
                this.comboBoxChose = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;

            case "requestJob":
                JOptionPane jrOptionPane = new JOptionPane();
                JTextField jobRField = new JTextField(10);
                JPanel myRPanel = new JPanel();
                myRPanel.setLayout(new BorderLayout());
                myRPanel.add(new JLabel("Job Name :"), BorderLayout.NORTH);
                myRPanel.add(jobRField);
                myRPanel.add(new JLabel("File Name :"), BorderLayout.SOUTH);

                jobFileName = jrOptionPane.showInputDialog(null, myRPanel, "UIMA Management Ver. 0.0.1",
                        JOptionPane.INFORMATION_MESSAGE);
                jobName = jobRField.getText();

                this.comboBoxChose = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;

            default:
                break;
        }

    }

    public void execute() {
        switch (comboBoxChose) {

            case "getJobList":
                sdr.sendMessage("getJobList");
                makeTree();
                break;

            case "requestJob" :
                Protocol requestProtocol = new Protocol();
                requestProtocol.makeProtocol(jobName, null, "1.0.0", devName, jobFileName);
                requestProtocol.setMsgType("requestJob");
                sdr.sendMessage2(requestProtocol);
                break;

            case "getNodeInfo":
                sdr.sendMessage("getNodeInfo");
                break;

            default:
                break;
        }
    }

    private void makeTree() {
        tree.repaintTree();
    }

    public void setPacker(TaskPacker packer) {
        this.tp = packer;
    }

    public void setSender(Sender sdr) {
        this.sdr = sdr;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public void setTree(JobListTree tree) {
        this.tree = tree;
    }

}
