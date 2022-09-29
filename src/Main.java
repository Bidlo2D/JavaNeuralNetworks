import SimpleClasses.Signal;

public class Main {
    public static void main (String[] args){
        Signal signal = new Signal(2,3,5);
        signal.setSignal(0,0,0, 0.335);
        System.out.println(signal.getSignal(0,0,0));
    }
}
