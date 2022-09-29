package Layers;

import Collector.Network;
import Layers.Activation.ActivationFunctions;
import Layers.Activation.TypeActivation;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

public class FCLayer extends Layer {
    private Weight weights;
    private Signal bias;
    private TypeActivation typeActivation;
    public FCLayer(int countNeurons, TypeActivation typeActivation){
        Network.Initialization();
        GenerationWB();
    }
    private void GenerationWB(){
        for (int j = 0; j < weights.m; j++)
        {
            bias.setSignal(j, 0, 0, 0.001);
            for (int i = 0; i < weights.n; i++)
            {
                weights.setWeight(i, j, 0);
            }
        }
    }
    @Override
    public void Forward(Signal input) {
        this.input = input;
        for (int w1 = 0; w1 < weights.m; w1++)
        {
            double Sum = bias.getSignal(w1, 0, 0);
            for (int w2 = 0; w2 < weights.n; w2++)
            {
                Sum += input.getSignal(w2, 0, 0) * weights.getWeight(w2, w1);
            }
            double result = ActivationFunctions.Activation(typeActivation, Sum, input, output, w1);
            output.setSignal(w1,0,0, result);
        }
    }

    @Override
    public void BackPropagation() {

    }
}
