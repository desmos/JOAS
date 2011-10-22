package frontend.ui.run;

import static android.util.Log.INFO;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static common.logging.AppLogger.log;
import static common.logging.AppLogger.logMethod;
import static frontend.ui.run.GameThread.ThreadState.*;
import static frontend.ui.run.GameThread.ThreadState.STATE_PAUSE;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * @author sfraim
 */
public class GameThread {
  public static enum ThreadState {
    STATE_READY,
    STATE_RUN,
    STATE_PAUSE,
    STATE_STOP,
    STATE_TERMINATED,
  }

	private ThreadState threadState;
  private SurfaceHolder surfaceHolder;
  private GameView gameView;
  private ExecutorService executorService;
  private Future<?> gameLoopFuture;
	private boolean runApp;

	protected GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
    logMethod();
		this.surfaceHolder = checkNotNull(surfaceHolder, "surfaceHolder");
		this.gameView = checkNotNull(gameView, "gameView");
    executorService = Executors.newSingleThreadExecutor();
    this.runApp = false;
		threadState = STATE_READY;
  }

  public void startGameThread() {
    logMethod();
    checkState(!runApp && gameLoopFuture == null, "App is already running.");
    setGameThreadState(STATE_RUN);
    runApp = true;
    gameLoopFuture = executorService.submit(new Runnable() {
      public void run() {
        doGameLoop();
      }
    });
  }

  public void resume() {
    logMethod();
    // We can resume a thread from any state {@see http://developer.android.com/reference/android/app/Activity.html}
    if (threadState != ThreadState.STATE_READY) {
      setGameThreadState(STATE_RUN);
    }
  }

  public void pause() {
    logMethod();
    setGameThreadState(STATE_PAUSE);
  }

  public void stop() {
    logMethod();
    setGameThreadState(STATE_STOP);
    // TODO(sfraim) This assumes views are recreated (and the thread) as part of the activitry lifecycle?
    // If this assumption is invalid, this may may need to be changed.
    gameLoopFuture.cancel(true /* mayInterruptIfRunning */);
  }

  public void terminateGameThread() {
    checkState(runApp && gameLoopFuture != null, "App has already been shutdown.");
    runApp = false;
    boolean retry = true;
	  while (retry) {
	    try {
        gameLoopFuture.get();
        setGameThreadState(STATE_TERMINATED);
	      retry = false;
	    } catch (Exception e) {
	      log(Log.ERROR, e.toString());
	    }
    }
  }

	public ThreadState getGameThreadState() {
    logMethod();
	  return threadState;
	}

  /**
   * Analogoes to Thread.isAlive(). Returns true if the thread has called run and has not currently executed.
   */
  public boolean isAlive() {
    return threadState != ThreadState.STATE_READY && threadState != ThreadState.STATE_TERMINATED;
  }

  private void setGameThreadState(ThreadState threadState) {
    synchronized (this.threadState) {
      this.threadState = threadState;
    }
  }

	private void doGameLoop() {
    logMethod();
		while (runApp) {
		  Canvas canvas = null;
			try {
        doGameLogic(canvas);
			} catch (Exception e) {
        log(INFO, Log.getStackTraceString(e));
			}
		}
	}

  private void doGameLogic(Canvas canvas) throws Exception {
    logMethod();
    switch (threadState) {
      case STATE_RUN :
        draw();
        break;
      case STATE_PAUSE:
        // Save cpu-cycles if the game logic is paused.
        Thread.sleep(100);
        break;
      case STATE_STOP:
        
      default:
        break;
    }
  }

  private void draw() throws Exception {
    logMethod();
    Canvas canvas = null;
    try {
      canvas = checkNotNull(surfaceHolder.lockCanvas(), "canvas");
      gameView.draw(canvas);
    } catch (Exception e) {
      throw new Exception("Error drawing on the canvas.", e);
    } finally {
      if (canvas != null) {
        surfaceHolder.unlockCanvasAndPost(canvas);
      }
    }
  }
}
