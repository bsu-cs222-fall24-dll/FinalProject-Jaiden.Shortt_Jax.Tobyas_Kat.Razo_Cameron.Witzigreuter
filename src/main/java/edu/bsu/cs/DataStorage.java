package edu.bsu.cs;

public class DataStorage {

    private final String timestamp;

    public String getTimestamp() {
        return timestamp;
    }

    public DataStorage(String data){
        timestamp = timestampConvert(data);
    }
    private String timestampConvert(String timeString){
        timeString = timeString.replace("PT", "");
        timeString = timeString.replace("M", ":");
        timeString = timeString.replace(".",":");
        timeString = timeString.replace("S","");
        return timeString;
    }
}
