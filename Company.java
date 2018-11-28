import java.util.LinkedList;

public class Company {

  //Qualitative
  private String name;
  private String ticker;
  private String date;
  private double enterpriseValue;
  private double numberOfShares;
  private double pricePerShare;
  LinkedList<HistFreeCashFlow> HistoricalData;
  LinkedList<DCFModel> DCFModelForecast;
  private double terminalValue;

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
    this.DCFModelForecast = new LinkedList<>();
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
  // public LinkedList<HistFreeCashFlow> getHistoricalData() {
  //   return this.HistoricalData<HistoricalData>();
  // }
  public void addHistoricalData(HistFreeCashFlow data) {
    this.HistoricalData.add(data);
  }

  public void addDCFModelForecast(DCFModel data) {
    this.DCFModelForecast.add(data);
  }

  public double computePresentValue() {
    double sum = 0.00;
    for(DCFModel cashflow : this.DCFModelForecast) {
      sum += cashflow.getDiscountedFCF();
    }
    return enterpriseValue = sum;
  }

  public void computeWacc() {
    this.wacc = (this.totalDebt/(this.totalDebt + this.totalEquity)) * this.costOfDebt + (this.totalEquity/(this.totalDebt + this.totalEquity)) * costOfEquity;
  }

  public double computePricePerShare() {
    pricePerShare = enterpriseValue / numberOfShares;
    return pricePerShare;
  }

  public double getPricePerShare() {
    return this.pricePerShare;
  }


}
