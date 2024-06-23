package project;

class Data {
	int A1, A2, A3, B1, B2, B3;

	boolean goFunA2 = false;
	boolean goFunA3 = false;
	boolean goFunB2 = false;
	boolean goFunB3 = false;
}

public class Project {
    public static void main(String[] args) {
        int testSize = 1000; // High number of iterations
        Data mySample = new Data();
        for (int i = 0; i < testSize; i++) {
            System.out.println("This iteration " + i);
            mySample.goFunA2 = false;
            mySample.goFunA3 = false;
            mySample.goFunB2 = false;
            mySample.goFunB3 = false;

            ThreadA ta = new ThreadA(mySample);
            ThreadB tb = new ThreadB(mySample);
            ta.start();
            tb.start();

            try {
                ta.join();
                tb.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Verify the correctness of the implementation
            int expectedA3 = mySample.B3 + (400 * (400 + 1) / 2);
            if (mySample.A3 != expectedA3) {
                System.out.println("erorr " + i + " A3 is incorrect");
                break;
            }
        }

        System.out.println("All iterations completed successfully.");
    }
}

class ThreadA extends Thread {
	private Data sample;

	public ThreadA(Data sample) {
		super();
		this.sample = sample;

	}

	public void run() {
		synchronized (sample) {
			int n = 500;
			sample.A1 = n * (n + 1) / 2;
			System.out.println("A1 finished " + sample.A1);
			sample.goFunB2 = true;
			sample.notify();
			// may need break
		}
		synchronized (sample) {
			while (sample.goFunA2 == false) {
				try {
					sample.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Waiting in A2 for B2 to be completed");
				// may need break
			}
			synchronized (sample) {
				int n = 300;
				sample.A2 = sample.B2 + (n * (n + 1) / 2);
				System.out.println("A2 finished " + sample.A2);
				sample.goFunB3 = true;
				sample.notify();
				// may need break
			}
			synchronized (sample) {
				while (sample.goFunA3 == false) {
					try {
						sample.wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Waiting in A3 for B3 to be completed");
					// may need break
				}
			}
			synchronized (sample) {
				int n = 400;
				sample.A3 = sample.B3 + (n * (n + 1) / 2);
				System.out.println("A3 finished " + sample.A3);
				// may need break
			}
		}
	}

}

class ThreadB extends Thread {
	private Data sample;

	public ThreadB(Data sample) {
		super();
		this.sample = sample;

	}

	public void run() {
		synchronized (sample) {
			while (sample.goFunB2 == false) {
				try {
					sample.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Waiting in B2 for A1 to be completed");
				// may need break
			}
		}
		synchronized (sample) {
			int n = 200;
			sample.B2 = sample.A1 + (n * (n + 1) / 2);
			System.out.println("B2 finished " + sample.B2);
			sample.goFunA2 = true;
			sample.notify();
			// may need break
		}
		synchronized (sample) {
			while (sample.goFunB3 == false) {
				try {
					sample.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Waiting in B3 for A2 to be completed");
				// may need break
			}
		}
		synchronized (sample) {
			int n = 400;
			sample.B3 = sample.A2 + (n * (n + 1) / 2);
			System.out.println("B3 finished " + sample.B3);
			sample.goFunA3 = true;
			sample.notify();
			// may need break
		}
	}
}