package net.openhft.chronicle.core.latencybenchmark;

import net.openhft.chronicle.core.Jvm;
import net.openhft.chronicle.core.util.NanoSampler;

/**
 * Created by daniel on 08/03/2016.
 */
public class ExampleLatencyMain implements LatencyTask {
    int count = 0;
    private LatencyTestHarness lth;
    //private NanoSampler nanoSamplerSin;
    //private NanoSampler nanoSamplerWait;

    public static void main(String[] args) {
        LatencyTestHarness lth = new LatencyTestHarness()
                .warmUp(50_000)
                .messageCount(100_000)
                .throughput(25_000)
                .accountForCoordinatedOmmission(true)
                .runs(3)
                .accountForCoordinatedOmmission(true)
                .build(new ExampleLatencyMain());
        lth.start();
    }

    double sin;
    @Override
    public void run(long startTimeNS) {
        count++;
        if(count==160_000) {
            System.out.println("PAUSE");
            //long now = System.nanoTime();
            Jvm.pause(1000);
            //nanoSamplerWait.sampleNanos(System.nanoTime()-now);
        }

        long now = System.nanoTime();
        sin = Math.sin(count);
        //nanoSamplerSin.sampleNanos(System.nanoTime()-now);

        lth.sample(System.nanoTime()-startTimeNS);
    }

    @Override
    public void init(LatencyTestHarness lth) {

        this.lth = lth;
        //nanoSamplerSin = lth.createAdditionalSampler("sin");
        //nanoSamplerWait = lth.createAdditionalSampler("busyWait");
    }

    @Override
    public void complete() {
    }
}
