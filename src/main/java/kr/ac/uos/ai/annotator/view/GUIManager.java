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
    private JButton serverButton;
    private JButton nodeButton;
    private JButton fileImportButton;
    private JButton runButton;
    private String[] comboBoxContents;
    private CustomComboBox customComboBox;
    private Font font;

    public GUIManager() {
    }

    public void init() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        font = new Font("CourierNew", Font.PLAIN, 14);
        makeFrame();
        makePanels();
        setMsgTypeComboBox();
        setSecondComboBox();
        setBorderLayout();
        setTopBorderLayout();

        customFrame.add(customPanel, BorderLayout.CENTER);
        customFrame.add(consolePanel, BorderLayout.SOUTH);
        customFrame.pack();
        customFrame.setVisible(true);

        /*
            GUI init log.
         */
        consolePanel.printTextAndNewLine("GUI Initialization OK");
    }

    private void setSecondComboBox() {

    }

    private void setMsgTypeComboBox() {
        comboBoxContents = new String[] {"upload", "getJobList", "requestJob", "sendJob"};
        customComboBox = new CustomComboBox(comboBoxContents);
        customComboBox.setPreferredSize(new Dimension(Configuration.WIDTH / 5, 50));
        customComboBox.setSelectedIndex(0);
        customComboBox.setFont(font);
    }

    private void setTopBorderLayout() {
        serverButton = new JButton("Get Resource From UIMA Server");
        serverButton.setPreferredSize(new Dimension(Configuration.WIDTH / 2, 50));
        serverButton.setFont(font);
        nodeButton = new JButton("Get Resource From UIMA Node");
        nodeButton.setPreferredSize(new Dimension(Configuration.WIDTH / 2, 50));
        nodeButton.setFont(font);

        runButton = new JButton("Run");
        runButton.setPreferredSize(new Dimension(Configuration.WIDTH / 2, 50));
        runButton.setFont(font);
        fileImportButton = new JButton("Import Input File from File System");
        fileImportButton.setPreferredSize(new Dimension(Configuration.WIDTH / 2, 50));
        fileImportButton.setFont(font);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(serverButton, BorderLayout.WEST);
        topPanel.add(nodeButton, BorderLayout.EAST);
        customPanel.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(fileImportButton, BorderLayout.WEST);
        centerPanel.add(runButton, BorderLayout.EAST);
        customPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.add(customComboBox);
        customPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setBorderLayout() {
        customFrame.setLayout(new BorderLayout());
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