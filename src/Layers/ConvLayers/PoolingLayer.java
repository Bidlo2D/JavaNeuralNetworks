package Layers.ConvLayers;

import Layers.Layer;
import SimpleClasses.Signal;

public class PoolingLayer extends Layer {
    private Signal mask;
    private int scale = 2;
    public PoolingLayer(int scale){ this.scale = scale; }
    @Override
    public Signal Forward(Signal input) {
        this.input = input;
        if(mask == null ||
           output == null){ Initialization(input); }
        // проходимся по каждому из каналов
        for (int d = 0; d < input.sizeZ; d++) {
            for (int i = 0; i < input.sizeX; i += scale) {
                for (int j = 0; j < input.sizeY; j += scale) {
                    int imax = i; // индекс строки максимума
                    int jmax = j; // индекс столбца максимума
                    double max = input.getValueSignal(d, i, j); // начальное значение максимума - значение первой клетки подматрицы

                    // проходимся по подматрице и ищем максимум и его координаты
                    for (int y = i; y < i + scale; y++) {
                        for (int x = j; x < j + scale; x++) {
                            double value = input.getValueSignal(d, y, x); // получаем значение входного тензора

                            // если очередное значение больше максимального
                            if (value > max)
                                max = value; // обновляем максимум
                        }
                    }

                    output.setValueSignal(d, i / scale, j / scale, max); // записываем в выходной тензор найденный максимум
                    mask.setValueSignal(d, imax, jmax, 1); // записываем 1 в маску в месте расположения максимального элемента
                }
            }
        }

        return output; // возвращаем выходной тензор
    }

    @Override
    public Signal BackPropagation(Signal delta, int Right, double E, double A) {
        Signal deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY); // создаём тензор для градиентов

        for (int d = 0; d < input.sizeZ; d++)
            for (int i = 0; i < input.sizeX; i++)
                for (int j = 0; j < input.sizeY; j++){
                    double value = delta.getValueSignal(d, i / scale, j / scale) * mask.getValueSignal(d, i, j);
                    deltaOutput.setValueSignal(d, i, j, value);
                }

        return deltaOutput; // возвращаем посчитанные градиенты
    }

    private void Initialization(Signal input) {
        output = new Signal(input.sizeZ, input.sizeX / scale, input.sizeY / scale);
        mask = new Signal(input.sizeZ, input.sizeX, input.sizeY);
    }
}
