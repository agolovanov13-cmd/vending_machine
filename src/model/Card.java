package model;

public class Card {
    private int balance;
    private String code;
    private int authCode;

    public Card(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance += balance;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setAuthCode(int authCode) {
        this.authCode = authCode;
    }
}
