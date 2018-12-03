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

    for(Forecast x: comp1.FCFForecast) {
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
    

    //i want to be able to do this with less lines and dynamic based on the input from last historical year
    //Creates Fields for Forecast
    Forecast FCF2019 = new Forecast();
    comp1.addFCFForecast(FCF2019);
    Forecast FCF2020 = new Forecast();
    comp1.addFCFForecast(FCF2020);
    Forecast FCF2021 = new Forecast();
    comp1.addFCFForecast(FCF2021);
    Forecast FCF2022 = new Forecast();
    comp1.addFCFForecast(FCF2022);
    Forecast FCF2023 = new Forecast();
    comp1.addFCFForecast(FCF2023);
    Forecast FCF2024 = new Forecast();
    comp1.addFCFForecast(FCF2024);
    Forecast FCF2025 = new Forecast();
    comp1.addFCFForecast(FCF2025);
    Forecast FCF2026 = new Forecast();
    comp1.addFCFForecast(FCF2026);
    Forecast FCF2027 = new Forecast();
    comp1.addFCFForecast(FCF2027);
    Forecast FCF2028 = new Forecast();
    comp1.addFCFForecast(FCF2028);

    //I want to be able to do this on a method separate from the main method
    //Sets FCFModel Years
    int increment = 1;
    int baseYear = 2018;//comp1.getLastHistoricalYear();
    for(Forecast x: comp1.FCFForecast) {
      x.setYear(increment + baseYear);
      x.setDiscountFactor(Math.pow(1+x.getWacc(), increment));
      increment += 1;
    }
    // comp1.computeForecastYear();

    // I want to move this outside of the main method. Create a method that does this in one of the classes
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

    for(Forecast x: comp1.FCFForecast) {
      double forecastedGrowthRate = -1 * (1 - ((1 + historicalGrowthRate) - (y * gRateDecayToTV)));
      x.setGrowthRate(forecastedGrowthRate);
      y++;
    }

    //i want to create a method on a separate class to do this
    double salesCurrentYear;
    for(Forecast x: comp1.FCFForecast) {
      salesCurrentYear = salesPreviousYear * (1+x.getGrowthRate());
      x.setSales(salesCurrentYear);
      salesPreviousYear = salesCurrentYear;
    }


    //I want to possibly move this to the Forecast Class
    //Calculates Forecasts for Components of FCF in Forecast
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
    for(Forecast x: comp1.FCFForecast) {
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
    // double terminalValue = ((FCF2028.getFCF() * (1 + terminalGrowthRate))/(comp1.getWacc() - terminalGrowthRate))/(Math.pow(1 + comp1.FCFForecast.getLast().getWacc(), 10));

    // double LastYearCashFlowPlusTV = comp1.FCFForecast.getLast().getDiscountedFCF() + terminalValue;

    // FCF2028.setDCF(LastYearCashFlowPlusTV);
    comp1.computeTerminalValue();
    
    comp1.computeEnterpriseValue();

    comp1.computeEquityValue();
    comp1.computePricePerShare();
    


    System.out.println(comp1.FCFForecast.toString());
    System.out.println("Discounted Terminal Value: " + comp1.getTerminalValue());
    System.out.println("Enterprise Value: " + comp1.getEV());
    System.out.println("Price Per Share: " + comp1.getPricePerShare());
    System.out.println("Discounted FCF 2018: " + FCF2028.getDiscountedFCF());
    System.out.println(YearOverYear);
    System.out.println(historicalGrowthRate);
    System.out.println(gRateDecayToTV);
}
}
