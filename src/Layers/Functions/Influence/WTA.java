package Layers.Functions.Influence;

import Layers.Functions.Kohonen;

public class WTA extends Kohonen {
    private int indexWin;
    @Override
    public double activation() {
        var winner = output.max();
        indexWin = output.indexOf(winner);
        winner.win();
        return 0;
    }
    @Override
    public double influence(int i) {
        return i != indexWin ? 0 : 1;
    }
}
