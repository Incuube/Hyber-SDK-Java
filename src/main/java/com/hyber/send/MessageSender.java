package com.hyber.send;

import com.hyber.domain.sdkmessage.Message;
import org.json.JSONObject;

import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

import static com.hyber.constants.jsonfields.ErrorResponseFields.ERROR_CODE;
import static com.hyber.constants.jsonfields.ErrorResponseFields.ERROR_TEXT;
import static com.hyber.constants.jsonfields.SuccessResponseFields.MESSAGE_ID;

public class MessageSender extends HttpSender {

    private String login;
    private String password;
    private Integer identifier;

    public MessageSender(String login, String password, int identifier) {
        this.login = login;
        this.password = password;
        this.identifier = identifier;
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

    @Override
    public Response send(Message message) throws Exception {
        message.validate();
        String url = String.format(getUrlPattern(), identifier);
        String body = message.toJson().toString();
        System.out.println(body);
        return post(url, body, login, password, "application/json", getConnectionTimeout(), getReadTimeout());
    }

    private Response post(String url, String body, String login, String password, String contentType, int connectionTimeout, int readTimeout) throws Exception {

        HttpURLConnection connection = null;
        try {
            URI uri = new URI(url);
            connection = createConnection(uri.toURL(), contentType);
            setTimeOut(connection, connectionTimeout, readTimeout);
            setAuth(connection, login, password);
            sendHttp(connection.getOutputStream(), body);
            return getResponse(connection);
        } finally {
            if (connection != null) {
                connection.disconnect();
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

    private static HttpURLConnection createConnection(URL url, String contentType) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Content-Type", contentType);
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
        JSONObject answer = new JSONObject(getResponseBody(connection.getInputStream()));
        if (responseCode == HttpURLConnection.HTTP_OK) {
            response = new SuccessResponse();
            Long messageId = answer.getLong(MESSAGE_ID);
            response.setMessageId(messageId);
        } else {
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

}
