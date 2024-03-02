package com.esri.core.geometry;

import java.security.InvalidParameterException;

/**
 * Helper class to work with geohash
 */
public class Geohash {

  private static final String base32 = "0123456789bcdefghjkmnpqrstuvwxyz";

  private static final String INVALID_CHARACTER_MESSAGE =
    "Invalid character in geohash: ";
  private static final String GEOHASH_EXCEED_MAX_PRECISION_MESSAGE =
    "Precision to high in geohash (max 24)";

  /**
   * Create an evelope from a given geohash
   * @param geoHash
   * @return The envelope that corresponds to the geohash
   * @throws InvalidParameterException if the precision of geoHash is greater than 24 characters
   */
  public static Envelope2D geohashToEnvelope(String geoHash) {
    if (geoHash.length() > 24) {
      throw new InvalidParameterException(GEOHASH_EXCEED_MAX_PRECISION_MESSAGE);
    }

    long latBits = 0;
    long lonBits = 0;
    for (int i = 0; i < geoHash.length(); i++) {
      int pos = base32.indexOf(geoHash.charAt(i));
      if (pos == -1) {
        throw new InvalidParameterException(
          new StringBuilder(INVALID_CHARACTER_MESSAGE)
            .append('\'')
            .append(geoHash.charAt(i))
            .append('\'')
            .toString()
        );
      }

      if (i % 2 == 0) {
        lonBits =
          (lonBits << 3) | ((pos >> 2) & 4) | ((pos >> 1) & 2) | (pos & 1);
        latBits = (latBits << 2) | ((pos >> 2) & 2) | ((pos >> 1) & 1);
      } else {
        latBits =
          (latBits << 3) | ((pos >> 2) & 4) | ((pos >> 1) & 2) | (pos & 1);
        lonBits = (lonBits << 2) | ((pos >> 2) & 2) | ((pos >> 1) & 1);
      }
    }

    int lonBitsSize = (int) Math.ceil(geoHash.length() * 5 / 2.0);
    int latBitsSize = geoHash.length() * 5 - lonBitsSize;

    double lat = -90;
    double latPrecision = 90;
    for (int i = 0; i < latBitsSize; i++) {
      if (((1 << (latBitsSize - 1 - i)) & latBits) != 0) {
        lat += latPrecision;
      }
      latPrecision /= 2;
    }

    double lon = -180;
    double lonPrecision = 180;
    for (int i = 0; i < lonBitsSize; i++) {
      if (((1 << (lonBitsSize - 1 - i)) & lonBits) != 0) {
        lon += lonPrecision;
      }
      lonPrecision /= 2;
    }

    return new Envelope2D(
      lon,
      lat,
      lon + lonPrecision * 2,
      lat + latPrecision * 2
    );
  }

  /**
   * Computes the geohash that contains a point at a certain precision
   * @param pt A point represented as lat/long pair
   * @param characterLength - The precision of the geohash
   * @return The geohash of containing pt as a String
   */
  public static String toGeohash(Point2D pt, int characterLength) {
    if (characterLength < 1) {
      throw new InvalidParameterException(
        "CharacterLength cannot be less than 1"
      );
    }
    int precision = characterLength * 5;
    double lat = pt.x;
    double lon = pt.y;

    String latBitStr = Geohash.convertToBinary(
      lat,
      new double[] { -90, 90 },
      precision
    );

    String lonBitStr = Geohash.convertToBinary(
      lon,
      new double[] { -180, 180 },
      precision
    );

    StringBuilder interwovenBin = new StringBuilder();
    for (int i = 0; i < latBitStr.length(); i++) {
      interwovenBin.append(lonBitStr.charAt(i));
      interwovenBin.append(latBitStr.charAt(i));
    }

    return Geohash
      .binaryToBase32(interwovenBin.toString())
      .substring(0, characterLength);
  }

  /**
   * Computes the base32 value of the binary string given
   * @param binStr Binary string with "0" || "1" that is to be converted to a base32 string
   * @return base32 string of the binStr in chunks of 5 binary digits
   */

  public static String binaryToBase32(String binStr) {
    StringBuilder base32Str = new StringBuilder();
    for (int i = 0; i < binStr.length(); i += 5) {
      String chunk = binStr.substring(i, i + 5);
      int decVal = Integer.valueOf(chunk, 2);
      base32Str.append(base32.charAt(decVal));
    }
    return base32Str.toString();
  }

  /**
   * Converts the value given to a binary string with the given precision and range
   * @param value The value to be converted to a binString
   * @param r The range at which the value is to be compared with
   * @param precision The Precision (number of bits) that the binary string needs
   * @return A binary string representation of the value with the given range and precision
   */

  public static String convertToBinary(
    double value,
    double[] r,
    int precision
  ) {
    StringBuilder binString = new StringBuilder();
    for (int i = 0; i < precision; i++) {
      double mid = (r[0] + r[1]) / 2;
      if (value >= mid) {
        binString.append("1");
        r[0] = mid;
      } else {
        binString.append("0");
        r[1] = mid;
      }
    }
    return binString.toString();
  }

  /**
   * Compute the longest geohash that contains the envelope
   * @param envelope
   * @return the geohash as a string
   */
  public static String containingGeohash(Envelope2D envelope) {
    return "";
  }

  /**
   *
   * @param envelope
   * @return up to four geohashes that completely cover given envelope
   */
  public static String[] coveringGeohash(Envelope2D envelope) {
    return new String[] {};
  }
}
