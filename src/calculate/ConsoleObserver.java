package calculate;

import java.util.Observable;
import java.util.Observer;

public class ConsoleObserver implements Observer {

    private final KochFractal kochFractal;

    public ConsoleObserver(KochFractal kochFractal){
        this.kochFractal = kochFractal;
    }

    @Override
    public void update(Observable o, Object arg) {
        Edge e = (Edge)arg;
        System.out.println("Startpunt Edge: (" + e.X1 +"," + e.Y1 + "), Eindpunt Edge: " + e.X2 +"," + e.Y2 + ")");
    }
}
