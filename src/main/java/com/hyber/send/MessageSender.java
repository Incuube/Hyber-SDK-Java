package com.hyber.send;

import com.hyber.constants.HyberConstants;
import com.hyber.domain.Message;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.*;

import static com.hyber.constants.jsonfields.ErrorResponseFields.ERROR_CODE;
import static com.hyber.constants.jsonfields.ErrorResponseFields.ERROR_TEXT;
import static com.hyber.constants.jsonfields.SuccessResponseFields.MESSAGE_ID;

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
    public Response send(Message message) throws Exception {
        String url = String.format(urlPattern, identifier);
        String body = message.toString();
        return post(url, body, login, password, connectionTimeoutSec, readTimeoutSec, true);
    }

    @Override
    public Response send(Message message, boolean closeConnectionAfterSend) throws Exception {
        String url = String.format(urlPattern, identifier);
        String body = message.toString();
        return post(url, body, login, password, connectionTimeoutSec, readTimeoutSec, closeConnectionAfterSend);
    }

    private Response post(String url, String body, String login, String password, int connectionTimeout, int readTimeout, boolean closeConnection) throws Exception {

        HttpURLConnection connection = null;
        try {
            URI uri = new URI(url);
            connection = createConnection(uri.toURL());
            setTimeOut(connection, connectionTimeout, readTimeout);
            setAuth(connection, login, password);
            sendHttp(connection.getOutputStream(), body);
            return getResponse(connection);
        } catch (Exception e) {
            return getErrorsResponse(e);
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
        if (responseCode == HttpURLConnection.HTTP_OK) {
            JSONObject answer = new JSONObject(getResponseBody(connection.getInputStream()));
            response = new SuccessResponse();
            Long messageId = answer.getLong(MESSAGE_ID);
            response.setMessageId(messageId);
        } else {
            JSONObject answer = new JSONObject(getResponseBody(connection.getErrorStream()));
            response = new ErrorResponse();
            response.setHttpCode(responseCode);
            response.setErrorCode(answer.getInt(ERROR_CODE));
            response.setErrorText(answer.getString(ERROR_TEXT));
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

    private static Response getErrorsResponse(Exception e) {
        Response response = new ErrorResponse();
        if (e instanceof SocketTimeoutException) {
            response.setErrorCode(HttpURLConnection.HTTP_CLIENT_TIMEOUT);
            response.setErrorText(e.getMessage());
        } else {
            response.setErrorCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
            response.setErrorText(e.getMessage());
        }
        return response;
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
