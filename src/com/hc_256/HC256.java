package com.hc_256;

public class HC256 {
	public long P[], Q[], X[], Y[], counter2048;
	HC256(){
		P =  new long[1024];
		Q =  new long[1024];
		X =  new long[16];
		Y =  new long[16];
		
	}

	public long h1(long x, long y){
		short a,b,c,d;
		a = u8((short)x);

		b = u8((short)(x >> 8));

		c = u8((short)(x >> 16));

		d = u8((short)(x >> 24));

		y = Q[a]+Q[256+b]+Q[512+c]+Q[768+d];
		return y;
	}
	public long h2(long x, long y){
		short a,b,c,d;
		a = u8((short)x);

		b = u8((short)(x >> 8));

		c = u8((short)(x >> 16));

		d = u8((short)(x >> 24));

		y = P[a] + P[256+b] + P[512 + c] + P[768+d];
		return y;
	}
	public long step_A(long u, long v, long a, long b, long c, long d, long m) {
		long tem0, tem1, tem2, tem3 = 0;
		tem0 = rotr(v,23);
		tem1 = rotr(c,10);
		tem2 = ((v ^ c) & 0x3ff) ;

		P[(int)u] += b + (tem0^tem1) + Q[(int)tem2];
		X[(int)a] = P[(int)u];
		h1(d,tem3);
		m ^= tem3 ^ P[(int)u];
		return m;
		
	}
	public long step_B(long u, long v, long a, long b, long c, long d, long m) {
		long tem0, tem1, tem2, tem3 = 0;
		tem0 = rotr(v,23);
		tem1 = rotr(c,10);
		tem2 = ((v ^ c) & 0x3ff);

		Q[(int)u] += b + (tem0^tem1) + P[(int)tem2];
		Y[(int)a] = Q[(int)u];
		h2(d,tem3);
		m ^= tem3 ^ Q[(int)u];
		return m;
		
	}
	long[] encrypt(long data[]) { //each time it encrypts 512-bit data
		long cc,dd; 
		cc = (counter2048 & 0x3ff); 
		dd = ((cc+16)&0x3ff);
		if (counter2048 < 1024) {
			counter2048 = (counter2048 + 16) & 0x7ff; 
			data[0] = step_A((int)(cc), P[(int)(cc+1)], 0, X[6], X[13],X[4], data[0]);
			data[1] = step_A((int)(cc+1), P[(int)(cc+2)], 1, X[7], X[14],X[5], data[1]);
			data[2] = step_A((int)(cc+2), P[(int)(cc+3)], 2, X[8], X[15],X[6], data[2]);
			data[3] = step_A((int)(cc+3), P[(int)(cc+4)], 3, X[9], X[0], X[7], data[3]); 
			data[4] = step_A((int)(cc+4), P[(int)(cc+5)], 4, X[10],X[1], X[8], data[4]);
			data[5] = step_A((int)(cc+5), P[(int)(cc+6)], 5, X[11],X[2], X[9], data[5]);
			data[6] = step_A((int)(cc+6), P[(int)(cc+7)], 6, X[12],X[3], X[10],data[6]); 
			data[7] = step_A((int)(cc+7), P[(int)(cc+8)], 7, X[13],X[4], X[11],data[7]); 
			data[8] = step_A((int)(cc+8), P[(int)(cc+9)], 8, X[14],X[5], X[12],data[8]); 
			data[9] = step_A((int)(cc+9), P[(int)(cc+10)],9, X[15],X[6], X[13],data[9]); 
			data[10] = step_A((int)(cc+10),P[(int)(cc+11)],10,X[0], X[7], X[14],data[10]);
			data[11] = step_A((int)(cc+11),P[(int)(cc+12)],11,X[1], X[8], X[15],data[11]);
			data[12] = step_A((int)(cc+12),P[(int)(cc+13)],12,X[2], X[9], X[0], data[12]); 
			data[13] = step_A((int)(cc+13),P[(int)(cc+14)],13,X[3], X[10],X[1], data[13]);
			data[14] = step_A((int)(cc+14),P[(int)(cc+15)],14,X[4], X[11],X[2], data[14]); 
			data[15] = step_A((int)(cc+15),P[(int)(dd)], 15,X[5], X[12],X[3], data[15]);
		}
		else {
			counter2048 = (counter2048 + 16) & 0x7ff; 
			data[0] = step_B((int)(cc), Q[(int)(cc+1)], 0, Y[6], Y[13],Y[4], data[0]); 
			data[1] = step_B((int)(cc+1), Q[(int)(cc+2)], 1, Y[7], Y[14],Y[5], data[1]); 
			data[2] = step_B((int)(cc+2), Q[(int)(cc+3)], 2, Y[8], Y[15],Y[6], data[2]); 
			data[3] = step_B((int)(cc+3), Q[(int)(cc+4)], 3, Y[9], Y[0], Y[7], data[3]);
			data[4] = step_B((int)(cc+4), Q[(int)(cc+5)], 4, Y[10],Y[1], Y[8], data[4]); 
			data[5] = step_B((int)(cc+5), Q[(int)(cc+6)], 5, Y[11],Y[2], Y[9], data[5]);
			data[6] = step_B((int)(cc+6), Q[(int)(cc+7)], 6, Y[12],Y[3], Y[10],data[6]);
			data[7] = step_B((int)(cc+7), Q[(int)(cc+8)], 7, Y[13],Y[4], Y[11],data[7]); 
			data[8] = step_B((int)(cc+8), Q[(int)(cc+9)], 8, Y[14],Y[5], Y[12],data[8]); 
			data[9] = step_B((int)(cc+9), Q[(int)(cc+10)],9, Y[15],Y[6], Y[13],data[9]); 
			data[10] = step_B((int)(cc+10),Q[(int)(cc+11)],10,Y[0], Y[7], Y[14],data[10]); 
			data[11] = step_B((int)(cc+11),Q[(int)(cc+12)],11,Y[1], Y[8], Y[15],data[11]); 
			data[12] = step_B((int)(cc+12),Q[(int)(cc+13)],12,Y[2], Y[9], Y[0], data[12]);
			data[13] = step_B((int)(cc+13),Q[(int)(cc+14)],13,Y[3], Y[10],Y[1], data[13]); 
			data[14] = step_B((int)(cc+14),Q[(int)(cc+15)],14,Y[4], Y[11],Y[2], data[14]);
			data[15] = step_B((int)(cc+15),Q[(int)(dd)], 15,Y[5], Y[12],Y[3], data[15]);
		
		}
		return data;
	}


