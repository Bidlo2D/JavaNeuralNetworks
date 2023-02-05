package SimpleClasses;

import java.io.Serializable;
import java.util.*;

public class Signal implements Serializable {
     // Data
     private Neuron[] neurons;
     // Sizes
     public int sizeZ = 0, sizeX = 0, sizeY = 0, right = 0;
     public int DW () { return sizeZ * sizeX; }
     public int fullSize () {  return sizeZ * sizeX * sizeY; }
     // Max and Min
     public double Max()
     {
          return Arrays.stream(neurons).max(Neuron::compare).get().getValue();
     }

     public double Min()
     {
          return Arrays.stream(neurons).min(Neuron::compare).get().getValue();
     }
     // Answer
     public Map<Integer, Double> getAnswer()
     {
          var max = Arrays.stream(neurons).max(Neuron::compare).get();
          var index = IndexOf(max);
          var value = max.getValue();
          Map<Integer, Double> answer = new HashMap<>();
          answer.put(index, value);
          return answer;
     }
     // Indexer
     public double getValueSignal(int z, int x, int y){
          return neurons[x * DW() + y * sizeZ + z].getValue();
     }
     public void setValueSignal(int z, int x, int y, double value){
          neurons[x * DW() + y * sizeZ + z].setValue(value);
     }
     // Values
     public Neuron getSignal(int z, int x, int y){
          return neurons[x * DW() + y * sizeZ + z];
     }
     public Neuron[] getCloneSignals(){
          var massLen = Arrays.stream(neurons).map(x->new Neuron(x.getValue())).toArray(Neuron[]::new);
          return massLen;
     }
     public void setSignal(int z, int x, int y, Neuron value){
          neurons[x * DW() + y * sizeZ + z] = value;
     }
     public void setValueVector(List<Double> list){
          if(list.size() != sizeZ){ return; }
          for(int i = 0; i < list.size(); i++){
               neurons[i] = new Neuron(list.get(i));
          }
     }
     //IndexOf
     public int IndexOf(Neuron x){
          return Arrays.asList(neurons).indexOf(x);
     }
     // Constructors
     public Signal(){}
     public Signal(List<Neuron> list, int right) {
          this.sizeZ = list.size();
          this.sizeX = 1;
          this.sizeY = 1;
          this.right = right;
          neurons = list.toArray(Neuron[]::new);
     }
     public Signal(int z, int x, int y, boolean fill) {
          this.sizeZ = z; this.sizeX = x; this.sizeY = y;
          neurons = new Neuron[z * x * y];
          if(fill) {
               for (int i = 0; i < neurons.length; i++) {
                    neurons[i] = new Neuron();
               }
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

