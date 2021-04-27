package gal.usc.mercadovalores.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import gal.usc.mercadovalores.aplicacion.Beneficios;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.Participacion;
import gal.usc.mercadovalores.aplicacion.UsuarioDeMercado;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;

public class DAOParticipaciones extends DAO<Participacion> {
    public DAOParticipaciones(Connection con) {
        super(con);
    }

    
    public void crearParticipaciones(UsuarioEmpresa u, Integer x) throws SQLException {
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        Participacion participaciones;

        try {
            participaciones = tenerParticipaciones(u, u);
            if (participaciones != null) {
                preparedStatement = c.prepareStatement(
                        "update tener_participaciones set num_participaciones=? where id1=? and id2=?");
                preparedStatement.setInt(1, x + participaciones.getNumero());
                preparedStatement.setString(2, u.getId());
                preparedStatement.setString(3, u.getId());
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = c.prepareStatement("insert into tener_participaciones values(?,?,?)");
                preparedStatement.setString(1, u.getId());
                preparedStatement.setString(2, u.getId());
                preparedStatement.setInt(3, x);
                preparedStatement.executeUpdate();
            }

            preparedStatement.close();
            preparedStatement = c.prepareStatement("update usuario_empresa set importe_bloqueado=? where id=?");
            //preparedStatement.setDouble(1,
            //FachadaDB.getFachada().getParticipacionesEmpresa(u) * this.getImportePorParticipacion(u));
            preparedStatement.setString(2, u.getId());
            preparedStatement.executeUpdate();

            c.commit();

        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                FachadaAplicacion.muestraExcepcion(e);
            }
        }
    }

    public void bajaParticipaciones(UsuarioEmpresa u, Integer x) throws SQLException {
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        Participacion participaciones;

        try {
            c.setAutoCommit(false);
            participaciones = tenerParticipaciones(u, u);
            if (participaciones != null) {
                preparedStatement = c.prepareStatement(
                        "update tener_participaciones set num_participaciones=? where id1=? and id2=?");
                preparedStatement.setInt(1, participaciones.getNumero() - x);
                preparedStatement.setString(2, u.getId());
                preparedStatement.setString(3, u.getId());
            } else {
                preparedStatement = c.prepareStatement("delete from tener_participaciones where id1=? and id2=?");
                preparedStatement.setString(1, u.getId());
                preparedStatement.setString(2, u.getId());

            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            preparedStatement = c.prepareStatement("update usuario_empresa set importe_bloqueado=? where id=?");
            //preparedStatement.setDouble(1, this.numeroParticipacionesTotales(u) * this.getImportePorParticipacion(u));
            preparedStatement.setString(2, u.getId());
            preparedStatement.executeUpdate();
            c.commit();

        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                FachadaAplicacion.muestraExcepcion(e);
            }
        }
    }

    /**
     * Devuelve un objeto Participacion entre u1 y u2. Si el numero de
     * participaciones es 0 o no hay una entrada en la BD, se devuelve null.
     * 
     * @param u1
     * @param u2
     * @return
     * @throws SQLException
     */
    public Participacion tenerParticipaciones(UsuarioDeMercado u1, UsuarioEmpresa u2) throws SQLException {
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        Participacion result = null;
        try {
            c.setAutoCommit(false);
            preparedStatement = c
                    .prepareStatement("select num_participaciones from tener_participaciones where id1=? and id2=?");
            preparedStatement.setString(1, u1.getId());
            preparedStatement.setString(2, u2.getId());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {// Si la consulta devuelve 0 tuplas no entra aqui y se devuelve 0
                if (resultSet.getInt("num_participaciones") > 0) {
                    try {
                        result = new Participacion(u1, u2, resultSet.getInt("num_participaciones"));
                    } catch (EnumConstantNotPresentException e) {
                        FachadaAplicacion.muestraExcepcion(e);
                    }
                }
            }
            c.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                FachadaAplicacion.muestraExcepcion(e);
            }
        }
        return result;
    }

    public void venderParticipaciones(Participacion p, UsuarioDeMercado u, Integer numAVender, Timestamp fechapublicacion)
            throws SQLException {// El que vende, elque compra , cuanto se vende
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        FachadaDB f = FachadaDB.getFachada();
        Participacion participaciones;

        try {
            c.setAutoCommit(false);
            participaciones = tenerParticipaciones(u, p.getEmpresa());// Nº Participaciones que tiene el usuario que
                                                                      // compra
            if (participaciones != null) {
                preparedStatement = c.prepareStatement(// Le sumamos al numero al que compra
                        "update tener_participaciones set num_participaciones=? where id1=? and id2=?");
                preparedStatement.setInt(1, numAVender + participaciones.getNumero());
                preparedStatement.setString(2, u.getId());
                preparedStatement.setString(3, p.getEmpresa().getId());
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } else {
                preparedStatement = c.prepareStatement(// Añadimos la tupla a la tabla
                        "insert into tener_participaciones values (?,?,?)");
                preparedStatement.setString(1, u.getId());
                preparedStatement.setString(2, p.getEmpresa().getId());
                preparedStatement.setInt(3, numAVender);
                preparedStatement.executeUpdate();
                preparedStatement.close();
            }

            if (p.getNumero() - numAVender == 0) {// Si no es cero esta resta se actualiza el valor si no se elimina de la
                                         // tabla
                preparedStatement = c.prepareStatement(// Le restamos al numero al que vende
                        "delete from tener_participaciones where id1=? and id2=?");
                preparedStatement.setString(1, p.getUsuarioMercado().getId());
                preparedStatement.setString(2, p.getEmpresa().getId());
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = c.prepareStatement(// Le restamos al numero al que vende
                        "update tener_participaciones set num_participaciones=? where id1=? and id2=?");
                preparedStatement.setInt(1, p.getNumero() - numAVender);
                preparedStatement.setString(2, p.getUsuarioMercado().getId());
                preparedStatement.setString(3, p.getEmpresa().getId());
                preparedStatement.executeUpdate();
            }
            preparedStatement.close();

            f.confirmarVenta(p.getUsuarioMercado().getId(), p.getEmpresa().getId(), fechapublicacion);

            c.commit();

        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                FachadaAplicacion.muestraExcepcion(e);
            }
        }
    }

    public Set<Participacion> getAll() {
        FachadaDB f = FachadaDB.getFachada();
        Connection c = startTransaction();
        Set<Participacion> setFinal = new HashSet<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            preparedStatement = c.prepareStatement("select * from tener_participaciones");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Participacion p;
                try {
                    String id = resultSet.getString("id1");
                    String id2 = resultSet.getString("id2");
                    Integer numero = resultSet.getInt("num_participaciones");

                    p = new Participacion((UsuarioDeMercado) f.getUsuarioById(id),
                            (UsuarioEmpresa) f.getUsuarioById(id2), numero);
                    setFinal.add(p);
                } catch (EnumConstantNotPresentException e) {
                    FachadaAplicacion.muestraExcepcion(e);
                }
            }
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

        return setFinal;
    }

    public double getImportePorParticipacion(UsuarioEmpresa u) throws SQLException {
        double ret = 0;
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet;
        try {
            c.setAutoCommit(false);
            preparedStatement = c.prepareStatement("select importe_por_participacion from beneficios where id=?");
            preparedStatement.setString(1, u.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {// Si la consulta devuelve 0 tuplas no entra aqui y se devuelve 0
                try {
                    ret = resultSet.getDouble("importe_por_participacion");
                } catch (EnumConstantNotPresentException e) {
                    FachadaAplicacion.muestraExcepcion(e);
                }
            }
            c.commit();
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                FachadaAplicacion.muestraExcepcion(e);
            }
        }
        return ret;

    }

    public Set<Participacion> getAllUsuarioMercado(UsuarioDeMercado u) {
        FachadaDB f = FachadaDB.getFachada();
        Connection c = startTransaction();
        Set<Participacion> setFinal = new HashSet<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            preparedStatement = c.prepareStatement("select * from tener_participaciones where id1=?");
            preparedStatement.setString(1, u.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Participacion p;
                try {
                    String id2 = resultSet.getString("id2");
                    Integer numero = resultSet.getInt("num_participaciones");

                    p = new Participacion((UsuarioDeMercado) u, (UsuarioEmpresa) f.getUsuarioById(id2), numero);
                    setFinal.add(p);
                } catch (EnumConstantNotPresentException e) {
                    FachadaAplicacion.muestraExcepcion(e);
                }
            }
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
        return setFinal;
    }

    public Set<Participacion> getAllEmpresa(UsuarioEmpresa u) {
        FachadaDB f = FachadaDB.getFachada();
        Connection c = startTransaction();
        Set<Participacion> setFinal = new HashSet<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            preparedStatement = c.prepareStatement("select * from tener_participaciones where id2=?");
            preparedStatement.setString(1, u.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Participacion p;
                try {
                    String id1 = resultSet.getString("id1");
                    Integer numero = resultSet.getInt("num_participaciones");

                    p = new Participacion((UsuarioDeMercado) f.getUsuarioById(id1), u, numero);
                    setFinal.add(p);
                } catch (EnumConstantNotPresentException e) {
                    FachadaAplicacion.muestraExcepcion(e);
                }
            }
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
        return setFinal;
    }

    public void altaBeneficios(UsuarioEmpresa u, double porcentaje, Timestamp fecha) {
        Connection c = startTransaction();
        Set<Participacion> setFinal = new HashSet<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            preparedStatement = c.prepareStatement("insert into beneficios values(?,?,?)");
            preparedStatement.setString(1, u.getId());
            preparedStatement.setTimestamp(2, fecha);
            preparedStatement.setDouble(3, porcentaje);
            preparedStatement.executeUpdate();
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

     public Set<Beneficios> getAllBeneficios() {
        FachadaDB f = FachadaDB.getFachada();
        Connection c = startTransaction();
        Set<Beneficios> setFinal = new HashSet<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet;

        try {
            preparedStatement = c.prepareStatement("select * from beneficios");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Beneficios b;
                try {
                    String id1 = resultSet.getString("id");
                    Timestamp t = resultSet.getTimestamp("fecha_pago");
                    Double precio = resultSet.getDouble("importe_por_participacion");
                    b = new Beneficios((UsuarioEmpresa)f.getUsuarioById(id1),t,precio);
                    setFinal.add(b);
                } catch (EnumConstantNotPresentException e) {
                    FachadaAplicacion.muestraExcepcion(e);
                }
            }
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
        return setFinal;
    }
    
    public void BajaBeneficios(Beneficios b) {
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = c.prepareStatement("delete from beneficios where id=? and fecha_pago=?");
            preparedStatement.setString(1, b.getEmpresa().getId());
            preparedStatement.setTimestamp(2, b.getFecha());
            preparedStatement.executeUpdate();
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

    public void pagoBeneficios(UsuarioEmpresa u, double pagoPorParticipacion) {
        Connection c = startTransaction();
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = c.prepareStatement("select pagar_beneficios(?,?)");
            preparedStatement.setString(1, u.getId());
            preparedStatement.setDouble(2, pagoPorParticipacion);
            preparedStatement.executeUpdate();
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
    
    private double calcularBeneficioUsuario(String u,UsuarioEmpresa u2){//FUNCION AUXILIAR PARA PGAO BENEFICIOS
        double ret=0.0;
        /*Connection c = startTransaction();
        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        PreparedStatement preparedStatement3 = null;
        ResultSet resultSet;
        try {

            preparedStatement = c.prepareStatement("select id1 from tener_participaciones where id2=?");
            preparedStatement.setString(1, u.getId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String id = resultSet.getString("id1");
                preparedStatement2 = c.prepareStatement("update usuario_mercado set saldo=? where id=?");
                preparedStatement2.setDouble(1, calcularBeneficioUsuario(id, u));
                preparedStatement2.setString(2, id);
                preparedStatement2.executeUpdate();

            }
            c.commit();
        } catch (SQLException e) {
            FachadaAplicacion.muestraExcepcion(e);
        } finally {
            try {
                preparedStatement.close();
                preparedStatement2.close();
            } catch (SQLException e) {
                FachadaAplicacion.muestraExcepcion(e);
            }
        }*/
        return ret;
    }
}