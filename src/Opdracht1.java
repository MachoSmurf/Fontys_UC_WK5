import calculate.ConsoleObserver;
import calculate.KochFractal;

public class Opdracht1 {

    public static void main(String[] args) {
        KochFractal fractal = new KochFractal();

        ConsoleObserver consoleObserver = new ConsoleObserver(fractal);
        fractal.addObserver(consoleObserver);

        fractal.setLevel(2);
        fractal.generateBottomEdge();
        fractal.generateLeftEdge();
        fractal.generateRightEdge();

    }
}
