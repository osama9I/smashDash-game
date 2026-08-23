package smashdash;

public class Player extends  Actor {
    // festen Zustände
    private enum PlayerSize{
        KLEIN(100,"player-small"),
        MITTEL(128, "player-medium"),
        GROSS (160, "player-large");

        private final int width;
        private final String imageName;

       PlayerSize(int width, String imageName) {
            this.width = width;
            this.imageName = imageName;
        }
    }
    private PlayerSize currentSize = PlayerSize.MITTEL;

private int lastX;
private double speed;

public Player () {
    super(512, 680);
    this.lastX = 512;  // muss isn
    this.speed = 0.0;
}
 //  Übeschreibung von Methoden Getter für GUI
@Override
public  String getImage() {
    return this.currentSize.imageName;  // aktule Zustand von Player
}
@Override
public int getWidth() {  // weit von player
    return this.currentSize.width;
}
@Override
public int getHeight() {  // höhe von player
    return 32;
}

    public void grow() {   // um Player vergrößen
        if (this.currentSize == PlayerSize.KLEIN) {
            this.currentSize = PlayerSize.MITTEL;
        } else if (this.currentSize == PlayerSize.MITTEL) {
            this.currentSize = PlayerSize.GROSS;
        }
    }
    public void shrink() {   // um Player verkleinen
        if (this.currentSize == PlayerSize.GROSS) {
            this.currentSize = PlayerSize.MITTEL;
        } else if (this.currentSize == PlayerSize.MITTEL) {
            this.currentSize = PlayerSize.KLEIN;
        }
    }
//***   Berechnung von speed von player
@Override
// wie Player bewagen
    public void update(double deltaTime){
    this.lastX=this.getX(); // letzte Postion von Player
    int mouseX = SmashDashGUI.getMouseX();
    int minX = this.getWidth()/2;      //
    int maxX = 1024 - (this.getWidth()/2);
    if (mouseX < minX) {   // für die Mouse im richtige Grenzen bleibt
        mouseX = minX;
    }
        else  if( mouseX > maxX) {
            mouseX = maxX;
    }
        this.setX(mouseX);  // um zu Player bewagen
        this.speed = (this.getX()- this.lastX) * 0.005;   // Weg-Differenz / Zeit-Differenz // 0.005 für Speed verkleinert
    }
    public double getSpeed() {
    return speed;
    }
}


