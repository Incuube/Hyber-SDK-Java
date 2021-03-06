package com.hyber.sdk.send;

import com.hyber.sdk.constants.HyberConstants;
import com.hyber.sdk.constants.jsonfields.ErrorResponseFields;
import com.hyber.sdk.constants.jsonfields.SuccessResponseFields;
import com.hyber.sdk.domain.Message;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.*;

import static com.hyber.sdk.constants.HyberConstants.INTERNAL_SERVER_RESPONSE_CODE;
import static com.hyber.sdk.constants.jsonfields.ErrorResponseFields.ERROR_CODE;
import static java.net.HttpURLConnection.HTTP_CLIENT_TIMEOUT;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;

public final class MessageSender implements Sender {

    private String urlPattern = HyberConstants.URL_PATTERN;
    private Integer connectionTimeoutSec = HyberConstants.DEFAULT_CONNECTION_TIMEOUT_SEC;
    private Integer readTimeoutSec = HyberConstants.DEFAULT_READ_TIMEOUT_SEC;
    private String login;
    private String password;
    private Integer identifier;

    public MessageSender(String login, String password, int identifier) {
        this.login = login;
        this.password = password;
        this.identifier = identifier;
    }

    @Override
    public Response send(Message message) throws IOException, URISyntaxException {
        String url = String.format(urlPattern, identifier);
        String body = message.toString();
        return post(url, body, login, password, connectionTimeoutSec, readTimeoutSec, true);
    }

    @Override
    public Response send(Message message, boolean closeConnectionAfterSend) throws IOException, URISyntaxException {
        String url = String.format(urlPattern, identifier);
        String body = message.toString();
        return post(url, body, login, password, connectionTimeoutSec, readTimeoutSec, closeConnectionAfterSend);
    }

    private Response post(String url, String body, String login, String password, int connectionTimeout, int readTimeout, boolean closeConnection) throws IOException, URISyntaxException {

        HttpURLConnection connection = null;
        try {
            URI uri = new URI(url);
            connection = createConnection(uri.toURL());
            setTimeOut(connection, connectionTimeout, readTimeout);
            setAuth(connection, login, password);
            sendHttp(connection.getOutputStream(), body);
            return getResponse(connection);
        } finally {
            if (connection != null) {
                if (closeConnection) {
                    connection.disconnect();
                } else {
                    try {
                        connection.getInputStream().close();
                    } catch (Exception ignored) {
                    }
                    try {
                        connection.getErrorStream().close();
                    } catch (Exception ignored) {
                    }
                }
            }
        }

    }

    private void sendHttp(OutputStream outputStream, String json) throws IOException {

        try (DataOutputStream postOut = new DataOutputStream(outputStream)) {
            postOut.write(json.getBytes("UTF-8"));
            postOut.flush();
        }
    }

    private static void setTimeOut(URLConnection connection, Integer connectionTimeout, Integer readTimeout) {

        connection.setConnectTimeout(connectionTimeout * 1000);
        connection.setReadTimeout(readTimeout * 1000);
    }

    private static HttpURLConnection createConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type", "application/json");
        return connection;

    }

    private static void setAuth(URLConnection connection, String login, String pass) {
        if (login != null && pass != null) {
            String auth = "Basic " + DatatypeConverter.printBase64Binary((login + ":" + pass).getBytes());
            connection.setRequestProperty("Authorization", auth);
        }
    }

    private static Response getResponse(HttpURLConnection connection) throws IOException {
        Response response;
        int responseCode;
        responseCode = connection.getResponseCode();
        String rawResponse;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            rawResponse = getResponseBody(connection.getInputStream());
            try {
                JSONObject answer = new JSONObject(rawResponse);
                response = new SuccessResponse(responseCode, answer.getLong(SuccessResponseFields.MESSAGE_ID), rawResponse);
            } catch (Exception e) {
                response = new ErrorResponse(responseCode, INTERNAL_SERVER_RESPONSE_CODE, e.getMessage(), rawResponse);
            }
        } else {
            rawResponse = getResponseBody(connection.getErrorStream());
            try {

                JSONObject answer = new JSONObject(getResponseBody(connection.getErrorStream()));
                response = new ErrorResponse(responseCode, answer.getInt(ERROR_CODE), answer.getString(ErrorResponseFields.ERROR_TEXT), rawResponse);
            } catch (Exception e) {
                response = new ErrorResponse(responseCode, INTERNAL_SERVER_RESPONSE_CODE, e.getMessage(), rawResponse);
            }
        }

        return response;
    }

    private static String getResponseBody(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public String getUrlPattern() {
        return urlPattern;
    }

    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }

    public Integer getConnectionTimeoutSec() {
        return connectionTimeoutSec;
    }

    public void setConnectionTimeoutSec(Integer connectionTimeoutSec) {
        this.connectionTimeoutSec = connectionTimeoutSec;
    }

    public Integer getReadTimeoutSec() {
        return readTimeoutSec;
    }

    public void setReadTimeoutSec(Integer readTimeoutSec) {
        this.readTimeoutSec = readTimeoutSec;
    }
}
