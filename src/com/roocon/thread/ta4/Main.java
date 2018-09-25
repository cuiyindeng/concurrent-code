package com.roocon.thread.ta4;

public class Main {

	private int value;
	private MyLock2 lock = new MyLock2();

	public int next() {
		lock.lock();

		try {
			Thread.sleep(300);
			return value++;
		} catch (InterruptedException e) {
			throw new RuntimeException();
		} finally {
			lock.unlock();
		}
	}
	
	public void a() {
		lock.lock();
		System.out.println("a");
		b();
		lock.unlock();
	}
	
	public void b() {
		lock.lock();
		System.out.println("b");
		lock.unlock();
	}

	public static void main(String[] args) {

		Main m = new Main();

		new Thread(new Runnable() {

			@Override
			public void run() {
				m.a();
			}
		}).start();

		for (int i = 0; i < 20; i++) {
		    /*
		    author:cyd
			Thread-2:-----enq is running
			Thread-2:-----enq is running
			//说明队列为空时，需要初始化空的Node。
			Thread-2:------acquireQueued is running
			Thread-2:------before wait status is 0
			Thread-2:------after wait status is -1
			Thread-2:------acquireQueued is running
			Thread-2:------before wait status is -1
			Thread-2:------is park
			Thread-6:------acquireQueued is running
			Thread-6:------before wait status is 0
			Thread-6:------after wait status is -1
			Thread-6:------acquireQueued is running
			Thread-6:------before wait status is -1
			Thread-6:------is park
			Thread-3:------acquireQueued is running
			Thread-3:------before wait status is 0
			Thread-3:------after wait status is -1
			Thread-3:------acquireQueued is running
			Thread-3:------before wait status is -1
			Thread-3:------is park
			Thread-7:------acquireQueued is running
			Thread-7:------before wait status is 0
			Thread-7:------after wait status is -1
			Thread-7:------acquireQueued is running
			Thread-7:------before wait status is -1
			Thread-7:------is park
			//说明每个节点都在自旋尝试获取同步状态，
			未获取到时，会将自己的waitStatus设为SIGNAL，然后阻塞（park）该节点中的线程。
            ...
            Thread-2:------acquireQueued is running
			Thread-2:------is unpark
			Thread-6:------acquireQueued is running
			1
			Thread-6:------is unpark
			Thread-3:------acquireQueued is running
			2
			Thread-3:-----is unpark
			Thread-7:------acquireQueued is running
			3
			Thread-7:------is unpark
            说明唤醒（unpark）线程后正确执行打印语句；并且唤醒（unpark）的线程与阻塞（park）的线程相对应，遵循FIFO的原则。
		     */
			new Thread(new Runnable() {
				@Override
				public void run() {
                    System.out.println(m.next());
				}
			}).start();
		}
	}

}
