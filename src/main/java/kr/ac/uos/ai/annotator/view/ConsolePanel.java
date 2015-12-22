package kr.ac.uos.ai.annotator.view;

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
    private JScrollPane scrollPane;

    public ConsolePanel() {
    }

    public void init() {
        font = new Font("Courier New", Font.BOLD, 14);
        textArea = new JTextArea(17, 145);
        textArea.setBackground(new Color(16, 78, 139));
        textArea.setForeground(Color.WHITE);
        textArea.setFont(font);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(scrollPane);
    }

    public void printTextAndNewLine(String text) {
        textArea.append(text + "\n");
//        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
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

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }
}
