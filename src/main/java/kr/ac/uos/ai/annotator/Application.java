package kr.ac.uos.ai.annotator;

import kr.ac.uos.ai.annotator.activemq.ActiveMQManager;
import kr.ac.uos.ai.annotator.activemq.Broadcaster;
import kr.ac.uos.ai.annotator.activemq.Sender;
import kr.ac.uos.ai.annotator.controller.EventAnalyst;
import kr.ac.uos.ai.annotator.taskarchiver.TaskAchieverCore;
import kr.ac.uos.ai.annotator.taskarchiver.TaskPacker;
import kr.ac.uos.ai.annotator.taskdistributor.TaskDistributor;
import kr.ac.uos.ai.annotator.taskdistributor.TaskDistributorCore;
import kr.ac.uos.ai.annotator.view.GUIManager;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-20
 * @link http://github.com/lovebube
 */
public class Application {


    private String serverIP;
    private GUIManager guiManager;
    private ActiveMQManager activemqManager;
    private Sender sdr;
    private TaskDistributor td;
    private TaskPacker tp;
    private TaskAchieverCore tac;
    private TaskDistributorCore tdc;
    private EventAnalyst eventAnalyst;
    private Broadcaster broadCaster;
    private String developerName;

    public Application() {
        go();
        setActiveMQ();
        init();
        guiManager.getConsolePanel().printTextAndNewLine("UIMA Framework Management Ready");
    }

    private void setActiveMQ() {
        String[] stringArray = guiManager.makeInputIPDialog();
        serverIP = stringArray[0];
        developerName = stringArray[1];
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
        eventAnalyst.setDevName(developerName);
        System.out.println(developerName);
        activemqManager = new ActiveMQManager();
        activemqManager.setServerIP(serverIP);
        guiManager.getConsolePanel().printTextAndNewLine("Receiver Initialization OK");

        broadCaster = new Broadcaster("basicTopicName", serverIP);
        broadCaster.setConsolePanel(guiManager.getConsolePanel());
        broadCaster.init();
        guiManager.getConsolePanel().printTextAndNewLine("Broadcaster Initialization OK");

        sdr = new Sender();
        sdr.setServerIP(serverIP);
        sdr.setConsolePanel(guiManager.getConsolePanel());
        sdr.init();
        sdr.createQueue("client2node");
        guiManager.getConsolePanel().printTextAndNewLine("Sender Initialization OK");

        eventAnalyst.setSender(sdr);
        eventAnalyst.setBroadCaster(broadCaster);

        activemqManager.setConsolePanel(guiManager.getConsolePanel());
        activemqManager.setSender(sdr);
        activemqManager.init("node2client");          // This init method makes receiver and starts receiver

        guiManager.getCustomFrame().addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (serverIP == null) {
                    System.exit(0);
                } else {
                    activemqManager.getReceiver().destory();
                    sdr.destory();
                    System.exit(0);
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        guiManager.getConsolePanel().printTextAndNewLine("ActiveMQ Initialization OK");
    }

    public static void main(String[] args) {
        new Application();
    }
}
