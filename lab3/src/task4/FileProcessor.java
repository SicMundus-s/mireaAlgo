package task4;

import rx.Observable;
import rx.schedulers.Schedulers;

public class FileProcessor {
    private String fileType;

    public FileProcessor(String fileType) {
        this.fileType = fileType;
    }

    public Observable<File> processFiles(Observable<File> fileStream) {
        return fileStream.filter(file -> file.type().equals(fileType))
                .map(file -> {
                    int processingTime = file.size() * 7;
                    try {
                        Thread.sleep(processingTime);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return file;
                })
                .subscribeOn(Schedulers.newThread());
    }
}