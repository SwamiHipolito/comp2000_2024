// import java.awt.*;
// import javax.swing.JFrame;
// import javax.swing.JPanel;

// public class Main extends JFrame {
//     private static int trailLength = 100;
//     private Point[] mouseTrail = new Point[trailLength];
//     private int trailIndex = 0;
//     private int updateCounter = 0; // Counter to slow down the update
//     private static final int delay = 5; // Adjust this value to slow down updates

//     public static void main(String[] args) {
//         Main window = new Main();
//     }

//     class Canvas extends JPanel {
//         Grid grid = new Grid();

//         public Canvas() {
//             setPreferredSize(new Dimension(720, 720));
//         }

//         @Override
//         public void paint(Graphics g) {
//             super.paint(g);

//             // Increment the update counter
//             updateCounter++;

//             // Only update the trail every UPDATE_THRESHOLD frames
//             if (updateCounter >= delay) {
//                 Point mousePos = getMousePosition();
//                 if (mousePos != null) {
//                     // Store the current mouse position in the array
//                     mouseTrail[trailIndex] = mousePos;
//                     trailIndex = (trailIndex + 1) % trailLength; // Circular increment
//                 }
//                 updateCounter = 0; // Reset the counter
//             }

//             grid.paint(g, getMousePosition());

//             // Draw the mouse trail
//             for (int i = 0; i < trailLength; i++) {
//                 if (mouseTrail[i] != null) {
//                     g.setColor(new Color(128, 128, 128, 128)); // semi-transparent gray
//                     g.fillOval(mouseTrail[i].x - 10, mouseTrail[i].y - 10, 10, 10); // draw circle with radius 10
//                 }
//             }

//             // Trigger a repaint to keep the loop running
//             repaint();
//         }
//     }

//     private Main() {
//         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         Canvas canvas = new Canvas();
//         this.setContentPane(canvas);
//         this.pack();
//         this.setVisible(true);
//     }
// }







import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame {
    private static  int trailLength = 100; // Number of trail points
    private Point[] mouseTrail = new Point[trailLength]; // Array to store mouse trail
    private int trailIndex = 0; // Index for storing new trail points
    private long lastUpdateTime = 0; // Last time the trail was updated
    private static  long Delay = 10; // Delay between updates in milliseconds

    public static void main(String[] args) {
        Main window = new Main();
    }

    class Canvas extends JPanel {
        Grid grid = new Grid();
        private Point lastMousePosition = null; // To keep track of the last recorded mouse position

        public Canvas() {
            setPreferredSize(new Dimension(720, 720));
           
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Ensure proper painting by calling superclass method

            Point currentMousePos = getMousePosition();
            long currentTime = System.currentTimeMillis();

            // Update the mouse trail if the mouse has moved and the delay has passed
            if (currentMousePos != null && (lastMousePosition == null || !currentMousePos.equals(lastMousePosition))) {
                if (currentTime - lastUpdateTime >= Delay) {
                    mouseTrail[trailIndex] = currentMousePos;
                    trailIndex = (trailIndex + 1) % trailLength; // Circular increment
                    lastMousePosition = currentMousePos;
                    lastUpdateTime = currentTime; // Update the time of the last update
                }
            }

            grid.paint(g, currentMousePos);

            // Draw the mouse trail
            g.setColor(new Color(128, 128, 128, 128)); // Semi-transparent gray
            for (int i = 0; i < trailLength; i++) {
                if (mouseTrail[i] != null) {
                    g.fillOval(mouseTrail[i].x - 10, mouseTrail[i].y - 10, 10, 10); // Draw circle with radius 10
                }
            }

            // Schedule the next repaint
            repaint();
        }
    }

    private Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Canvas canvas = new Canvas();
        this.setContentPane(canvas);
        this.pack();
        this.setVisible(true);
    }
}
