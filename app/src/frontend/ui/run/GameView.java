package frontend.ui.run;

import static com.google.common.base.Preconditions.checkState;
import static common.logging.AppLogger.logMethod;
import static frontend.ui.run.GameThread.ThreadState.STATE_READY;
import static frontend.ui.run.GameThread.ThreadState.STATE_TERMINATED;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
	private GameThread gameThread;
	
	public GameView(Context context, AttributeSet attrs) {
    super(context, attrs);
    logMethod();

    SurfaceHolder holder = getHolder();
    holder.addCallback(this);
    this.gameThread = new GameThread(holder, this);
	}

  public GameThread getGameThread() {
    return gameThread;
  }

  @Override
	public void surfaceCreated(SurfaceHolder holder) {
	  logMethod();
	  checkState(!gameThread.isAlive(), "The GameThread should not be alive when the surface is called."
        + "is created. State was " + gameThread.getGameThreadState());
    if (gameThread.getGameThreadState() == STATE_TERMINATED) {
      gameThread = new GameThread(getHolder(), this);
    }
    gameThread.startGameThread();
	}

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
    logMethod();
  }
  
  @Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	  logMethod();
	  gameThread.terminateGameThread();
	}
}
