package com.ston.spring.webflux.microservice.util;

public class SleepUtil {

	public static void sleepSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}
	}
}
