import java.lang.Math;
//All figures are in millions (USD)
public class HistFreeCashFlow {

  //identifier
  private int year;
  //Forecast Driver
  private double sales;
  private double freeCashFlow;
  //FCF Components
  private double ebit;
  private double DepreciationAndAmortization;
  private double capEx;
  private double netWorkingCapitalChange;
  private double taxRate;

  public HistFreeCashFlow(int year, double sales, double ebit,double DepreciationAndAmortization, double capEx, double netWorkingCapitalChange, double taxRate) {
    // this.netIncome = netIncome;
    this.year = year;
    this.sales = sales;
    this.ebit = ebit;
    this.DepreciationAndAmortization = DepreciationAndAmortization;
    this.capEx = capEx;
    this.netWorkingCapitalChange = netWorkingCapitalChange;
    this.taxRate = taxRate;
  }

  public double getSales() {
    return this.sales;
  }

  public int getYear() {
    return this.year;
  }

  public double getEbit() {
    return this.ebit;
  }

  public double getCapEx() {
    return this.capEx;
  }

  public double getNwc() {
    return this.netWorkingCapitalChange;
  }

  public double getDandA() {
    return this.DepreciationAndAmortization;
  }

  public double computeFCF() {
    freeCashFlow = ebit * (1-taxRate) + DepreciationAndAmortization - capEx - netWorkingCapitalChange;
    return freeCashFlow;
  }


  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Year ");
    sb.append(year + "\n");
    sb.append("FCF: \t");
    sb.append(freeCashFlow + "\n");
    sb.append("EBIT: \t");
    sb.append(ebit + "\n");
    sb.append("D&A: \t");
    sb.append(DepreciationAndAmortization + "\n");
    sb.append("CapEx: \t");
    sb.append(capEx + "\n");
    sb.append("^NWC: \t");
    sb.append(netWorkingCapitalChange + "\n");

    return sb.toString();
  }


}
