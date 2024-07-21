package com.lesson_14.homework.tms;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Runner {

    public static void main(String[] args) {
        // Создаем объект Scanner для чтения ввода пользователя
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        // Создаем объект CheckerValidDoc для проверки и обработки файла
        CheckerValidDoc checkerValidDoc = new CheckerValidDoc();

        while (true) {
            System.out.print("Введите путь до файла Doc.txt:");
            String string = scanner.nextLine();

            // Проверка существования и правильности файла
            if (checkerValidDoc.checkerOfFile(string)) {
                // Установка пути к файлу
                checkerValidDoc.setFile(string);
                // Запуск проверки файла
                checkerValidDoc.runCheck();

                // Спрашиваем пользователя, хочет ли он вывести данные на экран
                System.out.print("Вывести данные на экран ?(y/n):");
                String displayData = scanner.nextLine();
                // Проверка правильности ввода
                while (!displayData.equalsIgnoreCase("y") && !displayData.equalsIgnoreCase("n")) {
                    System.out.print("Неверный ввод. Вывести данные на экран ?(y/n):");
                    displayData = scanner.nextLine();
                }
                if (displayData.equalsIgnoreCase("y")) {
                    System.out.println("Валидные данные:");
                    // Выводим валидные данные
                    System.out.println(checkerValidDoc.printValidFile());
                    System.out.println("Невалидные данные:");
                    // Выводим невалидные данные
                    System.out.println(checkerValidDoc.printNotValidFile());
                }

                // Спрашиваем пользователя, хочет ли он удалить файлы с данными
                System.out.print("Удалить файлы с данными ?(y/n):");
                String deleteData = scanner.nextLine();
                // Проверка правильности ввода
                while (!deleteData.equalsIgnoreCase("y") && !deleteData.equalsIgnoreCase("n")) {
                    System.out.print("Неверный ввод. Удалить файлы с данными ?(y/n):");
                    deleteData = scanner.nextLine();
                }

                if (deleteData.equalsIgnoreCase("y")) {
                    // Удаляем файлы с данными
                    checkerValidDoc.delete();
                }
                // Выходим из цикла, так как работа завершена
                break;
            } else {
                // Сообщение о неверном пути или отсутствии файла
                System.out.println("Введен неверный путь или такого файла не существует!");
            }
        }
    }
}
