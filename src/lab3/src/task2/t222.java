package task2;

import rx.Observable;

public class t222 {
    public static void execute() {
        Observable<Integer> stream1 = Observable.range(1, 1000).map(i -> (int) (Math.random() * 100));
        Observable<Integer> stream2 = Observable.range(1, 1000).map(i -> (int) (Math.random() * 100));

        Observable<Integer> mergedStream = Observable.merge(stream1, stream2);

        mergedStream.subscribe(System.out::println);
    }
}
