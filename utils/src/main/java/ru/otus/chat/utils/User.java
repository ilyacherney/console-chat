package ru.otus.chat.utils;

public class User {
    private String name;
    private String role;
    private static int count;

    public User() {
        count++;
        name = "user" + count;
        role = count == 1 ? "ADMIN" : "USER";
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
