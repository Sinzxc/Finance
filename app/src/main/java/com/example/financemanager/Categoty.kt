package com.example.financemanager
class Category {//Класс для хранения данных о каждой категории
    var  name:String?=null;
    var  value:Float?=null;
    var  color:String?=null;
    var  price:Float?=null;
    var  date:String?=null;

    constructor(){
        name=""
        value=0f;
        color="#ffffff";
        price=0f;
        date="01-11-2000"
    }
    constructor(_name: String?, _value: Float?, _color: String?, _price: Float?, _date: String?){
        name=_name;
        value=_value;
        color=_color;
        price=_price;
        date=_date;
    }
}