package Layers.ConvLayers;

import Collector.Initializations.Generation;
import Layers.Layer;
import SimpleClasses.Signal;

import java.util.List;

public class ConvolutionLayer extends Layer {
    private int stride = 0, matrix = 0, channel = 0, countCore = 0, sizeCX = 0, sizeCY, ratioPadding = 0;
    private Signal[] cores; // фильтры
    private Signal[] cBias; // градиенты фильтров
    private Signal db; //  градиенты смещений
    private Signal bias; // смещений

    public ConvolutionLayer(int matrix, int countCore, int stride, int ratioPadding){
        this.countCore = countCore;
        this.stride = stride;
        this.matrix = matrix;
        this.ratioPadding = ratioPadding;
    }

    @Override
    public List<Double> getWeightList() {
        return  null;
    }

    @Override
    public Signal forward(Signal input) {
        this.input = input;
        if(db == null ||
           bias == null ||
           cores == null ||
           cBias == null ||
           output == null) { initialization(input); }

        // проходимся по каждому из фильтров
        for (int f = 0; f < countCore; f++) {
            for (int y = 0; y < output.sizeY; y++) {
                for (int x = 0; x < output.sizeX; x++) {
                    double sum = bias.getValueSignal(f, 0, 0); // сразу прибавляем смещение

                    // проходимся по фильтрам
                    for (int i = 0; i < matrix; i++) {
                        for (int j = 0; j < matrix; j++) {
                            int i0 = stride * y + i - ratioPadding;
                            int j0 = stride * x + j - ratioPadding;

                            // поскольку вне границ входного тензора элементы нулевые, то просто игнорируем их
                            if (i0 < 0 || i0 >= input.sizeX || j0 < 0 || j0 >= input.sizeY)
                                continue;

                            // проходимся по всей глубине тензора и считаем сумму
                            for (int c = 0; c < channel; c++)
                                sum += input.getValueSignal(c, i0, j0) * cores[f].getValueSignal(c, i, j);
                        }
                    }
                    output.setValueSignal(f, y, x, sum); // записываем результат свёртки в выходной тензор
                }
            }
        }
        return output;
    }

    @Override
    public Signal backPropagation(Signal delta, int right, double E, double A)
    {
        // расчитываем размер для дельт
        int height = stride * (output.sizeX - 1) + 1;
        int width = stride * (output.sizeY - 1) + 1;
        int depth = output.sizeZ;

        Signal deltas = new Signal(depth, height, width); // создаём тензор для дельт

        // расчитываем значения дельт
        for (int d = 0; d < depth; d++)
            for (int i = 0; i < output.sizeX; i++)
                for (int j = 0; j < output.sizeY; j++)
                    deltas.setValueSignal(d, i * stride, j * stride,  delta.getValueSignal(d, i, j));

        // расчитываем градиенты весов фильтров и смещений
        for (int f = 0; f < countCore; f++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    double d = deltas.getValueSignal(f, y, x); // запоминаем значение градиента

                    for (int i = 0; i < matrix; i++) {
                        for (int j = 0; j < matrix; j++) {
                            int i0 = i + y - ratioPadding;
                            int j0 = j + x - ratioPadding;

                            // игнорируем выходящие за границы элементы
                            if (i0 < 0 || i0 >= input.sizeX || j0 < 0 || j0 >= input.sizeY)
                                continue;

                            // наращиваем градиент фильтра
                            for (int c = 0; c < channel; c++)
                                cBias[f].setValueSignal(c, i, j,
                                        (d * input.getValueSignal(c, i0, j0))
                                                + cBias[f].getValueSignal(c, i, j));
                        }
                    }

                    db.setValueSignal(f, 0 ,0, db.getValueSignal(f, 0 ,0) + d); // наращиваем градиент смещения
                }
            }
        }

        int pad = matrix - 1 - ratioPadding; // заменяем величину дополнения
        Signal deltaOutput = new Signal(input.sizeZ, input.sizeX, input.sizeY); // создаём тензор градиентов по входу

        // расчитываем значения градиента
        for (int y = 0; y < input.sizeX; y++) {
            for (int x = 0; x < input.sizeY; x++) {
                for (int c = 0; c < channel; c++) {
                    double sum = 0; // сумма для градиента

                    // идём по всем весовым коэффициентам фильтров
                    for (int i = 0; i < matrix; i++) {
                        for (int j = 0; j < matrix; j++) {
                            int i0 = y + i - pad;
                            int j0 = x + j - pad;

                            // игнорируем выходящие за границы элементы
                            if (i0 < 0 || i0 >= height || j0 < 0 || j0 >= width)
                                continue;

                            // суммируем по всем фильтрам
                            for (int f = 0; f < countCore; f++)
                                sum += cores[f].getValueSignal(c, matrix - 1 - i, matrix - 1 - j)
                                        * deltas.getValueSignal(f, i0, j0); // добавляем произведение повёрнутых фильтров на дельты
                        }
                    }
                    deltaOutput.setValueSignal(c, y, x, sum); // записываем результат в тензор градиента
                }
            }
        }
        UpdateWeight(E);
        return deltaOutput; // возвращаем тензор градиентов
    }

    public Signal backPropagation(Signal delta, double E, double A){
        return null;
    }

    private void UpdateWeight(double E)
    {
        for (int c = 0; c < countCore; c++)
        {
            for (int i = 0; i < matrix; i++)
            {
                for (int j = 0; j < matrix; j++)
                {
                    for (int d = 0; d < channel; d++)
                    {
                        double valueCore = cores[c].getValueSignal(d, i, j) - (E * cBias[c].getValueSignal(d, i, j));
                        cores[c].setValueSignal(d, i, j, valueCore); // вычитаем градиент, умноженный на скорость обучения
                        cBias[c].setValueSignal(d, i, j, 0); // обнуляем градиент фильтра
                    }
                }
            }
            double valueBias = bias.getValueSignal(c,0,0) - (E * db.getValueSignal(c, 0,0));
            bias.setValueSignal(c,0,0, valueBias); // вычитаем градиент, умноженный на скорость обучения
            db.setValueSignal(c,0,0, 0); // обнуляем градиент веса смещения
        }
    }

    public void initialization(Signal input)
    {
        //#1
        channel = input.sizeZ;
        cores = new Signal[countCore];
        cBias = new Signal[countCore];
        db = new Signal(countCore, 1, 1);
        bias = Generation.RandomSignal(countCore, 1, 1, 0, 0, 0.1);
        //#2
        for (int i = 0; i < countCore; i++) {
            cores[i] = Generation.RandomSignal(channel, matrix, matrix, 0, -1, 1);
            cBias[i] = Generation.RandomSignal(channel, matrix, matrix, 0, 0, 0.1);
        }
        //#3
        int PadX = input.sizeX + 2 * ratioPadding;
        int PadY = input.sizeY + 2 * ratioPadding;
        sizeCX = ((PadX - matrix) / stride) + 1;
        sizeCY = ((PadY - matrix) / stride) + 1;
        output = new Signal(countCore, sizeCX, sizeCY);
    }
}
