package common.logging;

import android.util.Log;

import common.annotations.Nullable;

/**
 * More fine grained logging levels will be set in config files and retrieved
 * using android isLoggable
 * 
 * @author sfraim
 */
public class AppLogger {

  // TODO(sfraim) swtich to using a local.prop for logging so isLoggable can be
  // overriden per module
  private static int GLOBAL_LOG_LEVEL = Log.VERBOSE;

  /**
   * Logs the specified message with the tag set to the name of the calling
   * class.
   */
  public static void log(int level, String message) {
    final Throwable t = new Throwable();
    final StackTraceElement methodCaller = t.getStackTrace()[1];

    String tag = shortClassNameOf(methodCaller.getClassName());
    log(level, tag, message);
  }

  /**
   * Logs a {@link Log.INFO} message containing the calling method's name.
   * 
   * Tag is the name of the calling class.
   */
  public static void logMethod() {
    final Throwable t = new Throwable();
    final StackTraceElement methodCaller = t.getStackTrace()[1];
    String tag = shortClassNameOf(methodCaller.getClassName());
    String message = methodCaller.getMethodName();
    log(Log.INFO, tag, message);
  }

  /**
   * Log the specified message under the given tag at the specified priority
   * level.
   */
  public static void log(int level, String tag, String message) {
    if (Log.isLoggable(tag, level) && GLOBAL_LOG_LEVEL <= level) {
      Log.println(level, tag, message);
    }
  }

  private static String shortClassNameOf(String fullName) {
    return last(fullName.split("\\."));
  }

  @Nullable
  private static <T> T last(T[] arr) {
    return arr.length > 0 ? arr[arr.length - 1] : null;
  }
}
