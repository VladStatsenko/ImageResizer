import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import org.imgscalr.*;

public class ImageResizer implements Runnable {

    private File[] files;
    private int newWidth;
    private String dstFolder;
    private long start;

    public ImageResizer(File[] files, int newWidth, String dstFolder, long start) {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    public void run() {
        try
        {
            for(File file : files)
            {
                BufferedImage image = ImageIO.read(file);
                if(image == null) {
                    continue;
                }

                int newWidth = 300;
                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );
                BufferedImage newImage = Scalr.resize(image,Scalr.Method.QUALITY,Scalr.Mode.FIT_TO_WIDTH,newWidth,newHeight,Scalr.OP_ANTIALIAS);

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(newImage, "jpg", newFile);
                System.out.println(Thread.currentThread().getName() + " Обработка.." + newFile);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Finished: " + (System.currentTimeMillis()-start));
    }
}
