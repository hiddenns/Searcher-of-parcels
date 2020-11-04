package main.classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class parsUkr {

    private String URL1 = "https://posylka.net/uk/parcel/";

    public String resultStatus;
    public String resultSender;
    public String resultDateNow;

    public String resultRouteNow;
    public String resultDayInTheRoad;

    public String newRightTTN = "";

    static Document doc = null;


    public void parseUkr(String getTTN){ // Сам парсинг

        newRightTTN = TtnEnterWithSpace(getTTN);

        connectToWeb(newRightTTN);

        getStatusOfTrack();
        parseOtherData();
    }

    private void connectToWeb(String getTTN) {  // Соединение с страничкой
        try {
            doc = Jsoup.connect(URL1 + getTTN).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getStatusOfTrack(){ //получение статуса посылки
        resultStatus = doc.select(".package-status-header").text();

    }

    private void parseOtherData() { // парсим остальные елементы
        try { // Обработка ошибки в случае неправильного ввода ттн
            Elements importantElems = doc.select(".package-route-box-content");
            resultDateNow = importantElems.get(1).text();
            resultRouteNow = importantElems.get(2).text();
            resultSender = importantElems.last().child(1).text();
            resultDayInTheRoad = doc.select(".package-info-delivery-days-value").text();
        } catch (Exception e){
            exceptionInTTN();
        }
    }

    private void exceptionInTTN(){ // Неправильный ввод ттн
        resultStatus = "Неправильный номер ТТН!";
        resultDateNow = "Недоступно";
        resultDayInTheRoad = "Недоступно";
        resultSender = "Недоступно";
        resultRouteNow = "Недоступно";
    }

    private String TtnEnterWithSpace(String ttn){ // Если пользователь ввел номер ттн с пробелом -> мы проверяем и перезаписываем строку
        String[] wrongArr = ttn.split("");
        ArrayList rightArr = new ArrayList();

        for (int i = 0; i < wrongArr.length; i++) {
            if(!wrongArr[i].equals(" ")){
                rightArr.add(wrongArr[i]);
            }
        }

        String joinStr = String.join("",rightArr);

        return joinStr;
    }

}

