package com.example.demoa4;

import com.example.demoa4.domain.Comanda;
import com.example.demoa4.domain.ComandaFactory;
import com.example.demoa4.domain.Tort;
import com.example.demoa4.domain.TortFactory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.example.demoa4.repository.*;
import com.example.demoa4.service.ServiceComanda;
import com.example.demoa4.service.ServiceTort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

//import javax.swing.text.TableView;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.example.demoa4.util.Settings;

public class HelloApplication extends Application {

    public static MemoryRepository<Tort> getRepository() throws SQLException, RepositoryException, ParseException {

        Settings settings = Settings.getInstance("src/main/settings.properties");

        if ("file".equals(settings.getRepoType())) {
            return new FileRepository<Tort>(settings.getFileTort(), new TortFactory());
        } else if ("bin".equals(settings.getRepoType())) {
            return new BinaryFileRepository<Tort>(settings.getFileTort());
        } else if ("memory".equals(settings.getRepoType())) {
            return new MemoryRepository<Tort>();
        } else if ("sql".equals(settings.getRepoType())) {
            return new SQLTortRepository();
        }
        throw new IllegalArgumentException("Fisierul de setari e gresit.");
    }

    public static MemoryRepository<Comanda> getRepositoryC() throws RepositoryException, ParseException, SQLException {

        Settings settings = Settings.getInstance("src/main/settings.properties");

        if ("file".equals(settings.getRepoType())) {
            return new FileRepository<Comanda>(settings.getFileComanda(), new ComandaFactory());
        } else if ("bin".equals(settings.getRepoType())) {
            return new BinaryFileRepository<Comanda>(settings.getFileComanda());
        } else if ("memory".equals(settings.getRepoType())) {
            return new MemoryRepository<Comanda>();
        } else if ("sql".equals(settings.getRepoType())) {
            return new SQLComandaRepository();
        }
        throw new IllegalArgumentException("Fisierul de setari e gresit.");
    }

