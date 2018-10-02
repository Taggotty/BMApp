package services.Share;

import gui.Controller;
import util.Util;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class OAuth2Controller implements Controller {
    private String url;
    private TODOConnection connection;
    @FXML
    private WebView browser;
    private WebEngine webEngine;

    public OAuth2Controller(TODOConnection connection, String url) {
        this.url = url;
        this.connection = connection;
    }


    @FXML
    protected void initialize() {
        webEngine = browser.getEngine();
        webEngine.load(url);
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (Worker.State.SUCCEEDED.equals(newValue)) {
                try {
                    URL url = new URL(webEngine.getLocation());
                    Map<String, String> queryMap= Util.splitQuery(url);
                    if(queryMap.containsKey("code")){
                        browser.getEngine().load(null);
                        Stage stage = (Stage) browser.getScene().getWindow();
                        stage.close();
                        connection.aquireAccessToken(queryMap.get("code"));

                    }
                } catch (MalformedURLException malformedException) {
                    // ignore
                } catch (UnsupportedEncodingException unsupportedException) {
                    unsupportedException.printStackTrace();
                }
            }
        });
        System.out.println(webEngine.getLocation());
    }
}
