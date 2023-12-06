package task2;

import rx.Observable;

public class t232 {

    public static void execute() {
        Observable<Integer> numbersStream = Observable.range(1, 10).map(i -> (int) (Math.random() * 100));

        Observable<Integer> first5NumbersStream = numbersStream.take(5);

        first5NumbersStream.subscribe(System.out::println);
    }
}
