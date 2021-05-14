package dds.monedero.model;

import java.time.LocalDate;

abstract public class Movimiento {
  private LocalDate fecha;
  private double monto;

  abstract boolean isDeposito();
  abstract double calcularValor(Cuenta cuenta);

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
