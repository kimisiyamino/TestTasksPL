// 10.09.2021
// LevSergeev
//
// Task4
//
// Обязательные входные параметры:
// -fileName - Файл с числами, разделёнными \n
//
//

package task4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Тут будут числа из файла
        StringBuilder stringBuilder = new StringBuilder();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]))){

            // Читаем файл
            while(bufferedReader.ready()){
                stringBuilder.append(bufferedReader.readLine() + " ");
            }

            // Получаем строковый массив чисел
            String[] _nums = stringBuilder.toString().split(" ");
            int[] nums = new int[_nums.length];

            int sum = 0;
            int average = 0;

            // Парсим массив в int
            for(int i = 0; i < _nums.length; i++){
                nums[i] = Integer.parseInt(_nums[i]);
                // Считаем сумму чисел массива
                sum+=nums[i];
            }

            // Получаем среднее арифметическое
            average = sum / nums.length;

            int steps = 0;

            // Считаем шаги до нужного числа
            for (int num : nums) {
                steps += Math.abs(average - num);
            }

            System.out.print(steps);

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
