package kr.ac.uos.ai.annotator.taskarchiver;

import kr.ac.uos.ai.annotator.activemq.Sender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class is written by Chan Yeon, Cho
 * AI-Laboratory, Seoul, Korea
 * 2015. 9. 15.
 */

public class TaskUnpacker {

    private Sender sdr;

    public TaskUnpacker() {
    }

    public void init() {
    }

    /**
     * @param pathName
     * @param byteArray
     */
    public void makeFileFromByteArray(String pathName, byte[] byteArray) {
        FileOutputStream fos;
        File dir = new File(System.getProperty("user.dir") + "\\lib\\");
        try {
            if (!dir.isDirectory()) {
                dir.mkdirs();
            } else {
                fos = new FileOutputStream(pathName);
                fos.write(byteArray);
                fos.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not make file to custom path name");
        } catch (IOException e) {
            System.out.println("Byte array error");
        }
    }

    public void setSender(Sender sdr) {
        this.sdr = sdr;
    }
}
