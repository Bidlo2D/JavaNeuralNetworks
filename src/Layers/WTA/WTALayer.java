package Layers.WTA;
import Collector.Initializations.Generation;
import Collector.Network;
import Layers.Activation.Functions.Function;
import Layers.Activation.Functions.Softmax;
import Layers.FullyLayers.FCHLayer;
import SimpleClasses.ComputingUnits.NeuronFC;
import SimpleClasses.ComputingUnits.NeuronWTA;
import SimpleClasses.Signal;

import java.lang.reflect.InvocationTargetException;

public class WTALayer extends FCHLayer {
    protected Signal<NeuronWTA> output;
    protected double loss = 0;

    public WTALayer(int countNeurons, Function typeActivation) {
        super(countNeurons, typeActivation);
        //this.sourceNet = sourceNet;
    }

    @Override
    public Signal forward(Signal input) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        this.input = input;
        if(weights == null ||
                biases == null ||
                corrections == null ||
                output == null) { initialization(); }
        for (int w1 = 0; w1 < weights.n; w1++)
        {
            double Sum = biases.getValueSignal(w1, 0, 0);
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                Sum += Math.pow(input.getValueSignal(w2, 0, 0) - weights.getWeight(w1, w2), 2);
            }

            output.setValueSignal(w1, 0, 0, Math.sqrt(Sum));
            output.getSignal(0,0,0).plus();
        }
        activation(output);
        return output;
    }

    @Override
    protected void activation(Signal activation) {
        var winner = output.max();
        int indexWin = output.indexOf(winner);
        output.allZero();
        winner.setValue(1);
    }

    public Signal backPropagation(Signal delta, int right, double E, double A) {
        //delta = errorCounting();
        //Signal deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY);
        for (int w1 = 0; w1 <= 1; w1++)
        {
            //double df = 0;
            for (int w2 = 0; w2 < weights.m; w2++)
            {
                //df += weights.getWeight(w1, w2) * Math.exp(-sourceNet.getCurrentEpoth() / sourceNet.getEpoth());
                double oldW = weights.getWeight(w1, w2);
                double updateW = oldW + E * (input.getValueSignal(w2, 0 , 0) - oldW);
                weights.setWeight(w1, w2, updateW);
            }
        }
        return null;
    }

    protected Signal errorCounting() {
        double error = 0; loss = 0;
        Signal deltaOutput = new Signal(output.sizeZ, output.sizeX, output.sizeY);
        return deltaOutput;
    }

    @Override
    protected void initialization() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int sizeZ = input.fullSize();
        Class s = Class.forName("SimpleClasses.ComputingUnits.NeuronWTA");
        output = new Signal(s, countNeurons, 1, 1);
        deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY);
        if(typeActivation.getClass().getSimpleName().equals("Softmax")){
            Softmax castedDog = (Softmax) typeActivation;
            castedDog.output = output;
        }
        corrections = new Signal(countNeurons, 1,1);
        biases = Generation.RandomSignal(countNeurons, 1 , 1 , 0, 0, 0.1);
        weights = Generation.RandomWeight(countNeurons, sizeZ, -0.05, 0.05);
    }

}
