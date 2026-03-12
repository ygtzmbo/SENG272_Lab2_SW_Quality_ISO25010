import java.util.ArrayList;
import java.util.HashMap;

/**
 * Factory/utility class that generates and supplies software systems to be evaluated.
 * All access is via a class-level (static) method — no instantiation needed.
 */
public class SWSystemData {

    // Prevent instantiation
    private SWSystemData() {}

    /**
     * Returns a map of category → list of software systems.
     * The map is keyed by category name (e.g. "Web", "Mobile", "Enterprise").
     */
    public static HashMap<String, ArrayList<SWSystem>> getAllSystems() {
        HashMap<String, ArrayList<SWSystem>> map = new HashMap<>();

        // ---- Web systems ----
        ArrayList<SWSystem> webList = new ArrayList<>();
        webList.add(createECommercePlatform());
        webList.add(createBankingPortal());
        map.put("Web", webList);

        // ---- Mobile systems ----
        ArrayList<SWSystem> mobileList = new ArrayList<>();
        mobileList.add(createHealthApp());
        map.put("Mobile", mobileList);

        return map;
    }

    // -----------------------------------------------------------------------
    // ShopSphere — Web E-Commerce Platform
    // -----------------------------------------------------------------------
    private static SWSystem createECommercePlatform() {
        SWSystem s = new SWSystem("ShopSphere", "Web", "3.2.1");

        // ISO 25010: Functional Suitability [QC.FS]
        QualityDimension funcSuit = new QualityDimension("Functional Suitability", "QC.FS", 25);
        funcSuit.addCriterion(new Criterion(
                "Functional Completeness Ratio", 50, "higher", 0, 100, "%"));
        funcSuit.addCriterion(new Criterion(
                "Functional Correctness Ratio",  50, "higher", 0, 100, "%"));
        s.addDimension(funcSuit);

        // ISO 25010: Reliability [QC.RE]
        QualityDimension reliability = new QualityDimension("Reliability", "QC.RE", 25);
        reliability.addCriterion(new Criterion(
                "Availability Ratio", 50, "higher", 0, 100, "%"));
        reliability.addCriterion(new Criterion(
                "Defect Density",     50, "lower",  0,  20,  "defect/KLOC"));
        s.addDimension(reliability);

        // ISO 25010: Performance Efficiency [QC.PE]
        QualityDimension performance = new QualityDimension("Performance Efficiency", "QC.PE", 25);
        performance.addCriterion(new Criterion(
                "Response Time",   50, "lower", 0, 1000, "ms"));
        performance.addCriterion(new Criterion(
                "CPU Utilisation", 50, "lower", 20, 100,  "%"));
        s.addDimension(performance);

        // ISO 25010: Maintainability [QC.MA]
        QualityDimension maintainability = new QualityDimension("Maintainability", "QC.MA", 25);
        maintainability.addCriterion(new Criterion(
                "Test Coverage Ratio",   50, "higher", 0,   100, "%"));
        maintainability.addCriterion(new Criterion(
                "Cyclomatic Complexity", 50, "lower",  1,   25,  "score"));
        s.addDimension(maintainability);

        return s;
    }

    // -----------------------------------------------------------------------
    // SecureBank — Web Banking Portal
    // -----------------------------------------------------------------------
    private static SWSystem createBankingPortal() {
        SWSystem s = new SWSystem("SecureBank", "Web", "2.0.4");

        // ISO 25010: Security [QC.SE]
        QualityDimension security = new QualityDimension("Security", "QC.SE", 40);
        security.addCriterion(new Criterion(
                "Security Test Coverage", 60, "higher", 0, 100,   "%"));
        security.addCriterion(new Criterion(
                "Vulnerability Count",    40, "lower",  0,  50,   "count"));
        s.addDimension(security);

        // ISO 25010: Reliability [QC.RE]
        QualityDimension reliability = new QualityDimension("Reliability", "QC.RE", 30);
        reliability.addCriterion(new Criterion(
                "Availability Ratio", 50, "higher", 95, 100, "%"));
        reliability.addCriterion(new Criterion(
                "MTBF",               50, "higher", 0,  5000, "hours"));
        s.addDimension(reliability);

        // ISO 25010: Usability [QC.US]
        QualityDimension usability = new QualityDimension("Usability", "QC.US", 30);
        usability.addCriterion(new Criterion(
                "Task Completion Rate", 50, "higher", 0, 100, "%"));
        usability.addCriterion(new Criterion(
                "User Error Rate",      50, "lower",  0, 100, "%"));
        s.addDimension(usability);

        return s;
    }

    // -----------------------------------------------------------------------
    // HealthTrack — Mobile Health Application
    // -----------------------------------------------------------------------
    private static SWSystem createHealthApp() {
        SWSystem s = new SWSystem("HealthTrack", "Mobile", "1.5.0");

        // ISO 25010: Usability [QC.US]
        QualityDimension usability = new QualityDimension("Usability", "QC.US", 30);
        usability.addCriterion(new Criterion(
                "Task Completion Rate", 60, "higher", 0, 100, "%"));
        usability.addCriterion(new Criterion(
                "User Error Rate",      40, "lower",  0, 100, "%"));
        s.addDimension(usability);

        // ISO 25010: Performance Efficiency [QC.PE]
        QualityDimension performance = new QualityDimension("Performance Efficiency", "QC.PE", 35);
        performance.addCriterion(new Criterion(
                "Response Time", 50, "lower",  0, 3000, "ms"));
        performance.addCriterion(new Criterion(
                "Throughput",    50, "higher", 0, 500,  "req/s"));
        s.addDimension(performance);

        // ISO 25010: Reliability [QC.RE]
        QualityDimension reliability = new QualityDimension("Reliability", "QC.RE", 35);
        reliability.addCriterion(new Criterion(
                "Availability Ratio", 50, "higher", 95, 100, "%"));
        reliability.addCriterion(new Criterion(
                "Defect Density",     50, "lower",  0,  20,  "defect/KLOC"));
        s.addDimension(reliability);

        return s;
    }
}
