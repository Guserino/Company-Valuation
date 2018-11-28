// Created by Gustavo Velez with the help of Schuyler Manchester
import java.lang.Math;
import java.util.LinkedList;

class Main {
  public static void main(String[] args) {
    double ForecastedTaxRate = .21;
    double terminalGrowthRate = .02;
   
   
    Company target = new Company("Target", "TGT", "11/14/2018", 526.35);
    
    target.setCostOfDebt(.034);
    target.setTotalDebt(11317);
    target.setCostOfEquity(.086);
    target.setTotalEquity(45145);
    target.computeWacc();

    for(DCFModel x: target.DCFModelForecast) {
      x.setWacc(target.getWacc());
    }
    
    //Section Obtains Historical Data
    //year, NI, EBIT, D&A, CapEx, NWC, TaxRate
    HistFreeCashFlow FCF2016 = new HistFreeCashFlow(2016, 73785, 5102, 2213, 1438, 490, .29);
    target.addHistoricalData(FCF2016);
    HistFreeCashFlow FCF2017 = new HistFreeCashFlow(2017, 69495, 5012, 2298, 1547, -195, .29);
    target.addHistoricalData(FCF2017);
    HistFreeCashFlow FCF2018 = new HistFreeCashFlow(2018, 71879, 4403, 2445, 2533, 1241, .21);
    target.addHistoricalData(FCF2018);
    //Model
    
    //Creates Fields for DCFModel
    DCFModel FCF2019 = new DCFModel();
    target.addDCFModelForecast(FCF2019);
    DCFModel FCF2020 = new DCFModel();
    target.addDCFModelForecast(FCF2020);
    DCFModel FCF2021 = new DCFModel();
    target.addDCFModelForecast(FCF2021);
    DCFModel FCF2022 = new DCFModel();
    target.addDCFModelForecast(FCF2022);
    DCFModel FCF2023 = new DCFModel();
    target.addDCFModelForecast(FCF2023);
    DCFModel FCF2024 = new DCFModel();
    target.addDCFModelForecast(FCF2024);
    DCFModel FCF2025 = new DCFModel();
    target.addDCFModelForecast(FCF2025);
    DCFModel FCF2026 = new DCFModel();
    target.addDCFModelForecast(FCF2026);
    DCFModel FCF2027 = new DCFModel();
    target.addDCFModelForecast(FCF2027);
    DCFModel FCF2028 = new DCFModel();
    target.addDCFModelForecast(FCF2028);

    //Sets FCFModel Years
    int increment = 1;
    int baseYear = 2018;
    for(DCFModel x: target.DCFModelForecast) {
      int year = baseYear + increment;
      x.setYear(increment + baseYear);
      x.setDiscountFactor(Math.pow(1+x.getWacc(), increment));
      increment += 1;
    }

    LinkedList<Double> historicalSales = new LinkedList<>();
    for(HistFreeCashFlow x: target.HistoricalData) {
      historicalSales.add(x.getSales());
    }
    LinkedList<Double> YearOverYear = new LinkedList<>();
    Double prevYear = null;
    for(Double curYear: historicalSales) {
      if(prevYear == null) {
        prevYear = curYear;
        continue;
      }
      double YoY = (curYear - prevYear)/prevYear;
      YearOverYear.add(YoY);
      prevYear = curYear;
    }

    double historicalGrowthRate = 0;
    for(Double x: YearOverYear) {
      historicalGrowthRate += x;
    }

    historicalGrowthRate /= YearOverYear.size();

    double gRateDecayToTV = (historicalGrowthRate - terminalGrowthRate) / 9;
    double salesCurentYear = FCF2018.getSales();
    double modelYear = 0;

    double y = 0;

    for(DCFModel x: target.DCFModelForecast) {
      double forecastedGrowthRate = -1 * (1 - ((1 + historicalGrowthRate) - (y * gRateDecayToTV)));
      x.setGrowthRate(forecastedGrowthRate);
      y++;
    }

    double salesPrevForecastYear;
    for(DCFModel x: target.DCFModelForecast) {
      salesPrevForecastYear = salesCurentYear * (1+x.getGrowthRate());
      x.setSales(salesPrevForecastYear);
      salesCurentYear = salesPrevForecastYear;
    }



    //Calculates Forecasts for Components of FCF in DCFModel
    double ebitForecast = 0;
    double nwcForecast = 0;
    double CapExForecast = 0;
    double DandAForecast = 0;
    for(HistFreeCashFlow x: target.HistoricalData) {
        double YearlycapEx = x.getCapEx() / x.getSales();
        CapExForecast += YearlycapEx;
        double YearlyEbit = x.getEbit() / x.getSales();
        ebitForecast += YearlyEbit;
        double YearlyNwc = x.getNwc() / x.getSales();
        nwcForecast += YearlyNwc;
        double YearlyDandA = x.getDandA() / x.getSales();
        DandAForecast += YearlyDandA;
    }
    CapExForecast /= target.HistoricalData.size();
    ebitForecast /= target.HistoricalData.size();
    nwcForecast /= target.HistoricalData.size();
    DandAForecast /= target.HistoricalData.size();

    double forecastYear = 1;
    for(DCFModel x: target.DCFModelForecast) {
      x.setEbit(ebitForecast * x.getSales());
      x.setDandA(DandAForecast * x.getSales());
      x.setCapEx(CapExForecast * x.getSales());
      x.setNwc(nwcForecast * x.getSales());
      x.setTaxRate(ForecastedTaxRate);
      x.computeFCF();
      x.setForecastYear(forecastYear);
      forecastYear++;
      x.computeDiscountedFCF();
    }

    System.out.println(FCF2028.getDiscountedFCF());

    double terminalValue = ((FCF2028.getFCF() * (1 + terminalGrowthRate))/(target.getWacc() - terminalGrowthRate))/(Math.pow(1 + target.DCFModelForecast.getLast().getWacc(), 10));

    double LastYearCashFlowPlusTV = target.DCFModelForecast.getLast().getDiscountedFCF() + terminalValue;

    FCF2028.setDCF(LastYearCashFlowPlusTV);
    target.computePresentValue();

    target.setEV(target.getEV() - target.getTotalDebt());
    target.computePricePerShare();
    
    target.computePresentValue();

  

    


    // //Section computes Free Cash Flow and Discounts it to the Present
    // for(HistFreeCashFlow x: target.HistoricalData) {
    //   x.computeFCF();
    //   x.computeDiscountFactor();
    //   x.computeDiscountedFCF();
    //   // System.out.println(x);
    // }

    // System.out.printf("Sum of DCF: $%.02f \n",target.computePresentValue());
    // // Uses Present value to compute price per share
    // target.computePricePerShare();
    // System.out.printf("Price Per Share: $%.02f \n", target.getPricePerShare());

    //System.out.printf("Model Growth Rate: %.03f \n", historicalGrowthRate);

    System.out.println("Capex%Sales " + CapExForecast);
    System.out.println("EBIT%Sales" + ebitForecast);
    System.out.println("NWC%Sales " + nwcForecast);
    System.out.println("D&A%Sales " + DandAForecast);
    System.out.println(target.DCFModelForecast.toString());
    System.out.println(terminalValue);
    System.out.println(target.getEV());
    System.out.println(target.getPricePerShare());
    System.out.println(FCF2028.getDiscountedFCF());
}
}
