package task1;

import rx.Observer;

public class Alarm implements Observer<Integer> {
    private int temperatureThreshold = 25;
    private int co2Threshold = 70;

    @Override
    public void onNext(Integer value) {
        if (value > temperatureThreshold && value <= co2Threshold) {
            System.out.println("Предупреждение: Перевышение нормы температуры!");
        } else if (value <= temperatureThreshold && value > co2Threshold) {
            System.out.println("Предупреждение: Перевышение нормы CO2!");
        } else if (value > temperatureThreshold && value > co2Threshold) {
            System.out.println("ALARM!!!");
        }
    }

    @Override
    public void onCompleted() {
        // Не используется в данной реализации
    }

    @Override
    public void onError(Throwable e) {
        // Не используется в данной реализации
    }

}