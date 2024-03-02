package com.esri.core.geometry;


/**
 * Helper class to work with geohash
 */
public class Geohash {
    
    /**
     * Create an evelope from a given geohash
     * @param geoHash
     * @return The envelope that corresponds to the geohash
     */
    public static Envelope2D geohashToEnvelope(String geoHash){
        return null;
    }

    /**
     * Computes the geohash that contains a point at a certain precision
     * @param pt 
     * @param characterLength - The precision of the geohash
     * @return The geohash of containing pt as a String
     */
    public static String toGeohash(Point2D pt, int characterLength){
        return "";
    }

    /**
     * Compute the longest geohash that contains the envelope
     * @param envelope
     * @return the geohash as a string
     */
    public static String containingGeohash(Envelope2D envelope){
        return "";
    }

    /**
     * 
     * @param envelope 
     * @return up to four geohashes that completely cover given envelope
     */
    public static String[] coveringGeohash(Envelope2D envelope){
        return new String[]{};
    }
}
