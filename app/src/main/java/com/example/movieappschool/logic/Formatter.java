package com.example.movieappschool.logic;

public class Formatter {
    public static String dateToShortTextDate(String date) {
        String splitDate[] = date.split("-");
        String result;

        if (splitDate[2].startsWith("0")) {
            result = splitDate[2].replace("0", "");
        } else {
            result = splitDate[2];
        }

        //2021-04-10

        switch (splitDate[1]) {
            case "01":
                result += " Jan";
                break;
            case "02":
                result += " Feb";
                break;
            case "03":
                result += " Mar";
                break;
            case "04":
                result += " Apr";
                break;
            case "05":
                result += " Mei";
                break;
            case "06":
                result += " Jun";
                break;
            case "07":
                result += " Jul";
                break;
            case "08":
                result += " Aug";
                break;
            case "09":
                result += " Sep";
                break;
            case "10":
                result += " Oct";
                break;
            case "11":
                result += " Nov";
                break;
            case "12":
                result += " Dec";
                break;
            default:
                break;
        }

        return result;
    }
}
