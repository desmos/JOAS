package frontend.ui.main;

import android.app.Activity;
import android.os.Bundle;
import com.google.common.collect.ImmutableSet;
import common.annotations.Nullable;
import common.logging.AppLogger;
import org.apache.commons.logging.Log;

public class MainActivity extends Activity
{
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    // Use Immutable collections everywhere!
    ImmutableSet environmentSet = ImmutableSet.of(testEnvironment("Test that you have guava setup correctly"));
    // Use AppLogger it is better than {@link Log}
    AppLogger.log(android.util.Log.INFO, environmentSet.toString());
  }

  /**
   * Use {@link Nullable} whenever a value can be null!
   */
  @Nullable public String testEnvironment(@Nullable String testEnvironment) {
    return testEnvironment;
  }
}
