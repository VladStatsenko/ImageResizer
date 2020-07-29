import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static int newWidht = 300;

    public static void main(String[] args) throws InterruptedException {
        String srcFolder = "srcImage";
        String dstFolder = "dstImage";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int proc = Runtime.getRuntime().availableProcessors();
        int fourth = files.length /proc;
        int additionalFile = files.length%proc;

        List<Thread> threads = new LinkedList<Thread>();

        for (int i = 0; i < proc; i++) {

            if (i < additionalFile) {
                File[] files1 = new File[fourth + 1];
                int index = i * (fourth + 1);
                System.arraycopy(files, index, files1, 0, files1.length);
                threads.add(new Thread(new ImageResizer(files1, newWidht, dstFolder, start)));

            } else {

                File[] files2 = new File[fourth];
                int index = additionalFile*(fourth+1) + (i - additionalFile - 1) * fourth;
                System.arraycopy(files, index, files2, 0, files2.length);
                threads.add(new Thread(new ImageResizer(files2, newWidht, dstFolder, start)));
            }
        }
        for (Thread thread:threads){
            thread.start();
        }
        for (Thread thread: threads){
            thread.join();
        }
        System.out.println("Duration: " + (System.currentTimeMillis()-start));
    }

}


