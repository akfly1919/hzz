package com.akfly.hzz.util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomGenUtils {

	/**
	 * This will generates a random integer between min (inclusive) and max (inclusive).
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumberInRange(int min, int max) {
    	// nextInt is normally exclusive of the top value,
    	// so add 1 to make it inclusive
    	return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static String getRandomUUID() {
		return UUID.randomUUID().toString();
	}

	public static String genFlowNo(String prefix) {
		String dateInfo = DateUtil.getCurrentDate(DateUtil.FORMAT_FULLTIME);
		return prefix+dateInfo+ getRandomNumberInRange(100000,999999);
	}
	public static void main(String[] args) {
		int x = RandomGenUtils.getRandomNumberInRange(100000, 999999);
		System.out.println(x);
	}
}
