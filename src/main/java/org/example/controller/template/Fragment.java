package org.example.controller.template;

public class Fragment {

    private static final String fragmentTag =  "::content";

    public static String get(String fragment) {
        return fragment + fragmentTag;
    }
}
