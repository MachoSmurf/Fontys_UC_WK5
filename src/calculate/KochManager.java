package calculate;

import uckochfractalfx.UCKochFractalFX;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class KochManager implements Observer{

    private KochFractal kochFractal;
    private UCKochFractalFX application;
    private ArrayList<Edge> edgeList;

    public KochManager(UCKochFractalFX ucKochFractalFX) {
        this.application = ucKochFractalFX;
        kochFractal = new KochFractal();
        kochFractal.addObserver(this);
        edgeList = new ArrayList<>();
    }

    public void changeLevel(int nextLevel) {
        kochFractal.setLevel(nextLevel);
        drawEdges();
    }

    public void drawEdges() {
        application.clearKochPanel();
        kochFractal.generateRightEdge();
        kochFractal.generateLeftEdge();
        kochFractal.generateBottomEdge();
        for (Edge e : edgeList){
            application.drawEdge(e);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        edgeList.add((Edge)arg);
    }
}
