package gal.usc.mercadovalores.aplicacion;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author 
 */
public class Beneficios{
    
    private UsuarioEmpresa empresa;
    private Timestamp fecha;
    private double importeParticipacion;

    /**
     * @return the empresa
     */
    public UsuarioEmpresa getEmpresa() {
        return empresa;
    }
    /**
     * @return the fecha
     */
    public Timestamp getFecha() {
        return fecha;
    }
    /**
     * @return the importeParticipacion
     */
    public double getImporteParticipacion() {
        return importeParticipacion;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Beneficios other = (Beneficios) obj;
        if (Double.doubleToLongBits(this.importeParticipacion) != Double.doubleToLongBits(other.importeParticipacion)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return true;
    }
    
    

}