    @Override
    public void start(Stage stage) throws IOException, SQLException, RepositoryException, ParseException {

        MemoryRepository<Tort> repoTort = getRepository();
        ServiceTort serviceTort = new ServiceTort(repoTort);
        MemoryRepository<Comanda> repoComanda = getRepositoryC();
        ServiceComanda serviceComanda = new ServiceComanda(repoComanda);

        ListView<Tort> tortListView = new ListView<>();
        ObservableList<Tort> tortObservableList = FXCollections.observableArrayList(serviceTort.getAll());
        tortListView.setItems(tortObservableList);

        ListView<Comanda> comandaListView = new ListView<>();
        ObservableList<Comanda> comandaObservableList = FXCollections.observableArrayList(serviceComanda.getAll());
        comandaListView.setItems(comandaObservableList);

        Button addButton = new Button("Add tort");
        Button deleteButton = new Button("Delete tort");
        Button updateButton = new Button("Update tort");

        Button addComandaButton = new Button("Add comanda");
        Button deleteComandaButton = new Button("Delete comanda");
        Button updateComandaButton = new Button("Update comanda");

        Button generateEntitiesButton = new Button("Generate entities");

        Button raportZilnicButton = new Button("Raport zilniz");
        Button raportLunarButton = new Button("Raport lunar");
        Button torturiPopulareButton = new Button("Torturi populare");

        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(addButton);
        buttonBox.getChildren().add(deleteButton);
        buttonBox.getChildren().add(updateButton);
        buttonBox.setSpacing(10);

        HBox buttonBox2 = new HBox();
        buttonBox2.getChildren().add(addComandaButton);
        buttonBox2.getChildren().add(deleteComandaButton);
        buttonBox2.getChildren().add(updateComandaButton);
        buttonBox2.setSpacing(10);

        Label idLabel = new Label("Id");
        Label tipLabel = new Label("Tip");

        TextField idTextField = new TextField();
        TextField tipTextField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.add(idLabel, 0, 0);
        gridPane.add(idTextField, 1, 0);
        gridPane.add(tipLabel, 0, 1);
        gridPane.add(tipTextField, 1, 1);

        Label comandaidlabel = new Label("Comanda id");
        Label comandadatalabel = new Label("Data");
        Label comandatortlabel = new Label("Torturi");

        TextField comandaidTextField = new TextField();
        TextField comandadateTextField = new TextField();
        TextField comandatortTextField = new TextField();

        GridPane gridPane2 = new GridPane();
        gridPane2.add(comandaidlabel, 0, 0);
        gridPane2.add(comandaidTextField, 1, 0);
        gridPane2.add(comandadatalabel, 0, 1);
        gridPane2.add(comandadateTextField, 1, 1);
        gridPane2.add(comandatortlabel, 0, 2);
        gridPane2.add(comandatortTextField, 1, 2);

        TableView<Tort> tortTableView = new TableView<>();
        TableColumn<Tort, Integer> idColumn = new TableColumn<>("Id");
        TableColumn<Tort, String> tipColumn = new TableColumn<>("Tip");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tipColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));
        tortTableView.getColumns().add(idColumn);
        tortTableView.getColumns().add(tipColumn);
        tortTableView.setItems(tortObservableList);

        TableView<Comanda> comandaTableView = new TableView<>();
        TableColumn<Comanda, Integer> comandaIdColumn = new TableColumn<>("Id");
        TableColumn<Comanda, Date> comandaDataColumn = new TableColumn<>("Data");
        TableColumn<Comanda, String> comandaTortColumn = new TableColumn<>("Torturi");
        comandaIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        comandaDataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        comandaTortColumn.setCellValueFactory(new PropertyValueFactory<>("tort"));
        comandaTableView.getColumns().add(comandaIdColumn);
        comandaTableView.getColumns().add(comandaDataColumn);
        comandaTableView.getColumns().add(comandaTortColumn);
        comandaTableView.setItems(comandaObservableList);

        HBox mainBox = new HBox();
        VBox rightBox = new VBox();
        rightBox.getChildren().add(buttonBox);
        rightBox.getChildren().add(gridPane);
        rightBox.setSpacing(20);
        rightBox.getChildren().add(buttonBox2);
        rightBox.getChildren().add(gridPane2);
        rightBox.getChildren().add(generateEntitiesButton);
        rightBox.getChildren().add(raportZilnicButton);
        rightBox.getChildren().add(raportLunarButton);
        rightBox.getChildren().add(torturiPopulareButton);


        mainBox.getChildren().add(tortListView);
        mainBox.getChildren().add(rightBox);
        mainBox.getChildren().add(comandaListView);
        mainBox.setSpacing(10);

        torturiPopulareButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Collection<Comanda> allCom = serviceComanda.getAll();
                    serviceComanda.raportTorturiPopulare(allCom);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        raportLunarButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Collection<Comanda> allCom = serviceComanda.getAll();
                    serviceComanda.raportLunar(allCom);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        raportZilnicButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Collection<Comanda> allCom = serviceComanda.getAll();
                    serviceComanda.raportZilnic(allCom);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        generateEntitiesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Settings settings = Settings.getInstance("src/main/settings.properties");
                if ("sql".equals(settings.getRepoType())) {
                    Random rand = new Random();
                    int id,id2;
                    List<String> tipuri = new ArrayList<>();
                    tipuri.add("copt");
                    tipuri.add("diplomat");
                    tipuri.add("delice");
                    tipuri.add("tropical");
                    tipuri.add("pere");
                    tipuri.add("fructe");
                    tipuri.add("iaurt");
                    for (int i = 1; i <= 100; i++) {
                        int randomIndex = rand.nextInt(tipuri.size());
                        String randomTip = tipuri.get(randomIndex);
                        try {
                            serviceTort.add(i, randomTip);
                            tortObservableList.setAll(serviceTort.getAll());
                        } catch (Exception e) {
                            System.out.println("Nu s-a putut efectua adaugarea");
                        }
                    }

                    Date d1 = Date.valueOf("2020-01-01");
                    Date d2 = Date.valueOf("2025-01-01");

                    for (int i = 1; i <= 100; i++) {
                        Date randomDate = new Date(ThreadLocalRandom.current()
                                .nextLong(d1.getTime(), d2.getTime()));
                        int max = 4, min = 1;
                        int nr_torturi = rand.nextInt(min, max);
                        List<Tort> lista = new ArrayList<>();
                        for (int j = 1; j <= nr_torturi; j++) {
                            try {
                                int randomTort = rand.nextInt(1, 101);
                                lista.add(serviceTort.print(randomTort));
                            } catch (SQLException e) {
                                System.out.println("Nu s-a putut adauga tortul in comanda");
                            }
                        }
                        try {
                            serviceComanda.add(i, randomDate, lista);
                            comandaObservableList.setAll(serviceComanda.getAll());
                        } catch (RepositoryException | IOException | SQLException e) {
                            System.out.println("Nu s-a putut adauga comanda");
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText("Nu este folosita o baza de date");
                    alert.show();
                }
            }
        });

        addButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(idTextField.getText());
                    String tip = tipTextField.getText();
                    serviceTort.add(id, tip);
                    tortObservableList.setAll(serviceTort.getAll());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int id = Integer.parseInt(idTextField.getText());
                try {
                    serviceTort.remove(id);
                    tortObservableList.setAll(serviceTort.getAll());
                } catch (RepositoryException | IOException | SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });

        updateButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int id = Integer.parseInt(idTextField.getText());
                try {
                    String tip = tipTextField.getText();
                    serviceTort.update(id, tip);
                    tortObservableList.setAll(serviceTort.getAll());
                } catch (RepositoryException | IOException | SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });

        tortListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Tort selectedTort = tortListView.getSelectionModel().getSelectedItem();
                if (selectedTort != null) {
                    idTextField.setText(String.valueOf(selectedTort.getId()));
                    tipTextField.setText(selectedTort.getTip());
                } else {
                    System.out.println("");
                }
            }
        });

        addComandaButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    int id = Integer.parseInt(comandaidTextField.getText());
                    Date data = Date.valueOf(comandadateTextField.getText());
                    String[] split = comandatortTextField.getText().split(",");
                    List<Tort> listatort = new ArrayList<>();
                    for (int i = 0; i < split.length; i++) {
                        int j = Integer.parseInt(split[i]);
                        listatort.add(serviceTort.print(j));
                    }
                    serviceComanda.add(id, data, listatort);
                    comandaObservableList.setAll(serviceComanda.getAll());
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });

        deleteComandaButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int id = Integer.parseInt(comandaidTextField.getText());
                try {
                    serviceComanda.remove(id);
                    comandaObservableList.setAll(serviceComanda.getAll());
                } catch (RepositoryException | IOException | SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });

        updateComandaButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int id = Integer.parseInt(comandaidTextField.getText());
                try {
                    Date data = Date.valueOf(comandadateTextField.getText());
                    String[] split = comandatortTextField.getText().split(",");
                    List<Tort> listatort = new ArrayList<>();
                    for (int i = 0; i < split.length; i++) {
                        int j = Integer.parseInt(split[i]);
                        listatort.add(serviceTort.print(j));
                    }
                    serviceComanda.update(id, data, listatort);
                    comandaObservableList.setAll(serviceComanda.getAll());
                } catch (RepositoryException | IOException | SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Eroare");
                    alert.setContentText(e.getMessage());
                    alert.show();
                }
            }
        });

        comandaListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Comanda selectedComanda = comandaListView.getSelectionModel().getSelectedItem();
                if (selectedComanda != null) {
                    comandaidTextField.setText(String.valueOf(selectedComanda.getId()));
                    comandadateTextField.setText(String.valueOf(selectedComanda.getData()));
                    comandatortTextField.setText(selectedComanda.getTort().toString());
                } else {
                    System.out.println("You're clicking on the list, but no player is selected.");
                }
            }
        });

        Scene scene = new Scene(mainBox, 1000, 600);
        stage.setTitle("Cake app");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();

//        try{
//            MemoryRepository<Tort> com.example.demoa4.repository = getRepository();
//            ServiceTort serviceTort = new ServiceTort(com.example.demoa4.repository);
//            MemoryRepository<Comanda> comandaRepository = getRepositoryC();
//            ServiceComanda serviceComanda = new ServiceComanda(comandaRepository);
//            UserInterface com.example.demoa4.ui = new UserInterface(serviceTort, serviceComanda);
//            com.example.demoa4.ui.run();
//        } catch (RepositoryException | SQLException e) {
//            System.out.println(e.getMessage());
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
    }
}