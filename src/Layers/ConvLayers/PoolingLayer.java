package Layers.ConvLayers;

import Layers.Enums.TypeLayer;
import Layers.Layer;
import SimpleClasses.Signal;

import java.io.Serializable;

public class PoolingLayer extends Layer {
    private Signal mask;
    private int scale = 2;
    public PoolingLayer(int scale){
        this.scale = scale;
    }
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
                            if (x< 0 || x >= input.sizeX || y < 0 || y >= input.sizeY)
                                continue;
                            double value = input.getValueSignal(d, y, x); // получаем значение входного тензора
                            mask.setValueSignal(d, imax, jmax, 0);

                            // если очередное значение больше максимального
                            if (value > max){
                                max = value; // обновляем максимум
                                imax = y; // обновляем индекс строки максимума
                                jmax = x; // обновляем индекс столбца максимума
                            }
                        }
                    }
                    int x = i / scale; int y = j / scale;
                    if (x< 0 || x >= output.sizeX || y < 0 || y >= output.sizeY)
                        continue;
                    output.setValueSignal(d, x, y, max); // записываем в выходной тензор найденный максимум
                    mask.setValueSignal(d, imax, jmax, 1); // записываем 1 в маску в месте расположения максимального элемента
                }
            }
        }

        return output; // возвращаем выходной тензор
    }

    @Override
    public Signal BackPropagationTeacher(Signal delta, int Right, double E, double A) {
        Signal deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY, true); // создаём тензор для градиентов

        for (int d = 0; d < input.sizeZ; d++)
            for (int i = 0; i < input.sizeX; i++)
                for (int j = 0; j < input.sizeY; j++){
                    if (i< 0 || i >= delta.sizeX || j < 0 || j >= delta.sizeY)
                        continue;
                    double value = delta.getValueSignal(d, i / scale, j / scale) * mask.getValueSignal(d, i, j);
                    deltaOutput.setValueSignal(d, i, j, value);
                }

        return deltaOutput; // возвращаем посчитанные градиенты
    }

    @Override
    public Signal BackPropagationNoTeacher(Signal delta, double E, double A) {
        return null;
    }

    private void Initialization(Signal input) {
        output = new Signal(input.sizeZ, input.sizeX / scale, input.sizeY / scale, true);
        mask = new Signal(input.sizeZ, input.sizeX, input.sizeY, true);
    }
}
