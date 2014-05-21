/******************************************************************************
 *
 * Copyright (C) 1998 Logi Ragnarsson
 *
 * Adapted 1999 for use in MindTerm by Mats Andersson (mats@mindbright.se)
 * This class is the HashState class of the Cryptonite library found at:
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
 * $Date: 1999/01/10 15:15:34 $
 * $Name: rel0-98-4 $
 *****************************************************************************/
//package mindbright.security;
package se.modlab.generics.crypto;

/**
 * An object of this class holds the status of a fingerprint still being
 * calculated.
 * <p>
 * A fingerprint state object can be repeatedly updated with data. At
 * any time a 16-byte digest can be requested for the data that has
 * then been added to the fingerprint state.
 * 
 * @see is.hi.logir.cryptonite.hash.SHA1State
 * @see is.hi.logir.cryptonite.sign.Signature
 *
 * @author <a href="http://www.hi.is/~logir/">Logi Ragnarsson</a> (<a href="mailto:logir@hi.is">logir@hi.is</a>)
 */
public abstract class MessageDigest {
    
    // FINGERPRINT CLASS LIBRARIAN
    public static MessageDigest getInstance(String algorithm)
	throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
	Class<?> c;
	c = Class.forName("mindbright.security." + algorithm + "State");
	return (MessageDigest)c.newInstance();
    }
    
    // INSTANCE METHODS

    /** Return the name of the algorithm used by this HashState object. */
    public abstract String getName();
    
    /** Reset the state. */
    public abstract void reset();
    
    /**
     * Update the fingerprint state with the bytes from
     * <code>buf[offset, offset+length-1]</code>. */
    public abstract void update(byte[] buf, int offset, int length);
    
    /** Update the fingerprint state with the bytes from <code>buf</code>. */
    public void update(byte[] buf){
        update(buf, 0, buf.length);
    }
    
    /** Update the fingerprint state with the characters from <code>s</code>. */
    public void update(String s){
        update(s.getBytes());
    }
    
    /**
     * Return a digest for the curret state, without
     * destroying the state. */
    public abstract byte[] digest();
    
    /**
     * Return the number of bytes needed to make a valid hash. If a multiple
     * of this number of bytes is hashed, no padding is needed. If no such
     * value exists, returns 0. */
    public abstract int blockSize();
    
    /**
     * Returns the size of a fingerprint in bytes. */
    public abstract int hashSize();

}
