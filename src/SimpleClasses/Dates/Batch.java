package SimpleClasses.Dates;

import SimpleClasses.Dates.ConverterImage.ConverterImage;
import SimpleClasses.Signal;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Batch {
    public List<MiniBatch> miniBatches = new ArrayList();

    public Batch(){}
    // Images to signals
    public Batch(List<Signal> dates, int sizeMini) {
        int countMini = dates.size() / sizeMini;
        int d = 0;
        // add general
        for(int m = 0; m < countMini; m++){
            MiniBatch miniBatch = new MiniBatch();
            for(int s = 0; s < sizeMini; s++){
                miniBatch.signals.add(dates.get(d)); d++;
            }
            miniBatches.add(miniBatch);
        }
        // add remains
        if(d < dates.size()){
            MiniBatch miniBatch = new MiniBatch();
            for(;d < dates.size(); d++){
                miniBatch.signals.add(dates.get(d)); d++;
            }
            miniBatches.add(miniBatch);
        }
        System.out.println();
    }

}
