package kr.ac.uos.ai.annotator.controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-22
 * @link http://github.com/lovebube
 */
public class CustomChooser extends JFileChooser {

    public CustomChooser() {
        setFileFilter(new FileNameExtensionFilter("Annotator", "jar"));
        setFileFilter(new FileNameExtensionFilter("Input", "txt"));
        setMultiSelectionEnabled(false);
        setApproveButtonText("Select");
    }
}
