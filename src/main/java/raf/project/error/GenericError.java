package raf.project.error;

import java.util.ArrayList;
import java.util.List;

public class GenericError {
    private static volatile GenericError instance;
    List<String> errors;

    private GenericError() {
        // Private constructor to prevent instantiation
        errors = new ArrayList<>();
    }


    public static GenericError getInstance() {
        if (instance == null) {
            synchronized (GenericError.class) {
                if (instance == null) {
                    instance = new GenericError();
                }
            }
        }
        return instance;
    }

    public void addError(String s) {

        errors.add(s);
    }

    public void printStackTrace(){
        if(!errors.isEmpty())
            for (String error : errors)
                System.out.println(error);
    }

    // Other methods and properties of the class
}
