package dds.monedero.model;

import java.time.LocalDate;

abstract public class Movimiento {
  private LocalDate fecha;
  private double monto;

  abstract boolean isDeposito();

  public Movimiento(LocalDate fecha, double monto) {
    this.fecha = fecha;
    this.monto = monto;
  }

  public double getMonto() {
    return monto;
  }

  public LocalDate getFecha() {
    return fecha;
  }

  /*
    public boolean fueDepositado(LocalDate fecha) {
      return isDeposito() && esDeLaFecha(fecha);
    }

    public boolean fueExtraido(LocalDate fecha) {
      return isExtraccion() && esDeLaFecha(fecha);
    }

    public boolean esDeLaFecha(LocalDate fecha) {
      return this.fecha.equals(fecha);
    }

    public boolean isDeposito() {
      return esDeposito;
    }

    public boolean isExtraccion() {
      return !esDeposito;
    }

  public void agregateA(Cuenta cuenta) {
    cuenta.setSaldo(calcularValor(cuenta));
    cuenta.agregarMovimiento(fecha, monto);
    //Esto quizas puedo hacerlo directamente en la cuenta

  }

  public double calcularValor(Cuenta cuenta) {
    if (esDeposito) {
      return cuenta.getSaldo() + getMonto();
    } else {
      return cuenta.getSaldo() - getMonto();
      //En vez de poner deposito true puedo hacer una subclase de Movimiento y que cada una calcule el valor

    }
  }
}
*/

}

 class Deposito extends Movimiento{
  public Deposito(LocalDate fecha, double monto) {
    super(fecha, monto);
  }
  public boolean isDeposito() { return true;}

  public double calcularValor(Cuenta cuenta){
    return cuenta.getSaldo() + getMonto();
  }


}

 class Extraccion extends Movimiento{
  public Extraccion(LocalDate fecha, double monto) {
    super(fecha, monto);
  }

  public boolean isDeposito() { return false; }

  public double calcularValor(Cuenta cuenta){
    return cuenta.getSaldo() - getMonto();
  }
}
