package task1;

import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TemperatureSensor implements Sensor {
    @Override
    public Observable<Integer> getData() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .map(tick -> new Random().nextInt(16) + 15);
    }
}