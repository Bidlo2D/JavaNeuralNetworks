package Layers;

import Collector.Initializations.Generation;
import Layers.Activation.ActivationFunctions;
import Layers.Activation.DeactivationFunctions;
import Layers.Activation.TypeActivation;
import SimpleClasses.Signal;
import SimpleClasses.Weight;

public class FCHLayer extends Layer {
    protected Weight weights;
    protected Signal biases;
    protected Signal corrections;
    public int countNeurons;
    public FCHLayer(int countNeurons, TypeActivation typeActivation){
        this.countNeurons = countNeurons;
        this.typeActivation = typeActivation;
    }

    @Override
    public Signal Forward(Signal input) {
        this.input = input;
        if(weights == null ||
           biases == null ||
           corrections == null ||
           output == null) { Initialization(); }

        for (int w1 = 0; w1 < weights.n; w1++)
        {
            double Sum = biases.getSignal(w1, 0, 0);
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                Sum += input.getSignal(w2, 0, 0) * weights.getWeight(w1, w2);
            }
            double result = ActivationFunctions.Activation(typeActivation, Sum, input, output, w1);
            output.setSignal(w1,0,0, result);
        }
        return output;
    }

    @Override
    public Signal BackPropagation(Signal delta, int Right, double E, double A) {
        Signal deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY);
        for (int i = 0; i < weights.n; i++)
        {
            double df = 0;
            for (int j = 0; j < weights.m; j++)
            {
                double dout = DeactivationFunctions.Deactivation(typeActivation, input.getSignal(j, 0, 0), input);
                df += dout * delta.getSignal(i, 0, 0);
                double gradient = (dout * (weights.getWeight(i, j) * delta.getSignal(i, 0, 0))) + deltaOutput.getSignal(j, 0, 0);
                deltaOutput.setSignal(j, 0, 0, gradient);
                double GRADw = input.getSignal(j, 0, 0) * delta.getSignal(i, 0, 0);
                double gradientNext = E * GRADw + A * corrections.getSignal(i, 0, 0);
                corrections.setSignal(i, 0, 0, gradientNext);
                weights.setWeight(i, j, weights.getWeight(i, j) + corrections.getSignal(i, 0, 0));
                biases.setSignal(i, 0, 0, (df * E) + biases.getSignal(i, 0, 0));
            }
        }
        return deltaOutput;
    }

    protected void Initialization(){
        int sizeZ = input.fullSize();
        output = new Signal(countNeurons, 1, 1);
        corrections = new Signal(countNeurons, 1,1);
        biases = Generation.RandomSignal(countNeurons, 1 , 1 , 0);
        weights = Generation.RandomWeight(countNeurons, sizeZ);
    }
}
