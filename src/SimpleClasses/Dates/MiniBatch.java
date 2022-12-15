package SimpleClasses.Dates;

import Collector.Initializations.Generation;
import SimpleClasses.Signal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MiniBatch implements Serializable {
    public List<Signal> signals = new ArrayList();
}
