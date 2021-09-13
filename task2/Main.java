// 10.09.2021
// LevSergeev
//
// Task2
//
// Обязательные входные параметры:
// -fileName1 - Координаты центра окружности\n Радиус окружности
// -fileName2 - Координаты точек x y (от 1 до 100)
//

package task2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    // Максимальное количество точек (по условию задачи 100)
    public static final int MAX_COORDS = 100;

    public static void main(String[] args) {

        // 0 - Координаты центра окружности
        // 1 - Радиус окружности
        String[] circleData = new String[2];
        // Итератор
        int i = 0;

        // Здесь будем хранить файл
        List<String> lines = new ArrayList<>();

        try(BufferedReader fileReaderCircle = new BufferedReader(new FileReader(new File(args[0])));
            BufferedReader fileReaderCoords = new BufferedReader(new FileReader(new File(args[1])))){

            // Читаем первый файл
            while (fileReaderCircle.ready()){
                circleData[i] = fileReaderCircle.readLine();
                i++;
            }

            // Парсим и сохраняем данные окружности
            float circleCoordX = Float.parseFloat(circleData[0].split(" ")[0]);
            float circleCoordY = Float.parseFloat(circleData[0].split(" ")[1]);
            float radius = Float.parseFloat(circleData[1]);

            // Счётчик точек (Условие задачи от 1 до 100)
            int coordCount = 0;

            // Читаем второй файл
            while (fileReaderCoords.ready()){
                lines.add(fileReaderCoords.readLine());
                coordCount++;
            }

            // Проверяем количество точек
            if(coordCount > MAX_COORDS || coordCount <= 0){
                return;
            }

            for(String line : lines) {
                // Получаем коордиаты точки
                float x = Float.parseFloat(line.split(" ")[0]);
                float y = Float.parseFloat(line.split(" ")[1]);

                /*
                    a = (x - xo)^2 + (y - yo)^2
                    a == R^2 - Лежит на окружности [0]
                    a < R^2 - Принадлежит окружности [1]
                    a > R^2 - Не принадлежит окружности [2]
                */

                double a = Math.pow(x - circleCoordX, 2) + Math.pow(y - circleCoordY, 2);
                double radiusSquared = Math.pow(radius, 2);

                if (a == radiusSquared)
                    System.out.println(0);
                else if (a < radiusSquared)
                    System.out.println(1);
                else
                    System.out.println(2);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}