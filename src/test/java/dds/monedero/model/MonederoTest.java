package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  void SiSeHaceUnIngresoEntoncesLaCuentaTieneEseSaldo() {
    cuenta.poner(1500);
    assertEquals(cuenta.getSaldo(),1500);
  }

  @Test
  void NoSePuedeIngregarUnMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  void SiSeHacen3DepositosLaCuentaTieneElSaldoTotal() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
    assertEquals(cuenta.getSaldo(),3856);

  }

  @Test
  void NoSePuedenRealizarMasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
          cuenta.poner(1500);
          cuenta.poner(456);
          cuenta.poner(1900);
          cuenta.poner(245);
    });
  }

  @Test
  void NoSePuedeExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
          cuenta.setSaldo(90);
          cuenta.sacar(1001);
    });
  }

  @Test
  public void NoSePuedeSuperarElLimite() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.sacar(1001);
    });
  }

  @Test
  public void SiSeExtraeDineroSeRestaDelLimite(){
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(2000);
      cuenta.sacar(400);
      cuenta.sacar(700);
    });
  }

  @Test
  public void NoSePuedeExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  }

  @Test
  public void SiSeAgregaDineroYSeSacaHay2Movimientos(){
    cuenta.setSaldo(0);
    cuenta.poner(1000);
    cuenta.sacar(500);
    assertEquals(cuenta.getMovimientos().size(),2 );
  }
}

