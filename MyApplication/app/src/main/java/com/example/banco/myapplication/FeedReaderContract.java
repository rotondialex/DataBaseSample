package com.example.banco.myapplication;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Fornitori";
        public static final String COLUMN_NAME_nome = "Nome";
        public static final String COLUMN_NAME_indirizzo = "Indirizzo";
        public static final String COLUMN_NAME_telefono = "Telefono";
        public static final String COLUMN_NAME_email = "Email";
    }
}
