package game.help;

public class Resolution {
    private double windowWidth;
    private double windowHeight;

    public double width() {
        return windowWidth;
    }

    public double height() {
        return windowHeight;
    }

    public void change(double width, double height) {
        this.windowWidth = width;
        this.windowHeight = height;
    }
}
