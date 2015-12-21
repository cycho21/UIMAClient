package kr.ac.uos.ai.annotator;

import kr.ac.uos.ai.annotator.view.GUIManager;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-20
 * @link http://github.com/lovebube
 */
public class Application {

    private GUIManager guiManager;

    public Application() {
        init();
        go();
    }

    private void go() {
        guiManager = new GUIManager();
        guiManager.init();
    }

    private void init() {

    }

    public static void main(String[] args) {
        new Application();
    }
}
