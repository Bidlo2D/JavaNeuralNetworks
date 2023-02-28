package Layers.Functions.Influence;

public class WTA extends Kohonen {
    private int indexWin;
    @Override
    public void activation() {
        var winner = output.max();
        indexWin = output.indexOf(winner);
        winner.win();
    }
    @Override
    public double influence(int i) { return i != indexWin ? 0 : 1; }

}
