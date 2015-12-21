package kr.ac.uos.ai.annotator.view;

import javax.swing.*;
import java.awt.*;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-21 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class CustomPanel extends JPanel {

    public CustomPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

    public CustomPanel(LayoutManager layout) {
        super(layout);
    }

    public CustomPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public CustomPanel() {
    }

}
