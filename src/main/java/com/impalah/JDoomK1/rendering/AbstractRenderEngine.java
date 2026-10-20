package com.impalah.JDoomK1.rendering;

import com.impalah.JDoomK1.model.CalculationValues;
import com.impalah.JDoomK1.model.Player;
import com.impalah.JDoomK1.model.environment.Environment;

import java.awt.*;

public abstract class AbstractRenderEngine implements IRenderWorld{
    private CalculationValues calcVals;
    private Environment env;
    private Player player;
    private Point canvasOffset;

    //Jpanel height size -> the acual size of the screen the game is rendered on
    private int renderPaneWidth;
    private int renderPaneHeight;

    public AbstractRenderEngine(int renderPaneWidth, int renderPaneHeight){
        this.renderPaneHeight = renderPaneHeight;
        this.renderPaneWidth = renderPaneWidth;
        this.calcVals = new CalculationValues();
    }

    public AbstractRenderEngine(int renderPaneWidth, int renderPaneHeight, Environment env, Player player) {
        this.renderPaneHeight = renderPaneHeight;
        this.renderPaneWidth = renderPaneWidth;
        this.env = env;
        this.player = player;
        this.calcVals = new CalculationValues();

    }

    public CalculationValues getCalcVals() {
        return calcVals;
    }

    public Environment getEnv() {
        return env;
    }

    public Player getPlayer() {
        return player;
    }

    public int getRenderPaneWidth() {
        return renderPaneWidth;
    }

    public int getRenderPaneHeight() {
        return renderPaneHeight;
    }
}