	public void initialization(long key[], long iv[])
	{
		int i,j;
		
		//expand the key and iv into P and Q
		for(i = 0; i < 8; i++) P[i] = key[i];
		for(i = 8; i < 16; i++) P[i] = iv[i-8];
		
		for(i=16; i < 528; i++) {
			P[i] = f(P[i-2],P[i-7],P[i-15],P[i-16])+i;
			
		}
		for (i = 0; i < 16; i++) {
			P[i] = P[i+512];
			
		}
		for (i = 16; i < 1024; i++) {
			P[i] = f(P[i-2],P[i-7],P[i-15],P[i-16])+512+i;
			
		}
		for (i = 0; i < 16; i++)
			Q[i] = P[1024-16+i];
		for (i = 16; i < 32; i++)
			Q[i] = f(Q[i-2],Q[i-7],Q[i-15],Q[i-16])+1520+i;
		for (i = 0; i < 16; i++)
			Q[i] = Q[i+16];
		for (i = 16; i < 1024;i++)
			Q[i] = f(Q[i-2],Q[i-7],Q[i-15],Q[i-16])+1536+i;
		
		//run the cipher 4096 steps without generating output
		for (i = 0; i < 2; i++) {
			for (j = 0; j < 10; j++)
				feedback_1(P[j],P[j+1],P[(j-10)&0x3ff],P[(j-3)&0x3ff]);
			for (j = 10; j < 1023; j++)
				feedback_1(P[j],P[j+1],P[j-10],P[j-3]);
				feedback_1(P[1023],P[0],P[1013],P[1020]);
			for (j = 0; j < 10; j++)
				feedback_2(Q[j],Q[j+1],Q[(j-10)&0x3ff],Q[(j-3)&0x3ff]);
			for (j = 10; j < 1023; j++)
				feedback_2(Q[j],Q[j+1],Q[j-10],Q[j-3]);
				feedback_2(Q[1023],Q[0],Q[1013],Q[1020]);
		}
		for(i = 0; i < 4; i++) {
		System.out.println(Long.toHexString(u32(P[i])));
		}
		for(i = 0; i < 4; i++) {
		System.out.println(Long.toHexString(u32(Q[i])));
		}


		
		//initialize counter2048, and tables X and Y
		counter2048 = 0;
		for (i = 0; i < 16; i++) {
			X[i] = P[1008+i];

		}	
		for (i = 0; i < 16; i++) {
			Y[i] = Q[1008+i];

		}
		
	}
	
	
	
	public long rotr(long x, long n) {
		return (((x)>>(n))|((x)<<(32-(n))));
	}
	
	public long f1(long x) {
		return  (rotr((x),7) ^ rotr((x),18) ^ ((x) >> 3));
	}
	public long f2(long x) {
		return (rotr((x),17) ^ rotr((x),19) ^ ((x) >> 10));
	}
	
	public long f(long a, long b, long c, long d) {
		return (f2((a)) + (b) + f1((c)) + (d));
	}
	
	public void feedback_1(long u,long v,long b,long c) {
		long tem0,tem1,tem2; 
		tem0 = rotr((v),23); tem1 = rotr((c),10); 
		tem2 = ((v) ^ (c)) & 0x3ff; 
		(u) += (b)+(tem0^tem1)+Q[(int)tem2]; //please check if type casting ???
		}
	
	public void  feedback_2(long u,long v,long b, long c) { 
		long tem0,tem1,tem2; 
		tem0 = rotr((v),23); tem1 = rotr((c),10); 
		tem2 = ((v) ^ (c)) & 0x3ff; 
		(u) += (b)+(tem0^tem1)+P[(int)tem2]; //please check if type casting ???
		}
	public short u8(short x) {
		return (short) ((x | 0xff00)^0xff00);
	}
	public long u32(long x) {
		return (long) ((x|0xffff0000)^0xffff0000);
	}

	

	
}
