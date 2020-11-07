package main.classes;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField EnterTTN;

    @FXML
    private Label labelStat;

    @FXML
    private Label labelRout;

    @FXML
    private Label labelData;

    @FXML
    private Label labelAdress;

    @FXML
    private ChoiceBox<String> choiceDelivery;
    ObservableList obsList = FXCollections.observableArrayList();

    @FXML
    private Button ButtonMain;

    @FXML
    private Label LabelTrack;

    @FXML
    private Label resultStat;

    @FXML
    private Label resultAdress;

    @FXML
    private Label resultData;

    @FXML
    private Label resultRout;

    @FXML
    private Label labelStatUkr;

    @FXML
    private Label labelSenderUkr;

    @FXML
    private Label labelDataNowUkr;

    @FXML
    private Label labelRouteNowUkr;

    @FXML
    private Label labelDayInTheRoad;

    @FXML
    private Label resultStatusUkr;

    @FXML
    private Label resultSenderUkr;

    @FXML
    private Label resultDataNowUkr;

    @FXML
    private Label resultRouteNowUkr;

    @FXML
    private Label resultDayInTHeRoadUkr;

    @FXML
    public ProgressBar progressBarLoad;

    @FXML
    public Label TimeNow;

    @FXML
    public Label DataNow;

    @FXML
    void initialize() {

        setDataAndTime(); // Установка даты и времени
        loadDataToCheckBox(); // Установка значений в Choice Box

        ButtonMain.setOnAction(actionEvent -> { // События на кнопку "Отследить"
            if(EnterTTN.getText().equals("")){
                EnterTTN.setText(createAlertEnterTtn());// создаем алерт, если TextField пустой (не введен номер ттн) и присваиваем данные EnterTTN
            } else {
                choiceDeliver();
            }
        });
    }

    private String createAlertEnterTtn() { // Алерт, если не введен номер ттн в Text Field "EnterTTN"
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Введите номер накладной, пожалуйста :)");
        dialog.setTitle("Не введен номер ттн");

        ButtonType btnOk = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<String> result = dialog.showAndWait();

        if(result.isPresent()){
            return result.get();
        }

        return null;
    }

    private void createAlertChoice() { // Создаем алерт, если почта не выбрана
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Выберите почту!");
        alert.setHeaderText("Выберите службу доставки!");
        alert.setContentText("Без определения почты, я не смогу\nее отследить :(");

        ButtonType btnOk = new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(btnOk);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == btnOk) {
            alert.close();
        }
    }

    private void setDataAndTime() {

        Date date = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd.MM.yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        TimeNow.setText(timeFormat.format(date));
        DataNow.setText(dateFormat.format(date));
        }

    private void loadDataToCheckBox(){ // Загрузка данных в Check box
        obsList.removeAll(obsList);
        String NovaPoch = "Новая Почта";
        String UkrPoch = "Укр.почта";

        obsList.addAll(NovaPoch,UkrPoch);
        choiceDelivery.getItems().addAll(obsList);
    }

    private void choiceDeliver(){ // Выбор почты Choice Box
        String choise = choiceDelivery.getValue();

        if (choise == "Новая Почта"){
            loadNovaPoshta();
        } else if(choise == "Укр.почта"){
            loadUkrPoshta();
        } else {
            createAlertChoice(); // Создаем алерт, если не выбрана служба доставки
        }

    }
    private void loadNovaPoshta(){ // Парсинг Новой почты и установка значений данных
        setVisibleToLabelUkr0();
        parsNova NovaP = new parsNova();

        NovaP.parsing(EnterTTN.getText());

        EnterTTN.setText(NovaP.newRightTTN);
        setVisibleToLabelNova1();

        resultStat.setText(NovaP.statusMain);
        resultAdress.setText(NovaP.adress);
        resultRout.setText(NovaP.route);
        resultData.setText(NovaP.data);
    }

    private void loadUkrPoshta() { // Парсинг укр почты и установка значений данных
        setVisibleToLabelNova0();
        parsUkr UkrP = new parsUkr();
        UkrP.parseUkr(EnterTTN.getText());

        EnterTTN.setText(UkrP.newRightTTN);

        setVisibleToLabelUkr1();

        resultStatusUkr.setText(UkrP.resultStatus);
        resultSenderUkr.setText(UkrP.resultSender);
        resultDataNowUkr.setText(UkrP.resultDateNow);
        resultRouteNowUkr.setText(UkrP.resultRouteNow);
        resultDayInTHeRoadUkr.setText(UkrP.resultDayInTheRoad);
    }

    private void setVisibleToLabelUkr1() { // Устанавливаем прозрачность данных 100%
        labelStatUkr.setOpacity(1);
        labelRouteNowUkr.setOpacity(1);
        labelDataNowUkr.setOpacity(1);
        labelSenderUkr.setOpacity(1);
        labelDayInTheRoad.setOpacity(1);

        resultStatusUkr.setOpacity(1);
        resultSenderUkr.setOpacity(1);
        resultDataNowUkr.setOpacity(1);
        resultRouteNowUkr.setOpacity(1);
        resultDayInTHeRoadUkr.setOpacity(1);
    }
    private void setVisibleToLabelUkr0() { // Устанавливаем прозрачн-чость данных 0%
        labelStatUkr.setOpacity(0);
        labelRouteNowUkr.setOpacity(0);
        labelDataNowUkr.setOpacity(0);
        labelSenderUkr.setOpacity(0);
        labelDayInTheRoad.setOpacity(0);

        resultStatusUkr.setOpacity(0);
        resultSenderUkr.setOpacity(0);
        resultDataNowUkr.setOpacity(0);
        resultRouteNowUkr.setOpacity(0);
        resultDayInTHeRoadUkr.setOpacity(0);
    }

    private void setVisibleToLabelNova0() { // Устанавливаем прозрачность данных 0%
        labelAdress.setOpacity(0);
        labelData.setOpacity(0);
        labelRout.setOpacity(0);
        labelStat.setOpacity(0);

        resultRout.setOpacity(0);
        resultData.setOpacity(0);
        resultStat.setOpacity(0);
        resultAdress.setOpacity(0);
    }
    private void setVisibleToLabelNova1() { // Устанавливаем прозрачность данных 100%
        labelAdress.setOpacity(1);
        labelData.setOpacity(1);
        labelRout.setOpacity(1);
        labelStat.setOpacity(1);

        resultRout.setOpacity(1);
        resultData.setOpacity(1);
        resultStat.setOpacity(1);
        resultAdress.setOpacity(1);
    }
}



