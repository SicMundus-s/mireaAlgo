package task1;

import java.util.Random;
import java.util.concurrent.*;

public class MultithreadedFileProcessingSystem {
    public static void task3() {
        // Создание блокирующей очереди
        BlockingQueue<File> fileQueue = new LinkedBlockingQueue<>(5);

        // Создание и запуск генератора файлов
        FileGenerator fileGenerator = new FileGenerator(fileQueue);
        new Thread(fileGenerator).start();

        // Создание и запуск обработчиков файлов для каждого типа
        FileProcessor xmlProcessor = new FileProcessor(fileQueue, "XML");
        FileProcessor jsonProcessor = new FileProcessor(fileQueue, "JSON");
        FileProcessor xlsProcessor = new FileProcessor(fileQueue, "XLS");

        new Thread(xmlProcessor).start();
        new Thread(jsonProcessor).start();
        new Thread(xlsProcessor).start();
    }
}

record File(String fileType, int size) {
}

class FileGenerator implements Runnable {
    private final BlockingQueue<File> fileQueue;

    public FileGenerator(BlockingQueue<File> fileQueue) {
        this.fileQueue = fileQueue;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (!Thread.interrupted()) {
            try {
                String fileType = generateRandomFileType();
                int fileSize = random.nextInt(91) + 10;

                File file = new File(fileType, fileSize);
                fileQueue.put(file);

                Thread.sleep(random.nextInt(901) + 100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private String generateRandomFileType() {
        String[] fileTypes = {"XML", "JSON", "XLS"};
        Random random = new Random();
        return fileTypes[random.nextInt(fileTypes.length)];
    }
}

class FileProcessor implements Runnable {
    private final BlockingQueue<File> fileQueue;
    private final String supportedFileType;

    public FileProcessor(BlockingQueue<File> fileQueue, String supportedFileType) {
        this.fileQueue = fileQueue;
        this.supportedFileType = supportedFileType;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                File file = fileQueue.take();

                if (file.fileType().equals(supportedFileType)) {
                    int processingTime = file.size() * 7;
                    Thread.sleep(processingTime);

                    System.out.println("task1.File: Type=" + file.fileType() +
                            ", Size=" + file.size() +
                            ", Time=" + processingTime + " ms");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}