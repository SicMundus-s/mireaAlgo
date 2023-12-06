package task4;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class FileGenerator {
    public Observable<File> generateFiles() {
        return Observable.range(1, 10)
                .map(i -> new File(getRandomType(), (int) (Math.random() * 91) + 10))
                .delay(getRandomDelay(), TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io());
    }

    private String getRandomType() {
        String[] types = {"XML", "JSON", "XLS"};
        return types[(int) (Math.random() * types.length)];
    }

    private int getRandomDelay() {
        return (int) (Math.random() * 901) + 100;
    }
}
