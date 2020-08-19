package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        Scanner sc =new Scanner ( System.in );
        SellerDao sellerDao = DaoFactory.createSellerDao ();
        System.out.println ("===== TEST 1 :: FIND BY ID ======");
        Seller seller = sellerDao.findById ( 4);
        System.out.println (seller);
        System.out.println ("===== TEST 2 :: FIND BY ID ======");
        List<Seller> list = sellerDao.findByDepartment ( new Department ( 2,null ) );
        for (Seller obj: list){
            System.out.println (obj);
        }
        System.out.println ("===== TEST 3 :: FIND ALL ======");
        List<Seller> list2 = sellerDao.findAll ( );
        for (Seller obj: list2){
            System.out.println (obj);
        }

       /* System.out.println ("===== TEST 4 :: INSERT SELLER ======");
        Seller seller2 = new Seller ( null,"Rogerg", "Roger@gmail", new Date (  ), 2000.0, new Department ( 2,null ) );
        sellerDao.insert ( seller2 );
        System.out.println ("inserted: " + seller2.getId ());*/

        System.out.println ("===== TEST 5 :: UPDATE SELLER ======");
        seller = sellerDao.findById ( 12 );
        seller.setName ("Martha Wayne ");
        sellerDao.update ( seller );

        System.out.println ("===== TEST 6 :: DELETE SELLER ======");
        System.out.println ("ENTER WITH A ID");
        int id = sc.nextInt ();
        sellerDao.deleteById ( id );
        System.out.println ("deleted! ");

        sc.close ();

    }


}
