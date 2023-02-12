package Layers.WTA;
import Collector.Initializations.Generation;
import Layers.Activation.Functions.Function;
import Layers.Activation.Functions.Softmax;
import Layers.FullyLayers.FCHLayer;
import SimpleClasses.Neuron;
import SimpleClasses.Signal;

import java.util.HashMap;

public class WTALayer extends FCHLayer {
    HashMap<Integer, HashMap<Neuron, Integer>> victories = new HashMap();
    int nowWinner;
    public WTALayer(int countNeurons, Function typeActivation) {
        super(countNeurons, typeActivation);
    }


    @Override
    public Signal forward(Signal input) {
        this.input = input;
        if(weights == null ||
                biases == null ||
                corrections == null ||
                output == null) { initialization(); }
        for (int w1 = 0; w1 < weights.n; w1++)
        {
            Neuron Sum = new Neuron();
            Sum.setValue(biases.getValueSignal(w1, 0, 0));
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                double plus = input.getValueSignal(w2, 0, 0) * weights.getWeight(w1, w2);
                Sum.setValue(Sum.getValue() + plus);
            }
            output.setSignal(w1, 0, 0, Sum);
        }
        var winner = output.max();
        var index = output.indexOf(winner);
        addWinner(index, winner);
        output.allZero();
        winner.setValue(1);
        return output;
    }

    private void addWinner(Integer index, Neuron n){
        HashMap<Neuron, Integer> winner = new HashMap<>();
        if(victories.containsKey(index)){
            winner = victories.get(index);
            winner.put(n, winner.getOrDefault(n, 0) + 1);
        }
        else{
            winner.put(n, 1);
            victories.put(index, winner);
        }
        nowWinner = index;
    }

    @Override
    public Signal backPropagationTeacher(Signal delta, int right, double E, double A) {
        Signal deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY, true);
        for (int w1 = nowWinner; w1 < nowWinner; w1++)
        {
            double df = 0;
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                double derivative = typeActivation.Derivative(input.getSignal(w2, 0, 0));
                df += derivative * delta.getValueSignal(w1, 0, 0);
                double gradient = (derivative * (weights.getWeight(w1, w2) * delta.getValueSignal(w1, 0, 0)));
                deltaOutput.setValueSignal(w2, 0, 0, gradient + deltaOutput.getValueSignal(w2, 0, 0));
                double GRADw = input.getValueSignal(w2, 0, 0) * delta.getValueSignal(w1, 0, 0);
                double gradientNext = E * GRADw + A * corrections.getValueSignal(w1, 0, 0);
                corrections.setValueSignal(w1, 0, 0, gradientNext);
                weights.setWeight(w1, w2, weights.getWeight(w1, w2) + corrections.getValueSignal(w1, 0, 0));
            }
            biases.setValueSignal(w1, 0, 0, (df * E) + biases.getValueSignal(w1, 0, 0));
        }
        return deltaOutput;
    }

    @Override
    public Signal backPropagationNoTeacher(Signal delta, double E, double A) {
        return null;
    }
}
