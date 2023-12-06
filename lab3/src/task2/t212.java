package task2;

import rx.Observable;

public class t212 {

    public static void execute() {
        Observable<Integer> numbersStream = Observable.range(0, 1000)
                .map(i -> (int) (Math.random() * 1000));

        Observable<Integer> filteredStream = numbersStream.filter(number -> number > 500);

        filteredStream.subscribe(System.out::println);
    }
}
