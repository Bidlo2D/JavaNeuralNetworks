package SimpleClasses;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Signal {
     // Data
     private Neuron[] neurons;
     // Sizes
     public int sizeZ, sizeX, sizeY, right;
     public int DW () { return sizeZ * sizeX; }
     public int fullSize () {  return sizeZ * sizeX * sizeY; }
     //public int
     public Map<Integer, Double> getAnswer()
     {
          var max = Arrays.stream(neurons).max(Neuron::compare).get();
          var index = Arrays.asList(neurons).indexOf(max);
          var value = max.getValue();
          Map<Integer, Double> answer = new HashMap<>();
          answer.put(index, value);
          return answer;
     }
     // Indexer
     public double getSignal(int z, int x, int y){
          return neurons[x * DW() + y * sizeZ + z].getValue();
     }
     public void setSignal(int z, int x, int y, double value){
          neurons[x * DW() + y * sizeZ + z].setValue(value);
     }
     // Constructors
     public Signal(){}
     public Signal(int z, int x, int y) {
          this.sizeZ = z; this.sizeX = x; this.sizeY = y;
          neurons = new Neuron[z * x * y];
          for(int i = 0; i < neurons.length; i++) {
               neurons[i] = new Neuron();
          }
     }
     public Signal(int z, int x, int y, int right) {
          this.sizeZ = z;
          this.sizeX = x;
          this.sizeY = y;
          this.right = right;
          neurons = new Neuron[z * x * y];
          for(int i = 0; i < neurons.length; i++) {
               neurons[i] = new Neuron();
          }
     }

}

