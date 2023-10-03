package task1;

import java.util.Scanner;
import java.util.concurrent.*;

public class UserFutureInput {
    public static void task2() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите число (или 'exit' для выхода): ");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                int number = Integer.parseInt(userInput);
                Future<Integer> futureResult = executorService.submit(() -> processRequest(number));

                try {
                    int result = futureResult.get(5, TimeUnit.SECONDS);
                    System.out.println("Результат: " + result);
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    System.out.println("Произошла ошибка при обработке запроса.");
                    System.out.println(e.getMessage());
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите целое число.");
            }
        }

        executorService.shutdown();
        scanner.close();
    }

    private static int processRequest(int number) {
        int delaySeconds = ThreadLocalRandom.current().nextInt(1, 6);
        System.out.println("Обработка запроса...");
        try {
            TimeUnit.SECONDS.sleep(delaySeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return number * number;
    }
}