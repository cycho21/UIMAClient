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

    public EventAnalyst(CustomFrame customFrame, ConsolePanel consolePanel) {
        this.customFrame = customFrame;
        this.consolePanel = consolePanel;
        JFileChooser.setDefaultLocale(Locale.US);
        customChooser = new CustomChooser();

    }

    public String importFile() {
        String inputFile = null;
        if (customChooser.showOpenDialog(customFrame) == JFileChooser.APPROVE_OPTION) {
            if (!customChooser.getSelectedFile().equals(null)) {
                inputFile = customChooser.getSelectedFile().toString();
                consolePanel.printText("Input File Select : " + inputFile);
            }
        }
        return inputFile;
    }

    public void firstCombo(String actionCommand) {
        /*
            "upload", "getJobList", "requestJob", "sendJob"
         */
        switch (actionCommand) {
            case "upload":
                System.out.println(actionCommand);
                break;
            case "getJobList":
                System.out.println(actionCommand);
                break;
            case "requestJob":
                System.out.println(actionCommand);
                break;
            case "sendJob":
                System.out.println(actionCommand);
                break;
        }

    }
}
