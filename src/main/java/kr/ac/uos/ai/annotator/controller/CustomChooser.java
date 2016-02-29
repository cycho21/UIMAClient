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
        setFileFilter(new FileNameExtensionFilter("Input", "txt"));
        setFileFilter(new FileNameExtensionFilter("Annotator", "jar"));
        setMultiSelectionEnabled(false);
        setApproveButtonText("Select");
    }

    public void setting(String string){
        switch (string) {
            case "jar":
                setFileFilter(new FileNameExtensionFilter("Annotator", "jar"));
                setMultiSelectionEnabled(false);
                setApproveButtonText("Select");
                break;
            case "input":
                setFileFilter(new FileNameExtensionFilter("Input File", "txt"));
                setMultiSelectionEnabled(false);
                setApproveButtonText("Select");
                break;
            default:
                setFileFilter(new FileNameExtensionFilter("Input", "txt"));
                setFileFilter(new FileNameExtensionFilter("Annotator", "jar"));
                setMultiSelectionEnabled(false);
                setApproveButtonText("Select");
                break;
        }
    }
}
