package Layers.FullyLayers;

import Collector.Initializations.Generation;
import Layers.Activation.Functions.Function;
import Layers.Activation.Functions.Softmax;
import Layers.Enums.TypeLayer;
import SimpleClasses.Signal;

public class FCCLayer extends FCHLayer {
    private double loss = 0;

    public FCCLayer(int countNeurons, Function typeActivation) {
        super(countNeurons, typeActivation);
    }
    @Override
    public Signal BackPropagation(Signal delta, int right, double E, double A) {
        // Подсчет ошибок.
        double error = 0; loss = 0;
        Signal deltaOutput = new Signal(output.sizeZ, output.sizeX, output.sizeY, true);
        for (int i = 0; i < output.sizeZ; i++)
        {
            if (i == right)
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
        return super.BackPropagation(deltaOutput, right, E, A);
    }

    @Override
    protected void Initialization(){
        int sizeZ = input.fullSize();
        output = new Signal(countNeurons, 1, 1, true);
        if(typeActivation.getClass().getSimpleName().equals("Softmax")){
            Softmax castedDog = (Softmax) typeActivation;
            castedDog.output = output;
        }
        corrections = new Signal(sizeZ, 1,1, true);
        biases = Generation.RandomSignal(sizeZ, 1 , 1 , 0, 0, 0.1);
        weights = Generation.RandomWeight(countNeurons, sizeZ, -1, 1);
    }
}
