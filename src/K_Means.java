import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;

public class K_Means {
    ArrayList<DataPoint> DataPoints = new ArrayList<>();
    ArrayList<DataPoint> ClassRepresentatives = new ArrayList<>();
    int numberOfClassRepresentatives;

    public K_Means(int numberOfClassRepresentatives) {
        this.numberOfClassRepresentatives = numberOfClassRepresentatives;
    }

    public ArrayList<DataPoint> getDataPoints(String filePath) {
        // Clear the DataPoints list to ensure it doesn't retain any previous data
        DataPoints.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read the first line


            while ((line = br.readLine()) != null) {
                // Split the line into columns
                String[] values = line.split(",");

                // Parse values to create a DataPoint object
                double x1 = Double.parseDouble(values[0]);
                double x2 = Double.parseDouble(values[1]);
                String category = null; // could be null???

                // Create a new DataPoint object and add it to the list
                DataPoints.add(new DataPoint(x1, x2, category));


            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while parsing a number: " + e.getMessage());
        }

        return DataPoints;
    }

    public double euclidianDistance(DataPoint a, DataPoint b) {
        double a_xComponent = a.getX1();
        double a_yComponent = a.getX2();

        double b_xComponent = b.getX1();
        double b_yComponent = b.getX2();

        double X_coordinate = Math.abs(a_xComponent - b_xComponent);
        double Y_coordinate = Math.abs(a_yComponent - b_yComponent);
        return Math.sqrt(X_coordinate * X_coordinate + Y_coordinate * Y_coordinate);


    }

    public ArrayList<DataPoint> pickRandomPoints(ArrayList<DataPoint> dataPoints, int x) {
        ArrayList<DataPoint> copy = new ArrayList<>(dataPoints); // Create a copy of the original list
        ArrayList<DataPoint> randomPoints = new ArrayList<>();
        Collections.shuffle(copy); // Shuffle the copy
        for (int i = 0; i < x; i++) {
            copy.get(i).setCategory("C"+Integer.toString(i+1));
            randomPoints.add(copy.get(i));
        }
        return randomPoints;
    }

    public ArrayList<DataPoint> filterByCluster(ArrayList<DataPoint> dataPoints, String cluster) {
        ArrayList<DataPoint> filteredPoints = new ArrayList<>();
        for (DataPoint point : dataPoints) {
            if (point.getCategory().equals(cluster)) {
                filteredPoints.add(point);
            }
        }
        return filteredPoints;
    }

    public void assignCategories(ArrayList<DataPoint> dataPoints, ArrayList<DataPoint> classRepresentatives) {
        for (DataPoint dataPoint : dataPoints) {
            double minDistance = Double.MAX_VALUE; // Initialize with a very large value
            String closestCategory = null;

            for (DataPoint representative : classRepresentatives) {
                double distance = euclidianDistance(dataPoint, representative);

                if (distance < minDistance) {
                    minDistance = distance;
                    closestCategory = representative.getCategory();
                }
            }

            // Update the category of the dataPoint to the closest representative's category
            dataPoint.setCategory(closestCategory);
        }
    }

    public void updateClassRepresentatives(ArrayList<DataPoint> representative_dataPoints) {
        // Iterate over each DataPoint in the provided list
        for (DataPoint representative : representative_dataPoints) {
            // Get the category of the current DataPoint
            String cluster = representative.getCategory();

            // Filter the data points by the current cluster name
            ArrayList<DataPoint> filteredPoints = filterByCluster(DataPoints, cluster);

            // Check if there are no points in the cluster to avoid division by zero
            if (filteredPoints.isEmpty()) {
                System.out.println("Cluster " + cluster + " has no points. No update performed.");
                continue; // Skip to the next DataPoint
            }

            // Initialize variables to accumulate the sum of coordinates
            double sumX1 = 0;
            double sumX2 = 0;

            // Calculate the sum of x1 and x2 for all points in the cluster
            for (DataPoint filteredPoint : filteredPoints) {
                sumX1 += filteredPoint.getX1();
                sumX2 += filteredPoint.getX2();
            }

            // Compute the mean coordinates
            double meanX1 = sumX1 / filteredPoints.size();
            double meanX2 = sumX2 / filteredPoints.size();

            // Find the corresponding class representative and update its coordinates
            representative.setCoordinates(meanX1, meanX2);
        }

    }

    public double classificationError() {
        double error = 0;
        for (DataPoint classRepresentative : ClassRepresentatives) {
            String category = classRepresentative.getCategory();
            ArrayList<DataPoint> clusterDataPoints = filterByCluster(DataPoints, category);
            for (DataPoint dataPoint : clusterDataPoints) {
                error += euclidianDistance(dataPoint, classRepresentative);
            }

        }
        return error;
    }

    public void showRunInfo(double error) {
        int centres = numberOfClassRepresentatives;
        System.out.println(centres + " classes representatives have been assigned.");
        for (DataPoint dataPoint : ClassRepresentatives) {
            System.out.println(dataPoint.getCategory() + " " + dataPoint.getX1() + " " + dataPoint.getX2());
        }
        System.out.println("Error is " + error);
    }

    public double executeKmeans(String filePath) {
        DataPoints = getDataPoints(filePath);
        ClassRepresentatives = pickRandomPoints(DataPoints, numberOfClassRepresentatives);
        // isos na valoyme check gia to pote na telioni anti gia random arithmo xd ?
        for (int i = 0; i < 15; i++) {
            assignCategories(DataPoints, ClassRepresentatives);
            updateClassRepresentatives(ClassRepresentatives);
        }
        double RunError = classificationError();
        showRunInfo(RunError);

        return RunError;

    }

    public void Run20(String filePath) {
        double minError = Double.MAX_VALUE;
        ArrayList<DataPoint> BestReps = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            System.out.println("EXECUTING RUN " + i);
            double ExecutionError = executeKmeans(filePath);

            if (ExecutionError < minError) {
                minError = ExecutionError;
                BestReps = ClassRepresentatives;

            }


            System.out.println("-------------------------------------------");
        }
        System.out.println("Best Representatives are :");
        for (DataPoint classRepresentative : BestReps) {
            System.out.println("Class : " + classRepresentative.getCategory() + " " + classRepresentative.getX1() + " " + classRepresentative.getX2());


        }
        System.out.println("Min Error is " + minError);

    }
}


