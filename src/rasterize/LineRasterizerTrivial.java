package rasterize;

import java.awt.*;
import java.awt.image.BufferedImage;

import static java.lang.Integer.max;
import static java.lang.Math.round;
//todo modifikovat aby fungoval, implementace polygonu (co vsechno tam bude, array/list a tak)
public class LineRasterizerTrivial extends LineRasterizer {

    public LineRasterizerTrivial(rasterize.Raster raster) { super(raster); }

    public void test()
    {
        raster.setPixel(5,5, 0xff0000);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        if(x2<x1 || y2<y1)
        {
            int d = x2;
            x2=x1;
            x1=d;
            d = y2;
            y2=y1;
            y1=d;
        }
        int dx = x2-x1;
        int dy = y2-y1;
        float x, y, krokX, krokY;
        x = x1; y = y1;
        if (dx > dy) //víc horizontální
        {
            krokX = 1;
            krokY = (float)dy/dx;
        } else { //víc svíslý
            krokX = (float)dx/dy;
            krokY = 1;
        }
        for(int i = 0;i <= max(dx,dy);i++){
            raster.setPixel(round(x), round(y), color.getRGB());
            x = x + krokX;
            y = y + krokY;
        }
    }
}
