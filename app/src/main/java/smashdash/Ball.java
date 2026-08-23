package smashdash;

public class Ball extends Actor {
    private  double speed;
    private BallMechanics ballMechanics;

    public Ball() {
        // startposition
        super(512,500);
        this.speed = 500.0; // pixel pro Sekunde
        this.ballMechanics = new BallMechanics(this);
    }
    @Override
    public String getImage() {
        return "ball" ;
    }
    @Override
    public void update(double deltaTime){
        this.ballMechanics.update(deltaTime);
    }  // fix speed
    @Override
    public int getWidth (){
        return 16;
    }
    @Override
    public int getHeight (){
        return 16;
    }
    public double getSpeed(){
        return this.speed;
    }
    public void changeSpeed(double deltaSpeed) {         //  Ändert die Geschwindigkeit das Balls und beschränkt bis 100
        this.speed += deltaSpeed;
        if (this.speed < 100) {
            this.speed = 100.0;
        }
    }
    //   Gibt dem Ball einen "Schubser", wenn er die Plattform trifft.
    public void applyPlayerFriction(Player player){
        this.ballMechanics.applyPlayerFriction(player);
    }
    //  Prüft Kollision mit einem anderen Actor und passt ggf die Richtung an.
    public boolean checkCollision(Actor other){
        return this.ballMechanics.checkCollision(other);


    }
}
