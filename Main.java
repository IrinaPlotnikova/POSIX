package com.company;

import java.util.Arrays;

public class Main {

    private static void printHelp(){
        new LsCommand().help();
        System.out.println();
        System.out.println();
    }

    //@param args[0] директория для работы
    public static void main(String[] args) {
        if (args.length <= 1){
            printHelp();
            return;
        }

        POSIXCommand command = null; // класс выполняемой команды

        if (args[1].equals("ls")){
            command = new LsCommand();
        }
        else{
            System.err.println("Ошибка: неизвестная команда " + args[1]);
        }
        if (command != null){
            String[] allParams = Arrays.copyOfRange(args, 2, args.length);

            char[] options = command.getOptions(allParams);
            String[] notOptions = command.getNotOptions(allParams);

            command.run(args[0].trim(), options, notOptions);
        }
    }
}
