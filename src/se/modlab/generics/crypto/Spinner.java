/******************************************************************************
 *
 * Copyright (C) 1998 Logi Ragnarsson
 *
 * Adapted 1999 for use in MindTerm by Mats Andersson (mats@mindbright.se)
 * This class is the Spinner class of the Cryptonite library found at:
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
 * $Date: 1999/01/10 15:15:35 $
 * $Name: rel0-98-4 $
 *****************************************************************************/
//package mindbright.security;
package se.modlab.generics.crypto;

/** Helper class for the RandomSpinner (SecureRandom) class. */
public class Spinner extends Thread {

    /** Return the number of spins performed in t milliseconds. */
    public static int spin(long t) {
        int counter = 0;
        Thread s = new Spinner(t);
        s.start();
        do {
            ++counter;
            Thread.yield();
        } while (s.isAlive());
        return counter;
    }

    /* This one is completely bogus, but after the initial seeding we trust the
       milliseconds to be "random-enough"...
    */
    public static int bogusSpin() {
	Runtime rt = Runtime.getRuntime();
	int bogus;
	Thread.yield();
	rt.gc();
	bogus = ((int)System.currentTimeMillis() & 0xff);
	return bogus;
    }
    
    private long t;
    
    private Spinner(long t) {
        this.t=t;
    }
    
    public void run() {
        try {
            Thread.sleep(t);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Returns t such that spin(t) is larger than n. This value may change as
     * the load of the system changes.
     */
    public static int guessTime(int n) {
        int t=5;
        while(spin(t)<n)
            t=(t*3)/2;
        return t;
    }

}
