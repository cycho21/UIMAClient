package kr.ac.uos.ai.annotator.view;

import kr.ac.uos.ai.annotator.configure.Configuration;

import javax.swing.*;
import java.awt.*;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-21 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class GUIManager {

    private CustomFrame customFrame;
    private CustomPanel customPanel;
    private ConsolePanel consolePanel;
    private JButton button;
    private boolean shouldWeightX = true;

    public GUIManager() {
    }

    public void init() {
        makeFrame();
        makePanels();
        setBorderLayout();
        setGridBagLayout();

        consolePanel.printTextAndNewLine("GUI Initialization OK");

        customFrame.add(customPanel, BorderLayout.NORTH);
        customFrame.add(consolePanel, BorderLayout.SOUTH);
        customFrame.pack();
        customFrame.setVisible(true);
    }

    private void setBorderLayout() {
        customFrame.setLayout(new BorderLayout());
    }

    private void setGridBagLayout() {
        customPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        button = new JButton("Get Resource From UIMA Server");

        if (shouldWeightX) {
            constraints.weightx = 0.5;
            constraints.weighty = 0.5;
        }

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        customPanel.add(button, constraints);


        button = new JButton("Get Resource From UIMA Node");

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;
        constraints.gridx = 0;
        constraints.gridy = 2;
        customPanel.add(button, constraints);

        button = new JButton("Run");
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 0;       //reset to default
        constraints.weighty = 1.0;   //request any extra vertical space
        constraints.anchor = GridBagConstraints.PAGE_END; //bottom of space
        constraints.insets = new Insets(10,0,0,0);  //top padding
        constraints.gridx = 1;       //aligned with button 2
        constraints.gridwidth = 2;   //2 columns wide
        constraints.gridy = 2;       //third row
        customPanel.add(button, constraints);

    }

    private void makePanels() {
        customPanel = new CustomPanel();
        customPanel.setBackground(Color.WHITE);
        customPanel.setPreferredSize(new Dimension(Configuration.WIDTH, Configuration.HEIGHT - 200));

        consolePanel = new ConsolePanel();
        consolePanel.init();
        consolePanel.setPreferredSize(new Dimension(Configuration.WIDTH, 200));
    }

    private void makeFrame() {
        customFrame = new CustomFrame("UIMA Framework Management");
        customFrame.setBackground(Color.WHITE);
        customFrame.setResizable(false);
        customFrame.setDefaultCloseOperation(CustomFrame.EXIT_ON_CLOSE);
    }
}