package kr.ac.uos.ai.annotator;

import kr.ac.uos.ai.annotator.activemq.ActiveMQManager;
import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.controller.EventAnalyst;
import kr.ac.uos.ai.annotator.taskarchiver.TaskAchieverCore;
import kr.ac.uos.ai.annotator.taskarchiver.TaskPacker;
import kr.ac.uos.ai.annotator.taskdistributor.TaskDistributor;
import kr.ac.uos.ai.annotator.taskdistributor.TaskDistributorCore;
import kr.ac.uos.ai.annotator.view.GUIManager;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-20
 * @link http://github.com/lovebube
 */
public class Application {

    private GUIManager guiManager;
    private ActiveMQManager activemqManager;
    private Sender sdr;
    private TaskDistributor td;
    private TaskPacker tp;
    private TaskAchieverCore tac;
    private TaskDistributorCore tdc;
    private EventAnalyst eventAnalyst;

    public Application() {
        go();
        init();
        guiManager.getConsolePanel().printTextAndNewLine("UIMA Framework Management Ready");
    }

    private void go() {
        guiManager = new GUIManager();
        guiManager.makeFrame();
        guiManager.makePanels();
        guiManager.getConsolePanel().printTextAndNewLine("Make Main Frame and Panels OK");
        eventAnalyst = new EventAnalyst(guiManager.getCustomFrame(), guiManager.getConsolePanel());
        guiManager.setEventAnalyst(eventAnalyst);
        guiManager.setSender(sdr);
        guiManager.init();
    }

    private void init() {
        tac = new TaskAchieverCore();
        tdc = new TaskDistributorCore();

        td = tdc.getTaskDistributor();
        tac.init();
        tdc.init();
        tp = tac.getPacker();
        eventAnalyst.setPacker(tp);
        activemqManager = new ActiveMQManager();
        activemqManager.init("testQueue2");          // This init method makes receiver and starts receiver
        activemqManager.setConsolePanel(guiManager.getConsolePanel());
        guiManager.getConsolePanel().printTextAndNewLine("Receiver Initialization OK");

        sdr = new Sender();
        sdr.setConsolePanel(guiManager.getConsolePanel());
        sdr.init();
        guiManager.getConsolePanel().printTextAndNewLine("Sender Initialization OK");
        sdr.createQueue("testQueue2");
        eventAnalyst.setSender(sdr);
        guiManager.getConsolePanel().printTextAndNewLine("ActiveMQ Initialization OK");
    }

    public static void main(String[] args) {
        new Application();
    }
}
