package ru.ithex.center.communication.emailsender.model;

public enum EmailTypes {
    SIMPLE("plain"),
    HTML("html");
    private final String name;

    private EmailTypes(String name){
        this.name = name;
    }

    public String toString(){
        return this.name;
    }
}