/******************************************************************************
 *
 * Copyright (C) 1998 Logi Ragnarsson
 *
 * Adapted 1999 for use in MindTerm by Mats Andersson (mats@mindbright.se)
 * This class is the RandomSpinner class of the Cryptonite library found at:
 *     <http://www.hi.is/~logir/cryptonite/>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 *****************************************************************************
 * $Author: mats $
 * $Date: 1999/01/31 19:00:36 $
 * $Name: rel0-98-4 $
 *****************************************************************************/
//package mindbright.security;
package se.modlab.generics.crypto;

import java.util.Random;

/**
 * This class uses the scheduler to generate random numbers. It counts the
 * number of times a loop is repeated before a thread has slept for a
 * specified number of milliseconds. These numbers are then fed to a hash
 * function to mask any possible correlations.
 * <p>
 * This generator is somewhat slower than the SecureRandom generator in the
 * java library, since it spends some time collecting entropy.
 * <p>
 * The <a href="http://www.cs.berkeley.edu/~daw/java-spinner2">helper class</a>
 * which does the actual number generation is by
 * Henry Strickland (<a href="strix@versant.com">strix@versant.com</a>) and
 * Greg Noel (<a href="greg@qualcomm.com">greg@qualcomm.com</a>). It is based on
 * <a href="ftp://ftp.research.att.com/dist/mab/librand.shar">similar C code</a>
 * by Matt Blaze, Jack Lacy, and Don Mitchell.
 *
 * @author <a href="http://www.hi.is/~logir/">Logi Ragnarsson</a> (<a href="mailto:logir@hi.is">logir@hi.is</a>)
 */
public class SecureRandom extends Random {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MD5State.SubState ss = new MD5State.SubState();
    private int t;

    public static int secureLevel = 0;

    public SecureRandom() {
        t=Spinner.guessTime(1024);

	int paranoia = ((secureLevel > 0) ? 2 : 1);

	for(int i=0; i<paranoia; i++) {
            // Estimate about 4 bits of entropy per call to spinner
            for(int j=ss.buffer.length-1; j>=0; j--) {
                // Fill the buffer with spin-counts from the Spinner class.
                ss.buffer[j] = (byte)Spinner.spin(t);
		if(secureLevel < 2)
		    ss.buffer[--j] = (byte)System.currentTimeMillis();
	    }
            ss.transform(ss.buffer,0);
	}
        unused=new byte[16];
        unusedPos=16;
    }

    public SecureRandom(byte[] seed) {
	try {
	    MD5State md5 = new MD5State();
	    md5.update(seed);
	    ss = md5.state;
	} catch (Exception e) {
	    // !!!
	    System.out.println("Can't operate, MD5 not available...");
	}
        t = Spinner.guessTime(1024);
        unused=new byte[16];
        unusedPos=16;
    }

    /** unused[unusedPos..15] is unused pseudo-random numbers. */
    byte[] unused;
    int unusedPos;

    int poolSweep=0;

    /** Get new unused bytes. */
    private void update() {
        // Inject entropy into the pool
	//
	if(secureLevel > 1) {
	    ss.buffer[poolSweep++] += Spinner.spin(t) + 1;
	    ss.buffer[poolSweep++] += Spinner.spin(t) + 1;
	} else {
	    ss.buffer[poolSweep++] += Spinner.bogusSpin();
	    ss.buffer[poolSweep]   += ss.buffer[poolSweep - 1];
	}
	poolSweep++;

        poolSweep %= 64;

        ss.transform(ss.buffer,0);
        writeBytes(ss.hash[0], unused, 0,4);
        writeBytes(ss.hash[1], unused, 4,4);
        writeBytes(ss.hash[2], unused, 8,4);
        writeBytes(ss.hash[3], unused,12,4);
        unusedPos=0;
    }
    
    /** Generates the next random number. */
    protected synchronized int next(int bits){
        //System.out.println(bits);
        if(unusedPos==16)
            update();
        int r=0;
        for(int b=0; b<bits; b+=8)
            r = (r<<8) + unused[unusedPos++];
        return r;
    }

    public static final void writeBytes(long a, byte[] dest, int i, int length){
        for (int j=i+length-1; j>=i; j--){
            dest[j]=(byte)a;
            a = a >>> 8;
        }
    }

}
