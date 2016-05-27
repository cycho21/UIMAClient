package kr.ac.uos.ai.annotator.controller;

import kr.ac.uos.ai.annotator.AnnotatorRunningInfo;
import kr.ac.uos.ai.annotator.bean.protocol.RequestJob;
import kr.ac.uos.ai.annotator.view.ConsolePanel;
import kr.ac.uos.ai.annotator.view.CustomComboBox;
import kr.ac.uos.ai.annotator.view.CustomFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2016-05-23 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class JobListener implements ActionListener {

    private JOptionPane jOptionPane;
    private RequestJob requestedJob;
    private JComboBox typeBox;
    private CustomFrame customFrame;
    private String filePath;
    private String fileName;
    private ConsolePanel consolePanel;

    public JobListener(JComboBox typeBox) {
        this.requestedJob = new RequestJob();
        this.typeBox = typeBox;
        this.jOptionPane = new JOptionPane();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("comboBoxChanged")) {

            switch (typeBox.getSelectedItem().toString()) {
                case "Set Annotator":
                    setAnnotator();
                    break;
                case "Input File":
                    addInputFile();
                    break;
                default:
                    break;
            }
        } else {

        }

    }

    private void addInputFile() {

    }

    private void setAnnotator() {
        JPanel tempPanel = new JPanel();

        CustomComboBox customComboBox = new CustomComboBox();

        for (String s : AnnotatorRunningInfo.getAnnotatorList().keySet()) {
            customComboBox.addItem(s);
        }

        if (customComboBox.getItemCount() == 0) {

        } else {

            AnnotatorListener annotatorListener = new AnnotatorListener();

            customComboBox.addActionListener(annotatorListener);
            annotatorListener.setCustomFrame(customFrame);
            annotatorListener.setCustomComboBox(customComboBox);

            tempPanel.setLayout(new BorderLayout());
            tempPanel.add(customComboBox, BorderLayout.NORTH);
            tempPanel.add(new JLabel("Annotator Quantity :"), BorderLayout.SOUTH);

            jOptionPane.showInputDialog(null, tempPanel, "Select Annotator", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    public void setCustomFrame(CustomFrame customFrame) {
        this.customFrame = customFrame;
    }

    public void setConsolePanel(ConsolePanel consolePanel) {
        this.consolePanel = consolePanel;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public RequestJob getRequestedJob() {
        return requestedJob;
    }

    public void setRequestedJob(RequestJob requestedJob) {
        this.requestedJob = requestedJob;
    }
}
