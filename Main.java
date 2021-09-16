package com.company;

import java.util.Arrays;

public class Main {

    private static void printHelp(){
        new LsCommand().help();
        System.out.println();
        System.out.println();
        new MkdirCommand().help();
        System.out.println();
        System.out.println();
        new EchoCommand().help();
        System.out.println();
        System.out.println();
        new CatCommand().help();
        System.out.println();
        System.out.println();
    }

    //@param args[0] директория для работы
    //@param args[1] команда (опционально)
    public static void main(String[] args) {
        if (args.length <= 1){
            printHelp();
            return;
        }

        POSIXCommand command = null; // класс выполняемой команды

        switch (args[1]) {
            case "ls" -> command = new LsCommand();
            case "mkdir" -> command = new MkdirCommand();
            case "echo" -> command = new EchoCommand();
            case "cat" -> command = new CatCommand();
            default -> System.err.println("Ошибка: неизвестная команда " + args[1]);
        }

        if (command != null){
            String[] allParams = Arrays.copyOfRange(args, 2, args.length);

            char[] options = command.getOptions(allParams);
            String[] notOptions = command.getNotOptions(allParams);

            command.run(args[0].trim(), options, notOptions);
        }
    }
}
