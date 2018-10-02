package util;

import core.Series;
import core.SeriesContainer;
import data.FileIO;
import gui.Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    public static Logger logger = Logger.getGlobal();

    public static void createDialog(String title, URL resource) {
        FXMLLoader loader = new FXMLLoader(resource);

        try {
            setUpDialog(loader, title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Make sure that the corresponding fxml does not include a fx:controller tag
    public static void createDialogWithController(String title, URL resource, Controller controller) {
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setController(controller);

        try {
            setUpDialog(loader,title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setUpDialog(FXMLLoader loader, String title) throws IOException {
        Parent parent = loader.load();
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.show();
    }

    public static void cancel(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> queryMap = new LinkedHashMap<>();
        String query = url.getQuery();
        for(String pair: query.split("&")) {
            int idx = pair.indexOf("=");
            String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
            String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
            queryMap.put(key, value);
        }
        return queryMap;
    }

    public static String toQuery(Map<String, String> queryMap) throws UnsupportedEncodingException{
        StringJoiner sj = new StringJoiner("&");
        for(Map.Entry<String, String> entry: queryMap.entrySet()) {
            String key = URLEncoder.encode(entry.getKey(), "UTF-8");
            String value = URLEncoder.encode(entry.getValue(), "UTF-8");
            sj.add(key + "=" + value);
        }
        return sj.toString();
    }

    public static String post(URL url, Map<String, String> arguments, Map<String, String> header) throws IOException{
        byte[] out = Util.toQuery(arguments).getBytes("UTF-8");

        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        for(Map.Entry<String, String> entry: header.entrySet()) {
            http.setRequestProperty(entry.getKey(), entry.getValue());
        }
        http.setDoOutput(true);

        try (OutputStream outStream = http.getOutputStream()) {
            outStream.write(out);
        }

        logger.log(Level.INFO, "Response from Server: {0}", http.getResponseCode() + " " + http.getResponseMessage());
        StringBuilder input = new StringBuilder();
        try (InputStream inStream = http.getInputStream()) {
            try (Reader in = new InputStreamReader(inStream, "UTF-8")) {
                try (BufferedReader reader = new BufferedReader(in)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        input.append(String.valueOf(line));
                    }
                }
            }
        }
        return input.toString();
    }

    public static void saveAll() {
        try {
            SeriesContainer.save();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not save container: {0}", e.getMessage());
        }
    }

    public static void loadAll() {
        try {
            SeriesContainer.load();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not load container: {0}", e.getMessage());
        }
    }
}
