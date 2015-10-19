package hhtask1;

/** 
 * Задача 1. Минимальное расстояние
 * 
 * Дан набор из N точек на плоскости (для простоты можно считать, что у всех 
 * точек целочисленные координаты). Найдите минимальное расстояние между двумя 
 * точками из этого набора.
 * 
 *
 * Пример входных данных:
 * 10 10
 * 20 10
 * 20 15
 *
 * Пример выходных данных:
 * 5
 * 
 * --------------------------------------------------------------------------
 * 
 * Выполнение:
 * 
 * java -jar MinimalDistance.jar [filename]
 * 
 * где filename - файл, содержащий входные данные (по умолчанию используется
 * имя input.txt) 
 * Файлы MinimalDistance.jar и демонстрационный input.txt находятся 
 * в директории dist проекта
 */

import java.io.*;
import java.util.*;

/**
 * Реализует решение задачи о поиске минимального расстояния между двумя 
 * точками на плоскости с использованием рекурсивного алгоритма
 * (принцип "разделяй и властвуй")
 * 
 * @author Vladimir Trusov
 */
public class MinimalDistance {

    /** Точки плоскости, для которых было найдено минимальное расстояние */
    private Point pt1;
    private Point pt2;
    /** Квадрат расстояния между ближайшими точками */
    private int minDistSqr = Integer.MAX_VALUE;
    /** Массив точек, среди которых осуществляется поиск минимального 
     расстояния*/
    private Point[] points;
    /** Вспомогательный массив точек */
    private Point[] tempPoints;

    /**
     * Конструктор класса. Выполняет инициализацию массива точек
     * @param points массив точек, для которых осуществляется поиск 
     * минимального расстояния
     */
    public MinimalDistance(Point[] points) {
        setPoints(points);
    }
    
    /**
     * Выполняет инициализацию массива точек
     * @param points массив точек, для которых осуществляется поиск 
     * минимального расстояния
     */
    public void setPoints(Point[] points) {
        this.points = points;
    }

    /**
     * Запускает рекурсивный алгоритм ппоиска минимального расстояния между 
     * точками. Выполняет предварительные проверки.
     */
    public void startSearch() {
        if (this.points.length < 2) {
            return;
        }
        sortPointsByX(this.points);
        if (checkForEqualPoints(this.points)) {
            return;
        }
        this.tempPoints = new Point[this.points.length];
        find(0, this.points.length - 1);
    }

    /**
     * Выводит информацию с результатами последнего поиска 
     */
    public void printSearchResult() {
        System.out.println(this.minDistSqr + " " + this.pt1 + " " + this.pt2);
    }

    /**
     * Выводит информацию о минимальном расстоянии между точками, 
     * найденном при последнем поиске
     */
    public void printMinDist() {
        if (this.minDistSqr >= 0) {
            System.out.println("Минимальное расстояние: "
                    + Math.sqrt(this.minDistSqr));
        } else {
            System.out.println("Превышена допустимая точность вычислений");
        }
    }

