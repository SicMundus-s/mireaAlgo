package task1;

import rx.Observable;

public interface Sensor {
    Observable<Integer> getData();
}