package com.example.androidprojectrecyclerview;

import java.util.List;

public class RestResponse {
    private Integer count;
    private String next;
    private List<pokemon> results;

    public Integer getCount() {
        return count;
    }

    public String getNext() {
        return next;
    }

    public List<pokemon> getResults(){
        return results;
    }
}
