package org.example;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class MatrixManager {
    private int[][] matrix;
    private int stringsAmount;
    private int stringsLength;
    private Searcher searcher;

    public void setMatrix(int stringsAmount, int stringsLength) {
        this.stringsAmount = stringsAmount;
        this.stringsLength = stringsLength;
        matrix = new int[stringsAmount+1][stringsLength+1];//увеличиваем немного размеры матрицы, нужно будет в дальнейшем для корректного поиска
        searcher = new Searcher();
    }
    public void fillMatrix(BufferedReader reader) {
        try {
            for (int i = 0; i < stringsAmount; i++) {
                String line = reader.readLine().replace(".","0").replace("*", "1"); //строка считывается и сразу обрабатывается
                for (int j = 0; j < line.length(); j++) {
                    matrix[i][j] = Character.getNumericValue(line.charAt(j));
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void solveMatrix (){//начало поиска рамок, передаем координаты всей матрицы для поиска
        searcher.search(matrix);
        searcher.findRectangles(new Point(0,0), new Point(stringsLength, stringsAmount));
        searcher.printResult();
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public int getStringsAmount() {
        return stringsAmount;
    }

    public int getStringsLength() {
        return stringsLength;
    }

    public Searcher getSearcher() {
        return searcher;
    }
}

