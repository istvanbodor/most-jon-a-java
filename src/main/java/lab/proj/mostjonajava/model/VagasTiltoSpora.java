package lab.proj.mostjonajava.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class VagasTiltoSpora extends Spora {
    public void hatasKifejtese() {}
    public Spora ujSporaLetrehozasa() { return null; }
}
