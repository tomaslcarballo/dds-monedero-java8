package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private double saldo;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

  public void poner(double cuanto) {

    montoNegativo(cuanto);
    excedeDepositosDiarios();

    Deposito deposito = new Deposito(LocalDate.now(), cuanto);
    agregarMovimiento(deposito);
  }

  public void sacar(double cuanto) {

    montoNegativo(cuanto);

    if (getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
    double montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }

    Extraccion extraccion = new Extraccion(LocalDate.now(), cuanto);
    agregarMovimiento(extraccion);

  }


  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

  public void montoNegativo(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  public void excedeDepositosDiarios() {
    if (this.cantidadMovimientos()>=3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }
  }

  public double cantidadMovimientos() {
    return getMovimientos().stream().filter(Movimiento::isDeposito).count();
  }

  void agregarMovimiento(Movimiento movimiento){
    movimientos.add(movimiento);
    this.setSaldo(movimiento.calcularValor(this));
  }

}
