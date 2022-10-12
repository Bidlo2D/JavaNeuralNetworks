package Layers;

import Collector.Initializations.Generation;
import Layers.Activation.ActivationFunctions;
import Layers.Activation.DeactivationFunctions;
import Layers.Activation.TypeActivation;
import SimpleClasses.Signal;

public class FCCLayer extends FCHLayer {
    private double[] Error;
    private double loss = 0;

    public FCCLayer(int countNeurons, TypeActivation typeActivation) {
        super(countNeurons, typeActivation);
    }

    @Override
    public Signal BackPropagation(Signal delta, int Right, double E, double A) {
        // Подсчет ошибок.
        loss = 0;
        Signal deltaOutput = new Signal(output.sizeZ, output.sizeX, output.sizeY);
        for (int i = 0; i < output.sizeZ; i++)
        {
            if (i == Right)
            {
                Error[i] = 1 - output.getSignal(i, 0, 0);//Подсчет ошибки(Положительный)
                double grad = Error[i] * DeactivationFunctions.Deactivation(typeActivation, output.getSignal(i, 0, 0), output);
                deltaOutput.setSignal(i, 0, 0, grad);
            }
            else
            {
                Error[i] = 0 - output.getSignal(i, 0, 0);//Подсчет ошибки(Отрицательный)
                double grad = Error[i] * DeactivationFunctions.Deactivation(typeActivation, output.getSignal(i, 0, 0), output);
                deltaOutput.setSignal(i, 0, 0, grad);
            }
            loss += Math.pow(Error[i], 2);
        }
        loss /= output.sizeZ;

        //Обновление весов
        return super.BackPropagation(deltaOutput, Right, E, A);
    }
    @Override
    protected void Initialization(){
        int sizeZ = input.fullSize();
        Error = new double[countNeurons];
        output = new Signal(countNeurons, 1, 1);
        corrections = new Signal(sizeZ, 1,1);
        biases = Generation.RandomSignal(sizeZ, 1 , 1 , 0);
        weights = Generation.RandomWeight(sizeZ, countNeurons);
    }
}
