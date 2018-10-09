package com.greatdreams.learn.java.fonts;

import java.awt.*;

public class FontListDemo {
    public static void main(String[] args) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        System.out.println("graphic environment is headless: " + GraphicsEnvironment.isHeadless());

        String[] fontFamilyNames = env.getAvailableFontFamilyNames();
        System.out.println("---------------------------");
        System.out.println("all availabele font family names");
        for(String name: fontFamilyNames) {
            System.out.println("\t" + name);
        }

        Font[] fonts = env.getAllFonts();
        System.out.println("---------------------------");
        System.out.println("All font information: ");
        for(Font font: fonts) {
            System.out.println("\t" + font.getFamily() + "," + font.getName() + "," +
                    font.getNumGlyphs());
        }
    }
}
