package Logger;


/**
 * Write a description of class Logger here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Logger
{
    
    static LogLevel logLevel = LogLevel.INFO;
    
    public static void setLogLevel(LogLevel level) {
        logLevel = level;
    }

    public static void log(String message, LogLevel level) {
        if (level.ordinal() >= logLevel.ordinal()) {
            System.out.println("[" + level + "] " + "(" + getCallerMethodName() + ")"  + message);
        }
    }
    
    private static String getCallerMethodName()
    {
       return StackWalker.
          getInstance().
          walk(stream -> stream.skip(2).findFirst().get()).
          getMethodName();
    }
    
    public Logger()
    {
        
    }
    
}
