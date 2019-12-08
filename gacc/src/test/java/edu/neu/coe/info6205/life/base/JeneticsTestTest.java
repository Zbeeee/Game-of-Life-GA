package edu.neu.coe.info6205.life.base;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JeneticsTestTest {

    @Test
    public void main() {
    }

    @Test
    public void expression(){
        int[] cells=new int[100];
        cells[0]=1;
        cells[10]=1;
        cells[66]=1;

        List<Point> testPoints=new ArrayList<>();
        for(int position=0;position<100;position++){
            if (cells[position]!=1) continue;

            int x=position%10;
            int y=position/10;
            Point point=new Point(x,y);
            testPoints.add(point);
        }

        List<Point> realPoints=new ArrayList<>();
        realPoints.add(new Point(0,0));
        realPoints.add(new Point(0,1));
        realPoints.add(new Point(6,6));

        assertEquals(testPoints,realPoints);
    }

    @Test
    public void getStartingPattern() {
    }
}