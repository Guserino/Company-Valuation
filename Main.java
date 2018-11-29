// Created by Gustavo Velez with the help of Schuyler Manchester
//Input all figures in Millions
import java.lang.Math;
import java.util.LinkedList;

class Main {
  public static void main(String[] args) {
    double ForecastedTaxRate = .21;
    // double terminalGrowthRate = .02;
   
   
    Company comp1 = new Company("Target", "TGT", "11/14/2018", 526.35);
    
    comp1.setCostOfDebt(.034);
    comp1.setTotalDebt(11317);
    comp1.setCostOfEquity(.086);
    comp1.setTotalEquity(45145);
    comp1.computeWacc();

    for(DCFModel x: comp1.DCFModelForecast) {
      x.setWacc(comp1.getWacc());
    }
    
    //Section Obtains Historical Data
    //year, NI, EBIT, D&A, CapEx, NWC, TaxRate
    HistFreeCashFlow FCF2016 = new HistFreeCashFlow(2016, 73785, 5102, 2213, 1438, 490, .29);
    comp1.addHistoricalData(FCF2016);
    HistFreeCashFlow FCF2017 = new HistFreeCashFlow(2017, 69495, 5012, 2298, 1547, -195, .29);
    comp1.addHistoricalData(FCF2017);
    HistFreeCashFlow FCF2018 = new HistFreeCashFlow(2018, 71879, 4403, 2445, 2533, 1241, .21);
    comp1.addHistoricalData(FCF2018);
    //Model
    
    //Creates Fields for DCFModel
    DCFModel FCF2019 = new DCFModel();
    comp1.addDCFModelForecast(FCF2019);
    DCFModel FCF2020 = new DCFModel();
    comp1.addDCFModelForecast(FCF2020);
    DCFModel FCF2021 = new DCFModel();
    comp1.addDCFModelForecast(FCF2021);
    DCFModel FCF2022 = new DCFModel();
    comp1.addDCFModelForecast(FCF2022);
    DCFModel FCF2023 = new DCFModel();
    comp1.addDCFModelForecast(FCF2023);
    DCFModel FCF2024 = new DCFModel();
    comp1.addDCFModelForecast(FCF2024);
    DCFModel FCF2025 = new DCFModel();
    comp1.addDCFModelForecast(FCF2025);
    DCFModel FCF2026 = new DCFModel();
    comp1.addDCFModelForecast(FCF2026);
    DCFModel FCF2027 = new DCFModel();
    comp1.addDCFModelForecast(FCF2027);
    DCFModel FCF2028 = new DCFModel();
    comp1.addDCFModelForecast(FCF2028);

    //Sets FCFModel Years
    int increment = 1;
    int baseYear = 2018;//comp1.getLastHistoricalYear();
    for(DCFModel x: comp1.DCFModelForecast) {
      x.setYear(increment + baseYear);
      x.setDiscountFactor(Math.pow(1+x.getWacc(), increment));
      increment += 1;
    }

    LinkedList<Double> historicalSales = new LinkedList<>();
    for(HistFreeCashFlow x: comp1.HistoricalData) {
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

    double gRateDecayToTV = (historicalGrowthRate - comp1.getTerminalGrowthRate()) / 9;
    double salesPreviousYear = FCF2018.getSales();
    double modelYear = 0;

    double y = 0;

    for(DCFModel x: comp1.DCFModelForecast) {
      double forecastedGrowthRate = -1 * (1 - ((1 + historicalGrowthRate) - (y * gRateDecayToTV)));
      x.setGrowthRate(forecastedGrowthRate);
      y++;
    }

    double salesCurrentYear;
    for(DCFModel x: comp1.DCFModelForecast) {
      salesCurrentYear = salesPreviousYear * (1+x.getGrowthRate());
      x.setSales(salesCurrentYear);
      salesPreviousYear = salesCurrentYear;
    }



    //Calculates Forecasts for Components of FCF in DCFModel
    double ebitForecast = 0;
    double nwcForecast = 0;
    double CapExForecast = 0;
    double DandAForecast = 0;
    for(HistFreeCashFlow x: comp1.HistoricalData) {
        double YearlycapEx = x.getCapEx() / x.getSales();
        CapExForecast += YearlycapEx;
        double YearlyEbit = x.getEbit() / x.getSales();
        ebitForecast += YearlyEbit;
        double YearlyNwc = x.getNwc() / x.getSales();
        nwcForecast += YearlyNwc;
        double YearlyDandA = x.getDandA() / x.getSales();
        DandAForecast += YearlyDandA;
    }
    CapExForecast /= comp1.HistoricalData.size();
    ebitForecast /= comp1.HistoricalData.size();
    nwcForecast /= comp1.HistoricalData.size();
    DandAForecast /= comp1.HistoricalData.size();

    double forecastYear = 1;
    for(DCFModel x: comp1.DCFModelForecast) {
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

    comp1.computeTerminalValue();
    // double terminalValue = ((FCF2028.getFCF() * (1 + terminalGrowthRate))/(comp1.getWacc() - terminalGrowthRate))/(Math.pow(1 + comp1.DCFModelForecast.getLast().getWacc(), 10));

    // double LastYearCashFlowPlusTV = comp1.DCFModelForecast.getLast().getDiscountedFCF() + terminalValue;

    // FCF2028.setDCF(LastYearCashFlowPlusTV);
    comp1.computeTerminalValue();
    
    comp1.computeEnterpriseValue();

    comp1.computeEquityValue();
    comp1.computePricePerShare();
    


    System.out.println(comp1.DCFModelForecast.toString());
    System.out.println("Discounted Terminal Value: " + comp1.getTerminalValue());
    System.out.println("Enterprise Value: " + comp1.getEV());
    System.out.println("Price Per Share: " + comp1.getPricePerShare());
    System.out.println("Discounted FCF 2018: " + FCF2028.getDiscountedFCF());
    System.out.println(YearOverYear);
    System.out.println(historicalGrowthRate);
    System.out.println(gRateDecayToTV);
}
}
