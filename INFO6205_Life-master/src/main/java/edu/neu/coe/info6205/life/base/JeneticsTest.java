package edu.neu.coe.info6205.life.base;
import io.jenetics.*;
import io.jenetics.engine.*;
import io.jenetics.util.Factory;
import io.jenetics.util.IntRange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static io.jenetics.engine.Codecs.ofVector;

public class JeneticsTest {

    private static int run;
    private static int eval(int[] cells) {

        List<Point> points=new ArrayList<>();
       for(int position=0;position<100;position++){
           if (cells[position]!=1) continue;

           int x=position%10;
           int y=position/10;
           Point point=new Point(x,y);
           points.add(point);
       }


        Game game=new Game();
        final Game.Behavior generations=game.run(0L, points);

        run++;
        return (int)generations.generation;

    }

    public static void main(String[] args) {
        // This part need to be optimized
        Engine<IntegerGene, Integer> engine = Engine
                .builder(JeneticsTest::eval, ofVector(IntRange.of(0,1), 100))
                .populationSize(1000)
                .alterers(new Mutator<>(0.1),new  SinglePointCrossover(0.2))
                .survivorsSelector(new TournamentSelector<>(10))
                .offspringSelector(new RouletteWheelSelector<>())
                .optimize(Optimize.MAXIMUM)
                .build();




        Genotype<IntegerGene> result = engine.stream()
                //.limit(Limits.bySteadyFitness(7))
                .limit(100)
                .collect(EvolutionResult.toBestGenotype());

        System.out.println("RUN:     "+run);



        List<int[]> pos=getStartingPattern(result);

        System.out.println(pos.size());

//        for (int[] p:pos){
//            System.out.println(p[0]+" "+p[1]+", ");
//        }
    }

    public static List<int[]> getStartingPattern(Genotype<IntegerGene> result){

        List<int[]> list=new ArrayList<>();

        IntegerChromosome chromosome=(IntegerChromosome) result.getChromosome();
        int[] nodes=chromosome.toArray();

        for(int i=0;i<100;i++){
            if(nodes[i]==1){
                int[] pos={i%10, i/10};
                list.add(pos);
            }
        }

        return list;
    }
}
