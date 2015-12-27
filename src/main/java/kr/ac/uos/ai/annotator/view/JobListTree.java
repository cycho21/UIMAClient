package kr.ac.uos.ai.annotator.view;

import kr.ac.uos.ai.annotator.bean.JobList;
import kr.ac.uos.ai.annotator.configure.Configuration;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - Snapshot
 *          on 2015-12-28
 * @link http://github.com/lovebube
 */
public class JobListTree extends JPanel {

    private DefaultMutableTreeNode rootNode;
    private JTree jTree;
    private JScrollPane scrollPane;
    private DefaultTreeModel model;

    public JobListTree() {
    }

    public void init() {
        rootNode = new DefaultMutableTreeNode("Job List");
        jTree = new JTree(rootNode);
        scrollPane = new JScrollPane(jTree);
        scrollPane.setPreferredSize(new Dimension(Configuration.WIDTH / 3 - 40, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setBackground(Color.WHITE);
        this.add(scrollPane);
    }

    public void repaintTree() {
        model = (DefaultTreeModel) jTree.getModel();
        System.out.println("OK");
        System.out.println(JobList.getJobList().size());
        System.out.println(JobList.getJobList().get("1").getVersion());
        for (int i = 0; i<JobList.getJobList().size(); i++){
            DefaultMutableTreeNode tempTree = new DefaultMutableTreeNode(JobList.getJobList().get(i+1).getJobName().toString());
            tempTree.add(new DefaultMutableTreeNode(JobList.getJobList().get(i+1).getDeveloper().toString()));
            tempTree.add(new DefaultMutableTreeNode(JobList.getJobList().get(i+1).getJobSize().toString()));
            tempTree.add(new DefaultMutableTreeNode(JobList.getJobList().get(i+1).getModifiedDate().toString()));
            tempTree.add(new DefaultMutableTreeNode(JobList.getJobList().get(i+1).getVersion().toString()));
            rootNode.add(tempTree);
        }

        model.reload();

    }
}
