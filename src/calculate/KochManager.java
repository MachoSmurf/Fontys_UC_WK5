package calculate;

import uckochfractalfx.UCKochFractalFX;

import java.util.Observable;
import java.util.Observer;

public class KochManager implements Observer{

    private KochFractal kochFractal;
    private UCKochFractalFX application;

    public KochManager(UCKochFractalFX ucKochFractalFX) {
        this.application = ucKochFractalFX;
        kochFractal = new KochFractal();
        kochFractal.addObserver(this);
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
    }

    @Override
    public void update(Observable o, Object arg) {
        application.drawEdge((Edge)arg);
    }
}
