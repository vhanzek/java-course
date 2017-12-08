package hr.fer.zemris.java.fractals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

/**
 * A factory for creating daemon threads used in an implementation
 * of the {@link ExecutorService} interface.
 * 
 * @author Vjeco
 */
public class DaemonicThreadFactory implements ThreadFactory{

	/**
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 */
	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setDaemon(true);
		return thread;
	}
}
