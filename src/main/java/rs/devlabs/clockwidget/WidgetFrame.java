package rs.devlabs.clockwidget;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

/**
 *
 * @author IQooLogic
 */
public class WidgetFrame extends JWindow {

    private final Settings settings = new Settings();
    private static final long INTERVAL = 1000;
    private final Timer timer = new Timer();
    private TransparentPanel panel;

    public WidgetFrame() {
        panel = new TransparentPanel(WidgetFrame.this);
        initComponents();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                panel.update();
            }
        }, 0, INTERVAL);
    }

    private void initComponents() {
        setBackground(new Color(0, 0, 0, 0));
        setLayout(null);
        setContentPane(panel);
        pack();

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 3) {
                    settings.setLocation(e.getComponent().getLocationOnScreen());
                    timer.cancel();
                    SwingUtilities.getWindowAncestor(e.getComponent()).dispose();
                }
            }
        });

        if(settings.exists()) {
            setLocation(settings.getLocation());
        } else {
            setLocationRelativeTo(null);
        }
    }

    public static void main(String args[]) {
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WidgetFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WidgetFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
