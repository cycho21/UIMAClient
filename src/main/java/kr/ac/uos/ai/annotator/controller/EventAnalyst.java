package kr.ac.uos.ai.annotator.controller;

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

    private ConsolePanel consolePanel;
    private CustomFrame customFrame;
    private CustomChooser customChooser;
    private String filePath;
    private String comboBoxChoosed;

    public EventAnalyst(CustomFrame customFrame, ConsolePanel consolePanel) {
        this.customFrame = customFrame;
        this.consolePanel = consolePanel;
        this.comboBoxChoosed = null;
        this.filePath = null;
        JFileChooser.setDefaultLocale(Locale.US);
        customChooser = new CustomChooser();

    }

    public void importFile() {
        if (customChooser.showOpenDialog(customFrame) == JFileChooser.APPROVE_OPTION) {
            if (!customChooser.getSelectedFile().equals(null)) {
                filePath = customChooser.getSelectedFile().toString();
                consolePanel.printTextAndNewLine("Input File Select : " + filePath);
            }
        }
    }

    public void firstCombo(String actionCommand) {
        /*
            "upload", "getJobList", "requestJob", "sendJob"
         */
        switch (actionCommand) {
            case "upload":
                this.comboBoxChoosed = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;
            case "getJobList":
                this.comboBoxChoosed = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;
            case "requestJob":
                this.comboBoxChoosed = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;
            case "sendJob":
                this.comboBoxChoosed = actionCommand;
                consolePanel.printTextAndNewLine("msgType Choose : " + actionCommand);
                break;
        }

    }

    public void execute() {

    }
}
