import rx.Observable;
import task1.Alarm;
import task1.CO2Sensor;
import task1.Sensor;
import task1.TemperatureSensor;
import task3.UserFriend;
import task4.File;
import task4.FileGenerator;
import task4.FileProcessor;
import task4.Queue;


public class Main {


    private static Integer[] randomUserIds = {1, 2, 3, 4, 5}; // Замените этот массив своими данными

    public static void main(String[] args) {
        Sensor temperatureSensor = new TemperatureSensor();
        Sensor co2Sensor = new CO2Sensor();

        Observable<Integer> temperatureData = temperatureSensor.getData();
        Observable<Integer> co2Data = co2Sensor.getData();

        Alarm alarm = new Alarm();

        temperatureData.subscribe(alarm);
        co2Data.subscribe(alarm);


        // task 3

        Observable<UserFriend> userIdStream = Observable.from(randomUserIds)
                .flatMap(UserFriend::getFriends);

        userIdStream.subscribe(System.out::println);


        // task 4

        FileGenerator fileGenerator = new FileGenerator();
        Queue queue = new Queue();
        FileProcessor xmlProcessor = new FileProcessor("XML");
        FileProcessor jsonProcessor = new FileProcessor("JSON");
        FileProcessor xlsProcessor = new FileProcessor("XLS");

        Observable<File> fileStream = fileGenerator.generateFiles();
        Observable<File> queuedFiles = queue.acceptFiles(fileStream);

        Observable<File> processedXMLFiles = xmlProcessor.processFiles(queuedFiles);
        Observable<File> processedJSONFiles = jsonProcessor.processFiles(queuedFiles);
        Observable<File> processedXLSFiles = xlsProcessor.processFiles(queuedFiles);

        processedXMLFiles.subscribe(file -> System.out.println("Processed XML file: " + file.type()));
        processedJSONFiles.subscribe(file -> System.out.println("Processed JSON file: " + file.type()));
        processedXLSFiles.subscribe(file -> System.out.println("Processed XLS file: " + file.type()));
    }
}