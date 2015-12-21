package kr.ac.uos.ai.annotator.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-21 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class CustomListener implements ActionListener {

    private JComboBox msgTypeComboBox;
    private EventAnalyst eventAnalyst;

    public CustomListener(JComboBox msgTypeComboBox) {
        this.msgTypeComboBox = msgTypeComboBox;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("comboBoxChanged")){
            eventAnalyst.firstCombo(msgTypeComboBox.getSelectedItem().toString());
        } else {
            switch (e.getActionCommand()) {
                case "Choose File from File System" :
                    eventAnalyst.importFile();
                    break;
                case "Run":
                    eventAnalyst.execute();
                    break;
            }
        }
    }

    public JComboBox getMsgTypeComboBox() {
        return msgTypeComboBox;
    }

    public void setMsgTypeComboBox(JComboBox msgTypeComboBox) {
        this.msgTypeComboBox = msgTypeComboBox;
    }

    public void setEventAnalyst(EventAnalyst eventAnalyst) {
        this.eventAnalyst = eventAnalyst;
    }
}
