package edu.neu.coe.info6205.life.base;

import edu.neu.coe.info6205.life.library.Library;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.engine.Limits;
import io.jenetics.util.Factory;
import io.jenetics.util.IntRange;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static io.jenetics.engine.Codecs.ofVector;
import static io.jenetics.engine.EvolutionResult.toBestEvolutionResult;
import static org.junit.Assert.*;

public class JeneticsTestTest {

    // fitness function for mutator test
    private static int eval(Genotype<BitGene> gt) {
        return gt.getChromosome()
                .as(BitChromosome.class)
                .bitCount();
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


    // if mutate probability is 0, then the genotype should be unchanged.
    @Test
    public void mutator(){
        Factory<Genotype<BitGene>> geno=Genotype.of(BitChromosome.of(10,1));

        Engine<BitGene,Integer> engine = Engine
                .builder(JeneticsTestTest::eval, geno)
                .populationSize(1)
                .alterers(new Mutator<>(0))
                .build();

//        EvolutionResult<BitGene, Integer> result=engine.stream()
//                .limit(1)
//                .collect(toBestEvolutionResult());

        Genotype<BitGene> result = engine.stream()
                .limit(100)
                .collect(EvolutionResult.toBestGenotype());


        System.out.println();
        System.out.println();

        assertEquals(result.getChromosome(), ((Genotype<BitGene>) geno).getChromosome());

    }
    @Test
    public void selectorTest(){


        Genotype<BitGene> gtf = Genotype.of(BitChromosome.of(5, 0.5));

        Engine<BitGene, Integer> engine = Engine
                .builder(JeneticsTestTest::eval, gtf)
                .populationSize(100)
                .selector(new TournamentSelector<>(2))
                .build();

        Genotype<BitGene> best = engine.stream()
                .limit(1)
                .collect(EvolutionResult.toBestGenotype());

        assertEquals("[00011111]", best.toString());
    }

    @Test
    public void testFitnessFunction0() {
        final String pattern = "1 2, 2 2, 1 4, 2 4, 4 3, 2 5, 4 5";
        final Game.Behavior generations = Game.run(0L, pattern);
        assertEquals(1, generations.generation);
    }

    @Test
    public void testFitnessFunction() {
        String patternName = "Blip";
        final String pattern = Library.get(patternName);
        final Game.Behavior generations = Game.run(0L, pattern);
        assertEquals(0, generations.generation);
    }

    @Test
    public void testFitnessFunction1() {
        String patternName = "Beehive";
        final String pattern = Library.get(patternName);
        final Game.Behavior generations = Game.run(0L, pattern);
        assertEquals(2, generations.generation);
    }

    @Test
    public void testFitnessFunction2() {
        String patternName = "Glider3";
        final String pattern = Library.get(patternName);
        final Game.Behavior generations = Game.run(0L, pattern);
        assertEquals(9, generations.generation);
    }

    @Test
    public void testFitnessFunction3() {
        String patternName = "Glider2";
        final String pattern = Library.get(patternName);
        final Game.Behavior generations = Game.run(0L, pattern);
        assertEquals(12, generations.generation);
    }

    @Test
    public void testFitnessFunction4() {
        String patternName = "Glider1";
        final String pattern = Library.get(patternName);
        final Game.Behavior generations = Game.run(0L, pattern);
        assertEquals(14, generations.generation);
    }

    @Test
    public void testFitnessFunction5() {
        String patternName = "Blinker";
        final String pattern = Library.get(patternName);
        final Game.Behavior generations = Game.run(0L, pattern);
        assertEquals(2, generations.generation);
    }

}