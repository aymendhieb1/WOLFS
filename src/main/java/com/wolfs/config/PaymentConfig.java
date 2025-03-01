package com.wolfs.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PaymentConfig {
    private static final Properties properties = new Properties();
    private static boolean isInitialized = false;

    private static void initialize() {
        if (!isInitialized) {
            try (InputStream input = PaymentConfig.class.getClassLoader().getResourceAsStream("payment.properties")) {
                if (input == null) {
                    System.err.println("Unable to find payment.properties");
                    return;
                }
                properties.load(input);
                isInitialized = true;
            } catch (IOException ex) {
                System.err.println("Error loading payment configuration: " + ex.getMessage());
            }
        }
    }

    public static String getStripePublicKey() {
        initialize();
        String key = System.getenv("STRIPE_PUBLIC_KEY");
        if (key == null || key.isEmpty()) {
            key = properties.getProperty("stripe.public.key", "pk_test_51OpLZkHhZukMPqPqGOXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        }
        return key;
    }

    public static String getStripeSecretKey() {
        initialize();
        String key = System.getenv("STRIPE_SECRET_KEY");
        if (key == null || key.isEmpty()) {
            key = properties.getProperty("stripe.secret.key", "sk_test_51OpLZkHhZukMPqPqXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        }
        return key;
    }

    public static boolean isTestMode() {
        initialize();
        return Boolean.parseBoolean(properties.getProperty("stripe.test.mode", "true"));
    }
} 