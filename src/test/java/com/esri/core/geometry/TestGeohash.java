package com.esri.core.geometry;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestGeohash {

  /**
   * Check if the center of the new envelope is well placed
   */
  @Test
  public void testGeohashToEnvelopeWellCentered() {
    double delta = 0.00000001;

    String geohash1 = "ghgh";

    double lat1 = 72.50976563;
    double lon1 = -40.60546875;
    Envelope2D env1 = Geohash.geohashToEnvelope(geohash1);
    double centerX1 = (env1.xmax + env1.xmin) * 0.5;
    double centerY1 = (env1.ymax + env1.ymin) * 0.5;

    assertEquals(lon1, centerX1, delta);
    assertEquals(lat1, centerY1, delta);

    String geohash2 = "p";

    double lat2 = -67.50000000;
    double lon2 = 157.50000000;
    Envelope2D env2 = Geohash.geohashToEnvelope(geohash2);
    double centerX2 = (env2.xmax + env2.xmin) * 0.5;
    double centerY2 = (env2.ymax + env2.ymin) * 0.5;

    assertEquals(lon2, centerX2, delta);
    assertEquals(lat2, centerY2, delta);
  }

  @Test
  public void testGeohashToEnvelopeWithLongGeohashes(){
    double delta = 0.00000001;
    double lon1 = -117.16176850225;
    double lat1 = 34.01274730565;
    
    String geohash1 = "9qh9mzv6sgtkwz34";
		Envelope2D env1 = Geohash.geohashToEnvelope(geohash1);
    double centerX1 = (env1.xmax + env1.xmin) * 0.5;
    double centerY1 = (env1.ymax + env1.ymin) * 0.5;
    assertEquals(lon1, centerX1, delta);
    assertEquals(lat1, centerY1, delta);
  }

  /**
   * Check if the dimension of the new envelope is correct for low precision
   */
  @Test
  public void testGeohashToEnvelopeGoodDimensions() {
    double delta = 0.00000001;

    double latDiff = 180 / 4;
    double lonDiff = 360 / 8;

    String geohash = "h";

    Envelope2D env = Geohash.geohashToEnvelope(geohash);

    assertEquals(lonDiff, env.xmax - env.xmin, delta);
    assertEquals(latDiff, env.ymax - env.ymin, delta);
  }

  /**
   * Check if the dimension of the new envelope is correct for higher precision
   */
  @Test
  public void testGeohashToEnvelopeGoodDimensions2() {
    double delta = 0.00000001;

    double latDiff = 180.0 / 32768;
    double lonDiff = 360.0 / 32768;

    String geohash = "hggggg";

    Envelope2D env = Geohash.geohashToEnvelope(geohash);

    assertEquals(lonDiff, env.xmax - env.xmin, delta);
    assertEquals(latDiff, env.ymax - env.ymin, delta);
  }

  /**
   * Check if BinaryToBase32 work as intended with a normal binary string
   */
  @Test
  public void testBinaryToBase32() {
    String testStr = "011011111111011";
    assertEquals("ezv", Geohash.binaryToBase32(testStr));
  }

  @Test
  public void testConvertToBinary() {
    double lat = 40.7128;
    double lon = -74.0060;
    String latStr = Geohash.convertToBinary(lat, new double[] { -90, 90 }, 10);
    String lonStr = Geohash.convertToBinary(
      lon,
      new double[] { -180, 180 },
      10
    );

    assertEquals("1011100111", latStr);
    assertEquals("0100101101", lonStr);
  }

  @Test
  public void testToGeoHash() {
    Point2D p1 = new Point2D(-4.329,48.669);
    Point2D p2 = new Point2D(-30.382, 70.273);
    Point2D p3 = new Point2D(14.276, 37.691);

    int chrLen = 5;

    String p1Hash = Geohash.toGeohash(p1, chrLen);
    String p2Hash = Geohash.toGeohash(p2, chrLen);
    String p3Hash = Geohash.toGeohash(p3, chrLen);

    assertEquals("gbsuv", p1Hash);
    assertEquals("gk6ru", p2Hash);
    assertEquals("sqdnk", p3Hash);
  }
    @Test
    public void testToGeohashHasGoodPrecision(){
        Point2D point = new Point2D(18.068581, 59.329323);
        assertEquals(6, Geohash.toGeohash(point, 6).length());
    }

    @Test
    public void testToGeohash2(){
        String expected = "u6sce";
        Point2D point = new Point2D(18.068581, 59.329323);
        String geoHash = Geohash.toGeohash(point, 5);

        assertEquals(expected, geoHash);
    }

    @Test
    public void testContainingGeohashWithHugeValues(){
        Envelope2D envelope = new Envelope2D(-179, -89, 179, 89);
        assertEquals("", Geohash.containingGeohash(envelope));
    }

    @Test
    public void testContainingGeohash(){
        Envelope2D envelope = new Envelope2D(-179, -89, -140, -50);
        assertEquals("0", Geohash.containingGeohash(envelope));
    }

    @Test
    public void testContainingGeohash2(){
        Envelope2D envelope = new Envelope2D(18.078, 59.3564, 18.1,59.3344);
        assertEquals("u6sce", Geohash.containingGeohash(envelope));
    }
    

}
