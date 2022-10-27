package ru.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.math.BigDecimal;

public class App
{
    public static void main(String[] args ) throws Exception {

        String filePathEu = "C:\\Users\\ben\\IdeaProjects\\rateTry\\src\\main\\java\\ru\\example\\resources\\Euro.csv";
        String filePathDL = "C:\\Users\\ben\\IdeaProjects\\rateTry\\src\\main\\java\\ru\\example\\resources\\Dollar.csv";
        String filePathLI = "C:\\Users\\ben\\IdeaProjects\\rateTry\\src\\main\\java\\ru\\example\\resources\\Lira.csv";

        System.out.println("Euro press - 1");
        System.out.println("Dollar press - 2");
        System.out.println("Lira press - 3");
        System.out.print("Input :");

        Scanner in = new Scanner(System.in);
        String val = in.nextLine().trim();

        switch (val) {
            case "1" -> {

                writeArr(filePathEu);
            }
            case "2" -> {
                System.out.print("Rate try tomorrow - ");
                writeArr(filePathDL);
            }
            case "3" -> {
                System.out.print("Rate try tomorrow - ");
                writeArr(filePathLI);
            }
            default -> System.out.println("Wrong input, try again");
        }
        in.close();


    }
;
    public static BigDecimal[] writeArr(String filePath) throws Exception {
        ArrayList<String[]> list = new ArrayList<>();
        BigDecimal[] predictArr = new BigDecimal[7];

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            reader.readLine();
            BigDecimal resultPredict = BigDecimal.valueOf(0.0);


            for (int i = 0; i < 7; i++) {
                list.add(reader.readLine().split(";"));
                String listString = list.get(i)[2].replace(",", ".");
                float f = Float.parseFloat(listString);
                resultPredict = resultPredict.add(BigDecimal.valueOf(f));
                predictArr[i] = BigDecimal.valueOf(f).setScale(2, RoundingMode.DOWN);
            }
            Predict(predictArr);
            return predictArr;
        }
    }
    public static void Predict(BigDecimal[] predictArr) throws Exception {
        BigDecimal RUW = new BigDecimal(0);
        BigDecimal RTT = new BigDecimal(0);
        LocalDate date = LocalDate.now();

        System.out.print("Rate try tomorrow - "  );
        for (int i = 0; i < 7; i++ ) { RTT = RTT.add(predictArr[i]); }
        Days(date);

        System.out.println(RTT.divide(BigDecimal.valueOf(7), RoundingMode.DOWN));
        System.out.println("Rate USD week");
        for(int j = 0; j < 7; j++) {
            for (int k = 0; k < 7; k++ ) {
                RUW = RUW.add(predictArr[k]) ;
            }
            predictArr[j] = RUW.divide(BigDecimal.valueOf(7), RoundingMode.DOWN);
            RUW = BigDecimal.valueOf(0);
            System.out.print(predictArr[j]);

            Days(date);
            Dates(date);
            date = date.plusDays(1);
        }


    }
//.setScale(2, RoundingMode.DOWN);S
    public static void Dates(LocalDate date) throws Exception {
        String formattedDate = date.format(DateTimeFormatter
                .ofLocalizedDate(FormatStyle.SHORT));
        System.out.println( formattedDate);
    }

    public static void Days(LocalDate date) throws Exception {
        DateTimeFormatter formatter = (DateTimeFormatter
                .ofPattern("eee"));
        System.out.print(" " + date.format(formatter) + " " );
    }
}



