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
    private Font font;

    public JobListTree() {
    }

    public void init() {
        font = new Font("Courier New", Font.BOLD, 14);
        rootNode = new DefaultMutableTreeNode("Job List");
        jTree = new JTree(rootNode);
        jTree.setFont(font);
        scrollPane = new JScrollPane(jTree);
        scrollPane.setPreferredSize(new Dimension(Configuration.WIDTH / 3 - 40, 200));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.setBackground(Color.WHITE);
        this.add(scrollPane);
    }

    public void repaintTree() {
        model = (DefaultTreeModel) jTree.getModel();
        for (int i = 0; i<JobList.getJobList().size(); i++){
            DefaultMutableTreeNode tempTree = new DefaultMutableTreeNode("Job Name  : " + JobList.getJobList().get(String.valueOf(i)).getJobName().toString());
            tempTree.add(new DefaultMutableTreeNode(                     "Developer : " + JobList.getJobList().get(String.valueOf(i)).getDeveloper().toString()));
            tempTree.add(new DefaultMutableTreeNode(                     "Job Size  : " + JobList.getJobList().get(String.valueOf(i)).getJobSize().toString() + " KB"));
            tempTree.add(new DefaultMutableTreeNode(                     "Modified  : " + JobList.getJobList().get(String.valueOf(i)).getModifiedDate().toString()));
            tempTree.add(new DefaultMutableTreeNode(                     "Version   : " + JobList.getJobList().get(String.valueOf(i)).getVersion().toString()));
            rootNode.add(tempTree);
        }

        JobList.getJobList().clear();
        model.reload();
        rootNode.removeAllChildren();
    }
}
