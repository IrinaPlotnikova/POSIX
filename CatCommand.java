package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CatCommand extends POSIXCommand{

    boolean isShowDollar;     // флаг отображения номера
    boolean isShowRowNumber;  // флаг отображения номера строки

    /* Выводит содержимое указанного текстового файла (или нескольких файлов).
     * В случае вывода нескольких файлов их содержимое показывается последовательно.
     * Если один или несколько файлов не существуют, выводит в stderr сообщение об этом файле.
     * Ключи:
     *     -E - опциональный ключ, который указывает, что необходимо печатать номер строки в ее начале
     *     -n - опциональный ключ, который указывает, что необходимо печатать символ '$' в конце строк
     * @param path       полный путь к директории для работы
     * @param options    список используемых ключей
     * @param notOptions список файлов, которые необходимо распечатать
     * */
    public int run(String path, char[] options, String[] notOptions){
        if (notOptions.length == 0){ // если не указаны файлы
            System.err.println("Ошибка: не указаны файлы");
            return 1;
        }

        isShowDollar = containsOption('E', options);     // флаг отображения номера
        isShowRowNumber = containsOption('n', options);  // флаг отображения номера строки

        int wrongFileNum = allFileExists(path, notOptions);
        if (wrongFileNum > 0){
            System.out.println("Ошибка: файл " + notOptions[wrongFileNum - 1] + " не существует");
            return 1;
        }

        for (String option : notOptions){
            File file = new File(path + '\\' + option);
            printFile(file);
            System.out.println();
        }

        return 0;
    }


    // Печатает справочную информацию о команде ls
    public void help(){
        System.out.println("posix cat [ключи] \"названия файлов\"");
        System.out.println("    (выводит содержимое указанного текстового файла (или нескольких файлов).\n" +
                           "     Если один из файлов не существует, выводит в stderr сообщение об этом файле.)");
        System.out.println("ключи:");
        System.out.println("    -n <указывает, что необходимо печатать символ '$' в конце строк>");
        System.out.println("    -E <указывает, что необходимо печатать номер строки в ее начале>");
    }


    /* Проверяет все ли текстовые файлы существуют.
     * @param path    полный путь к директории для работы
     * @param options список ключей и файлов
     * @return        0 - если все текстовые файлы существуют,
     *                номер первого несуществующего файла - иначе.
     * */
    public int allFileExists(String path, String[] options){
        for (int i = 0; i < options.length; i++) {
            File file = new File(path + '\\' + options[i]);
            if (!file.exists())
                return i + 1;
        }
        return 0;
    }


    /* Печатает содержимое файла.
     * @param file файл, который необходимо распечатать
     * */
    public void printFile(File file){
        System.out.println(file.getName() + ':');
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            int rowNumber = 1;
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (isShowRowNumber){
                    line = rowNumber + " " + line;
                }
                if (isShowDollar){
                    line += '$';
                }
                System.out.println(line);
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

}
