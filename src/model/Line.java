package model;

import java.awt.*;

public class Line {

    private Point start;
    private Point end;
    private Color color;

    public Line(int x1, int y1, int x2, int y2, int color) {
       start = new Point(x1, y1);
       end = new Point(x2, y2);
       this.color = new Color(color);
    }

    public Line(Point p1, Point p2, int color) {
        start = p1;
        end = p2;
        this.color = new Color(color);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public Color getColor() {
        return color;
    }
}
