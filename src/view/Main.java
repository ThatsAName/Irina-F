package view;

import model.Line;
import model.Point;
import model.Polygon;
import rasterize.LineRasterizerTrivial;
import rasterize.PolygonRasterizer;
import rasterize.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main {
    private Polygon polygon;
    private JPanel panel;
    private RasterBufferedImage raster;
    private int x,y, x2, y2;
    private LineRasterizerTrivial rasterizer;
    private PolygonRasterizer polygonRasterizer;
    private Color color;

    public Main(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());

        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBufferedImage(width, height);
        rasterizer = new LineRasterizerTrivial(raster);
        polygonRasterizer = new PolygonRasterizer(rasterizer);
        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                    x = e.getX();
                    y = e.getY();
                }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                x2 = e.getX();
                y2 = e.getY();
                draw();
            }
        });

        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (panel.getWidth()<1 || panel.getHeight()<1)
                    return;
                if (panel.getWidth()<=raster.getWidth() && panel.getHeight()<=raster.getHeight()) //no resize if new one is smaller
                    return;
                RasterBufferedImage newRaster = new RasterBufferedImage(panel.getWidth(), panel.getHeight());

                newRaster.draw(raster);
                raster = newRaster;
                rasterizer = new LineRasterizerTrivial(raster);

            }
        });
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0,200));
        points.add(new Point(200,0));
        points.add(new Point(300,300));
        polygon = new Polygon(points, 0x00ff00);

    }

    public void draw() {
        clear(0x000000);
//        rasterizer.rasterize(x,y,x2,y2, new Color(0xff0000));
        polygonRasterizer.rasterize(polygon);
        panel.repaint();
    }

    public void clear(int color) {
        raster.setClearColor(color);
        raster.clear();

    }

    public void present(Graphics graphics) {
        raster.repaint(graphics);
    }

    public void start() {
        clear(0xaaaaaa);
        raster.getGraphics().drawString("Use mouse buttons and try resize the window", 5, 15);
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main(800, 600).start());
    }
}
