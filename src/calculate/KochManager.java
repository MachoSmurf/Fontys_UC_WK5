package calculate;

import javafx.application.Platform;
import timeutil.TimeStamp;
import uckochfractalfx.UCKochFractalFX;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KochManager {

    private KochFractal kochFractal;
    private UCKochFractalFX application;
    private final List<Edge> edgeList;
    private TimeStamp timeStamp;
    private Thread rightThread;
    private Thread bottomThread;
    private Thread leftThread;
    private int edgesCalculatedCount;

    public KochManager(UCKochFractalFX ucKochFractalFX) {
        this.application = ucKochFractalFX;
        edgeList = new ArrayList<>();
        timeStamp = new TimeStamp();
        kochFractal = new KochFractal();
        edgesCalculatedCount = 0;
    }

    public void changeLevel(int nextLevel) {
        //this kochfractal is only to calculate number of edges
        kochFractal.setLevel(nextLevel);

        edgeList.clear();
        timeStamp = new TimeStamp();
        timeStamp.setBegin("Berekening start");
        if (rightThread != null && rightThread.isAlive()) {
            rightThread.interrupt();
        }
        if (leftThread != null && leftThread.isAlive()) {
            leftThread.interrupt();
        }
        if (bottomThread != null && bottomThread.isAlive()) {
            bottomThread.interrupt();
        }
        rightThread = new Thread(new EdgeCalculator(this, Side.RIGHT, nextLevel));
        leftThread = new Thread(new EdgeCalculator(this, Side.LEFT, nextLevel));
        bottomThread = new Thread(new EdgeCalculator(this, Side.BOTTOM, nextLevel));

        rightThread.setName("Right");
        leftThread.setName("Left");
        bottomThread.setName("Bottom");

        leftThread.start();
        rightThread.start();
        bottomThread.start();
    }

    public void edgeCalculated(List<Edge> edges) {
        /*edgeLock.lock();
        edgeList.addAll(edges);
        edgeLock.unlock();*/
        synchronized (edgeList) {
            edgeList.addAll(edges);
        }

        edgesCalculatedCount++;
        if (edgesCalculatedCount == 3) {
            Platform.runLater(() -> {
                drawEdges();
                timeStamp.setEnd("Berekening einde");
                application.setTextCalc(timeStamp.toString());
                edgesCalculatedCount = 0;
            });
        }
    }

    public void drawEdges() {
        application.setTextNrEdges("" + kochFractal.getNrOfEdges());
        TimeStamp ts = new TimeStamp();
        ts.setBegin("Tekenen Start");
        application.clearKochPanel();
        for (Edge e : edgeList) {
            application.drawEdge(e);
        }
        ts.setEnd("Tekenen Eind");
        application.setTextDraw(ts.toString());
    }
}
