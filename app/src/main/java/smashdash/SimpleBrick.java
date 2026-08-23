package smashdash;

public class SimpleBrick extends Brick  {
    public SimpleBrick(int x , int y){
        super(x,y);
    }
    @Override
    public String getImage () {
        return "brick-simple";  // name von Bild Datei
    }

    @Override
    public boolean onBallCollision(){  // ist der Stein noch befindet
        return false;
    }




}
