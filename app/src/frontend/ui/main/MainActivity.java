package frontend.ui.main;

import android.app.Activity;
import android.os.Bundle;
import org.apache.commons.logging.Log;

public class MainActivity extends Activity
{
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    android.util.Log.e("PENIS", "Penis");
  }
}