    /**
     * Рекурсивная функция, реализующая алгоритм поиска минимального 
     * расстояния между точками. Алгоритм действует по принципу "разделяй и
     * властвуй", рекурсивно разбивая множество точек на два подмножества
     * и осуществляя поиск решения для этих подмножеств и объединение 
     * полученных результатов.
     * @param left начальный индекс подмножества точек
     * @param right конечный индекс подмножества точек
     */
    private void find(int left, int right) {
        if (right - left < 4) {
            startBruteForce(left, right);
            return;
        }
        
        int middle = (left + right) / 2;
        int midX = this.points[middle].getX();
        find(left, middle);
        find(middle + 1, right);

        merge(this.points, this.tempPoints, left, middle, right);

        int count = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(this.points[i].getX() - midX) < this.minDistSqr) {
                for (int j = count - 1; (j >= 0)
                        && (this.points[i].getY() - this.tempPoints[j].getY() 
                        < this.minDistSqr); j--) {
                    updateResult(this.points[i], this.tempPoints[j]);
                }
                this.tempPoints[(count++)] = this.points[i];
            }
        }
    }

    /** 
     * Обновляет информацию о ближайших точках и квадрате расстояния между ними,
     * если точки переданные методу в качестве параметров обеспечивают лучшее
     * решение задачи
     * @param p1 первая рассматриваемая точка
     * @param p2 вторая рассматриваемая точка
     */
    private void updateResult(Point p1, Point p2) {
        int distSqr = calcDistanceSqr(p1, p2);
        if (distSqr < this.minDistSqr) {
            this.minDistSqr = distSqr;
            this.pt1 = p1;
            this.pt2 = p2;
        }
    }
    
    /**
     * Вычисляет квадрат расстояния между двумя точками на плоскости
     * @param p1 первая точка
     * @param p2 вторая точка
     * @return квадрат расстояния между точками
     */
    private int calcDistanceSqr(Point p1, Point p2) {
        return (p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + 
                (p1.getY() - p2.getY()) * (p1.getY() - p2.getY());
    }

    /**
     * Выполняет сортировку массива точек по координате X
     * @param points массив точек
     */
    private void sortPointsByX(Point[] points) {
        Arrays.sort(points, new Comparator<Point>() {
            public int compare(Point p1, Point p2) {                
                return (p1.getX() == p2.getX() && p1.getY() == p2.getY()) ? 
                        0 : p1.getX() < p2.getX() ||
                        (p1.getX() == p2.getX() && p1.getY() < p2.getY()) ? 
                        -1 : 1;
            }
        });
    }

    /**
     * Проверяет отсортированный (по X) массив точек на наличие совпадений
     * @param points массив точек
     * @return true, если в массиве имеются совпадающие точки и false
     * в противном случае
     * <p>Замечание: массив точек должен быть отсортирован по X</p>
     */
    private boolean checkForEqualPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].equals(points[(i + 1)])) {
                this.minDistSqr = 0;
                this.pt1 = points[i];
                this.pt2 = points[(i + 1)];
                return true;
            }
        }
        return false;
    }

    /**
     * Выполняет слияние множеств arr[left, middle] и arr[middle+1, right]
     * @param arr массив, для которого выполняется слияние
     * @param arrTemp вспомогательный массив
     * @param left левая граница подмножества
     * @param middle средняя граница подмножества
     * @param right левая граница подмножества
     */
    private void merge(Comparable[] arr, Comparable[] arrTemp, int left, int middle, int right) {
        for (int k = left; k <= right; k++) {
            arrTemp[k] = arr[k];
        }
        int i = left;
        int j = middle + 1;
        for (int k = left; k <= right; k++) {
            if (i > middle) {
                arr[k] = arrTemp[(j++)];
            } else if (j > right) {
                arr[k] = arrTemp[(i++)];
            } else if (arrTemp[j].compareTo(arrTemp[i]) < 0) {
                arr[k] = arrTemp[(j++)];
            } else {
                arr[k] = arrTemp[(i++)];
            }
        }
    }

    /**
     * Выполняет поиск наименьшего расстояния между точками
     * из диапазона методом прямого перебора
     * @param left  левая граница диапазона
     * @param right правая граница диапазона     
     */
    public void startBruteForce(int left, int right) {
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                updateResult(this.points[i], this.points[j]);
            }
        }
    }

    public static void main(String[] args) {
        String inFileName = args.length > 0 ? args[0] : "input.txt";
        File inFile = new File(inFileName);
        ArrayList<Point> pointList = new ArrayList();
        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(inFile));

            int count = 0;
            String strPoint;
            while ((strPoint = bufReader.readLine()) != null) {
                count++;
                try {
                    pointList.add(new Point(strPoint, " "));
                } catch (StrPointFormatException e) {
                    System.out.println("Строка " + count + " должна содержать два значения: " + strPoint);
                } catch (NumberFormatException e) {
                    System.out.println("Недопустимый формат записи числа в строке " + count + ": " + strPoint);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Отсутствует файл со входными данными");
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла");
            System.exit(0);
        }
        if (pointList.size() > 1) {
            MinimalDistance md = new MinimalDistance((Point[]) pointList.toArray(new Point[pointList.size()]));
            md.startSearch();
            System.out.println("Список точек: " + pointList);
            md.printMinDist();
        } else {
            System.out.println("Файл содержит некорректные данные");
        }
    }
}
