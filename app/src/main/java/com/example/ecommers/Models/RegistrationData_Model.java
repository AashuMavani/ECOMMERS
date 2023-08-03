package com.example.ecommers.Models;

public class RegistrationData_Model {
int connection;
int result;

    public int getConnection() {
        return connection;
    }

    public void setConnection(int connection) {
        this.connection = connection;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RegistrationData_Model{" +
                "connection=" + connection +
                ", result=" + result +
                '}';
    }
}
