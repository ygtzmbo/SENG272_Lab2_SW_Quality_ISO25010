import java.util.Locale;

/**
 * Represents a measurable ISO/IEC 25023 quality metric.
 * Encapsulates metric name, weight, evaluation direction, valid range, unit,
 * and the measured value obtained from the evaluated software system.
 */
public class Criterion {

    private String name;
    private int weight;
    private String direction; // "higher" or "lower"
    private double minValue;
    private double maxValue;
    private String unit;
    private double measuredValue;
    private boolean measured;

    /**
     * Creates a Criterion with all fixed attributes.
     * The measuredValue is not set at construction time.
     */
    public Criterion(String name, int weight, String direction,
                     double minValue, double maxValue, String unit) {
        this.name = name;
        this.weight = weight;
        this.direction = direction;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unit = unit;
        this.measured = false;
    }

    // ---------- Getters ----------

    public String getName()      { return name; }
    public int    getWeight()    { return weight; }
    public String getDirection() { return direction; }
    public double getMinValue()  { return minValue; }
    public double getMaxValue()  { return maxValue; }
    public String getUnit()      { return unit; }

    public double getMeasuredValue() {
        if (!measured) {
            throw new IllegalStateException("Measured value not set for: " + name);
        }
        return measuredValue;
    }

    // ---------- Setters ----------

    public void setName(String name)           { this.name = name; }
    public void setWeight(int weight)          { this.weight = weight; }
    public void setDirection(String direction) { this.direction = direction; }
    public void setMinValue(double minValue)   { this.minValue = minValue; }
    public void setMaxValue(double maxValue)   { this.maxValue = maxValue; }
    public void setUnit(String unit)           { this.unit = unit; }

    public void setMeasuredValue(double measuredValue) {
        this.measuredValue = measuredValue;
        this.measured = true;
    }

    /**
     * Converts the measured value into a normalized score in [1, 5].
     * <p>
     * For "higher is better":  score = 1 + (v - min) / (max - min) * 4
     * For "lower  is better":  score = 5 - (v - min) / (max - min) * 4
     * <p>
     * The raw score is clamped to [1, 5] and then rounded to the nearest 0.5.
     */
    public double calculateScore() {
        double v   = getMeasuredValue();
        double raw;

        if (direction.equalsIgnoreCase("higher")) {
            raw = 1.0 + (v - minValue) / (maxValue - minValue) * 4.0;
        } else {
            raw = 5.0 - (v - minValue) / (maxValue - minValue) * 4.0;
        }

        // Clamp to [1, 5]
        raw = Math.max(1.0, Math.min(5.0, raw));

        // Round down to nearest 0.5 (floor to half-integer)
        return Math.floor(raw * 2.0) / 2.0;
    }

    @Override
    public String toString() {
        String dirLabel = direction.equalsIgnoreCase("higher")
                ? "Higher is better"
                : "Lower is better";
        return String.format(Locale.US, "%s: %.1f %s -> Score: %.1f (%s)",
                name, measuredValue, unit, calculateScore(), dirLabel);
    }
}
