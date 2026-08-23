package smashdash;

// #ball
public class BallMechanics {

  private final static double MAX_X_TO_Y_RATIO = 3;
  private final static double FRICTION_FACTOR = 1;

  private double x;
  private double y;

  private Ball ball;

  public BallMechanics(Ball ball) {
    this.x = 0.5;
    this.y = -1;
    this.ball = ball;
    this.normalize();
  }

  private void normalize() {

    // avoid too flat movements
    double ratio = Math.abs(this.x / this.y);
    if (ratio > MAX_X_TO_Y_RATIO) {
      this.y = MAX_X_TO_Y_RATIO * this.x;
    }

    double magnitude = this.magnitude();

    this.x /= magnitude;
    this.y /= magnitude;

  }

  private double magnitude() {
    return Math.hypot(x, y);
  }

  private void flipX() {
    this.x *= -1;
  }

  private void flipY() {
    this.y *= -1;
  }

  public void applyPlayerFriction(Player player) {
    this.x +=  FRICTION_FACTOR * player.getSpeed();
    this.normalize();
  }

  public void update(double deltaTime) {
    ball.addX((int) (this.x * deltaTime * ball.getSpeed()));
    ball.addY((int) (this.y * deltaTime * ball.getSpeed()));

    if (ball.getX() < ball.getWidth() / 2) {
      ball.setX(ball.getWidth() / 2);
      flipX();
    }

    if (ball.getY() < ball.getWidth() / 2) {
      ball.setY(ball.getWidth() / 2);
      flipY();
    }

    if (ball.getX() > 1024 - ball.getWidth() / 2) {
      ball.setX(1024 - ball.getWidth() / 2);
      flipX();
    }

  }

  public boolean checkCollision(Actor other) {
    int dx = ball.getX() - other.getX();
    int dy = ball.getY() - other.getY();

    int width = other.getWidth() + ball.getWidth();
    int height = other.getHeight() + ball.getHeight();

    int ny = -width;
    int nx = -height;

    int s1 = dx * nx + dy * ny;
    int s2 = -dx * nx + dy * ny;

    if (s1 > 0 && s2 > 0 && dy > -height / 2) {
      flipY();
      ball.setY(other.getY() - height / 2 - 1);
      return true;
    }

    if (s1 < 0 && s2 > 0 && dx < width / 2) {
      flipX();
      ball.setX(other.getX() + width / 2 + 1);
      return true;
    }

    if (s1 < 0 && s2 < 0 && dy < height / 2) {
      flipY();
      ball.setY(other.getY() + height / 2 + 1);
      return true;
    }

    if (s1 > 0 && s2 < 0 && dx > -width / 2) {
      flipX();
      ball.setX(other.getX() - width / 2 - 1);
      return true;
    }

    return false;

  }

}

