// 09.09.2021
// LevSergeev
//
// Task1
//
// Обязательные входные параметры:
// -Длинна кругового массива N (int)
// -Интервал M (int)
//

package task1;

public class Main {
    public static void main(String[] args) {

        // Преобразовываем аргументы
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        if(n <= 0 || m <= 0){
            System.out.print(0);
            return;
        }

        int[] circleArr = new int[n];

        // Заполняем круговой массив
        for (int i = 0; i < n; i++) {
            circleArr[i] = i + 1;
        }

        // Изначальное значение M для кругового прохода по массиву и увеличения прохода по циклу
        int tempM = m;
        // Путь
        StringBuilder way = new StringBuilder(Integer.toString(circleArr[0]));

        // Если m > n, то: i % circleArr.length, где circleArr.length = n
        for (int i = 0; i < m; i++) {

            // Если это последний элемент интервала в цикле И если он не равен первому элементу
            if (i == m - 1 && circleArr[i % n] != circleArr[0]) {

                // Увеличиваем цикл, для дальнейшего циклирования
                m += tempM - 1;

                // Строим путь
                way.append(circleArr[i % n]);

                // Начинаем следующий проход с предыдущего элемента
                i--;
            }
        }
        // Вывод ответа в консоль
        System.out.print(way);
    }
}