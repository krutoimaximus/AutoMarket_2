package com.example.automarket;
//Пример абстрактных классов, пример интерфейса
public abstract class Configs {

    interface Config {

        String dbHost = "127.0.0.1";
        String dbPort = "3306";
        String dbUser = "root";
        String dbPass = "дупшщтук1991";
        String dbName = "AutoMarket";
    }

    interface Const {

        public static final String AUTO_TABLE = "auto";

        public static final String AUTO_ID = "idauto";
        public static final String AUTO_STAMP = "stamp";
        public static final String AUTO_MODEL = "model";
        public static final String AUTO_CATEGORY = "category";
        public static final String AUTO_NUMBER = "number";
        public static final String AUTO_TYPE = "type";
        public static final String AUTO_PRODUCTION = "production";
        // public static final String AUTO_TRAILER = "trailer";

    }



}
