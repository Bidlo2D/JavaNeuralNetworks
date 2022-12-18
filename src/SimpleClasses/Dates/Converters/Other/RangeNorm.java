package SimpleClasses.Dates.Converters.Other;

import java.lang.reflect.Array;

public class RangeNorm<T extends Double>  {
    T[] range;

    public RangeNorm(T min, T max) {
        range = createGenericArray(max.getClass(), 2);
        range[0] = max;
        range[1] = min;
    }

    private <N> T[] createGenericArray(Class<N> clazz, int size) {
        return (T[])Array.newInstance(clazz, size);
    }

    public T getMax() {
        return range[0];
    }

    public T getMin() {
        return range[1];
    }

}
