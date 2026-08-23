package smashdash;

public class PlayerChangerBrick extends Brick implements GameChanger{
    private boolean isGrow; // true = vergrößert , false = verkleinert

    public PlayerChangerBrick(int x,int y){
        super(x,y);
        this.isGrow = Math.random() > 0.5;
    }
    @Override
    public String getImage(){
        if (this.isGrow){
            return "brick-grow" ;
        }
        else {
            return "brick-shrink";
        }
    }
    @Override
    public boolean onBallCollision() {
        return false;
    }
    @Override
    public void changeGame(Player player, Ball ball){
        if (this.isGrow){
            player.grow();
        }else {
            player.shrink();
        }
    }
}
