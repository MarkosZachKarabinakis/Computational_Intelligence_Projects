public class DataPoint {
    private double x1;
    private double x2;
    private String category;

    public DataPoint(double x1, double x2, String category) {
        this.x1 = x1;
        this.x2 = x2;
        this.category = category;
    }

    public double getX1() {
        return x1;
    }

    public double getX2() {
        return x2;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return x1 + "," + x2 + "," + category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCoordinates(double x1, double x2) {
        this.x1 = x1;
        this.x2 = x2;

    }


}
