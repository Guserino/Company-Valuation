// Created by Gustavo Velez with the help of Schuyler Manchester

import java.util.LinkedList;

class Main {

  public static void main(String[] args) {
    Company target = new Company("Target", "TGT", "11/14/2018", 526.35);
    LinkedList<FreeCashFlow> fcfData = new LinkedList<FreeCashFlow>();
    LinkedList<FreeCashFlow> fcfModelForecast = new LinkedList<>();
    LinkedList<Double> YearOverYearGrowth = new LinkedList<>();


    //Section Obtains Historical Data
    //year, NI, EBIT, D&A, CapEx, NWC, TaxRate
    FreeCashFlow HistFCF2016 = new FreeCashFlow(2016, 73785, 5102, 2213, 1438, 490, .29, 0);
    target.addFCF(HistFCF2016);
    FreeCashFlow HistFCF2017 = new FreeCashFlow(2017, 69495, 5012, 2298, 1547, -195, .29, 0);
    target.addFCF(HistFCF2017);
    FreeCashFlow HistFCF2018 = new FreeCashFlow(2018, 71879, 4403, 2445, 2533, 1241, .21, 0);
    target.addFCF(HistFCF2018);
    // FreeCashFlow AnalystFCF2019 = new FreeCashFlow(2019, 74345, 4887, 2494, 2137, 408, .21, 1);
    // target.addFCF(AnalystFCF2019);
    // FreeCashFlow AnalystFCF2020 = new FreeCashFlow(2020, 76777, 5047, 2575, 2207, 422, .21, 2);
    // target.addFCF(AnalystFCF2020);
    // FreeCashFlow FCF2021 = new FreeCashFlow(2021, 79167, 5204, 2655, 2276, 435, .21, 3);
    // target.addFCF(FCF2021);
    // FreeCashFlow FCF2022 = new FreeCashFlow(2022, 81505, 5358, 2734, 2343, 448, .21, 4);
    // target.addFCF(FCF2022);
    // FreeCashFlow FCF2023 = new FreeCashFlow(2023, 83783, 5508, 2810, 2409, 460, .21, 5);
    // target.addFCF(FCF2023);
    // FreeCashFlow FCF2024 = new FreeCashFlow(2024, 85991, 5653, 2884, 2472, 472, .21, 6);
    // target.addFCF(FCF2024);
    // FreeCashFlow FCF2025 = new FreeCashFlow(2025, 88121, 5793, 2956, 2533, 484, .21, 7);
    // target.addFCF(FCF2025);
    // FreeCashFlow FCF2026 = new FreeCashFlow(2026, 90164, 5927, 3024, 2592, 495, .21, 8);
    // target.addFCF(FCF2026);
    // FreeCashFlow FCF2027 = new FreeCashFlow(2027, 92110, 6055, 3089, 2648, 506, .21, 9);
    // target.addFCF(FCF2027);
    // FreeCashFlow FCF2028 = new FreeCashFlow(2028, 93952, 6176, 3151, 2701, 516, .21, 10);
    // target.addFCF(FCF2028);

    //Section computes Free Cash Flow and Discounts it to the Present
    for(FreeCashFlow x: target.FCF) {
      x.computeFCF();
    }
    for(FreeCashFlow x: target.FCF) {
      x.computeDiscountFactor();
    }
    for(FreeCashFlow x: target.FCF) {
      x.computeDiscountedFCF();
    }
    for(FreeCashFlow x: target.FCF) {
      System.out.println(x);
    }
    System.out.printf("Sum of DCF: $%.02f \n",target.computePresentValue());


    // Uses Present value to compute price per share
    target.computePricePerShare();
    System.out.printf("Price Per Share: $%.02f \n", target.getPricePerShare());
  }
}
