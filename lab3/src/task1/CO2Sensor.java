package task1;

import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CO2Sensor implements Sensor {
    @Override
    public Observable<Integer> getData() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .map(tick -> new Random().nextInt(71) + 30);
    }
}
