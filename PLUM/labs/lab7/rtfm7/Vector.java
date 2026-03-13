import java.lang.Math;

public class Vector {
    double x, y, z;
    public Vector (double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector (String s) {
        String[] nums = s.split(" ");
        double x, y, z;
        this.x = Double.parseDouble(nums[0]);
        this.y = Double.parseDouble(nums[1]);
        this.z = Double.parseDouble(nums[2]);
    }

    public void changeX(double x) { this.x = x;}
    public void changeY(double y) { this.y = y;}
    public void changeZ(double z) { this.z = z;}


    public double norm() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    public String toString() {
        return x + " " + y + " " + z;
    }
}
