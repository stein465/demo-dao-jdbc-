package application;

import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {

    public static void main(String[] args) {
        Department obj = new Department ( 25,"Sells" );
        System.out.println (obj);

        Seller seller = new Seller ( 21,"bob","bob@gmail.com", new Date (  ),2000., obj );

        System.out.println (seller);
    }
}
