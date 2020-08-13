package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {

    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao ();
        System.out.println ("===== TEST 1 :: FIND BY ID ======");
        Seller seller = sellerDao.findById ( 4);
        System.out.println (seller);
        System.out.println ("===== TEST 2 :: FIND BY ID ======");
        List<Seller> list = sellerDao.findByDepartment ( new Department ( 2,null ) );
        for (Seller obj: list){
            System.out.println (obj);
        }


    }
}
