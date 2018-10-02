package services.Share;

import data.FileIO;
import org.json.JSONException;
import org.json.JSONObject;
import util.Util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class TODOConnection implements Taskable {
    private static final String HOST = "login.microsoftonline.com";
    private static final String CODE = "/common/oauth2/v2.0/authorize";
    private static final String TOKEN = "/common/oauth2/v2.0/token";
    private static final String TODO_KENNWORT = "yjjtNDHPKC32}@manF662";
    private static final String TODO_APP_ID = "6dca8d97-dbac-450d-a6d2-59c5c833d2a9";
    private static final String REDIRECT_URI = "https://login.live.com/oauth20_desktop.srf";
    private static TODOConnection instance;
    private Authorization authorization;

    private TODOConnection() {
    }

    public static TODOConnection getInstance() {
        if (instance == null)
            instance = new TODOConnection();
        return instance;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public void authorize() {
        if (!isAuthorized()) {
            Util.logger.log(Level.INFO, "User not authorized, try to load authorization");
            try {
                authorization = FileIO.load(Authorization.class);
            } catch (IOException e) {
                Util.logger.log(Level.WARNING, "Loading failed. Renew authentication fallback");
            }
            if (!isAuthorized()) {
                OAuth2Controller authController = new OAuth2Controller(this, getCodeURL());
                URL resource = getClass().getResource("OAuth2Controller.fxml");
                Util.createDialogWithController("Connect to TODO", resource, authController);
            }
        } else {
            Util.logger.log(Level.INFO, "User already authorized.");
        }
    }

    public void aquireAccessToken(String code) {
        String response = null;
        try {
            Map<String, String> arguments = new HashMap<>();
            arguments.put("client_id", TODO_APP_ID);
            arguments.put("code", code);
            arguments.put("redirect_uri", REDIRECT_URI);
            arguments.put("grant_type", "authorization_code");
            arguments.put("scope", "https://graph.microsoft.com/.default offline_access");

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");

            response = Util.post(getTokenUrl(), arguments, headers);

            authorization = parseAuthentication(response);
            FileIO.save(authorization);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            Util.logger.log(Level.SEVERE, "Received invalid Response from Server.", response);
        }
    }

    @Override
    public void createTask() {

    }

    private void refreshAuthentication() {
        String response = null;
        try {
            Map<String, String> arguments = new HashMap<>();
            arguments.put("client_id", TODO_APP_ID);
            arguments.put("refresh_token", authorization.getRefreshToken());
            arguments.put("redirect_uri", REDIRECT_URI);
            arguments.put("grant_type", "refresh_token");
            arguments.put("scope", "https://graph.microsoft.com/.default offline_access");

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/x-www-form-urlencoded");

            response = Util.post(getTokenUrl(), arguments, headers);

            authorization = parseAuthentication(response);
            FileIO.save(authorization);

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            Util.logger.log(Level.SEVERE, "Received invalid Response from Server.", response);
        }
    }

    private boolean isAuthorized() {
        return authorization != null;
    }

    private String getCodeURL() {
        try {
            String query =
                    "client_id=" + TODO_APP_ID + "&" +
                            "response_type=code&" +
                            "redirect_uri=" + REDIRECT_URI + "&" +
                            "scope=https://graph.microsoft.com/.default offline_access";
            URI uri = new URI("https", HOST, CODE, query, null);
            return uri.toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private URL getTokenUrl() throws URISyntaxException, MalformedURLException {
        URI uri = new URI("https", HOST, TOKEN, null, null);
        return uri.toURL();
    }

    /**
     * @param input in the form of a JSONString, like:
     *              {
     *              "access_token": "eyJ0...",
     *              "refresh_token": "AWabbe...",
     *              "expires_in": 3599,
     *              ...
     *              }
     */
    private Authorization parseAuthentication(String input) throws JSONException {
        JSONObject obj = new JSONObject(input);
        return new Authorization(obj.getString("access_token"), obj.getString("refresh_token"));
    }

}
