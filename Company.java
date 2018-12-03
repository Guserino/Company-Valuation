import java.util.LinkedList;

public class Company {

  //Qualitative
  private String name;
  private String ticker;
  private String date;
  private double enterpriseValue;
  private double equityValue;
  private double numberOfShares;
  private double pricePerShare;
  LinkedList<HistFreeCashFlow> HistoricalData;
  LinkedList<Forecast> FCFForecast;
  private double terminalValue;
  private double terminalGrowthRate = .02;

  //Cost of Capital
  private double totalDebt;
  private double totalEquity;
  private double costOfEquity;
  private double costOfDebt;
  private double wacc;

  public Company(String name, String ticker, String date, double numberOfShares) {
    this.name = name;
    this.ticker = ticker;
    this.date = date;
    this.numberOfShares = numberOfShares;
    this.HistoricalData = new LinkedList<>();
    this.FCFForecast = new LinkedList<>();
  }


  public double getEV() {
    return this.enterpriseValue;
  }

  public void setEV(double e) {
    this.enterpriseValue = e;
  }

  public double getTotalDebt() {
    return this.totalDebt;
  }

  public double getTotalEquity() {
    return this.totalEquity;
  }

  public double getCostOfEquity() {
   return this.costOfEquity;
  }

  public double getCostOfDebt() {
    return this.costOfDebt;
  }

  public double getWacc() {
    return this.wacc;
  }

  public double getTerminalGrowthRate() {
    return this.terminalGrowthRate;
  }

  public double getTerminalValue() {
    return this.terminalValue;
  }

  public void setTotalDebt(double d) {
    this.totalDebt = d;
  }

  public void setTotalEquity(double e) {
    this.totalEquity = e;
  }

  public void setCostOfEquity(double e) {
    this.costOfEquity = e;
  }

  public void setCostOfDebt(double d) {
    this.costOfDebt = d;
  }

  public double getPricePerShare() {
    return this.pricePerShare;
  }
  // public LinkedList<HistFreeCashFlow> getHistoricalData() {
  //   return this.HistoricalData<HistoricalData>();
  // }


  public void addHistoricalData(HistFreeCashFlow data) {
    this.HistoricalData.add(data);
  }

  public void addFCFForecast(Forecast data) {
    this.FCFForecast.add(data);
  }

  public void computeWacc() {
    this.wacc = (this.totalDebt/(this.totalDebt + this.totalEquity)) * this.costOfDebt + (this.totalEquity/(this.totalDebt + this.totalEquity)) * costOfEquity;
  }

  public double computeEnterpriseValue() {
    double sum = 0.00;
    for(Forecast cashflow : this.FCFForecast) {
      sum += cashflow.getDiscountedFCF();
    }
    return enterpriseValue = sum + terminalValue;
  }

  public double computePricePerShare() {
    pricePerShare = equityValue / numberOfShares;
    return pricePerShare;
  }

  public double computeTerminalValue() {
    double discountFactor = this.FCFForecast.getLast().getDiscountFactor();
    double CF = this.FCFForecast.getLast().getFCF();
    terminalValue = CF * (1 + terminalGrowthRate) / (wacc - terminalGrowthRate) / discountFactor;

    return terminalValue;
  }

  public double computeEquityValue() {
    return equityValue = enterpriseValue - totalDebt;
  }

  // public int getLastHistoricalYear() {
  //   return this.HistoricalData.getLast().getYear();
  // }

  public  void computeForecastYear() {
    int prevYear = this.HistoricalData.getLast().getYear();
    int increment = 1;
    for(Forecast x: this.FCFForecast) {
      int Year = prevYear + increment;
      x.setYear(Year);
      increment++;
    }
  }
}
