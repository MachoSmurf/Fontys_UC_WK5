package calculate;

import timeutil.TimeStamp;
import uckochfractalfx.UCKochFractalFX;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class KochManager implements Observer{

    private KochFractal kochFractal;
    private UCKochFractalFX application;
    private ArrayList<Edge> edgeList;
    private TimeStamp timeStamp;

    public KochManager(UCKochFractalFX ucKochFractalFX) {
        this.application = ucKochFractalFX;
        kochFractal = new KochFractal();
        kochFractal.addObserver(this);
        edgeList = new ArrayList<>();
        timeStamp = new TimeStamp();
    }

    public void changeLevel(int nextLevel) {
        kochFractal.setLevel(nextLevel);
        drawEdges();
    }

    public void drawEdges() {
        application.clearKochPanel();
        timeStamp = new TimeStamp();
        timeStamp.setBegin("Berekening start");
        kochFractal.generateRightEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateBottomEdge();
        timeStamp.setEnd("Berkening einde");
        application.setTextCalc(timeStamp.toString());
        application.setTextNrEdges("" + kochFractal.getNrOfEdges());
        for (Edge e : edgeList){
            application.drawEdge(e);
        }
        edgeList.clear();
    }

    @Override
    public void update(Observable o, Object arg) {
        edgeList.add((Edge)arg);
    }
}
