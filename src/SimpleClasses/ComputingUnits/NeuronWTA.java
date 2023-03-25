package SimpleClasses.ComputingUnits;

import java.io.Serializable;


public class NeuronWTA extends Neuron implements Serializable {

    private int countWins;

    private int winRate = 35;

    private boolean block = false;

    @Override
    public void setValue(double value) {
        this.value = block ? 0 : value;
    }

    @Override
    public double getValue() {
        if(countWins > winRate) { block = true;}
        if(block) {
            countWins -= 1;
            if(countWins == 0) { block = false; }
            return 0;
        }
        else {
            return value;
        }
    }

    public void win(){ countWins += 1; }

    public NeuronWTA() {  }
    public NeuronWTA(double value, int winRate) { this.value = value; this.winRate =  winRate; }
}
