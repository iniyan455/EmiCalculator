package com.iniya.emicalculator.Pojo;

import java.util.List;

public class Response  {


    private List<com.iniya.emicalculator.Pojo.ResponseYear> responseYear;

    public List<com.iniya.emicalculator.Pojo.ResponseYear> getResponseYear() {
        return responseYear;
    }

    public void setResponseYear(List<com.iniya.emicalculator.Pojo.ResponseYear> responseYear) {
        this.responseYear = responseYear;
    }


    public Response(List<com.iniya.emicalculator.Pojo.ResponseYear> responseYear) {
        this.responseYear = responseYear;
    }
}
