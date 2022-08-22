package ua.edu.znu.autoparkweb.utils;

import ua.edu.znu.autoparkweb.service.DriverDaoImpl;

import javax.persistence.Persistence;

/**
 * Use for DDL-script for drop tables and create tables generation.
 * You must enable properties for schema generation in the persistence.xml.
 */
public class GenerateDDLSchema {
    public static void main(String[] args) {
        Persistence.generateSchema("autoparkPU", null);
        /*For root directory printing*/
        System.out.println(System.getProperty("user.dir"));

        /*For Lombok's @String.Exclude checking*/
        DriverDaoImpl driverDao = new DriverDaoImpl();
        System.out.println(driverDao.findById(1L));

        System.exit(0);
    }
}
