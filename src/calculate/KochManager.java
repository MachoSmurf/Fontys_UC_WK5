package calculate;

import timeutil.TimeStamp;
import uckochfractalfx.UCKochFractalFX;

import java.lang.reflect.Array;
import java.security.Timestamp;
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
        edgeList.clear();
        kochFractal.setLevel(nextLevel);
        timeStamp = new TimeStamp();
        timeStamp.setBegin("Berekening start");
        kochFractal.generateRightEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateBottomEdge();
        timeStamp.setEnd("Berkening einde");
        application.setTextCalc(timeStamp.toString());
        drawEdges();
    }

    public void drawEdges() {
        application.setTextNrEdges("" + kochFractal.getNrOfEdges());
        TimeStamp ts = new TimeStamp();
        ts.setBegin("Tekenen Start");
        application.clearKochPanel();
        for (Edge e : edgeList){
            application.drawEdge(e);
        }
        ts.setEnd("Tekenen Eind");
        application.setTextDraw(ts.toString());
    }

    @Override
    public void update(Observable o, Object arg) {
        edgeList.add((Edge)arg);
    }
}
