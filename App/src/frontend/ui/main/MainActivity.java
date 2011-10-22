package frontend.ui.main;

import android.app.Activity;
import android.os.Bundle;
import com.google.common.collect.ImmutableSet;

public class MainActivity extends Activity
{
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    ImmutableSet.of("Test to make sure you all have Guava setup");
    new TestObject().testMethod("Test to make sure you have EasyMock setup");
  }

  // TODO(sfraim) remove this after everyone gets their project setup.
  public static class TestObject {
    public void testMethod(String testMethodInput) { }
  }
}
