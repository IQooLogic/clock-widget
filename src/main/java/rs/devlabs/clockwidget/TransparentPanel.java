package rs.devlabs.clockwidget;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author IQooLogic
 */
public class TransparentPanel extends javax.swing.JPanel {

    private Color color = new Color(208, 255, 87);
    private Color shadowColor = new Color(114, 140, 47);
    private static final int TIME_FONTSIZE = 200;
    private static final int DATE_FONTSIZE = 48;
    private static final int MILLIS_FONTSIZE = 24;
    private String fontName = "LCDMono";
    private Font timeFont = new Font(fontName, Font.PLAIN, TIME_FONTSIZE);
    private Font millisFont = new Font(fontName, Font.PLAIN, MILLIS_FONTSIZE);
    private Font dateFont = new Font(fontName, Font.PLAIN, DATE_FONTSIZE);
    private WidgetFrame frame;
    private int xm;
    private int ym;
    
    private boolean even = false;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int second;
    private int millis;

    public TransparentPanel(WidgetFrame frame) {
        this.frame = frame;
        initComponents();
        setOpaque(false);

        loadCustomFont();
    }

    private void loadCustomFont() {
        try {
            InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("fonts/lcd.TTF");
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(200f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(TransparentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update() {
        LocalDateTime now = LocalDateTime.now();
        year = now.getYear();
        month = now.getMonthValue();
        day = now.getDayOfMonth();
        hour = now.getHour();
        minute = now.getMinute();
        second = now.getSecond();
        millis = now.get(ChronoField.MILLI_OF_SECOND);

        invalidate();
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.clearRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setFont(timeFont);

        String text;
        if(even) {
            text = String.format("%02d:%02d:%02d", hour, minute, second);
            even = false;
        } else {
            text = String.format("%02d %02d %02d", hour, minute, second);
            even = true;
        }
        FontMetrics fm = g2d.getFontMetrics();
        int height = fm.getHeight();
        int width = fm.stringWidth(text);

        Dimension dimension = new Dimension(width, height + 3);
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setSize(dimension);
        frame.pack();

        // time
        g2d.setColor(shadowColor);
        g2d.drawString(text, 1, height + 1);
        g2d.setColor(color);
        g2d.drawString(text, 0, height);
        
//        g2d.setFont(millisFont);
//        g2d.setColor(shadowColor);
//        g2d.drawString(String.format("%02d", millis), width + 11, 25);
//        g2d.setColor(color);
//        g2d.drawString(String.format("%02d", millis), width + 10, 24);

        // date
        g2d.setComposite(AlphaComposite.SrcOver.derive(0f));
        g2d.setColor(getBackground());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setFocusable(false);
        setOpaque(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 171, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        frame.setLocation(x - xm, y - ym);
    }//GEN-LAST:event_formMouseDragged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        xm = evt.getX();
        ym = evt.getY();
    }//GEN-LAST:event_formMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
