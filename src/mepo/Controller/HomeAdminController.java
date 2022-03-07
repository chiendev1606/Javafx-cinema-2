/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mepo.Controller;

import com.jfoenix.controls.JFXButton;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import mepo.Components.*;
import mepo.Helper.*;
import mepo.Implements.UserImp;
import mepo.Navigator;


public class HomeAdminController {

    User user = null;
    private UserImp UserImp = new ClientHelper();
    private mepo.Implements.FeedbackImp FeedbackImp = new FeedbackHelper();
    private mepo.Implements.OrderDetailImp OrderDetailImp = new OrderDetailHelper();
    private mepo.Implements.OrderImp OrderImp = new OrderHelper();
    private MyAlert alert = new MyAlert();

    @FXML
    private Label txtCountUser;

    @FXML
    private Label txtCountGameOrder;

    @FXML
    private Label txtCountFeedback;

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnAdmin;

    @FXML
    private JFXButton btnCustomer;

    @FXML
    private JFXButton btnCategory;

    @FXML
    private JFXButton btnProduct;

    @FXML
    private JFXButton btnOrder;

    @FXML
    private JFXButton btnFeedback;

    @FXML
    private JFXButton btnLogout;

    @FXML
    private BarChart<Number, String> barChartCinema;

    @FXML
    private DatePicker dateFrom;

    @FXML
    private JFXButton btnWatch;

    @FXML
    private Label percentageLb;

    @FXML
    private DatePicker dateTo;

    @FXML
    private PieChart pieChartCinema;
    @FXML
    private Label lBtotalSold;

    @FXML
    private Label incomePerMovie;


    public void initialize(User username) {
        this.user = username;
        formatDatepicker(dateFrom);
        formatDatepicker(dateTo);
        List<OrderDetail> result = OrderDetailImp.selectAll();
        int count = 0;
        for (OrderDetail o : result) {
            count++;
        }
        lBtotalSold.setWrapText(true);

        txtCountUser.setText(String.valueOf(UserImp.countClient()));
        txtCountFeedback.setText(String.valueOf(FeedbackImp.countFeedback()));
        txtCountGameOrder.setText(String.valueOf(count));

        createPieChart(LocalDate.now().minusMonths(1), LocalDate.now());
        createBarChart(LocalDate.now().minusMonths(1), LocalDate.now());
        dateFrom.setValue(LocalDate.now().minusMonths(1));
        dateTo.setValue(LocalDate.now());


    }

    @FXML
    void logoClicked(MouseEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(user);
    }

    @FXML
    private void btnHomeClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToHomeAdmin(user);
    }

    @FXML
    void btnActionTuCoins(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToDialogHistoryAdmin(user);
    }

    @FXML
    void btnActionCard(ActionEvent actionEvent) throws IOException {
        Navigator.getInstance().goToCardAdmin(user);
    }

    @FXML
    private void btnAdminClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToAdmin(user);
    }

    @FXML
    private void btnCustomerClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCustomer(user);
    }

    @FXML
    private void btnCategoryClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToCategory(user);
    }

    @FXML
    private void btnProductClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToProducts(user);
    }

    @FXML
    private void btnOrderClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToOrderAdmin(user);
    }

    @FXML
    private void btnFeedbackClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToFeedbackAdmin(user);
    }

    @FXML
    private void btnLogoutClick(ActionEvent event) throws IOException {
        Navigator.getInstance().goToLoginAdmin();
    }

    private void formatDatepicker(DatePicker datePicker) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });

    }


    @FXML
    void btnActionWatch(ActionEvent event) {
        LocalDate from = dateFrom.getValue();
        LocalDate to = dateTo.getValue();
        if (from == null || to == null) {
            alert.alertError("Date input cannot be blank !!!");
            return;
        } else if (to.isBefore(from) || to.isEqual(from)) {
            alert.alertError("Date input is invalid !!!");
            return;
        }
        barChartCinema.getData().clear();
        createBarChart(from, to);
        createPieChart(from, to);


    }

    private void createBarChart(LocalDate from, LocalDate to) {
        int totalSold = 0;
        double totalMoney = 0;
        XYChart.Series<Number, String> series = new XYChart.Series();
        String sql = "select  c.productname, c.price, COUNT(*) as total  from orderdetail a, `order` b, product c where a.orderID = b.orderID and a.productID = c.productID and b.orderdate between ? and ? group by c.productname";
        try (
                Connection conn = DbHelper.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, Helper.formatDate(from));
                stmt.setString(2, Helper.formatDate(to));
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String productname = rs.getString("productname");
                    int total = rs.getInt("total");
                    totalMoney += rs.getDouble("price");
                    Double totalMoneyPerProduct = total * rs.getDouble("price");
                    totalSold += total;
                    series.getData().add(new XYChart.Data<Number, String>(totalMoneyPerProduct, productname));


                }
                series.getData().sort(Comparator.comparingDouble(d -> d.getXValue().doubleValue()));
                barChartCinema.getData().add(series);
                barChartCinema.setCategoryGap(1);
                lBtotalSold.setText("TOTAL INCOME FROM " + Helper.formatDate(from) + " TO " + Helper.formatDate(to) + " = " + Double.toString(totalMoney) + " $");
                for (XYChart.Series<Number, String> serie : barChartCinema.getData()) {
                    for (XYChart.Data<Number, String> item : serie.getData()) {
                        item.getNode().setOnMousePressed((MouseEvent event) -> {
                            incomePerMovie.setWrapText(true);
                            incomePerMovie.setText("TOTAL INCOME OF " + item.getYValue() + " " + Helper.formatDate(from) + " to " + Helper.formatDate(to) + " = " + item.getXValue() + " $");
                        });
                    }
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void createPieChart(LocalDate from, LocalDate to) {
        int totalSold1 = 0;
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        String sql = "select  d.categoryname, SUM(a.orderdetailID * c.price) as total  from orderdetail a, `order` b, product c, category d where a.orderID = b.orderID and a.productID = c.productID and c.categoryID = d.categoryID and b.orderdate between ? and ? group by d.categoryname  order by total ;";
        try (
                Connection conn = DbHelper.getConnection()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, Helper.formatDate(from));
                stmt.setString(2, Helper.formatDate(to));
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    String categoryname = rs.getString("categoryname");
                    int total = rs.getInt("total");
                    totalSold1 += total;

                    list.add(new PieChart.Data(categoryname, total));
                }

                pieChartCinema.setTitle(("percent of sales by film genre").toUpperCase());
                pieChartCinema.setData(list);
                percentageLb.setStyle("-fx-font: 15 Monospaced ");

                for (final PieChart.Data data : pieChartCinema.getData()) {
                    Double percentageDouble = data.getPieValue() * 100 / totalSold1;
                    String percentage = String.format("%.2f%%", percentageDouble);
                    data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            percentageLb.setText(data.getName() + ": " + percentage);
                        }
                    });
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
