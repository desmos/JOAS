package frontend.ui.main;

import static com.google.common.base.Preconditions.checkNotNull;
import static common.logging.AppLogger.logMethod;

/**
 * @author sfraim
 */
public class MainScreen {
  private final MainActivity mainActivity;

  public MainScreen(MainActivity mainActivity) {
    logMethod();
    this.mainActivity = checkNotNull(mainActivity, "mainActivity");
  }
}
