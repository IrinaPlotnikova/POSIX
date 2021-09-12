package com.company;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class LsCommand extends POSIXCommand{

     boolean isReversed;   // флаг ключа -r (сортировка в обратном порядке)


    /* Запускает вывод названий директорий и файлов в текущей директории,
     * отсортированных по имени, по возрастанию.
     * Ключи:
     *   -r - опциональный ключ, который указывает, что нужна
     *        сортировка в обратном порядке;
     *   -R - опциональный ключ, который указывает, что кроме текущей
     *        директории требуется вывести рекурсивно содержимое всех
     *        вложенных директорий.
     * @param path      полный путь к директории для работы
     * @param options   список используемых ключей
     * @param notOptions список параметров, не являющихся ключами
     * */
    public int run(String path, char[] options, String[] notOptions){
        boolean isRecursive = containsOption('R', options);  // флаг ключа -R
        isReversed = containsOption('r', options);  // флаг ключа -r

        if (isRecursive){
            printFileNamesRecursive(path);
        }
        else {
            printFileNames(path);
        }
        return 0;
    }


    // Печатает справочную информацию о команде ls
    public void help(){
        System.out.println("posix ls [ключи]");
        System.out.println("    (выводит названия директорий и файлов в текущей директории,\n" +
                           "     отсортированных по имени, по возрастанию)");
        System.out.println("ключи:");
        System.out.println("    -r <указывает, что нужна сортировка в обратном порядке.>");
        System.out.println("    -R <указывает, что кроме текущей директории требуется\n" +
                           "        вывести рекурсивно содержимое всех вложенных директорий.>");
    }


    /* Печатает все файлы и каталоги, находящиеся в указанной директории.
     * @param path       полный путь к директории для работы
     * */
    void printFileNames(String path){
        File[] files = getAllFilesInDirectory(path);
        for (File file : files){
            System.out.println(file.getName());
        }
    }


    /* Печатает все файлы и каталоги, находящиеся в указанной директории,
     * а также рекурсивно печатает содержимое всех вложенных директорий.
     * @param path полный путь к директории для работы
     * */
    void printFileNamesRecursive(String path){
        File folder = new File(path);
        Queue<File> folders = new LinkedList<File>();
        folders.add(folder);

        while (!folders.isEmpty()){
            File currentFolder = folders.poll();
            String currentPath = currentFolder.getPath();

            File[] files = getAllFilesInDirectory(currentPath);
            if (files != null && files.length > 0){
                System.out.println();
                System.out.println(currentPath + '\\');

                for (File file : files) {
                    System.out.println("    " + file.getName());
                    if (file.isDirectory()) {
                        folders.add(file);
                    }
                }
            }
        }
    }


    /* Cоздает массив из всех файлов и каталогов,
     * расположенных в указанной директории.
     * В зависимости от значения флага isReversed сортирует файлы и каталоги
     * по возрастанию или убыванию их названий.
     * @param path полный путь к директории для работы
     * */
    File[] getAllFilesInDirectory(String path){
        File folder = new File(path);
        File[] result = folder.listFiles();
        if (result != null){
            if (isReversed)
                Arrays.sort(result, Collections.reverseOrder());
            else
                Arrays.sort(result);
        }
        return result;
    }
}
