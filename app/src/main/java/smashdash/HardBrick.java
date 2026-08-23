package smashdash;

public class HardBrick extends Brick {
    private int hitCount; // die Zahle von schlage der Brick

    public HardBrick(int x, int y) {
        super(x, y);
        this.hitCount = 0;  // Initialer Zustand
    }

    @Override
    public String getImage() {
        switch (this.hitCount) {
            case 0:
                return "brick-red";
            case 1:
                return "brick-blue";
            case 2:
                return "brick-green";
            default:
                return "brick-red";
        }
    }

    @Override
    public boolean onBallCollision() {
        this.hitCount++;
        if (this.hitCount >= 3) {
            return false;
        }
        else {
            return true;
        }
    }

}
