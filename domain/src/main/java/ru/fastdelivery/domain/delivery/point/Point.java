package ru.fastdelivery.domain.delivery.point;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Point {
    double latitude;
    double longitude;

    static double EARTH_RADIUS = 6372.795;
    static double RADIAN = Math.PI / 180;

    public static double distance(Point point1, Point point2) {
        double lat1 = point1.getLatitude() * RADIAN;
        double lat2 = point2.getLatitude() * RADIAN;
        double lon1 = point1.getLongitude() * RADIAN;
        double lon2 = point2.getLongitude() * RADIAN;
        double cl1 = Math.cos(lat1);
        double cl2 = Math.cos(lat2);
        double sl1 = Math.sin(lat1);
        double sl2 = Math.sin(lat2);
        double delta = lon2 - lon1;
        double cdelta = Math.cos(delta);
        double sdelta = Math.sin(delta);
        double p1 = Math.pow(cl2 * sdelta, 2);
        double p2 = Math.pow((cl1 * sl2) - (sl1 * cl2 * cdelta), 2);
        double p3 = Math.sqrt(p1 + p2);
        double p4 = sl1 * sl2;
        double p5 = cl1 * cl2 * cdelta;
        double p6 = p4 + p5;
        double angle = Math.atan2(p3, p6);
        return angle * EARTH_RADIUS;
    }
}
