package kr.ac.uos.ai.annotator.view;

import javax.swing.*;
import java.awt.*;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-21 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class CustomFrame extends JFrame {

    public CustomFrame(String title) throws HeadlessException {
        super(title);
    }

    public CustomFrame(String title, GraphicsConfiguration gc) {
        super(title, gc);
    }

    public CustomFrame() throws HeadlessException {
    }

    public CustomFrame(GraphicsConfiguration gc) {
        super(gc);
    }

}
