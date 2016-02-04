package com.becomejavasenior;

public class SchemaVersion {
    private final static String DB_VERSION = "2.0";

    public static String getDbVersion() {
        return DB_VERSION;
    }

    private SchemaVersion() {
    }
}
