package Layers;

import Collector.Initializations.Generation;
import Layers.Activation.ActivationFunctions;
import Layers.Activation.DeactivationFunctions;
import Layers.Activation.TypeActivation;
import SimpleClasses.Signal;

public class FCCLayer extends FCHLayer {
    //private double[] Error;
    private double loss = 0;

    public FCCLayer(int countNeurons, TypeActivation typeActivation) {
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
                error = 1 - output.getSignal(i, 0, 0); //Подсчет ошибки(Положительный)
                double dout = error * DeactivationFunctions.Deactivation(typeActivation, output.getSignal(i, 0, 0), output);
                deltaOutput.setSignal(i, 0, 0, dout);
            }
            else
            {
                error = 0 - output.getSignal(i, 0, 0); //Подсчет ошибки(Отрицательный)
                double dout = error * DeactivationFunctions.Deactivation(typeActivation, output.getSignal(i, 0, 0), output);
                deltaOutput.setSignal(i, 0, 0, dout);
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
        corrections = new Signal(countNeurons, 1,1);
        biases = Generation.RandomSignal(countNeurons, 1 , 1 , 0);
        weights = Generation.RandomWeight(countNeurons, sizeZ);
    }
}
