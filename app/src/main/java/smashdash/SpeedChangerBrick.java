package smashdash;

public class SpeedChangerBrick extends Brick implements GameChanger{
private  boolean isSpeedup;   // speichert den Zustand _ true für Beschleuingung ,false für verlangsamt

public SpeedChangerBrick(int x, int y){
    super(x,y);
    this.isSpeedup = Math.random()> 0.5;
}

@Override
    public String getImage(){
    if (this.isSpeedup){        // Ball wird beschleuigt
        return "brick-speedup";
    }
    else{
        return "brick-slowdown";  // Ball wird verlangsamt
    }
}
@Override
    public boolean onBallCollision(){
    return false;
}
@Override   // Die Bewegung von Ball verändert
    public void changeGame(Player player, Ball ball){
    if (this.isSpeedup){
        ball.changeSpeed(100.0);  // Speed wird 100 pixel pro Sekunde erhört
    }
else {
    ball.changeSpeed(-100.0);  // Speed wird 100 pixel pro Sekunde gesenkt
    }

    }
}
