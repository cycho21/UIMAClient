package kr.ac.uos.ai.annotator.view;

import javax.swing.*;
import java.util.Vector;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-21 enemy
 * @link http://ai.uos.ac.kr:9000/lovebube/UIMA_Management_Client
 */

public class CustomComboBox extends JComboBox {

    public CustomComboBox(ComboBoxModel aModel) {
        super(aModel);
    }

    public CustomComboBox(Object[] items) {
        super(items);
    }

    public CustomComboBox(Vector items) {
        super(items);
    }

    public CustomComboBox() {
    }
}
