
/**
 * Write a description of class Logger here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Logger
{
    
    static LogLevel logLevel = LogLevel.DEBUG;
    
    public static void setLogLevel(LogLevel level) {
        logLevel = level;
    }

    public static void log(String message, LogLevel level) {
        if (level.ordinal() >= logLevel.ordinal()) {
            System.out.println("[" + level + "] " + message);
        }
    }
    
    public Logger()
    {
        
    }
}