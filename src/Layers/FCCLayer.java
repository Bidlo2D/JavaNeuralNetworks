package Layers;

import Collector.Initializations.Generation;
import Layers.Activation.Functions.IFunction;
import Layers.Activation.Functions.Softmax;
import SimpleClasses.Signal;

public class FCCLayer extends FCHLayer {
    private double loss = 0;

    public FCCLayer(int countNeurons, IFunction typeActivation) {
        super(countNeurons, typeActivation);
    }
    @Override
    public Signal BackPropagation(Signal delta, int Right, double E, double A) {
        // Подсчет ошибок.
        double error = 0; loss = 0;
        Signal deltaOutput = new Signal(output.sizeZ, output.sizeX, output.sizeY);
        for (int i = 0; i < output.sizeZ; i++)
        {
            if (i == Right)
            {
                error = 1 - output.getValueSignal(i, 0, 0); //Подсчет ошибки(Положительный)
                double dout = error * typeActivation.Derivative(output.getSignal(i, 0, 0));
                deltaOutput.setValueSignal(i, 0, 0, dout);
            }
            else
            {
                error = 0 - output.getValueSignal(i, 0, 0); //Подсчет ошибки(Отрицательный)
                double dout = error * typeActivation.Derivative(output.getSignal(i, 0, 0));
                deltaOutput.setValueSignal(i, 0, 0, dout);
            }
            loss += Math.pow(error, 2);
        }
        loss /= output.sizeZ;

        //Обновление весов
        return super.BackPropagation(deltaOutput, Right, E, A);
    }

    @Override
    protected void Initialization(){
        int sizeZ = input.fullSize();
        output = new Signal(countNeurons, 1, 1);
        if(typeActivation.getClass().getSimpleName().equals("Softmax")){
            Softmax castedDog = (Softmax) typeActivation;
            castedDog.output = output;
        }
        corrections = new Signal(sizeZ, 1,1);
        biases = Generation.RandomSignal(sizeZ, 1 , 1 , 0, 0, 0.1);
        weights = Generation.RandomWeight(countNeurons, sizeZ, -1, 1);
    }
}
