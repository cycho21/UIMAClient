package kr.ac.uos.ai.annotator.activemq;

import kr.ac.uos.ai.annotator.view.ConsolePanel;

public class ActiveMQManager {

	private String mqueueName;
	private Receiver receiver;
    private ConsolePanel consolePanel;

    public ActiveMQManager() {
	}

	public void init(String queueName) {
		this.mqueueName = queueName;
		receiver = new Receiver();
		receiver.setQueueName(queueName);
		receiver.init();
        receiver.setConsolePanel(consolePanel);
		Thread receiverThread = new Thread(receiver);
		receiverThread.start();
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
}
