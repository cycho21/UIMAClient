package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.analyst.RequestAnalyst;
import kr.ac.uos.ai.annotator.taskarchiver.TaskUnpacker;
import kr.ac.uos.ai.annotator.view.ConsolePanel;
import kr.ac.uos.ai.annotator.view.JobListTree;

public class ActiveMQManager {

	private String serverIP;
	private String mqueueName;
	private Receiver receiver;
    private ConsolePanel consolePanel;
	private TaskUnpacker taskUnpacker;
	private RequestAnalyst requestAnalyst;
	private Sender sdr;
	private Receiver mainReceiver;
    private JobListTree tree;

    public ActiveMQManager() {
	}

	public void init(String queueName) {
		this.mqueueName = queueName;
		receiver = new Receiver();
		requestAnalyst = new RequestAnalyst();
		requestAnalyst.init();
		receiver.setServerIP(serverIP);
		receiver.setQueueName(queueName);
		receiver.setRequestAnalyst(requestAnalyst);
		receiver.setSender(sdr);
        receiver.setConsolePanel(consolePanel);
		receiver.setTree(tree);
		receiver.init();
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();

		mainReceiver = new Receiver();
		mainReceiver.setServerIP(serverIP);
		mainReceiver.setQueueName("main2client");
		mainReceiver.setRequestAnalyst(requestAnalyst);
		mainReceiver.setSender(sdr);
		mainReceiver.setConsolePanel(consolePanel);
		mainReceiver.init();
		Thread receiverThread2 = new Thread(mainReceiver);
		receiverThread2.start();
	}

	public String getMqueueName() {
		return mqueueName;
	}
	
	public void setMqueueName(String mqueueName) {
		this.mqueueName = mqueueName;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
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
}
