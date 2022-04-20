package com.example.automarket;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller extends DatabaseHandler {

    @FXML
    private ResourceBundle resources;

    @FXML
    private Button buttonDelete;


    @FXML
    private URL location;

    @FXML
    private TableView<ModelTable> autoTableView;

    @FXML
    private Button buttonRefactor;

    @FXML
    private AnchorPane buttonUpdate;

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

    //@FXML
    //private TableColumn<?, ?> colTrailer;

    @FXML
    private TableColumn<ModelTable, String> colType;

    @FXML
    private TextField inputCategory;

    @FXML
    private TextField inputModel;

    @FXML
    private TextField inputNumber;

    @FXML
    private TextField inputProduction;

    @FXML
    private TextField inputStamp;

    @FXML
    private TextField txt_search;

    @FXML
    private AnchorPane next;

    @FXML
    private Button butUpdate;

    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    ObservableList<ModelTable> oblist = FXCollections.observableArrayList();

    //Заполнение таблицы
    private void loadDataFromDatabase(){
        oblist.clear();
        DatabaseHandler table = new DatabaseHandler();

        Connection conn;
        try (Connection connection = conn = table.getDbConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT * from auto");
            while (rs.next()) {
                oblist.add(new ModelTable(rs.getString("stamp"), rs.getString("category"),
                        rs.getString("model"), rs.getString("number"), rs.getString("production"), rs.getString("type")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        autoTableView.setItems(oblist);
    }

// Вывод из таблицы в поля для редактирования
    private void setCellValueFromTable() {
        autoTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ModelTable pl = autoTableView.getItems().get(autoTableView.getSelectionModel().getSelectedIndex());
                inputStamp.setText(pl.getStamp());
                inputCategory.setText(pl.getCategory());
                inputModel.setText(pl.getModel());
                inputNumber.setText(pl.getNumber());
                inputProduction.setText(pl.getProduction());

            }
        });
    }

    //Изменение значений в таблице
    @FXML
    private void handleUpdateProduct (ActionEvent event) {

        String sql = "UPDATE auto set  stamp = ?, model = ?, category = ?, production = ? where number = ?";


        try  {
            DatabaseHandler connector = new DatabaseHandler();
            String stamp = inputStamp.getText();
            String model = inputModel.getText();
            String category = inputCategory.getText();
            String number = inputNumber.getText();
            String production = inputProduction.getText();

            pst = connector.getDbConnection().prepareStatement(sql);

            pst.setString(1, stamp);
            pst.setString(2, model);
            pst.setString(3, category);
            pst.setString(4, production);
            pst.setString(5, number);

            int i = pst.executeUpdate();
            if (i==1) {
                loadDataFromDatabase();
                clearTextField();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void clearTextField(){
        inputModel.clear();
        inputStamp.clear();
        inputNumber.clear();
        inputProduction.clear();
        inputCategory.clear();
    }

    @FXML
    //Удаление значений из таблицы
    private void handleDeleteProduct(ActionEvent event){
        String sql = "DELETE from auto where number = ?";
        DatabaseHandler connector = new DatabaseHandler();
        try {
            pst = connector.getDbConnection().prepareStatement(sql);
            pst.setString(1, inputNumber.getText());
            int i = pst.executeUpdate();
            if (i==1) {
                loadDataFromDatabase();
                clearTextField();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Поиск ТС
    private  void searchProduct() {

        txt_search.setOnKeyReleased(e-> {
            if (txt_search.getText().equals("")) {
                loadDataFromDatabase();
            }
            else{
                oblist.clear();
                String sql = "SELECT * from auto where stamp LIKE '%"+txt_search.getText()+"%'"
                        + " UNION SELECT * from auto where category LIKE '%"+txt_search.getText()+"%'"
                        + " UNION SELECT * from auto where model LIKE '%"+txt_search.getText()+"%'"
                        + " UNION SELECT * from auto where number LIKE '%"+txt_search.getText()+"%'"
                        + " UNION SELECT * from auto where production LIKE '%"+txt_search.getText()+"%'"
                        + " UNION SELECT * from auto where type LIKE '%"+txt_search.getText()+"%'";
                DatabaseHandler connector = new DatabaseHandler();
                try {
                    pst = connector.getDbConnection().prepareStatement(sql);
                    rs = pst.executeQuery();
                    while (rs.next()) {
                        oblist.add(new ModelTable(rs.getString("stamp"), rs.getString("category"),
                                rs.getString("model"), rs.getString("number"), rs.getString("production"), rs.getString("type")));
                    }
                    autoTableView.setItems(oblist);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }

            }

        });
    }

    public void buttonRelease() {

        buttonRefactor.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("refactor.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException  {
        searchProduct();
        setCellValueFromTable();
        loadDataFromDatabase();
        buttonRelease();

        //Заполнение таблицы
        colStamp.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Stamp"));
        colModel.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Model"));
        colCategory.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Category"));
        colNumber.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Number"));
        colProduction.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Production"));
        colType.setCellValueFactory(new PropertyValueFactory<ModelTable, String>("Type"));


            }



    public void getSelected(MouseEvent mouseEvent) {
    }


}


