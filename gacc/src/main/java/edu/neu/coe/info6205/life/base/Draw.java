package edu.neu.coe.info6205.life.base;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static java.lang.Thread.sleep;


public class Draw extends JPanel{

    private List<Point> points;

    public void setPoints(List<Point> points) {
        this.points = points;
    }
    @Override
    public void paint(Graphics graphics){
        try {
            sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics.clearRect(0,0,1000,1000);
        for (Point point : points) {
            int x = point.getX() * 4 + 500;
            int y = point.getY() * 4 + 500;
            graphics.drawOval(x, y,4,4);
            graphics.fillOval(x, y,4,4);
        }

    }


}
