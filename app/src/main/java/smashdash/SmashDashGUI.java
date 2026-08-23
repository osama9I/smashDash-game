package smashdash;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SmashDashGUI extends JPanel implements MouseMotionListener {

  public static final int WIDTH = 1024 + 2 * 32;
  public static final int HEIGHT = 768 + 1 * 16;

  private SmashDashGameManager gameManager;

  private double time;

  private double startTime;
  private double lastUpdateTime;

  private Map<String, Image> imageCache;

  private List<DrawInfo> drawQueue;

  private static int mouseX;

  private record DrawInfo(String image, double x, double y) {
  };

  public SmashDashGUI(SmashDashGameManager gameManager) {
    super();

    if (gameManager == null)
      throw new IllegalArgumentException("game manager must not be null");

    this.gameManager = gameManager;
    this.drawQueue = new LinkedList<>();
    this.imageCache = new HashMap<>();
    SmashDashGUI.mouseX = WIDTH / 2;

    JFrame frame = new JFrame("Smash-Dash");
    frame.setSize(WIDTH, HEIGHT);
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    frame.add(this);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.setVisible(true);

    frame.addMouseMotionListener(this);
    BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

    // Create a new blank cursor.
    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
        cursorImg, new Point(0, 0), "blank cursor");

    // Set the blank cursor to the JFrame.
    frame.getContentPane().setCursor(blankCursor);

    startTime = System.currentTimeMillis() / 1000d;
    time = 0d;
    lastUpdateTime = time;

    Timer updateTimer = new Timer(16, this::updateState);
    updateTimer.setRepeats(true);
    updateTimer.start();
  }

  public double getTime() {
    return time;
  }

  private void updateState(ActionEvent event) {
    drawQueue.clear();

    time = System.currentTimeMillis() / 1000d - startTime;
    double deltaTime = time - lastUpdateTime;
    this.gameManager.update(deltaTime);
    lastUpdateTime = time;

    this.paintImmediately(this.getBounds());
    this.repaint();
    Toolkit.getDefaultToolkit().sync();

  }

  public void draw(String imageFile, double x, double y) {
    this.drawQueue.add(new DrawInfo(imageFile, x, y));
  }

  public void win() {
    int minutes = (int) (time / 60);
    int seconds = (int) (time % 60);
    JOptionPane.showMessageDialog(this,
        String.format("You WON! It took you %d minutes and %d seconds", minutes, seconds));

    System.exit(0);

  }

  public void gameOver() {
    int minutes = (int) (time / 60);
    int seconds = (int) (time % 60);
    JOptionPane.showMessageDialog(this,
        String.format("Game Over! You lasted %d minutes and %d seconds", minutes, seconds));

    System.exit(0);

  }

  private Image loadImage(String imageFile) throws IOException {

    if (!imageCache.containsKey(imageFile)) {
      imageCache.put(
          imageFile,
          ImageIO.read(ClassLoader.getSystemClassLoader().getResource(String.format("%s.png", imageFile))));
    }

    return imageCache.get(imageFile);

  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    try {
      Image background = loadImage("background");

      g.drawImage(background, 0, 0, null);

      for (DrawInfo drawInfo : drawQueue) {
        Image actorImage = loadImage(drawInfo.image());
        int x = (int) (drawInfo.x() + 32 - actorImage.getWidth(null) / 2.0);
        int y = (int) (drawInfo.y() + 32 - actorImage.getHeight(null) / 2.0);

        g.drawImage(actorImage, x, y, null);

      }

    } catch (IOException ioException) {
      System.err.println(ioException);
      System.exit(1);
    }

  }

  public static int getMouseX() {
    return SmashDashGUI.mouseX;
  }

  @Override
  public void mouseDragged(MouseEvent e) {
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    SmashDashGUI.mouseX = Math.clamp(e.getX() - 16, 0, 1024);
  }

}
