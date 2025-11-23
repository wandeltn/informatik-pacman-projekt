package GraphTraversal;

/**
 * Wrapper to call root-level Logger & LogLevel (default package) from packaged code
 * using reflection (Java forbids direct import of default-package classes).
 */
public class LoggerWrapper {
    public static void log(String message, String levelName) {
        try {
            Class<?> logLevelClass = Class.forName("LogLevel");
            @SuppressWarnings({"unchecked", "rawtypes"})
            Object level = Enum.valueOf((Class)logLevelClass, levelName);
            Class<?> loggerClass = Class.forName("Logger");
            java.lang.reflect.Method m = loggerClass.getMethod("log", String.class, logLevelClass);
            m.invoke(null, message, level);
        } catch (Exception e) {
            System.out.println("[LOG WRAPPER FALLBACK] " + levelName + ": " + message);
        }
    }
}