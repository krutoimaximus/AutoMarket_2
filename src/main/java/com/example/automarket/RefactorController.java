package com.example.automarket;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
//Пример наследования
public class RefactorController  extends Controller {


    @FXML
    private TableView<ModelTable> autoTableView;

    @FXML
    private Button buttonExitref;

    @FXML
    private Button buttonSaveref;

    @FXML
    private TableColumn<ModelTable, String> colCategory;

    @FXML
    private TableColumn<ModelTable, String> colModel;

    @FXML
    private TableColumn<ModelTable, String> colNumber;

    @FXML
    private TableColumn<ModelTable, String> colProduction;

    @FXML
    private TableColumn<ModelTable, String> colStamp;

    //  @FXML
   // private TableColumn<?, ?> colTrailer;

    @FXML
    private TableColumn<ModelTable, String> colType;

    @FXML
    private ComboBox<String> comboboxTyperef;

    @FXML
    private TextField inputCategoryref;

    @FXML
    private TextField inputModelref;

    @FXML
    private TextField inputNumberref;

    @FXML
    private TextField inputProductionref;

    @FXML
    private TextField inputProductionref1;

    @FXML
    private TextField inputProductionref3;

    @FXML
    private TextField inputStampref;

    @FXML
    private AnchorPane next;

    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    public RefactorController() throws SQLException {
    }

    //Полиморфизм
    @Override
    public void buttonRelease() {
        //Кнопка сохранения
        buttonSaveref.setOnAction(actionEvent -> {
            String Category = inputCategoryref.getText().trim();
            String Model = inputModelref.getText().trim();
            String Production = inputProductionref.getText().trim();
            String Stamp = inputStampref.getText().trim();

            if (!Category.equals("") && !Model.equals("") && !Production.equals("") && !Stamp.equals(""))
                fillDatabase(Category, Model, Production, Stamp);
            else
                System.out.println("Заполните все поля!");

        });


    }


    @FXML
    void initialize() throws SQLException, ClassNotFoundException {

        DatabaseHandler table = new DatabaseHandler();

        Connection conn;
        conn = table.getDbConnection();

        try (ResultSet resultSet = conn.createStatement().executeQuery("SELECT * from auto")) {

            while (resultSet.next()) {
                oblist.add(new ModelTable(resultSet.getString("stamp"), resultSet.getString("category"),
                        resultSet.getString("model"), resultSet.getString("number"), resultSet.getString("production"), resultSet.getString("type")));

            }
        }

        autoTableView.setItems(oblist);

        //Заполнение таблицы
        colStamp.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Stamp"));
        colModel.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Model"));
        colCategory.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Category"));
        colNumber.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Number"));
        colProduction.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Production"));
        colType.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Type"));

        //Кнопка сохранения

        buttonRelease();

        //Заполнение ComboBox
        comboboxTyperef.getItems().add("Легковой автомобиль");
        comboboxTyperef.getItems().add("Грузовой автомобиль");

        comboboxTyperef.setOnAction(actionEvent -> {
            Object selectedItem = comboboxTyperef.getSelectionModel().getSelectedItem();
        });


        DatabaseHandler dbHandler = new DatabaseHandler();

        //Кнопка сохранения

        buttonSaveref.setOnAction(actionEvent -> {

            try {
                dbHandler.signUpAuto(inputStampref.getText(), inputModelref.getText(), inputCategoryref.getText(), inputNumberref.getText(), comboboxTyperef.getValue(), inputProductionref.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            buttonSaveref.getScene().getWindow().hide();

        });

        buttonExitref.setOnAction(actionEvent -> {
            buttonExitref.getScene().getWindow().hide();
        });
    }


    private void fillDatabase(String category, String model, String production, String stamp) {
    }

}
