package kr.ac.uos.ai.annotator.view;

import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.configure.Configuration;
import kr.ac.uos.ai.annotator.controller.CustomListener;
import kr.ac.uos.ai.annotator.controller.EventAnalyst;

import javax.swing.*;
import java.awt.*;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-21 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class GUIManager {

    private CustomListener customListener;
    private CustomFrame customFrame;
    private CustomPanel customPanel;
    private ConsolePanel consolePanel;
    private JButton serverButton;
    private JButton nodeButton;
    private JButton fileImportButton;
    private JButton runButton;
    private String[] comboBoxContents;
    private CustomComboBox customComboBox;
    private Font font;
    private EventAnalyst eventAnalyst;
    private Sender sdr;
    private JOptionPane jOptionPane;
    private Sender sender;

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
        }
        font = new Font("Courier New", Font.PLAIN, 16);
        jOptionPane = new JOptionPane();
//        makeFrame();
//        makePanels();
        setMsgTypeComboBox();
        setSecondComboBox();
        setBorderLayout();
        setTopBorderLayout();
        setListener();

        customFrame.add(customPanel, BorderLayout.CENTER);
        customFrame.add(consolePanel, BorderLayout.SOUTH);
        customFrame.pack();
        customFrame.setVisible(true);

        /*
            GUI init log.
         */
        consolePanel.getTextArea().setEditable(false);
        consolePanel.printTextAndNewLine("GUI Initialization OK");
    }

    private void setListener() {
        customComboBox.addActionListener(customListener);
        runButton.addActionListener(customListener);
        fileImportButton.addActionListener(customListener);
        serverButton.addActionListener(customListener);
        nodeButton.addActionListener(customListener);
    }

    private void setSecondComboBox() {

    }

    public void setMsgTypeComboBox() {
        comboBoxContents = new String[] {"upload", "sendJob", "requestJob", "runAnnotator", "getJobList"};
        customComboBox = new CustomComboBox(comboBoxContents);
        customComboBox.setSelectedItem("upload");
        customListener = new CustomListener(customComboBox);
        DefaultListCellRenderer defaultListCellRenderer = new DefaultListCellRenderer();
        defaultListCellRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER);
        customComboBox.setRenderer(defaultListCellRenderer);
        customListener.setEventAnalyst(eventAnalyst);
        customComboBox.setPreferredSize(new Dimension(Configuration.WIDTH, 50));
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
        fileImportButton = new JButton("Choose File from File System");
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

    public void makePanels() {
        customPanel = new CustomPanel();
        customPanel.setBackground(Color.WHITE);
        customPanel.setPreferredSize(new Dimension(Configuration.WIDTH, Configuration.HEIGHT - 200));
        consolePanel = new ConsolePanel();
        consolePanel.setPreferredSize(new Dimension(Configuration.WIDTH, 300));
        consolePanel.init();
    }

    public void makeFrame() {
        customFrame = new CustomFrame("UIMA Framework Management");
        customFrame.setBackground(Color.WHITE);
        customFrame.setResizable(false);
        customFrame.setDefaultCloseOperation(CustomFrame.DO_NOTHING_ON_CLOSE);
    }

    public CustomListener getCustomListener() {
        return customListener;
    }

    public void setCustomListener(CustomListener customListener) {
        this.customListener = customListener;
    }

    public CustomFrame getCustomFrame() {
        return customFrame;
    }

    public void setCustomFrame(CustomFrame customFrame) {
        this.customFrame = customFrame;
    }

    public CustomPanel getCustomPanel() {
        return customPanel;
    }

    public void setCustomPanel(CustomPanel customPanel) {
        this.customPanel = customPanel;
    }

    public ConsolePanel getConsolePanel() {
        return consolePanel;
    }

    public void setConsolePanel(ConsolePanel consolePanel) {
        this.consolePanel = consolePanel;
    }

    public JButton getServerButton() {
        return serverButton;
    }

    public void setServerButton(JButton serverButton) {
        this.serverButton = serverButton;
    }

    public JButton getNodeButton() {
        return nodeButton;
    }

    public void setNodeButton(JButton nodeButton) {
        this.nodeButton = nodeButton;
    }

    public JButton getFileImportButton() {
        return fileImportButton;
    }

    public void setFileImportButton(JButton fileImportButton) {
        this.fileImportButton = fileImportButton;
    }

    public JButton getRunButton() {
        return runButton;
    }

    public void setRunButton(JButton runButton) {
        this.runButton = runButton;
    }

    public String[] getComboBoxContents() {
        return comboBoxContents;
    }

    public void setComboBoxContents(String[] comboBoxContents) {
        this.comboBoxContents = comboBoxContents;
    }

    public CustomComboBox getCustomComboBox() {
        return customComboBox;
    }

    public void setCustomComboBox(CustomComboBox customComboBox) {
        this.customComboBox = customComboBox;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public EventAnalyst getEventAnalyst() {
        return eventAnalyst;
    }

    public void setEventAnalyst(EventAnalyst eventAnalyst) {
        this.eventAnalyst = eventAnalyst;
    }

    public Sender getSdr() {
        return sdr;
    }

    public void setSdr(Sender sdr) {
        this.sdr = sdr;
    }

    public String[] makeInputIPDialog() {
        String[] stringArray = new String[2];
        JTextField devField = new JTextField(10);
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new BorderLayout());
        myPanel.add(new JLabel("Developer Name :"), BorderLayout.NORTH);
        myPanel.add(devField);
        myPanel.add(new JLabel("Server IP :"), BorderLayout.SOUTH);

        String serverIP = jOptionPane.showInputDialog(null, myPanel, "UIMA Management Ver. 0.0.1",
                JOptionPane.INFORMATION_MESSAGE);

        stringArray[0] = serverIP;
        stringArray[1] = devField.getText();

        if(serverIP==null || serverIP.equals("")) {
            stringArray[0] = "211.109.9.71";
        }

        if(devField.getText()==null || devField.getText().equals("")){
            stringArray[1] = "unnamedDev";
        }
        return stringArray;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }
}