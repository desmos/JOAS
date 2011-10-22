package common.logging;

import common.annotations.Nullable;
import android.util.Log;

public class AppLogger {

  private static int LOG_LEVEL = Log.VERBOSE;

  /**
   * Logs the specified message with the tag set to the name of the calling class.
   */
  public static void log(int level, String message) {
    if (shouldLog(level)) {
      final Throwable t = new Throwable();
      final StackTraceElement methodCaller = t.getStackTrace()[1];

      String tag = shortClassNameOf(methodCaller.getClassName());
      log(level, tag, message);
    }
  }

  /**
   * Logs a {@link Log.INFO} message containing the calling method's name.
   *
   * Tag is the name of the calling class.
   */
  public static void logMethod() {
    if (shouldLog(Log.INFO)) {
      final Throwable t = new Throwable();
      final StackTraceElement methodCaller = t.getStackTrace()[1];
      String tag = shortClassNameOf(methodCaller.getClassName());
      String message = methodCaller.getMethodName();
      log(Log.INFO, tag, message);
    }
  }

  /**
   * Log the specified message under the given tag at the specified priority level.
   */
  public static void log(int level, String tag, String message) {
    if (shouldLog(level)) {
      if (Log.isLoggable(tag, level)) {
        Log.println(level, tag, message);
      }
    }
  }

  private static boolean shouldLog(int level) {
    return LOG_LEVEL <= level;
  }

  private static String shortClassNameOf(String fullName) {
    return last(fullName.split("\\."));
  }

  @Nullable private static <T> T last(T[] arr) {
    return arr.length > 0 ? arr[arr.length - 1] : null;
  }
}
