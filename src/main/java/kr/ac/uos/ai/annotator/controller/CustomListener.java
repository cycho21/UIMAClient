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

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("comboBoxChanged")){
            System.out.println(msgTypeComboBox.getSelectedItem());
        } else {
            System.out.println(e.getActionCommand());
        }
    }

    public JComboBox getMsgTypeComboBox() {
        return msgTypeComboBox;
    }

    public void setMsgTypeComboBox(JComboBox msgTypeComboBox) {
        this.msgTypeComboBox = msgTypeComboBox;
    }
}
