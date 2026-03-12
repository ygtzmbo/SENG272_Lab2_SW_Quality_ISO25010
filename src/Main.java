import java.util.ArrayList;
import java.util.HashMap;


public class Main {

    public static void main(String[] args) {

        // Step 1 – Load all software systems
        HashMap<String, ArrayList<SWSystem>> allSystems = SWSystemData.getAllSystems();

        // Step 2 – Retrieve Web systems
        ArrayList<SWSystem> webSystems = allSystems.get("Web");

        // Step 3 – Select ShopSphere
        SWSystem shopSphere = null;
        for (SWSystem sys : webSystems) {
            if (sys.getName().equals("ShopSphere")) {
                shopSphere = sys;
                break;
            }
        }

        if (shopSphere == null) {
            System.out.println("ShopSphere system not found.");
            return;
        }

        // Step 4 – Inject test metric values
        for (QualityDimension dim : shopSphere.getDimensions()) {
            switch (dim.getIsoCode()) {

                case "QC.FS":
                    for (Criterion c : dim.getCriteria()) {
                        switch (c.getName()) {
                            case "Functional Completeness Ratio":
                                c.setMeasuredValue(94.0); break;
                            case "Functional Correctness Ratio":
                                c.setMeasuredValue(91.0); break;
                        }
                    }
                    break;

                case "QC.RE":
                    for (Criterion c : dim.getCriteria()) {
                        switch (c.getName()) {
                            case "Availability Ratio":
                                c.setMeasuredValue(99.2); break;
                            case "Defect Density":
                                c.setMeasuredValue(2.1);  break;
                        }
                    }
                    break;

                case "QC.PE":
                    for (Criterion c : dim.getCriteria()) {
                        switch (c.getName()) {
                            case "Response Time":
                                c.setMeasuredValue(220.0); break;
                            case "CPU Utilisation":
                                c.setMeasuredValue(38.0);  break;
                        }
                    }
                    break;

                case "QC.MA":
                    for (Criterion c : dim.getCriteria()) {
                        switch (c.getName()) {
                            case "Test Coverage Ratio":
                                c.setMeasuredValue(72.0); break;
                            case "Cyclomatic Complexity":
                                c.setMeasuredValue(8.5);  break;
                        }
                    }
                    break;
            }
        }

        // Step 5 – Print the ISO/IEC 25010 evaluation report
        shopSphere.printReport();
    }
}

