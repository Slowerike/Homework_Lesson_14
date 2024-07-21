package com.lesson_14.homework.tms;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckerValidDoc {
    private File file; // Файл, который будет проверяться
    private String reason; // Причина, по которой файл невалиден

    // Файлы для записи валидных и невалидных данных
    private final File outputFileValid = new File("D:\\core-spring-labfiles\\core-spring-labfiles\\Dz_14\\valid.txt");
    private final File outputFileNotValid = new File("D:\\core-spring-labfiles\\core-spring-labfiles\\Dz_14\\notValid.txt");

    // Конструктор по умолчанию
    public CheckerValidDoc() {
    }

    // Устанавливаем путь к файлу
    public void setFile(String stringOfPath) {
        Path path = Paths.get(stringOfPath);
        file = path.toFile();
    }

    // Проверяем, существует ли файл и имеет ли он имя "Doc.txt"
    public boolean checkerOfFile(String string) {
        Path path = Paths.get(string);
        File file = path.toFile();
        return path.getFileName().toString().equalsIgnoreCase("Doc.txt") && file.exists();
    }

    // Запускаем проверку файла
    public void runCheck() {
        // Получаем текущую дату и время
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateTimeFormatter.format(currentTime);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstValid = true; // Флаг для первой записи в файл валидных данных
            boolean isFirstNotValid = true; // Флаг для первой записи в файл невалидных данных

            // Читаем файл построчно
            while ((line = reader.readLine()) != null) {
                // Проверяем валидность строки
                if (valid(line)) {
                    writeToFile(outputFileValid, line, formattedDate, isFirstValid);
                    isFirstValid = false;
                } else {
                    writeToFile(outputFileNotValid, line + this.reason, formattedDate, isFirstNotValid);
                    isFirstNotValid = false;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Записываем строку в файл
    private void writeToFile(File file, String content, String formattedDate, boolean isFirst) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), StandardCharsets.UTF_8))) {
            if (isFirst) {
                writer.write(formattedDate + "\n");
            }
            writer.write(content + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Проверяем валидность строки
    public boolean valid(String input) {
        StringBuilder resultNotValide = new StringBuilder(" Причины ошибки:");
        boolean isValid = input.length() == 15;

        // Проверка длины строки
        if (input.length() < 15) {
            resultNotValide.append(" длина документа меньше 15 символов,");
        } else if (input.length() > 15) {
            resultNotValide.append(" длина документа больше 15 символов,");
        }

        // Проверка на содержание "docnum" или "contact" в начале строки
        if (!input.matches("^(docnum|contact).*")) {
            isValid = false;
            resultNotValide.append(" документ содержит не содержит docnum или contact в начале строки,");
        }

        // Проверка на валидные символы (буквы и цифры)
        if (!input.matches("[A-Za-z\\d]*")) {
            isValid = false;
            resultNotValide.append(" документ содержит не валидные символы (валидные: буквы, цифры),");
        }

        // Устанавливаем причину невалидности, если строка невалидна
        if (!isValid) {
            reasonNotValid(resultNotValide.replace(resultNotValide.length() - 1, resultNotValide.length(), ".").toString());
        }
        return isValid;
    }

    // Устанавливаем причину невалидности
    public void reasonNotValid(String string) {
        reason = string;
    }

    // Удаляем файлы валидных и невалидных данных
    public void delete() {
        deleteFile(outputFileValid);
        deleteFile(outputFileNotValid);
    }

    // Удаляем файл и выводим информацию об удалении
    private void deleteFile(File file) {
        if (file.exists()) {
            System.out.printf("Файл: %s весом: %d байт, удален из: %s%n", file.getName(), file.length(), file.getParent());
            file.delete();
        }
    }

    // Возвращаем содержимое файла с валидными данными
    public String printValidFile() {
        return printFileContent(outputFileValid);
    }

    // Возвращаем содержимое файла с невалидными данными
    public String printNotValidFile() {
        return printFileContent(outputFileNotValid);
    }

    // Читаем и возвращаем содержимое файла
    private String printFileContent(File file) {
        if (!file.exists()) {
            return "Файл с данными не существует";
        }

        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}