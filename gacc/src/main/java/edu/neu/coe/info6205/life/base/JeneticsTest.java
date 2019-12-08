package edu.neu.coe.info6205.life.base;
import io.jenetics.*;
import io.jenetics.engine.*;
import io.jenetics.util.IntRange;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.*;
import java.util.List;
import java.util.Timer;

import static io.jenetics.engine.Codecs.ofVector;

public class JeneticsTest {
    private static int run;
    public static HashMap<Long, Group> groupHashMap = new HashMap<>();

    private static int eval(int[] cells) {

        List<Point> points = new ArrayList<>();
        for (int position = 0; position < 100; position++) {
            if (cells[position] != 1) continue;

            int x = position % 10;
            int y = position / 10;
            Point point = new Point(x, y);
            points.add(point);
        }


        Game game = new Game();
        final Game.Behavior generations = game.run(0L, points, Game.MaxGenerations);

        run++;
        return (int) generations.generation;
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        Engine<IntegerGene, Integer> engine = Engine
                .builder(JeneticsTest::eval, ofVector(IntRange.of(0, 1), 100))
                .populationSize(100)
                .survivorsSelector(new TournamentSelector<>(10))
                .offspringSelector(new RouletteWheelSelector<>())
                .alterers(new Mutator<>(0.2), new SinglePointCrossover(0.5))
                .optimize(Optimize.MAXIMUM)
                .build();

        EvolutionStatistics<Integer, ?> statistics = EvolutionStatistics.ofNumber();

        Genotype<IntegerGene> result = engine.stream()
                .limit(Limits.byFitnessThreshold(999))
                .limit(1000)
                .peek(statistics)
                .collect(EvolutionResult.toBestGenotype());


        List<Point> pos = getStartingPattern(result);


//        List<Point> points=new ArrayList<>();
//
//        int[] array=result.getChromosome().toArray();
//        for(int i=0;i<array.length;i++){
//            for(int j=0;j<array[i].length;j++){
//                if(array[i][j]==1) points.add(new Point(j,i));
//            }
//        }

        JFrame frame = new JFrame();
        Draw draw = new Draw();
        draw.setPoints(pos);
        frame.add(draw);
        frame.setVisible(true);
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Label label = new Label();
        label.setText("Generation: origin" + "\n" + " size: " + pos.size());
        label.setBounds(5, 5, 30, 30);
        draw.add(label);
        draw.repaint();

        Game game = new Game();
        game.setFlag(true);
        final Game.Behavior generations = game.run(0L, pos, Game.MaxGenerations);

        System.out.println("Statistics:   " + statistics);

        System.out.println("Group size:" + groupHashMap.size());


        File resFlie = new File("F:/game.csv");
        resFlie.createNewFile();
        BufferedWriter writeRes = new BufferedWriter(new FileWriter(resFlie));
        for(Group group:groupHashMap.values()){
            writeRes.newLine();
            writeRes.write(group.getCount());
        }


        writeRes.flush();
        writeRes.close();




        while (true) {
            int i = 1;
            for (Group group : groupHashMap.values()) {
                //draw.getGraphics().clearRect(0,0,400,400);
                label.setText("Generation: " + i++ + "\n" + " size: " + group.getCount());
                draw.setPoints(group.getPoints());
                draw.repaint();
                Thread.sleep(1000);
            }
            label.setText("Generation: origin" + "\n" + "size: " + pos.size());
            draw.setPoints(pos);
            draw.repaint();
            Thread.sleep(1000);
        }

    }
        public static List<Point> getStartingPattern (Genotype < IntegerGene > result) {

            List<Point> list = new ArrayList<>();

            IntegerChromosome chromosome = (IntegerChromosome) result.getChromosome();
            int[] nodes = chromosome.toArray();

            for (int i = 0; i < nodes.length; i++) {
                if (nodes[i] == 1) {
                    Point point = new Point(i % 10, i / 10);
                    list.add(point);
                }
            }

            return list;
        }



}
