package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.view.ConsolePanel;
import kr.ac.uos.ai.annotator.view.JobListTree;

public class ActiveMQManager {

	private String serverIP;
	private Sender sdr;
	private Receiver mainReceiver;
    private ConsolePanel consolePanel;
    private JobListTree tree;

	public ActiveMQManager() {
	}

	public void init(String queueName) {

		mainReceiver = new Receiver();
		mainReceiver.setServerIP(serverIP);
		mainReceiver.setQueueName(queueName);
		mainReceiver.setSender(sdr);
		mainReceiver.setConsolePanel(consolePanel);
        mainReceiver.setTree(tree);
		mainReceiver.init();
		Thread receiverThread = new Thread(mainReceiver);
		receiverThread.start();
	}


    public void setConsolePanel(ConsolePanel consolePanel) {
        this.consolePanel = consolePanel;
    }

	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}

	public void setSender(Sender sdr) {
		this.sdr = sdr;
	}

    public void setTree(JobListTree tree) {
        this.tree = tree;
    }

	public Receiver getReceiver() {
		return mainReceiver;
	}
}
