module CostCalculation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.sql;
    requires mysql.connector.j;
    requires lombok;


    opens Client.Config to javafx.fxml;
    exports Client.Config;
    exports Client.Controllers;
    opens Client.Controllers to javafx.fxml;
    exports Client;
    opens Client to javafx.graphics;
    exports Client.Controllers.Entity;
    opens Client.Controllers.Entity to javafx.fxml;
    exports Client.Controllers.Intarfaces;
    opens Client.Controllers.Intarfaces to javafx.fxml;
    exports Client.Controllers.Admin;
    opens Client.Controllers.Admin to javafx.fxml;
    exports Client.Controllers.User;
    opens Client.Controllers.User to javafx.fxml;
}