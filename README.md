# Lab2 — Software Quality Evaluation (ISO/IEC 25010 & 25023)

A Java application that evaluates software system quality using the ISO/IEC 25010 quality model and ISO/IEC 25023 measurable metrics.

## Project Structure

```
Lab2-sw-quality/
├── src/
│   ├── Criterion.java          — ISO/IEC 25023 quality metric (name, weight, direction, range, unit, value)
│   ├── QualityDimension.java   — ISO/IEC 25010 quality characteristic (contains Criterion objects)
│   ├── SWSystem.java           — Software system under evaluation (contains QualityDimension objects)
│   ├── SWSystemData.java       — Factory class that generates pre-configured software systems
│   └── Main.java               — Entry point; injects test data and prints the evaluation report
├── .gitignore
└── README.md
```

## How to Compile and Run

```bash
cd src
javac *.java
java Main
```

## ISO/IEC 25010 — Quality Characteristics Used

| # | Characteristic        | ISO Code | Description                                        |
|---|-----------------------|----------|----------------------------------------------------|
| 1 | Functional Suitability| QC.FS    | Completeness, Correctness, Appropriateness         |
| 2 | Performance Efficiency| QC.PE    | Time Behaviour, Resource Utilisation, Capacity     |
| 3 | Usability             | QC.US    | Learnability, Operability, User Error Protection   |
| 4 | Reliability           | QC.RE    | Maturity, Availability, Fault Tolerance            |
| 5 | Security              | QC.SE    | Confidentiality, Integrity, Non-repudiation        |
| 6 | Maintainability       | QC.MA    | Modularity, Reusability, Analysability, Testability|
| 7 | Compatibility         | QC.CO    | Co-existence, Interoperability                     |
| 8 | Portability           | QC.PO    | Adaptability, Installability, Replaceability       |

## ISO/IEC 25023 — Metric Reference

| ISO 25010 Characteristic | ISO 25023 Metric Name         | Direction | Unit          | Formula Summary                               |
|--------------------------|-------------------------------|-----------|---------------|-----------------------------------------------|
| Functional Suitability   | Functional Completeness Ratio | Higher    | %             | Implemented functions / Planned × 100         |
| Functional Suitability   | Functional Correctness Ratio  | Higher    | %             | Correct-output tests / Total tests × 100      |
| Reliability              | Availability Ratio            | Higher    | %             | Uptime / (Uptime + Downtime) × 100            |
| Reliability              | Defect Density                | Lower     | defect/KLOC   | Defects found / 1000 LOC                      |
| Reliability              | MTBF                          | Higher    | hours         | Total operation time / Number of failures     |
| Performance Efficiency   | Response Time                 | Lower     | ms            | Average end-to-end response time              |
| Performance Efficiency   | Throughput                    | Higher    | req/s         | Requests processed per second                 |
| Performance Efficiency   | CPU Utilisation Ratio         | Lower     | %             | CPU used / Total CPU × 100                    |
| Usability                | Task Completion Rate          | Higher    | %             | Completed tasks / Total attempted × 100       |
| Usability                | User Error Rate               | Lower     | %             | Error operations / Total operations × 100     |
| Security                 | Security Test Coverage        | Higher    | %             | Passed security tests / Total security × 100  |
| Security                 | Vulnerability Count           | Lower     | count         | Number of detected vulnerabilities            |
| Maintainability          | Test Coverage Ratio           | Higher    | %             | Tested LOC / Total LOC × 100                  |
| Maintainability          | Cyclomatic Complexity (avg)   | Lower     | score         | Average cyclomatic complexity per module      |

## Score Calculation

- **Higher is better:** `score = 1 + (value − min) / (max − min) × 4`
- **Lower is better:** `score = 5 − (value − min) / (max − min) × 4`
- Score is **clamped** to `[1, 5]` and **rounded** to the nearest `0.5`.

## Quality Labels

| Score Range | Label              |
|-------------|--------------------|
| 4.5 – 5.0   | Excellent Quality  |
| 3.5 – 4.4   | Good Quality       |
| 2.5 – 3.4   | Needs Improvement  |
| 1.0 – 2.4   | Poor Quality       |

## Expected Output (ShopSphere — Test Data)

```
========================================
SOFTWARE QUALITY EVALUATION REPORT (ISO/IEC 25010)
System: ShopSphere v3.2.1 (Web)
========================================
--- Functional Suitability [QC.FS] (Weight: 25) ---
Functional Completeness Ratio: 94.0 % -> Score: 4.5 (Higher is better)
Functional Correctness Ratio: 91.0 % -> Score: 4.5 (Higher is better)
>> Dimension Score: 4.5/5 [Excellent Quality]
--- Reliability [QC.RE] (Weight: 25) ---
Availability Ratio: 99.2 % -> Score: 4.5 (Higher is better)
Defect Density: 2.1 defect/KLOC -> Score: 4.5 (Lower is better)
>> Dimension Score: 4.5/5 [Excellent Quality]
--- Performance Efficiency [QC.PE] (Weight: 25) ---
Response Time: 220.0 ms -> Score: 4.0 (Lower is better)
CPU Utilisation: 38.0 % -> Score: 4.0 (Lower is better)
>> Dimension Score: 4.0/5 [Good Quality]
--- Maintainability [QC.MA] (Weight: 25) ---
Test Coverage Ratio: 72.0 % -> Score: 3.5 (Higher is better)
Cyclomatic Complexity: 8.5-> Score: 3.5 (Lower is better)
>> Dimension Score: 3.5/5 [Good Quality]
========================================
OVERALL QUALITY SCORE: 4.1/5 [Good Quality]
========================================
GAP ANALYSIS (ISO/IEC 25010)
========================================
Weakest Characteristic : Maintainability [QC.MA]
Score: 3.5/5 | Gap: 1.5
Level: Good Quality
>> This characteristic requires the most improvement.
========================================
```
