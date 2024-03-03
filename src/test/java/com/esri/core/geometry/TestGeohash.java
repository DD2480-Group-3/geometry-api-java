package com.esri.core.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestGeohash {
    
    /**
     * Check if the center of the new envelope is well placed
     */
    @Test
    public void testGeohashToEnvelopeWellCentered(){
        double delta = 0.00000001;

        String geohash1 = "ghgh";

        double lat1 = 72.50976563;
        double lon1 =  -40.60546875;
        Envelope2D env1 = Geohash.geohashToEnvelope(geohash1);
        double centerX1 = (env1.xmax + env1.xmin)*0.5;
        double centerY1 = (env1.ymax + env1.ymin)*0.5;

        assertEquals(lon1, centerX1, delta);
        assertEquals(lat1, centerY1, delta);

        String geohash2 = "p";

        double lat2 = -67.50000000; 
        double lon2 =  157.50000000;
        Envelope2D env2 = Geohash.geohashToEnvelope(geohash2);
        double centerX2 = (env2.xmax + env2.xmin)*0.5;
        double centerY2 = (env2.ymax + env2.ymin)*0.5;

        assertEquals(lon2, centerX2, delta);
        assertEquals(lat2, centerY2, delta);
    }

    /**
     * Check if the dimension of the new envelope is correct for low precision
     */
    @Test
    public void testGeohashToEnvelopeGoodDimensions(){
        double delta = 0.00000001;

        double latDiff = 180/4;
        double lonDiff = 360/8;

        String geohash = "h";

        Envelope2D env = Geohash.geohashToEnvelope(geohash);

        assertEquals(lonDiff, env.xmax-env.xmin, delta);
        assertEquals(latDiff, env.ymax-env.ymin, delta);
    }

    /**
     * Check if the dimension of the new envelope is correct for higher precision
     */
    @Test
    public void testGeohashToEnvelopeGoodDimensions2(){
        double delta = 0.00000001;

        double latDiff = 180.0/32768;
        double lonDiff = 360.0/32768;

        String geohash = "hggggg";

        Envelope2D env = Geohash.geohashToEnvelope(geohash);

        assertEquals(lonDiff, env.xmax-env.xmin, delta);
        assertEquals(latDiff, env.ymax-env.ymin, delta);
    }

    @Test
    public void testToGeohashHasGoodPrecision(){
        Point2D point = new Point2D(18.068581, 59.329323);
        assertEquals(6, Geohash.toGeohash(point, 6).length());
    }

    @Test
    public void testToGeohash(){
        String expected = "u6sce";
        Point2D point = new Point2D(18.068581, 59.329323);
        String geoHash = Geohash.toGeohash(point, 5);

        assertEquals(expected, geoHash);
    }

}
