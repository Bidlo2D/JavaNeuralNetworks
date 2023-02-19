package SimpleClasses.ComputingUnits;

public interface INeuron {

    void setValue(double value);

    double getValue();

    static int compare (INeuron n1, INeuron n2) {
        if(n1.getValue()> n2.getValue())
            return 1;
        else if(n1.getValue()< n2.getValue())
            return -1;
        else
            return 0;
    }
}
