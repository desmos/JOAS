package frontend.ui.main;

import static common.logging.AppLogger.logMethod;

import android.app.Activity;
import android.os.Bundle;
import com.google.common.collect.ImmutableSet;
import common.annotations.Nullable;
import common.logging.AppLogger;
import frontend.ui.run.GameView;

/**
 * http://developer.android.com/reference/android/app/Activity.html
 *
 * @author sfraim
 */
public class MainActivity extends Activity {
  GameView gameView;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    logMethod();
    setContentView(R.layout.game);
    gameView = (GameView) findViewById(R.id.gameView);

    // Use Immutable collections everywhere!
    ImmutableSet<String> environmentSet = ImmutableSet.of(testEnvironment("Test that you have guava setup correctly"));
    // Use AppLogger it is better than {@link Log}
    AppLogger.log(android.util.Log.INFO, environmentSet.toString());
  }

  @Override
  public void onStart() {
    super.onStart();
    logMethod();
  }

  @Override
  public void onResume() {
    super.onResume();
    logMethod();
    gameView.getGameThread().resume();
  }

  @Override
  public void onPause() {
    super.onResume();
    logMethod();
    gameView.getGameThread().pause();

    // TODO This is where we need to save any state (save it to the thread).
  }

  @Override
  public void onStop() {
    super.onStop();
    logMethod();
    gameView.getGameThread().stop();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    logMethod();
  }

  /**
   * Use {@link Nullable} whenever a value can be null.
   */
  @Nullable public String testEnvironment(@Nullable String testEnvironment) {
    return testEnvironment;
  }
}
