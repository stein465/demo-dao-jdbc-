package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;

public class Program {

    public static void main(String[] args) {
        Department obj = new Department ( 25,"Sells" );
        System.out.println (obj);
        SellerDao sellerDao = DaoFactory.createSellerDao ();
        Seller seller = sellerDao.findById ( 4);


        System.out.println (seller);
    }
}
