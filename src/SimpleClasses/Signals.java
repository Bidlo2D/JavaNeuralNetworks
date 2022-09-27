package SimpleClasses;

public class Signals {
     // Data
     private Neuron[] neurons;
     // Sizes
     private int sizeZ, sizeX, sizeY, right;
     private int DW (){ return sizeZ * sizeX; }
     private int FullSize () {  return sizeZ * sizeX * sizeY; }
     // Indexer
     public double getSignal(int z, int x, int y){
          return neurons[x * DW() + y * sizeZ + z].getValue();
     }
     public void setSignal(int z, int x, int y, double value){
          neurons[x * DW() + y * sizeZ + z].setValue(value);
     }
     // Constructors
     public Signals(int z, int x, int y){
          this.sizeZ = z; this.sizeX = x; this.sizeY = y;
          neurons = new Neuron[z * x * y];
          for(int i = 0; i < neurons.length - 1; i++) {
               neurons[i] = new Neuron();
          }
          System.out.println("");
     }

}

