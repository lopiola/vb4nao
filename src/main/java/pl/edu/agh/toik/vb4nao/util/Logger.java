package pl.edu.agh.toik.vb4nao.util;

/**
 * Created by lopiola on 18.05.14.
 */
public class Logger {
    private String className;

    public Logger(Class<?> _class) {
        this.className = _class.getSimpleName();
    }

    public void debug(String message) {
        System.out.println("[DEBUG] " + className+ ": " + message);
    }

    public void info(String message) {
        System.out.println("[INFO] " + className+ ": " + message);
    }

    public void error(String message) {
        System.out.println("[ERROR] " + className+ ": " + message);
    }
}
