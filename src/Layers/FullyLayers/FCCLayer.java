package Layers.FullyLayers;

import Layers.Activation.Functions.Function;
import SimpleClasses.Signal;

public class FCCLayer extends FCHLayer {
    protected double loss = 0;

    public FCCLayer(int countNeurons, Function typeActivation) {
        super(countNeurons, typeActivation);
    }
    @Override
    public Signal backPropagation(Signal delta, int right, double E, double A) {
        // Подсчет ошибок.
        Signal deltaOutput = errorCounting(right);
        //Обновление весов
        return super.backPropagation(deltaOutput, right, E, A);
    }

    protected Signal errorCounting(int right) {
        double error = 0; loss = 0;
        Signal deltaOutput = new Signal(output.sizeZ, output.sizeX, output.sizeY);
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
        return deltaOutput;
    }

}
