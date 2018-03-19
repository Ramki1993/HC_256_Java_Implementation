package com.hc_256;

public class HC256 {
	public long P[], Q[], X[], Y[], counter2048;
	HC256(){
		P =  new long[1024];
		Q =  new long[1024];
		X =  new long[16];
		Y =  new long[16];
		
	}

	public void initialization(long key[], long iv[])
	{
		int i,j;
		
		//expand the key and iv into P and Q
		for(i = 0; i < 8; i++) P[i] = key[i];
		for(i = 8; i < 16; i++) P[i] = iv[i-8];
		
		for(i=16; i < 528; i++)
			P[i] = f(P[i-2],P[i-7],P[i-15],P[i-216])+i;
		for (i = 0; i < 16; i++)
			P[i] = P[i+512];
		for (i = 16; i < 1024; i++)
			P[i] = f(P[i-2],P[i-7],P[i-15],P[i-16])+512+i;
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
		
		//initialize counter2048, and tables X and Y
		counter2048 = 0;
		for (i = 0; i < 16; i++) X[i] = P[1008+i];
		for (i = 0; i < 16; i++) Y[i] = Q[1008+i];
		
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
	
	
}
