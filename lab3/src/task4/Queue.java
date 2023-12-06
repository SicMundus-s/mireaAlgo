package task4;

import rx.Observable;
import rx.schedulers.Schedulers;

public class Queue {
    public Observable<File> acceptFiles(Observable<File> fileStream) {
        return fileStream.observeOn(Schedulers.newThread())
                .onBackpressureBuffer(5)
                .subscribeOn(Schedulers.io());
    }
}