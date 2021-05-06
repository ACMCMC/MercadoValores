package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;

public class DAORoles extends DAO {

    public DAORoles(Connection con) {
        super(con);
    }
    
    private void setRol(String rol) {
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
    
        try {
            preparedStatement = c
                    .prepareStatement("SET ROLE " + rol); // No es vulnerable a inyección SQL porque el string no viene del usuario, además no se puede usar sustitución de parámetros en este caso
            preparedStatement.execute();
            preparedStatement.close();
            c.commit();
        } catch (SQLException e) {
            FachadaAplicacion.muestraExcepcion(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                FachadaAplicacion.muestraExcepcion(e);
            }
        }
    }
    
    public void setRolRegulador() {
        setRol("regulador_user");
    }
    
    public void setRolEmpresa() {
        setRol("empresa_user");
    }
    
    public void setRolInversor() {
        setRol("inversor_user");
    }
    
    public void resetRol() {
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
    
        try {
            preparedStatement = c
                    .prepareStatement("reset role");
            preparedStatement.execute();
            preparedStatement.close();
            c.commit();
        } catch (SQLException e) {
            FachadaAplicacion.muestraExcepcion(e);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                FachadaAplicacion.muestraExcepcion(e);
            }
        }
    }
    
}
