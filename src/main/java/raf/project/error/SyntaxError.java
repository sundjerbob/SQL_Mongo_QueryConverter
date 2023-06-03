package raf.project.error;

public class SyntaxError extends RuntimeException{
    public  SyntaxError(String error) {
        super(error);
    }
}
