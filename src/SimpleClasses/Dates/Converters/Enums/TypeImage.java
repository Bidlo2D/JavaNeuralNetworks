package SimpleClasses.Dates.Converters.Enums;

public enum TypeImage {
    BW(1),
    Color(3);

    private int channel;

    TypeImage(int channel) {
        this.channel = channel;
    }

    public int getChannel() {
        return channel;
    }
}
