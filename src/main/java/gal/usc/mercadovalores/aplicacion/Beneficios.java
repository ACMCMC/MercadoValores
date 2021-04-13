package gal.usc.mercadovalores.aplicacion;
import java.sql.Timestamp;

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

}