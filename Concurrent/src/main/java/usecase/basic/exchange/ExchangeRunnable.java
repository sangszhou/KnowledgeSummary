package usecase.basic.exchange;

import java.util.concurrent.Exchanger;

public class ExchangeRunnable implements Runnable {

	Exchanger exchanger = null;
	Object exchanged = null;

	public ExchangeRunnable(Exchanger exhange, Object exchanged) {
		this.exchanger = exhange;
		this.exchanged = exchanged;
	}

	public void run() {
		// TODO Auto-generated method stub
		Object previous = this.exchanged;

		try {
			this.exchanged = this.exchanger.exchange(previous);
			System.out.println(Thread.currentThread().getName() + "  exchanged, get " + this.exchanged);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
