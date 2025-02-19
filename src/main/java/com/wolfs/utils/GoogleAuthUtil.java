package com.wolfs.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.Person;
import com.sun.net.httpserver.HttpServer;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class GoogleAuthUtil {
    private static final String CLIENT_ID = "";
    private static final String CLIENT_SECRET = "";
    private static final String REDIRECT_URI = "http://localhost:8081/callback";


    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private static final List<String> SCOPES = Arrays.asList(
            "https://www.googleapis.com/auth/userinfo.profile",
            "https://www.googleapis.com/auth/userinfo.email",
            "https://www.googleapis.com/auth/user.phonenumbers.read"

    );

    public static Credential authenticate() throws IOException {
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPES)
                .setAccessType("offline")
                .build();

        String authUrl = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();

        // Open URL in browser
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(URI.create(authUrl));
        } else {
            System.out.println("Open this URL in your browser: " + authUrl);
        }

        // Start local server to handle callback
        HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
        AtomicReference<Credential> credentialHolder = new AtomicReference<>();

        server.createContext("/callback", exchange -> {
            String query = exchange.getRequestURI().getQuery();
            String code = query != null && query.contains("code=") ? query.split("code=")[1].split("&")[0] : null;

            String response = (code != null) ? "Authorization successful! You can close this window." :
                    "Authorization failed!";
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
            exchange.close();

            if (code != null) {
                try {
                    GoogleTokenResponse tokenResponse = flow.newTokenRequest(code)
                            .setRedirectUri(REDIRECT_URI)
                            .execute();

                    Credential credential = flow.createAndStoreCredential(tokenResponse, null);
                    credentialHolder.set(credential);
                    System.out.println("Authentication successful!");

                } catch (HttpResponseException e) {
                    e.printStackTrace();
                }
            }

            server.stop(0);  // Stop server after handling request
        });

        server.setExecutor(Executors.newFixedThreadPool(1));
        server.start();
        System.out.println("Waiting for Google authentication...");

        // Wait for credential retrieval
        while (credentialHolder.get() == null) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        return credentialHolder.get();
    }

    public static Userinfo getUserInfo(Credential credential) throws IOException {
        if (credential == null || credential.getAccessToken() == null) {
            throw new IOException("Invalid credentials. Cannot fetch user info.");
        }

        Oauth2 oauth2 = new Oauth2.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("FitFusion")
                .build();

        return oauth2.userinfo().get().execute();
    }
    public static String getUserPhoneNumber(Credential credential) throws IOException {
        PeopleService peopleService = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName("FitFusion")
                .build();

        Person profile = peopleService.people().get("people/me")
                .setPersonFields("phoneNumbers")
                .execute();

        if (profile.getPhoneNumbers() != null && !profile.getPhoneNumbers().isEmpty()) {
            return profile.getPhoneNumbers().get(0).getValue();
        }
        return "No phone number found";
    }
}