package Layers;

import Collector.Initializations.Generation;
import Layers.Activation.ActivationFunctions;
import Layers.Activation.TypeActivation;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

public class FCHLayer extends Layer {
    private Weight weights;
    private Signal biases;
    private TypeActivation typeActivation;
    public int countNeurons;
    public FCHLayer(int countNeurons, TypeActivation typeActivation){
        this.countNeurons = countNeurons;
        this.typeActivation = typeActivation;
    }

    @Override
    public Signal Forward(Signal input) {
        this.input = input;
        if(weights == null){ Initialization(); }
        for (int w1 = 0; w1 < weights.m; w1++)
        {
            double Sum = biases.getSignal(w1, 0, 0);
            for (int w2 = 0; w2 < weights.n; w2++)
            {
                Sum += input.getSignal(w2, 0, 0) * weights.getWeight(w2, w1);
            }
            double result = ActivationFunctions.Activation(typeActivation, Sum, input, output, w1);
            output.setSignal(w1,0,0, result);
        }
        return output;
    }

    @Override
    public void BackPropagation() {

    }

    private void Initialization(){
        int sizeZ = input.fullSize();
        output = new Signal(sizeZ, 1, 1);
        biases = Generation.RandomSignal(sizeZ, 1 , 1 , 0); //new Signal(sizeZ,1,1);
        weights = Generation.RandomWeight(sizeZ, countNeurons); //new Weight(sizeZ, countNeurons);
    }
}
