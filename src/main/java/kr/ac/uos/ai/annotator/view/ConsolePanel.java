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

public class ConsolePanel extends JPanel {

    private JTextArea textArea;
    private Font font;

    public ConsolePanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public ConsolePanel(LayoutManager layout) {
        super(layout);
    }

    public ConsolePanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public ConsolePanel() {
    }

    public void init() {
        font = new Font("Verdana", Font.BOLD, 12);

        textArea = new JTextArea();
        textArea.setBackground(new Color(16, 78, 139));
        textArea.setPreferredSize(new Dimension(Configuration.WIDTH - 15, 200 - 15));
        textArea.setEditable(true);

        textArea.setFont(font);
        textArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setSize(new Dimension(Configuration.WIDTH, 200));
        this.add(scrollPane);
    }

    public void printTextAndNewLine(String text) {
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public void printText(String text) {
        textArea.append(text);
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea testArea) {
        this.textArea = testArea;
    }

}
