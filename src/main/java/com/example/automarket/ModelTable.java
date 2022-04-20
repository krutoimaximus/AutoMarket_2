package com.example.automarket;
// Пример инкапсуляции
public class ModelTable {
    private String Category, Model, Number, Production, Stamp, Type;


    public ModelTable(String stamp, String category, String model, String number, String production, String type) {
        this.Category = category;
        this.Model = model;
        this.Number = number;
        this.Production = production;
        this.Stamp = stamp;
        this.Type = type;
    }

    public String getCategory() {

        return Category;
    }

    public String getModel() {

        return Model;
    }

    public String getNumber() {

        return Number;
    }

    public String getProduction() {

        return Production;
    }

    public String getStamp() {

        return Stamp;
    }

    public String getType() {

        return Type;
    }
}
