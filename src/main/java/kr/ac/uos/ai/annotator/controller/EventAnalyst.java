package kr.ac.uos.ai.annotator.controller;

import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.bean.protocol.AnnotatorInfo;
import kr.ac.uos.ai.annotator.bean.protocol.Protocol;
import kr.ac.uos.ai.annotator.bean.protocol.RequestJob;
import kr.ac.uos.ai.annotator.configure.Configuration;
import kr.ac.uos.ai.annotator.taskarchiver.TaskPacker;
import kr.ac.uos.ai.annotator.view.ConsolePanel;
import kr.ac.uos.ai.annotator.view.CustomComboBox;
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

    private JOptionPane jOptionPane;
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
    private JobListTree tree;
    private String fileExtension;
    private String annotatorFileName;
    private CustomComboBox customComboBox;
    private JobListener jobListener;

    public EventAnalyst(CustomFrame customFrame, ConsolePanel consolePanel) {
        this.customFrame = customFrame;
        this.consolePanel = consolePanel;
        this.comboBoxChose = "upload";
        this.filePath = null;
        this.fileName = null;
        JFileChooser.setDefaultLocale(Locale.US);
        customChooser = new CustomChooser();
        jOptionPane = new JOptionPane();

    }

    public void importFile() {
        if (customChooser.showOpenDialog(customFrame) == JFileChooser.APPROVE_OPTION) {
            filePath = customChooser.getSelectedFile().toString();
            fileName = customChooser.getSelectedFile().getName().toString();
            consolePanel.printTextAndNewLine("Input File Select : " + filePath);
        }
    }

    public void upLoad(String filePath, String fileName) {

        AnnotatorInfo annotatorInfo = new AnnotatorInfo();

        annotatorInfo.setAuthor(Configuration.stringArray[1]);      // annotator's author name

        if (fileName.contains("jar")) {
            String[] stringArray = makeMetaData();
            annotatorInfo.setName(stringArray[0]);      // annotator's name
            annotatorInfo.setVersion(stringArray[1]);       // annotator's version
        } else {
        }


        byte[] tempByte = tp.file2Byte(filePath);

        if (fileName.contains("jar")) {
            sdr.uploadMessage(tempByte, fileName, annotatorInfo);
        } else {
            sdr.uploadTxtMessage(tempByte, fileName);
        }

    }

    private String[] makeMetaData() {
        String[] stringArray = new String[2];
        JTextField annoField = new JTextField(10);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BorderLayout());
        myPanel.add(new JLabel("Annotator Name :"), BorderLayout.NORTH);
        myPanel.add(annoField);
        myPanel.add(new JLabel("Annotator Ver :"), BorderLayout.SOUTH);
        String version = jOptionPane.showInputDialog(null, myPanel, "UIMA Management Ver. 1.0.1",
                JOptionPane.INFORMATION_MESSAGE);

        stringArray[0] = annoField.getText();
        stringArray[1] = version;

        if (annoField.getText() == null || annoField.getText().equals("")) {
            stringArray[0] = "unnamedAnnotator";
        }

        if (version == null || version.equals("")) {
            stringArray[1] = "1.0.0";
        }
        return stringArray;
    }

    public void firstCombo(String actionCommand) {
        /*
            "upload", "getJobList", "requestJob", "sendJob"
         */
        switch (actionCommand) {

            case "makeJob":
                this.comboBoxChose = actionCommand;
                consolePanel.printText("\n" + "msgType Choose : " + actionCommand);
                break;

            case "getAnnotatorList":
                this.comboBoxChose = actionCommand;
                consolePanel.printText("\n" + "msgType Choose : " + actionCommand);
                consolePanel.printTextAndNewLine("\n" + "Request annotator list to server ...");
                break;

            case "setInitialAnnotator":
                this.comboBoxChose = actionCommand;

                consolePanel.printTextAndNewLine("\n" + "msgType Choose : " + actionCommand);

                annotatorFileName = jOptionPane.showInputDialog(null, "Put Annotator File Name",
                        JOptionPane.INFORMATION_MESSAGE);
                break;

            case "getNodeInfo":
                break;

            case "upload":
                this.comboBoxChose = actionCommand;

                consolePanel.printTextAndNewLine("\n" + "msgType Choose : " + actionCommand);

                if (customChooser.showOpenDialog(customFrame) == JFileChooser.APPROVE_OPTION) {
                    filePath = customChooser.getSelectedFile().toString();
                    fileName = customChooser.getSelectedFile().getName().toString();
                    fileExtension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    if (fileExtension.equals(".jar")) {
                        consolePanel.printTextAndNewLine("Annotator File Select : " + filePath);
                    } else {
                        consolePanel.printTextAndNewLine("Input File Select : " + filePath);
                    }
                }
                break;

            case "getJobList":
                this.comboBoxChose = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;

            case "requestJob":
                this.comboBoxChose = actionCommand;

                sdr.sendMessage("getAnnotatorList", null);

                requestJob();

                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);

                break;

            default:
                break;
        }

    }

    private void requestJob() {
        JOptionPane jrOptionPane = new JOptionPane();
        JPanel myRPanel = new JPanel();

        String[] comboBoxContents = new String[]{"Input File", "Set Annotator"};
        CustomComboBox customComboBox = new CustomComboBox(comboBoxContents);
        customComboBox.setSelectedItem("Input File");

        JobListener jobListener = new JobListener(customComboBox);
        jobListener.setCustomFrame(customFrame);
        jobListener.setConsolePanel(consolePanel);

        customComboBox.addActionListener(jobListener);

        myRPanel.setLayout(new BorderLayout());
        myRPanel.add(customComboBox, BorderLayout.NORTH);
        myRPanel.add(new JLabel("Job Name :"), BorderLayout.SOUTH);

        String tempJobName = jrOptionPane.showInputDialog(null, myRPanel, "UIMA Management Ver. 0.0.1",
                JOptionPane.INFORMATION_MESSAGE);
        this.jobName = tempJobName;

        RequestJob requestedJob = jobListener.getRequestedJob();
        requestedJob.setJobName(tempJobName);
        this.jobFileName = requestedJob.getJobFileName();

    }

    public void execute() {
        switch (comboBoxChose) {
            case "makeJob":
                makeJob();
                break;
            case "getAnnotatorList":
                sdr.sendMessage("getAnnotatorList", null);
                break;
            case "setInitialAnnotator":
                sdr.sendMessage("initialAnnotator", annotatorFileName);
                break;
            case "upload":
                upLoad(filePath, fileName);
                break;
            case "getJobList":
                sdr.sendMessage("getJobList");
                makeTree();
                break;
            case "requestJob":
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

    private String[] makeJob() {

        String[] jobStringArray = null;

        sdr.sendMessage("getAnnotatorList", null);
        sdr.sendMessage("getJobList");

        return jobStringArray;
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
