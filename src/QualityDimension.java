import java.util.ArrayList;

/**
 * Represents an ISO/IEC 25010 quality characteristic (e.g. Reliability).
 * Contains multiple ISO/IEC 25023 metrics (Criterion objects).
 */
public class QualityDimension {

    private String name;
    private String isoCode;
    private int weight;
    private ArrayList<Criterion> criteria;

    public QualityDimension(String name, String isoCode, int weight) {
        this.name     = name;
        this.isoCode  = isoCode;
        this.weight   = weight;
        this.criteria = new ArrayList<>();
    }

    // ---------- Getters ----------

    public String getName()    { return name; }
    public String getIsoCode() { return isoCode; }
    public int    getWeight()  { return weight; }
    public ArrayList<Criterion> getCriteria() { return criteria; }

    // ---------- Setters ----------

    public void setName(String name)       { this.name = name; }
    public void setIsoCode(String isoCode) { this.isoCode = isoCode; }
    public void setWeight(int weight)      { this.weight = weight; }

    /** Adds a criterion (metric) to this quality dimension. */
    public void addCriterion(Criterion criterion) {
        criteria.add(criterion);
    }

    /**
     * Calculates the dimension score as the weighted average of all metrics.
     * dimensionScore = Σ(metricScore × metricWeight) / Σ(weights)
     */
    public double calculateDimensionScore() {
        double weightedSum = 0.0;
        double totalWeight = 0.0;
        for (Criterion c : criteria) {
            weightedSum += c.calculateScore() * c.getWeight();
            totalWeight += c.getWeight();
        }
        if (totalWeight == 0) return 0;
        return weightedSum / totalWeight;
    }

    /**
     * Returns a quality label based on the dimension score.
     *
     * 4.5 – 5.0 → Excellent Quality
     * 3.5 – 4.4 → Good Quality
     * 2.5 – 3.4 → Needs Improvement
     * 1.0 – 2.4 → Poor Quality
     */
    public String getQualityLabel() {
        double score = calculateDimensionScore();
        if (score >= 4.5) return "Excellent Quality";
        if (score >= 3.5) return "Good Quality";
        if (score >= 2.5) return "Needs Improvement";
        return "Poor Quality";
    }

    /**
     * Returns the quality gap: difference between the maximum possible score (5.0)
     * and the current dimension score.
     */
    public double getQualityGap() {
        return 5.0 - calculateDimensionScore();
    }

    @Override
    public String toString() {
        return String.format("--- %s [%s] (Weight: %d) ---", name, isoCode, weight);
    }
}
