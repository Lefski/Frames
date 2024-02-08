package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        MatrixManager matrixManager = new MatrixManager();

        try {
            int t = Integer.parseInt(reader.readLine());
            for (int i = 0; i < t; i++) { //итерации по наборам тестовых данных
                String[] sizeLine = reader.readLine().split(" ");
                int stringsAmount = Integer.parseInt(sizeLine[0]);
                int stringsLength = Integer.parseInt(sizeLine[1]);
                matrixManager.setMatrix(stringsAmount, stringsLength);
                matrixManager.fillMatrix(reader);//заполнение матрицы, передаем читалку чтобы менеджер сам считал из инпута
                matrixManager.solveMatrix();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}

