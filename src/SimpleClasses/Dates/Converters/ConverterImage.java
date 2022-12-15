package SimpleClasses.Dates.Converters;

import SimpleClasses.Dates.Converters.Exceptions.NoDirectoryException;
import SimpleClasses.Dates.Converters.Exceptions.Normalization;
import SimpleClasses.Dates.FormatImages;
import SimpleClasses.Signal;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConverterImage {
    public List<Signal> dates = new ArrayList();
    // Pattern pattern = Pattern.compile("А.+а"); TODO: pattern format file

    public ConverterImage(String pathDir) throws NoDirectoryException {
        File dir = new File(pathDir);
        if (!dir.isDirectory()) {
            throw new NoDirectoryException("Путь не является директорией!");
        }
        File[] files = dir.listFiles();

        for(int b = 0; b < files.length; b++) {
            if(isImage(files[b])){
                dates.add(ReadImageRGB(files[b]));
            }
        }
    }
    private Signal ReadImageRGB(File file){
        try {
            BufferedImage img = ImageIO.read(file);
            var name = file.getName();
            //var r = pattern.matches();
            //if(pattern.matches("")){ TODO: throw InvalidFormat

            //}
            int answer = Integer.parseInt(name.split("\\.")[0].split(" - ")[1]);
            Signal imgSignal = new Signal(3, img.getHeight(), img.getWidth(), answer);
            for(int x = 0; x < img.getHeight(); x++) {
                for(int y = 0; y < img.getWidth(); y++) {
                    var color = getPixelData(img, x, y);
                    for(int z = 0; z < color.length; z++) {
                        var value = Normalization.ZeroToOne((double) color[z], 0.0, 255.0);
                        imgSignal.setValueSignal(z, x, y, value);
                    }
                }
            }
            return imgSignal;
        } catch (IOException e) {
            return null;
        }
    }

    private int[] getPixelData(BufferedImage img, int x, int y) {
        int argb = img.getRGB(x, y);

        int rgb[] = new int[]{
                (argb >> 16) & 0xff, //red
                (argb >> 8) & 0xff, //green
                (argb) & 0xff  //blue
        };
        return rgb;
    }

    private boolean isImage(File file) {
        for (FormatImages format : FormatImages.values()) {
            if (file.getName().endsWith("." + format.toString())) {
                return true;
            }
        }
        return false;
    }
}
