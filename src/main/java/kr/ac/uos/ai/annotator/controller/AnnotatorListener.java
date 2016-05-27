package kr.ac.uos.ai.annotator.controller;

import kr.ac.uos.ai.annotator.view.CustomComboBox;
import kr.ac.uos.ai.annotator.view.CustomFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2016-05-26 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class AnnotatorListener implements ActionListener {

    private CustomFrame customFrame;
    private CustomComboBox customComboBox;

    public AnnotatorListener() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(customComboBox.getSelectedItem());
        if (e.getActionCommand().equals("comboBoxChanged")) {

        }
    }

    public void setCustomFrame(CustomFrame customFrame) {
        this.customFrame = customFrame;
    }

    public void setCustomComboBox(CustomComboBox customComboBox) {
        this.customComboBox = customComboBox;
    }
}
