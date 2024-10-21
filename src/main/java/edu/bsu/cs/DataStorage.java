package edu.bsu.cs;
import java.util.List;

public class DataStorage {

    private final String redirect;
    private final List<String> users;
    private final List<String> times;
    private final List<String> dates;

    protected DataStorage(String redirect, List<String> users, List<String> times, List<String> dates) {

        this.redirect= redirect;
        this.users = users;
        this.times = times;
        this.dates = dates;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        if (!redirect.isEmpty())
            stringBuilder.append(String.format("Redirected to %s%n", redirect));

        for (int i = 0; i < users.size(); i++){
            stringBuilder.append(String.format("%s  %s  %s%n", users.get(i), times.get(i), dates.get(i)));
        }
        return stringBuilder.toString();
    }
}
