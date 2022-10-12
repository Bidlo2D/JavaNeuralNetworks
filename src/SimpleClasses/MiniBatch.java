package SimpleClasses;

import Collector.Initializations.Generation;

public class MiniBatch {
    public Signal[] signals;
    public MiniBatch(int size){
        signals = new Signal[size];
        for(int s = 0; s < size; s++){
            signals[s] = Generation.RandomSignal(3,2,2,0);
        }
    }
}
