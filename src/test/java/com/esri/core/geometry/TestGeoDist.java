package com.esri.core.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestGeoDist {
    
    @Test
    public void testGeodesicDistanceNgsAzimuth(){

        Point pt_0 = new Point(10, 10);
		Point pt_1 = new Point(20, 20);
        double a = 6378137.0; // radius of spheroid for WGS_1984
		double e = 0;
		double rpu = Math.PI / 180.0;
        
        PeDouble a_az12 = new PeDouble();
        PeDouble a_az21 = new PeDouble();

        // value from https://www.omnicalculator.com/other/azimuth
        PeDouble true_az12 = new PeDouble(0.7472);
        PeDouble true_az21 = new PeDouble(-2.3491);

        GeoDist.geodesic_distance_ngs(a, e, pt_0.getX() * rpu,
        pt_0.getY() * rpu, pt_1.getX() * rpu, pt_1.getY()
                * rpu, null, a_az12, a_az21);
        
        assertTrue(
            "The computed azimuth from p1 to p2 should be close to 0.7472",
            Math.abs(a_az12.val - true_az12.val) < 0.001
        );
        assertTrue(
            "The computed azimuth from p2 to p1 should be close to -2.3491", 
            Math.abs(a_az21.val - true_az21.val) < 0.001
        );

    }

    @Test
    public void testGeodesicDistanceNgsWithEQPoints(){
        Point pt_0 = new Point(20, 20);
		Point pt_1 = new Point(20, 20);
        double a = 6378137.0; // radius of spheroid for WGS_1984
		double e = 0;
		double rpu = Math.PI / 180.0;
        
        PeDouble a_dist = new PeDouble(1);
        PeDouble a_az12 = new PeDouble(1);
        PeDouble a_az21 = new PeDouble(1);

        GeoDist.geodesic_distance_ngs(a, e, pt_0.getX() * rpu,
        pt_0.getY() * rpu, pt_1.getX() * rpu, pt_1.getY()
                * rpu, null, null, a_az21);
        
        GeoDist.geodesic_distance_ngs(a, e, pt_0.getX() * rpu,
        pt_0.getY() * rpu, pt_1.getX() * rpu, pt_1.getY()
                * rpu, a_dist, null, null);
        
        GeoDist.geodesic_distance_ngs(a, e, pt_0.getX() * rpu,
        pt_0.getY() * rpu, pt_1.getX() * rpu, pt_1.getY()
                * rpu, null, a_az12, null);

        assertEquals(0, a_dist.val, 0);
        assertEquals(0, a_az12.val, 0);
        assertEquals(0, a_az21.val, 0);

    }
}
