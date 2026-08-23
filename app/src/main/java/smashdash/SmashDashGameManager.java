package smashdash;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class SmashDashGameManager {

  private static final int BRICKS_ROWS = 5;
  private static final int BRICKS_COLUMNS = 8;

  private SmashDashGUI smashDashGUI;
  private Player player;
  private Actor[] actors = new Actor[1024];
  private Ball ball;

  public SmashDashGameManager() {
    // DON'T CHANGE
    this.smashDashGUI = new SmashDashGUI(this);
    // /DON'T CHANGE
this.player = new Player();     // Player zu erstellen
this.addActor(this.player);   // um Player zu Array von Actor hinzufügen
this.ball = new Ball();          // Ball erstellen
this.addActor(this.ball);  // um Ball zu Array von Actor hinzufügen
this.fillWithBricks();  // um Bricks zu bauen

  }

  // ** Game Loop ***
  public void update(double deltaTime) {
    this.checkCollissions();
    boolean hasBricks = false;  // kein Brick

    for (int i = 0; i < actors.length; i++) {
      Actor actor = actors[i];
      if (actor != null) {
        actor.update(deltaTime);
        this.smashDashGUI.draw(
            actor.getImage(),
            actor.getX(),
            actor.getY()
    );
        if (actor instanceof Brick) {
          hasBricks = true;    // noch Brick
        }
  }

}
    if (!hasBricks) {
      this.smashDashGUI.win();
    }
    if (this.ball.getY() > 768){
      this.smashDashGUI.gameOver();
    }

  }

   // #actor

  private void addActor(Actor actor) {
    if (actor == null)
      throw new IllegalArgumentException("actor must not be null");

    for (int i = 0; i < actors.length; i++) {
      if (actors[i] == null) {
        actors[i] = actor;
        break;
      }
    }
  }

  // #bricks
  //*** um Bricks zu erzeugen  >>>
   private void fillWithBricks() {
     Random random = new Random();
     for (int row = 0; row < BRICKS_ROWS; row++) {
       for (int column = 0; column < BRICKS_COLUMNS; column++) {
       final int x = 512 + (column - BRICKS_COLUMNS / 2) * 64;
       final int y = 128 + row * 32;
      List<Supplier<Brick>> brickSuppliers = List.of(
          // #simplebrick
              () -> new SimpleBrick(x, y)
             // /#simplebrick
             // #hardbrick
              , () -> new HardBrick(x, y)
             // /#hardbrick
             // #speedchanger
              , () -> new SpeedChangerBrick(x, y)
             // /#speedchanger
             // #playerchanger
             , () -> new PlayerChangerBrick(x, y)
             // /#playerchanger
         );

         if (!brickSuppliers.isEmpty()) {
           this.addActor(brickSuppliers.get(random.nextInt(brickSuppliers.size())).get());
         }
       }
     }
   }
   //#bricks

  private void checkCollissions() {
    for (int index = 0; index < actors.length; index++) {
      Actor actor = actors[index];
      if (actor == null|| actor == this.ball) {
        continue;
      }
      if (this.ball.checkCollision(actor)) {   // ,ob Collision passiert

        if (actor instanceof GameChanger){ // um Type von Brick wiessen
          GameChanger changer = (GameChanger) actor; // Cast notwendig, um auf  Methode changeGame() zuzugreifen
          changer.changeGame(this.player,this.ball);
        }
        if (actor instanceof Brick) {
          Brick brick = (Brick) actor;

          if (!brick.onBallCollision()) {
            this.actors[index] = null; //  um Brick zu zerstören
          }

        }
      }

    }
    }
}
