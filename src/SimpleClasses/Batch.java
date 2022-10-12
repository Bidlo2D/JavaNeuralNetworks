package SimpleClasses;

import Collector.Initializations.Generation;

public class Batch {
    public MiniBatch[] miniBatches;
    public Batch(int sizeB, int sizeS){
        miniBatches = new MiniBatch[sizeB];
        for(int b = 0; b < sizeB; b++){
            miniBatches[b] = new MiniBatch(sizeS);
        }
    }
}
