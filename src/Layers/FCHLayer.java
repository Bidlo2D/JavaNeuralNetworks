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
        if(weights == null || biases == null){ Initialization(); }
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
    public Signal BackPropagation(Signal delta, int Right, double E, double A) {
        Signal deltaOutput = new Signal(output.sizeZ, output.sizeX, output.sizeY);
        for (int i = 0; i < weights.m; i++)
        {
            double df = 0;
            for (int j = 0; j < weights.n; j++)
            {
                double dout = DeactivationFunctions.Deactivation(typeActivation, output.getSignal(j, 0, 0), output); //производная функции активации
                df += dout * delta.getSignal(i, 0, 0); //градиент для смещений
                double gradient = (dout * (weights.getWeight(j, i) * delta.getSignal(i, 0, 0))) + deltaOutput.getSignal(j, 0, 0);
                deltaOutput.setSignal(j, 0, 0, gradient); //Градиент для следующего слоя
                double GRADw = output.getSignal(j, 0, 0) * delta.getSignal(i, 0, 0); //Градиент данного слоя
                double gradientNext = E * GRADw + A * corrections.getSignal(j, 0, 0);
                corrections.setSignal(j, 0, 0, gradientNext); //Посчет обновления весов по градиенту и коф.обучения
                weights.setWeight(j, i, weights.getWeight(j, i) + corrections.getSignal(j, 0, 0)); //Обновление весов
            }
            biases.setSignal(i, 0, 0, (df * E) + biases.getSignal(i, 0, 0)); //Обновление смещений
        }
        return deltaOutput;
    }

    protected void Initialization(){
        int sizeZ = input.fullSize();
        output = new Signal(sizeZ, 1, 1);
        corrections = new Signal(sizeZ, 1,1);
        biases = Generation.RandomSignal(sizeZ, 1 , 1 , 0);
        weights = Generation.RandomWeight(sizeZ, countNeurons);
    }
}
