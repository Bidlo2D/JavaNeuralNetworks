package SimpleClasses;

import SimpleClasses.ComputingUnits.INeuron;
import SimpleClasses.ComputingUnits.NeuronFC;

import java.io.Serializable;
import java.util.*;

public class Signal<T extends INeuron> implements Serializable {
     private T[] neurons;
     // Sizes
     public int sizeZ = 0, sizeX = 0, sizeY = 0, right = 0;
     public int DW () { return sizeZ * sizeX; }
     public int fullSize () {  return sizeZ * sizeX * sizeY; }
     // Max and Min
     public T max()
     {
          return Arrays.stream(neurons).max(INeuron::compare).get();
     }

     public T min()
     {
          return Arrays.stream(neurons).min(INeuron::compare).get();
     }
     // Answer
     public Map<Integer, Double> getAnswer()
     {
          var max = Arrays.stream(neurons).max(INeuron::compare).get();
          var index = indexOf(max);
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
     public T getSignal(int z, int x, int y){
          return neurons[x * DW() + y * sizeZ + z];
     }
     public T[] getCloneSignals(){
          var massLen = Arrays.stream(neurons).map(x->new NeuronFC(x.getValue())).toArray(INeuron[]::new);
          return (T[]) massLen;
     }
     public void setSignal(int z, int x, int y, INeuron value){
          neurons[x * DW() + y * sizeZ + z] = (T) value;
     }
     public void setValueVector(List<Double> list){
          if(list.size() != sizeZ){ return; }
          for(int i = 0; i < list.size(); i++){
               neurons[i] = (T) new NeuronFC(list.get(i));
          }
     }
     //IndexOf
     public int indexOf(INeuron x){
          return Arrays.asList(neurons).indexOf(x);
     }
     // Actions
     public void allZero(){ Arrays.stream(neurons).forEach(n -> n.setValue(0)); }

     public void fill(){
          neurons = (T[]) new INeuron[fullSize()];
          var s = neurons.getClass().getComponentType();

          for (int i = 0; i <  neurons.length; i++) {
               neurons[i] = (T) new NeuronFC(0);
          }
     }
     // Constructors
     public Signal(){}

     public Signal(List<INeuron> list) {
          this.sizeZ = list.size();
          this.sizeX = 1;
          this.sizeY = 1;
          neurons = (T[]) list.toArray(INeuron[]::new);
     }

     public Signal(List<INeuron> list, int right) {
          this.sizeZ = list.size();
          this.sizeX = 1;
          this.sizeY = 1;
          this.right = right;
          neurons = (T[]) list.toArray(INeuron[]::new);
     }

     public Signal(int z, int x, int y) {
          this.sizeZ = z; this.sizeX = x; this.sizeY = y;
          fill();
     }

     public Signal(int z, int x, int y, int right) {
          this.sizeZ = z;
          this.sizeX = x;
          this.sizeY = y;
          this.right = right;
          fill();
     }
}

