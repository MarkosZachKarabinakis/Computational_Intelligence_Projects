import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// o xaris einai gay
public class DatasetGenerator {

    // Method to generate dataset
    public static List<DataPoint> generateDataset() {
        List<DataPoint> dataset = new ArrayList<>();
        Random random = new Random();

        // Generate points for each cluster
        dataset.addAll(generateCluster(-2, -1.6, 1.6, 2, 100, "C1"));
        dataset.addAll(generateCluster(-1.2, -0.8, 1.6, 2, 100, "C2"));
        dataset.addAll(generateCluster(-0.4, 0, 1.6, 2, 100, "C3"));
        dataset.addAll(generateCluster(-1.8, -1.4, 0.8, 1.2, 100, "C4"));
        dataset.addAll(generateCluster(-0.6, -0.2, 0.8, 1.2, 100, "C5"));
        dataset.addAll(generateCluster(-2, -1.6, 0, 0.4, 100, "C6"));
        dataset.addAll(generateCluster(-1.2, -0.8, 0, 0.4, 100, "C7"));
        dataset.addAll(generateCluster(-0.4, 0, 0, 0.4, 100, "C8"));
        dataset.addAll(generateCluster(-2, 0, 0, 2, 200, "C9"));

        return dataset;
    }

    // Helper method to generate points for a single cluster
    private static List<DataPoint> generateCluster(double xMin, double xMax, double yMin, double yMax, int count, String category) {
        List<DataPoint> cluster = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            double x1 = xMin + (xMax - xMin) * random.nextDouble();
            double x2 = yMin + (yMax - yMin) * random.nextDouble();
            cluster.add(new DataPoint(x1, x2, category));
        }
        return cluster;
    }

    // Method to save dataset to CSV file
    public static void saveDatasetToCSV(List<DataPoint> dataset, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("x1,x2,category\n");
            for (DataPoint dataPoint : dataset) {
                writer.write(dataPoint.toString() + "\n");
            }
            System.out.println("Dataset saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to run a Python script (same as before)
    public static void runPythonScript(String scriptPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", scriptPath);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Python script finished with exit code: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Generate dataset
        List<DataPoint> dataset = generateDataset();

        // Save dataset to CSV
        saveDatasetToCSV(dataset, "./dataset.csv");

        // Optionally, run a Python script (for further processing)
        runPythonScript("./graph.py");
    }
}
