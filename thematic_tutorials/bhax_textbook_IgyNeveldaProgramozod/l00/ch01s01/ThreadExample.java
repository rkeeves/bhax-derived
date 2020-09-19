
public class ThreadExample {
	static class WorkerThread extends Thread {

		private final String msg;

		WorkerThread(String msg) {
			super();
			this.msg = msg;
		}

		@Override
		public void run() {
			for (int i = 0; i < 4; i++) {
				System.out.println(msg);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

	static class WorkerThread2 extends Thread {

		private final String msg;

		WorkerThread2(String msg) {
			super();
			this.msg = msg;
		}

		@Override
		public void run() {
			synchronized (System.out) {
				for (int i = 0; i < 4; i++) {
					System.out.println(msg);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}

		}
	}

	public static void main(String[] args) {
		Thread[] wts = getWorkerThreads(true);
		for (Thread t : wts) {
			t.start();
		}
	}

	static Thread[] getWorkerThreads(boolean first) {
		if (first) {
			return new Thread[] { new WorkerThread("A"), new WorkerThread("B") };
		} else {
			return new Thread[] { new WorkerThread2("A"), new WorkerThread2("B") };
		}

	}
}
