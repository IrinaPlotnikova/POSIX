package com.company;

import java.util.Arrays;

public class Main {

    private static void printHelp(){
    }

    //@param args[0] директория для работы
    public static void main(String[] args) {
        if (args.length == 0){
            return;
        }
        args[0] = args[0].trim();
        String[] allParams = Arrays.copyOfRange(args, 2, args.length);
        POSIXCommand command = null;
        if (args.length <= 1){
            printHelp();
        }
        else{
            System.err.println("Ошибка: неизвестная команда " + args[1]);
        }
        if (command != null){
            char[] options = command.getOptions(allParams);
            String[] notOptions = command.getNotOptions(allParams);

            command.run(args[0], options, notOptions);
        }
    }
}
