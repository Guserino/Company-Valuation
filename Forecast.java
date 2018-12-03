public class Forecast {
   //identifier
  int year;
  //Forecast Driver
  double sales;
  double freeCashFlow;
  double discountedFCF;
  //FCF Components
  double ebit;
  double DandA;
  double capEx;
  double netWorkingCapitalChange;
  double taxRate;
  //Discounting FCF
  double forecastYear;
  double discountFactor;
  double wacc = 0.0756;
  double growthRate;

  public Forecast() {
    this.year = year;
    this.sales = sales;
    this.ebit = ebit;
    this.DandA = DandA;
    this.capEx = capEx;
    this.netWorkingCapitalChange = netWorkingCapitalChange;
    this.taxRate = taxRate;
    this.forecastYear = forecastYear;
    this.discountFactor = discountFactor;
    this.wacc = wacc;
    this.growthRate = growthRate;
  }

  public void setYear(int x) {
    this.year = x;
  }

  public void setSales(double s) {
    this.sales = s;
  }
  public double getSales() {
    return this.sales;
  }

  public void setGrowthRate(double x) {
    this.growthRate = x;
  }
  public double getGrowthRate() {
    return this.growthRate;
  }

  public void setFCF(double f) {
    this.freeCashFlow = f;
  }
  public double getFCF() {
    return this.freeCashFlow;
  }

  public double getDiscountedFCF() {
    return this.discountedFCF;
  }

  public void setDiscountFactor(double d) {
    this.discountFactor = d;
  }
  public void setEbit(double e) {
    this.ebit = e;
  }
  public void setDandA(double d) {
    this.DandA = d;
  }
  public void setCapEx(double c) {
    this.capEx = c;
  }
  public void setNwc(double n) {
    this.netWorkingCapitalChange = n;
  }
  public void setTaxRate(double t) {
    this.taxRate = t;
  }
  public void setForecastYear(double y) {
    this.forecastYear = y;
  }
  public double getForecastYear() {
    return this.forecastYear;
  }
  public void setDCF(double d) {
    this.discountedFCF = d;
  }

  public void setWacc(double w) {
    this.wacc = w;
  }

  public double getYear() {
    return this.year;
  }

  public double getEbit() {
    return this.ebit;
  }

  public double getWacc() {
    return this.wacc;
  }

  public double computeFCF() {
    freeCashFlow = ebit * (1-taxRate) + DandA - capEx - netWorkingCapitalChange;
    return freeCashFlow;
  }

  public double computeDiscountFactor() {
       discountFactor = Math.pow((1 + wacc), forecastYear);
       return discountFactor;
  }

  public double getDiscountFactor() {
    return this.discountFactor;
  }

  public double computeDiscountedFCF() { 
    discountedFCF = freeCashFlow / discountFactor;
    return discountedFCF;  
  }

  // public double computeFCFModelComponents() {
  //   for(Forecast x: this.ForecastForecast) {
  //     System.out.print("HI");
  //   }
  // }

   public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Year ");
    sb.append(year + "\n");
    sb.append("Sales: \t\t");
    sb.append(sales + "\n");
    sb.append("Growth Rate: \t");
    sb.append(growthRate + "\n");
    sb.append("FCF: \t");
    sb.append(freeCashFlow + "\n");
    sb.append("EBIT: \t");
    sb.append(ebit + "\n");
    sb.append("D&A: \t");
    sb.append(DandA + "\n");
    sb.append("CapEx: \t");
    sb.append(capEx + "\n");
    sb.append("^NWC: \t");
    sb.append(netWorkingCapitalChange + "\n");
    sb.append("ForecastYear: \t");
    sb.append(forecastYear + "\t");
    sb.append("Taxes: \t");
    sb.append(taxRate + "\n \n");
    return sb.toString();
  }
}
