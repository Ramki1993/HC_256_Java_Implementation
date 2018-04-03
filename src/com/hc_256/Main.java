
package com.hc_256;

public class Main {

	public static void main(String[] args) {
		long key[] = new long[8];
		long iv[] = new long [8];
		long data[] = new long[16];
		double duration, speed;
		long i;
		for( i = 0; i < 8; i++) {
			key[(int) i] = 0;
		}
		for(i = 0; i < 8; i++) {
			iv[(int)i]=0;
		}
		HC256 cipher = new HC256();
		cipher.initialization(key, iv);
		for(i = 0; i < 16; i++) {
			data[(int)i] = 0;
		}
		cipher.encrypt(data);

		long start = System.nanoTime();
		for(i = 0; i < 67108864; i++) {
			cipher.encrypt(data);	
		}
		long finish = System.nanoTime();
		duration = ((double)(finish - start)/ 1000000000.0);
		speed = (((double)i)*32*16)/duration;
		
		System.out.println("Duration: "+ duration);
		System.out.println("Speed: "+ speed);
		
	}


}