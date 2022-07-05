package com.example.embeddedprogrammingassignment;

import com.example.embeddedprogrammingassignment.modal.QrCode;
import com.example.embeddedprogrammingassignment.modal.QrHistory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class JsonToObjectConverter {

    public static QrCode JsonToObject(String result){

        Gson gson = new Gson();
        QrCode qrCode = gson.fromJson(result, QrCode.class);

        return qrCode;
    }
}
