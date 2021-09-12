package com.company;

import java.io.File;

public class MkdirCommand extends POSIXCommand{

    /*  Создает в текущей директории новую директорию с указанным именем.
     *  В случае, если директория уже существует, печатает ошибку в stderr и
     *  возвращается с ненулевым статус кодом.
     *  @param path       полный путь к директории для работы
     *  @param option     список ключей
     *  @param notOptions список из одного элемента - названия новой директории
     *  @return           нулевой статус код - если директория успешно создана,
     *                    ненулевой статус код - если:
     *                        - отсутствует имя новой директории;
     *                        - имя новой директории некорректно;
     *                        - директория уже существует.
     * */
    public int run(String path, char[] options, String[] notOptions){
        if (notOptions.length == 0){
            System.err.println("Ошибка: не указано название директории");
            return 1;
        }

        if (!isCorrectFileName(notOptions[0], "")) {
            System.err.println("Ошибка: некорректное название директории");
            return 1;
        }

        File folder = new File(path + '\\' + notOptions[0]);
        if (!folder.mkdir()){
            System.err.println("Ошибка: директория уже существует");
            return 1;
        }
        return 0;
    }


    // Печатает справочную информацию о команде mkdir
    public void help(){
        System.out.println("posix mkdir \"название директории\"");
        System.out.println("    (создает новую директорию с указанным именем, в случае, \n" +
                           "     если директория уже существует, печатает ошибку в stderr)");
    }
}
