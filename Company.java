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
  // private double wacc = .0756;

  public Company(String name, String ticker, String date, double numberOfShares) {
    this.name = name;
    this.ticker = ticker;
    this.date = date;
    this.numberOfShares = numberOfShares;
    this.HistoricalData = new LinkedList<>();
    this.DCFModelForecast = new LinkedList<>();
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



  public double computePricePerShare() {
    pricePerShare = enterpriseValue / numberOfShares;
    return pricePerShare;
  }

  public double getPricePerShare() {
    return this.pricePerShare;
  }


}
