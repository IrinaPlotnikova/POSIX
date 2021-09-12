package com.company;

public abstract class POSIXCommand {

    /* Запускает выполнение команды.
     * @param path    директория, в которой выполняется команда
     * @param options список ключей команды
     * @param options  список параметров команды, не являющихся ключами
     * @return        ненулевой код, если команда не завершилась успешно,
     *                нулевой - иначе
     */
    public abstract int run(String path, char[] options, String[] notOptions);


    /* Печатает справочную информацию о команде,
     * содержащую ключи команды и т.д.
     * */
    public abstract void help();


    /* Проверяет необходимость использовать ключ.
     * @param key     проверяемый ключ
     * @param options указанные пользователем ключи
     * @return        true, если ключ необходимо использовать,
     *                false - иначе
     * */
    protected boolean containsOption(char key, char[] options){
        for (char option : options) {
            if (key == option)
                return true;
        }
        return false;
    }


    /* Из всех параметров, передаваемых пользователем, выбирает ключи.
     * Параметр считается ключем, если он начинается с символа '-'.
     * Если параметр ключ, то он разбивается на атомарные ключи.
     * @param allParams список всех строк, введенных пользователем для выполнения команды
     * @return          список ключей, каждый ключ - один символ буквы
     * */
    protected char[] getOptions(String[] allParams){
        StringBuilder options = new StringBuilder();
        for (String param : allParams) {
            if (param.startsWith("-")) {
                for (int j = 1; j < param.length(); j++) {
                    char symbol = param.charAt(j);
                    if (Character.isLetter(symbol)) {
                        options.append(symbol);
                    }
                }
            }
        }

        char[] result = new char[options.length()];
        for (int i = 0; i < options.length(); i++){
            result[i] = options.charAt(i);
        }
        return result;
    }


    /* Из всех параметров, передаваемых пользователем, выбирает не являющиеся ключами.
     * Параметр считается ключем, если он начинается с символа '-'.
     * @param allParams список всех строк, введенных пользователем для выполнения команды
     * @return          список параметров, не являющихся ключами
     * */
    protected String[] getNotOptions(String[] allParams){
        int size = 0;
        for (String param : allParams){
            if (!param.startsWith("-"))
                size++;
        }

        String[] result = new String[size];
        int j = 0;
        for (String param : allParams) {
            if (!param.startsWith("-")) {
                result[j] = param;
                j++;
            }
        }
        return result;
    }


    /* Проверяет название файла на корректность.
     * Название считается корректным, если оно не содержит символов '/', '\', ':', '*', '?', '<', '>', '|',
     * а также имеет определенный постфиксом.
     * @param dirName название, которое необходимо проверить
     * @return        true - если название корректно,
     *                false - иначе.
     * */
    protected boolean isCorrectFileName(String dirName, String postfix){
        String forbiddenSymbols = "/\\:*?<>|";
        boolean isCorrect = dirName.length() > 0 && dirName.endsWith(postfix);
        for (int i = 0; i < forbiddenSymbols.length() && isCorrect; i++){
            isCorrect = dirName.indexOf(forbiddenSymbols.charAt(i)) == -1;
        }
        return isCorrect;
    }
}
