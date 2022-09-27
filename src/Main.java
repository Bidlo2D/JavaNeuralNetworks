import SimpleClasses.Signals;

public class Main {
    public static void main (String[] args){
        Signals signals = new Signals(2,3,5);
        signals.setSignal(0,0,0, 0.335);
        System.out.println(signals.getSignal(0,0,0));
    }
}
