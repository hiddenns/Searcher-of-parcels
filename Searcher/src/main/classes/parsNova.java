package main.classes;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class parsNova {

    private String URL1 = "https://novaposhta.ua/tracking/?cargo_number= ";
    private String URL2 = "&newtracking=1";

    public String newRightTTN = "";

    String statusMain = null;
    String route = null;
    String data = null;

    public void setStatusMain(String statusMain) {
        this.statusMain = statusMain;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    String adress= null;

    public void parsing(String getTTN){

        Document doc = null;
        statusMain = null; // Для проверки на правильность ТТН. Каждый раз нажимая на кнопку и вызывая метод parsing()

        try {
            newRightTTN = TtnEnterWithSpace(getTTN);
            doc = Jsoup.connect(URL1 + newRightTTN + URL2).get(); // Соединение с страничкой
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements cols3 = doc.select("td[colspan=3]"); // Парсинг html col = 3
        Elements cols2 = doc.select("td[colspan=2]"); // Парсинг html col = 2 для адреса доставки

        String status_des = doc.select(".status").text(); //
        String statusShort = refact(status_des); // Делаем короче статус посылки ( Вырезаем слово до пробела )


        SettingNames(cols3, cols2 ,statusShort);

        if (statusMain == null) {
            setStatusMain("Неправильный номер ТТН");
            setAdress("Недоступно");
            setRoute("Недоступно");
            setData("Недоступно");
        }

    }

    public String refact(String str){
        String arr[] = str.split("");
        ArrayList<String> answer = new ArrayList<String>();
        Boolean pass = true;
        int i = 0;

        while (pass){
            if(i == arr.length || arr[i].equals(" ")){
                pass = false;
            } else
                answer.add(arr[i]);

            i++;
        }

        StringBuffer stringBuffer = new StringBuffer();

        for (String s: answer) {
            stringBuffer.append(s);
        }

        String answerString = stringBuffer.toString(); // Соединение массива строк в строку

        return answerString;
    }

    public void SettingNames(Elements cols3, Elements cols2, String statusShort) {

        int count = 0;

        for (Element e : cols3) { // Назначение переменным данные для cols = 3

            switch (statusShort) {
                case "Відмова":
                    switch (count) {
                        case 0:
                            setStatusMain(e.child(1).text());
                            count++;
                            break;
                        case 1:
                            setRoute(e.child(1).text());
                            count++;
                            break;
                        case 2:
                            setData(e.child(1).text());
                            count++;
                            break;

                    }
                    break;

                case "Отримано":
                    switch (count) {
                        case 0:
                            setStatusMain(e.child(1).text());
                            count++;
                            break;

                        case 1:
                            setRoute(e.child(1).text());
                            count++;
                            break;

                        case 2:
                            setAdress(e.child(1).text());
                            count++;
                            break;
                        case 3:
                            setData(e.child(1).text());
                            count++;
                            break;
                    }
                    break;

                case "В": // В дорозі
                    switch (count) {
                        case 0:
                            setStatusMain(e.child(1).text());
                            count++;
                            break;

                        case 1:
                            count++;
                            break;

                        case 2:
                            setRoute(e.child(1).text());
                            count++;
                            break;
                    }
                    break;

                case "Чекаємо":
                    switch (count) {
                        case 0:
                            setStatusMain(e.child(1).text());
                            count++;
                            break;
                        case 1:
                            count++;
                            break;
                        case 2:
                            setRoute(e.child(1).text());
                            count++;
                            break;
                    }
                    break;

                case "У": // У відділенні
                    switch (count) {
                        case 0:
                            setStatusMain(e.child(1).text());
                            count++;
                            break;
                        case 1:
                            setRoute(e.child(1).text());
                            count++;
                            break;
                        case 2:
                            setData(e.child(1).text());
                            count++;
                            break;
                    }
                    break;

                case "Видалено":
                    switch (count) {
                        case 0:
                            setStatusMain(e.child(1).text());
                            count++;
                            break;
                    }
            }
        }

        count = 0;

        for (Element e : cols2) { // Адреса доставки для cols = 2
            switch (statusShort) {
                case "В": // В дорозі
                    switch (count) {
                        case 0:
                            setAdress(e.child(1).text());
                            count++;
                            break;
                    }
                    break;
                case "У": // У відділенні
                    switch (count) {
                        case 0:
                            setAdress(e.child(1).text());
                            count++;
                            break;
                    }
                    break;
            }
        }
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
