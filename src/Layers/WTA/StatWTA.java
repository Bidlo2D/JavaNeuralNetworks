package Layers.WTA;

import SimpleClasses.ComputingUnits.NeuronFC;

public class StatWTA {
    public int[] victories;
    public int indexWin;
    public int DQR;
    public double fineRate;

    public StatWTA(int size, int DQR, double fineRate){
        this.DQR = DQR;
        this.fineRate = fineRate;
        victories = new int[size];
    }

    public void fine(NeuronFC n, int index) {
        if(victories[index] >= DQR)
        {
            n.setValue(-1);
            victories[index] = 0;
        }
    }
}
