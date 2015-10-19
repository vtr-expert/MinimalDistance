###Задача 1. Минимальное расстояние
Дан набор из N точек на плоскости (для простоты можно считать, что у всех точек целочисленные координаты). Найдите минимальное расстояние между двумя точками из этого набора.

Пример входных данных:  
10 10  
20 10  
20 15  

Пример выходных данных:  
5  

####Решение:
Тривиальное решение путем перебора всех пар точек выполняется за время O(n^2), что очень неэффективно для больших значений n. Поэтому для решения задачи был выбран другой метод, а именно рекурсивный алгоритм, действующий по принципу "разделяй и властвуй", описанный Ф. Препаратой и М. Шеймосом и выполняющийся на время O(n log n).

####Использование:

**java -jar MinimalDistance.jar [filename]**

где **filename** - файл, содержащий входные данные (по умолчанию используется имя **input.txt**).  
Файлы **MinimalDistance.jar** и демонстрационный **input.txt** находятся в директории **dist** проекта.

#####Пример файла со входными данными:
10 5  
22 34  
-22 12  
0 1  
-3 -6  
-11 -12  
-11 31  
13 -33  
5 1  
54 32   
