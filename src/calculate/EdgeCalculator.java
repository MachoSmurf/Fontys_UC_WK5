package calculate;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class EdgeCalculator implements Runnable, Observer {

    private KochManager kochManager;
    private KochFractal fractal;
    private Side edgeSide;
    private int edgesForThisSide;
    private List<Edge> edgeBuffer;
    private int level;

    public EdgeCalculator(KochManager kochManager, Side side, int level) {
        this.kochManager = kochManager;
        this.edgeSide = side;
        this.fractal = new KochFractal();
        fractal.setLevel(level);
        fractal.addObserver(this);
        edgesForThisSide = fractal.getNrOfEdges() / 3;
        edgeBuffer = new ArrayList<>();
        this.level = level;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.out.println(level + "/" + edgeSide + "Started");
        calculateEdge();
    }

    private void calculateEdge() {
        switch (edgeSide) {
            case BOTTOM:
                fractal.generateBottomEdge();
                break;
            case RIGHT:
                fractal.generateRightEdge();
                break;
            case LEFT:
                fractal.generateLeftEdge();
                break;
        }
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {
        edgeBuffer.add((Edge) arg);
        if (edgeBuffer.size() == edgesForThisSide) {
           kochManager.edgeCalculated(edgeBuffer);
        }
    }
}
