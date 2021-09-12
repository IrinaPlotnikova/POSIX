package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class EchoCommand extends POSIXCommand{

    /*  Создает в текущей директории текстовый файл с указанным именем и содержимым.
     *  В случае, если файл уже существует, печатает ошибку в stderr
     * и возвращается с ненулевым статус кодом.
     *  @param path       полный путь к директории для работы
     *  @param notOptions список с содержимым и названием файла
     *  @return           нулевой статус код - если файл успешно создан,
     *                    ненулевой статус код - если:
     *                        - файл уже существует;
     *                        - не указано название или содержание файла.
     * */
    public int run(String path, char[] options, String[] notOptions){
        if (notOptions.length <= 1){ // не указано содержимое файла и / или его название
            System.err.println("Ошибка: не указано имя и / или содержимое текстового файла");
            return 1;
        }

        String text = notOptions[0];      // содержимое файла
        String fileName = notOptions[1];  // название создаваемого файла

        if (!isCorrectFileName(fileName, ".txt")){ // если название файла некорректно
            System.err.println("Ошибка: некорректное название файла");
            return 1;
        }

        String filePath = path + '\\' + fileName;  // полный путь к новому файлу
        File newFile = new File(filePath);         // создаваемый файл

        if (newFile.exists()){  // если файл существует
            System.err.println("Ошибка: файл с данным названием уже существует");
            return 1;
        }

        try(FileWriter writer = new FileWriter(newFile)){
            writer.write(text);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
            return 1;
        }

        return 0;
    }


    // Печатает справочную информацию о команде echo
    public  void help(){
        System.out.println("posix echo \"содержимое файла\" \"название_файла.txt\"");
        System.out.println("    (создает в директории текстовый файл с указанным именем и содержимым,\n" +
                           "     если файл существует - печатает ошибку и возвращается с ненулевым статус кодом)");
        System.out.println("параметры:");
        System.out.println("    параметр 1 <содержимое файла>");
        System.out.println("    параметр 2 <название файла>");
    }

}
