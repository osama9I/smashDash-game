package smashdash;

public  abstract class Brick extends Actor{
    public Brick(int x , int y){
        super(x,y);
    }
    @Override
    public int getWidth(){
        return 64;
    }
    @Override
    public int getHeight(){
        return 32;
    }
    @Override
    public void update(double delta ){
        // ** leeren Rumpf , Stein nicht bewget

    }
    public abstract boolean onBallCollision();
    }


