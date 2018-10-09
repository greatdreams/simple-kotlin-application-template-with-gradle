package com.greatdreams.learn.java.locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.util.Locale;

public class LocaleTestProgram {
    private static Logger logger = LoggerFactory.getLogger(LocaleTestProgram.class);

    public static void main(String[] args) {

        Locale[] locales = Locale.getAvailableLocales();
        System.out.println("installed locales " + locales.length + ", its list is ");
        for(Locale locale: locales) {
            System.out.println(locale.getDisplayName() + ", "
                    + locale.getLanguage() + ", " +
                    locale.getDisplayCountry());
        }

        // use of locale object in number format
        NumberFormat fnf = NumberFormat.getInstance(Locale.FRENCH);
        NumberFormat cnf = NumberFormat.getInstance(Locale.CHINESE);

        for(int i = 0; i < 1000; i++) {
            System.out.println(fnf.format(i * 3000) + " || " + cnf.format(i * 3000));
        }
    }
}
