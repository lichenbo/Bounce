import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;


public class BounceApplet extends JApplet{
    public BounceApplet() {
        add(new BallControl());
    }
}

class Ball {
    private int x = 0;
    private int y = 0;
    private int radius = 5;
    private int dx = 2;
    private int dy = 2;
    private Color color = Color.RED;

    public Ball() {

    }

    public void draw(Graphics g, BallControl.Canvas bc) {
        g.setColor(color);

        if (x < radius) {
            dx = Math.abs(dx);
        }
        if (x > bc.getWidth() - radius) {
            dx = -Math.abs(dx);
        }
        if (y < radius) {
            dy = Math.abs(dy);
        }
        if (y > bc.getHeight() - radius) {
            dy = -Math.abs(dy);
        }

        x = x + dx;
        y = y + dy;

        g.fillOval(x - radius, y - radius, radius * 2, radius * 2);

        System.out.println("draw");

    }

}

class BallControl extends JPanel {
    /// GUI components
    private JPanel jpButtons;
    private JPanel jpBalls;
    private JButton jbtSuspend;
    private JButton jbtResume;
    private JButton jbtAddBall;
    private JButton jbtRemoveBall;
    private JScrollBar jsbDelay;

    private int delay;
    private ArrayList<Ball> listBall;
    private Timer timer;

    /// Constructor
    public BallControl(){
        /// instantiate
        delay = 10;
        jpButtons = new JPanel();
        jpBalls = new Canvas();
        jbtSuspend = new JButton("Suspend");
        jbtResume = new JButton("Resume");
        jbtAddBall = new JButton("+1");
        jbtRemoveBall = new JButton("-1");
        jsbDelay = new JScrollBar();
        timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });

        jpBalls.setBorder(new LineBorder(Color.RED));
        jsbDelay.setOrientation(JScrollBar.HORIZONTAL);
        timer.setDelay(jsbDelay.getMaximum());

        jpButtons.add(jbtSuspend);
        jpButtons.add(jbtResume);
        jpButtons.add(jbtAddBall);
        jpButtons.add(jbtRemoveBall);

        this.setLayout(new BorderLayout());
        this.add(jsbDelay, BorderLayout.NORTH);
        this.add(jpBalls, BorderLayout.CENTER);
        this.add(jpButtons, BorderLayout.SOUTH);

        /// GUI Display end

        listBall = new ArrayList<Ball>();
        timer.start();
        listBall.add(new Ball());

        jbtSuspend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                  timer.stop();
            }
        });
        jbtResume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.start();
            }
        });
        jbtAddBall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listBall.add(new Ball());
                assert(listBall.size() > 0);
                jbtRemoveBall.setEnabled(true);
            }
        });
        jbtRemoveBall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assert (listBall.size() > 0);
                listBall.remove(0);

                if (listBall.size() == 0) {
                    jbtRemoveBall.setEnabled(false);
                }

                assert (listBall.size() >= 0);
            }
        });
        jsbDelay.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int delay = jsbDelay.getMaximum() - e.getValue();
                timer.setDelay(delay);
                assert (delay >= 0 && delay <= jsbDelay.getMaximum());
            }
        });
    }

    class Canvas extends JPanel{
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (Ball b: listBall) {
                b.draw(g,this);
            }
        }
    }
}






