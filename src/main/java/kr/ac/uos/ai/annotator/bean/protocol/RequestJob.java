package kr.ac.uos.ai.annotator.bean.protocol;

import lombok.Data;

import java.util.HashMap;

/**
 * @author Chan Yeon, Cho
 * @version 0.0.1 - SnapShot
 *          on 2015-12-15 enemy
 */
public @Data
class RequestJob {

    private HashMap<String, Integer> annotators;
    private String jobName;
    private String jobFileName;

    public RequestJob() {
        this.annotators = new HashMap<>();
        this.jobName = null;
    }

}
