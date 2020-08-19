package application;

import jdk.swing.interop.SwingInterOpUtils;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.util.List;
import java.util.Scanner;

public class Program2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DepartmentDao deptDao = DaoFactory.createDepartmentDao ();
        System.out.println ("===== TEST 1 :: FIND BY ID ======");
        Department dept = deptDao.findById ( 3 );
        System.out.println (dept);

        Department dept2 = new Department ( null,"D3" );

      /*  System.out.println ("===== TEST 2 :: INSERT ======");
        Department dept2 = new Department ( null,"D3" );
        deptDao.insert ( dept2 );
        System.out.println ("Done!!");*/

        System.out.println ("===== TEST 3 :: UPDATE ======");
        dept2 = deptDao.findById ( 3 );
        dept2.setName ( "Fashion" );
        deptDao.update ( dept2 );

        System.out.println ("===== TEST 4 :: FIND ALL ======");
        List<Department> list = deptDao.findAll ();
        list.forEach ( System.out::println );

        System.out.println ("===== TEST 5 :: DELETE ======");
        System.out.println ("digite o id do departamento deletado");
        int id = sc.nextInt ();
        deptDao.deleteById ( id );
    }
}
