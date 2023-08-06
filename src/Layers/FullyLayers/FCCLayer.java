package Layers.FullyLayers;

import Layers.Functions.Function;
import SimpleClasses.Signal;

public class FCCLayer extends FCHLayer {
    protected double loss = 0;

    public FCCLayer(int countNeurons, Function typeActivation) {
        super(countNeurons, typeActivation);
    }

    @Override
    public Signal backPropagation(Signal delta, int right, double E, double A) {
        // Подсчет ошибок
        Signal deltaOutput = errorCounting(right);
        // Обновление весов
        return super.backPropagation(deltaOutput, right, E, A);
    }

    protected Signal errorCounting(int right) {
        double loss = 0;
        Signal deltaOutput = new Signal(output.sizeZ, output.sizeX, output.sizeY);
        for (int i = 0; i < output.sizeZ; i++) {
            double target = (i == right) ? 1.0 : 0.0;
            double error = target - output.getValueSignal(i, 0, 0);
            double dout = error * typeActivation.derivative(output.getSignal(i, 0, 0));

            deltaOutput.setValueSignal(i, 0, 0, dout);

            loss += Math.pow(error, 2);
        }
        loss /= output.sizeZ;
        return deltaOutput;
    }

}
