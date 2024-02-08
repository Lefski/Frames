package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Searcher {
    //базовое значение, с которого мы начинаем считать прямоугольники. 2 т.к. 1 и 0 используются в базовой матрице для отрисовки прямоугольников
    private static final int DEFAULT_RECTANGLE_INDEX = 2;

    //базовое значение уровня вложенности
    private static final int DEFAULT_NESTING_LEVEL = 0;

    private List<Integer> foundRectanglesList;
    private HashMap<Integer, Integer> rectanglesLengthMap;
    private int rectangleIndex;
    private int nestingLevel;


    //вообще тут должна быть только область поиска, но её муторно и неэффективно выделять и передавать каждый раз,
    // поэтому хранится вся матрица
    private int[][] matrix;
    private int xLength;//x координата правого нижнего угла = длина одной строки в матрице
    private int yLength;//y координата правого нижнего угла = количество строк в матрице


    public void search(int[][] matrix) {
        prepareToSearch(matrix); //обнуляем рабочие поля или задаем им новые значения
        findRectangles(new Point(0, 0), new Point(xLength, yLength));
    }

    private void prepareToSearch(int[][] matrix) {
        this.matrix = matrix;
        foundRectanglesList = new ArrayList<>();
        rectanglesLengthMap = new HashMap<>();
        rectangleIndex = DEFAULT_RECTANGLE_INDEX;
        nestingLevel = DEFAULT_NESTING_LEVEL;
        xLength = matrix[0].length - 1;
        yLength = matrix.length - 1;
    }

    /*
        Рекурсивная функция, принимает левый верхний и правый нижний угол рамок, внутри которых производит поиск.
        Точки имеют координаты [x,y], где точка [0,0] это верхний левый угол матрицы, а [stringsLength,stringsAmount]
        это правый нижний угол матрицы
    */
    public void findRectangles(Point upperLeftCorner, Point lowerRightCorner) {

        for (int y = upperLeftCorner.getY(); y < lowerRightCorner.getY(); y++) {
            for (int x = upperLeftCorner.getX(); x < lowerRightCorner.getX(); x++) {
                //проверяем не нашли ли мы границу отмеченного прямоугольника
                if (checkIfFoundMarkedBorder(matrix[y][x])) {
                    //пропускаем эту часть строки матрицы, т.к. её занимает уже отмеченный прямоугольник
                    x += rectanglesLengthMap.get(matrix[y][x]); //todo можно написать лучше, здесь два раза и +1 в некст ифе обращаемся к матрице, можно наверное один, еще проблемы что перепрыгиваю за край матрицы с увеличением
                    //проверяем не нашли ли мы новый прямоугольник
                } else if (checkIfFoundNewRectangleCorner(x, y)) {//todo поменять проверку а то костыль
                    manageRectangle(x, y);//основная логика todo поменять название а то не очень отражает суть функции

                }


            }
        }

    }

    private void manageRectangle(int x, int y) {
        //получаем прямоугольник, параллельно отрисовываем его как отмеченный
        Rectangle foundRectangle = redrawRectangle(new Point(x, y));
        //увеличиваем количество найденных прямоугольников на 1
        rectangleIndex++;
        //заносим уровень вложенности прямоугольника в список, который потом отсортируем и вернем как ответ
        foundRectanglesList.add(foundRectangle.getNestingLevel());
        //запоминаем длину и индекс прямоугольника для дальнейшего скипа его длины при поиске прямоугольников
        rectanglesLengthMap.put(foundRectangle.getIndex(), foundRectangle.getLength());

        //если прямоугольник не может вмещать другие прямоугольники, не проводим поиск внутри
        if (foundRectangle.isSmall()) return;
        //перед рекурсивным вызовом поиска прямоугольников увеличиваем уровень вложенности
        nestingLevel++;
        //вызов поиска прямоугольников внутри найденного прямоугольника
        findRectangles(foundRectangle.getUpperLeftCornerOfSearchZone(), foundRectangle.getLowerRightCornerOfSearchZone());
        //после выхода из рекурсии уменьшаем уровень вложенности
        nestingLevel--;
    }

    //проверка не является ли переданная координата левым верхним углом прямоугольника
    public boolean checkIfFoundNewRectangleCorner(int x, int y) {
        return matrix[y][x] == 1 && matrix[y + 1][x] == 1 && matrix[y][x + 1] == 1;
    }

    public boolean checkIfFoundMarkedBorder(int value) {
        return (value != 1 && value != 0);
    }

    //правильней было бы разбить на две функции, но для оптимизации лучше если лишних проходов по массиву не будет
    public Rectangle redrawRectangle(Point upperLeftCorner) {
        int x = upperLeftCorner.getX();
        int y = upperLeftCorner.getY();
        //отрисовка верхней стороны
        while (matrix[y][x + 1] != 0 && matrix[y][x] == 1) {
            matrix[y][x] = rectangleIndex;
            x++;
        }
        //отрисовка правой стороны
        while (matrix[y + 1][x] != 0 && matrix[y][x] == 1) {
            matrix[y][x] = rectangleIndex;
            y++;
        }
        //мы в правом нижнем углу
        Point lowerRightCorner = new Point(x, y);
        //отрисовка нижней стороны + доп проверка что мы не вышли за границы матрицы
        while (x > 0 && matrix[y][x - 1] != 0 && matrix[y][x] == 1) {
            matrix[y][x] = rectangleIndex;
            x--;
        }
        //отрисовка левой стороны + доп проверка что мы не вышли за границы матрицы
        while (y > 0 && matrix[y - 1][x] != 0 && matrix[y][x] == 1) {
            matrix[y][x] = rectangleIndex;
            y--;
        }

        return new Rectangle(upperLeftCorner, lowerRightCorner, rectangleIndex, nestingLevel);

    }

    public void printResult() {
        Collections.sort(foundRectanglesList);
        StringBuilder result = new StringBuilder();
        for (Integer i : foundRectanglesList) {
            result.append(i).append(" ");
        }
        result.setLength(result.length() - 1);
        System.out.println(result);
    }


}
