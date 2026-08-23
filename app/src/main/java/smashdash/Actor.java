package smashdash;

import java.awt.*;

public abstract class  Actor {
    private  int x;
    private int y;

    public Actor(int x , int y){
        this.x=x;
        this.y=y;
    }
    // Abstrakte Methode
    public abstract String getImage();
    public abstract void update(double deltaTime);
    public abstract int getWidth();
    public abstract int getHeight();

    // konkrete Methode
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int delta){
        this.x=delta;
    }
    public void setY(int delta) {
        this.y = delta;
    }
    public void addX(int delta){
        this.x+=delta;
    }
    public void addY(int delta){
        this.y+=delta;
    }

}