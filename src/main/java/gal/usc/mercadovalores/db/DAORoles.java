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
            c.setAutoCommit(false);
            preparedStatement = c
                    .prepareStatement("set role ?");
            preparedStatement.setString(1, rol);
            preparedStatement.executeUpdate();
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
        setRol("ReguladorUser");
    }
    
    public void setRolEmpresa() {
        setRol("EmpresaUser");
    }
    
    public void setRolInversor() {
        setRol("InversorUser");
    }
    
    public void resetRol() {
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
    
        try {
            c.setAutoCommit(false);
            preparedStatement = c
                    .prepareStatement("reset role");
            preparedStatement.executeUpdate();
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
