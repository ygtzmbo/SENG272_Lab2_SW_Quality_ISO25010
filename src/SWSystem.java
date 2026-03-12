import java.util.ArrayList;
import java.util.Locale;

/**
 * Represents a software system being evaluated against ISO/IEC 25010 quality model.
 */
public class SWSystem {

    private String name;
    private String category;
    private String version;
    private ArrayList<QualityDimension> dimensions;

    public SWSystem(String name, String category, String version) {
        this.name       = name;
        this.category   = category;
        this.version    = version;
        this.dimensions = new ArrayList<>();
    }

    // ---------- Getters ----------

    public String getName()     { return name; }
    public String getCategory() { return category; }
    public String getVersion()  { return version; }
    public ArrayList<QualityDimension> getDimensions() { return dimensions; }

    // ---------- Setters ----------

    public void setName(String name)         { this.name = name; }
    public void setCategory(String category) { this.category = category; }
    public void setVersion(String version)   { this.version = version; }

    /** Adds a quality dimension (ISO 25010 characteristic) to this system. */
    public void addDimension(QualityDimension dimension) {
        dimensions.add(dimension);
    }

    /**
     * Calculates the overall quality score of the system by aggregating all
     * dimension scores weighted by their respective dimension weights.
     * overallScore = Σ(dimensionScore × dimensionWeight) / Σ(dimensionWeights)
     */
    public double calculateOverallScore() {
        double weightedSum = 0.0;
        double totalWeight = 0.0;
        for (QualityDimension d : dimensions) {
            weightedSum += d.calculateDimensionScore() * d.getWeight();
            totalWeight += d.getWeight();
        }
        if (totalWeight == 0) return 0;
        return weightedSum / totalWeight;
    }

    /**
     * Returns the quality label for the overall score.
     */
    public String getOverallQualityLabel() {
        double score = calculateOverallScore();
        if (score >= 4.5) return "Excellent Quality";
        if (score >= 3.5) return "Good Quality";
        if (score >= 2.5) return "Needs Improvement";
        return "Poor Quality";
    }

    /**
     * Finds and returns the quality dimension with the lowest calculated score.
     * This is the primary improvement area for the evaluated software system.
     */
    public QualityDimension findWeakestDimension() {
        QualityDimension weakest = null;
        double lowestScore = Double.MAX_VALUE;
        for (QualityDimension d : dimensions) {
            double score = d.calculateDimensionScore();
            if (score < lowestScore) {
                lowestScore = score;
                weakest    = d;
            }
        }
        return weakest;
    }

    /**
     * Prints the structured ISO/IEC 25010 evaluation report to the console.
     */
    public void printReport() {
        System.out.println("========================================");
        System.out.println("SOFTWARE QUALITY EVALUATION REPORT (ISO/IEC 25010)");
        System.out.printf(Locale.US, "System: %s v%s (%s)%n", name, version, category);
        System.out.println("========================================");

        for (QualityDimension d : dimensions) {
            System.out.println(d.toString()); // --- Name [Code] (Weight: N) ---
            for (Criterion c : d.getCriteria()) {
                System.out.println(c.toString()); // metric line
            }
            System.out.printf(Locale.US, ">> Dimension Score: %.1f/5 [%s]%n",
                    d.calculateDimensionScore(), d.getQualityLabel());
        }

        double overallScore = calculateOverallScore();
        System.out.println("========================================");
        System.out.printf(Locale.US, "OVERALL QUALITY SCORE: %.1f/5 [%s]%n",
                overallScore, getOverallQualityLabel());
        System.out.println("========================================");

        QualityDimension weakest = findWeakestDimension();
        if (weakest != null) {
            System.out.println("GAP ANALYSIS (ISO/IEC 25010)");
            System.out.println("========================================");
            System.out.printf(Locale.US, "Weakest Characteristic : %s [%s]%n",
                    weakest.getName(), weakest.getIsoCode());
            System.out.printf(Locale.US, "Score: %.1f/5 | Gap: %.1f%n",
                    weakest.calculateDimensionScore(), weakest.getQualityGap());
            System.out.printf("Level: %s%n", weakest.getQualityLabel());
            System.out.println(">> This characteristic requires the most improvement.");
            System.out.println("========================================");
        }
    }

    @Override
    public String toString() {
        return String.format("%s v%s (%s)", name, version, category);
    }
}
