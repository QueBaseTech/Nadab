package com.example.karokojnr.nadab.model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("token")
    private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Login{" +
                "token='" + token + '\'' +
                '}';
    }

    public class Token {
        @SerializedName("token")
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
