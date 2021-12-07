package ru.geekbrains.module2.lesson5;

import java.util.Arrays;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;
    static float[] arr = new float[size];
    static float[] arrFirstPart = new float[h];
    static float[] arrSecondPart = new float[size - h];

    public static void main(String[] args) {
        fill();
        final float[] resultOne = arr;
        fillInTread();
        final float[] resultTwo = arr;
        System.out.println(Arrays.equals(resultOne, resultTwo));
    }

    public static void fill(){
        Arrays.fill(arr, 1);

        final long startTime = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }

        final long finishTime = System.currentTimeMillis();

        System.out.println("Задача в методе fill выполнена за " + (finishTime - startTime) + "мс");
    }

    public static void fillInTread() {

        Arrays.fill(arr, 1);
        final long startTime = System.currentTimeMillis();

        System.arraycopy(arr, 0, arrFirstPart, 0, h);
        System.arraycopy(arr, h, arrSecondPart, 0, h);

        Thread treadOne = new Thread(() -> {
            for (int i = 0; i < h; i++) {
                arrFirstPart[i] = (float) (arrFirstPart[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread threadTwo = new Thread(() -> {
            for (int i = 0; i < size - h; i++) {
                arrSecondPart[i] = (float) (arrSecondPart[i] * Math.sin(0.2f + (i + h) / 5) * Math.cos(0.2f + (i + h) / 5) * Math.cos(0.4f + (i + h) / 2));
            }
        });

        treadOne.start();
        threadTwo.start();

        try {
            treadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arrFirstPart, 0, arr, 0, h);
        System.arraycopy(arrSecondPart, 0, arr, h, h);

        final long finishTime = System.currentTimeMillis();

        System.out.println("Задача в методе fillInTread выполнена за " + (finishTime - startTime) + "мс");
    }

}