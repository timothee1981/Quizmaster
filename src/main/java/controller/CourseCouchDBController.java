package controller;

import database.nosql.CouchDBaccess;
import database.nosql.CourseCouchDBDAO;
import model.Course;

public class CourseCouchDBController {

    private CouchDBaccess dBaccess;
    private CourseCouchDBDAO cdbf;

    public CourseCouchDBController() {
        super();
        dBaccess = new CouchDBaccess();
        cdbf = new CourseCouchDBDAO(dBaccess);
    }
        //Verbinding maken om een cursus weg te schrijven in CouchDB
        public void saveCourse(Course course){
          try {
              dBaccess.setupConnection();
              System.out.println("Connectie open");
          } catch (Exception exception) {
              System.out.println("Hm, er ging iets mis");
              exception.printStackTrace();
            } cdbf.saveSingleCourse(course);
        }

}
