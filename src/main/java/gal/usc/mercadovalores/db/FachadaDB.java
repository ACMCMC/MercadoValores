/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gal.usc.mercadovalores.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import gal.usc.mercadovalores.aplicacion.AnuncioVenta;
import gal.usc.mercadovalores.aplicacion.Beneficios;
import gal.usc.mercadovalores.aplicacion.Compra;
import gal.usc.mercadovalores.aplicacion.FachadaAplicacion;
import gal.usc.mercadovalores.aplicacion.Participacion;
import gal.usc.mercadovalores.aplicacion.Usuario;
import gal.usc.mercadovalores.aplicacion.UsuarioDeMercado;
import gal.usc.mercadovalores.aplicacion.UsuarioEmpresa;
import gal.usc.mercadovalores.aplicacion.UsuarioInversor;
import gal.usc.mercadovalores.aplicacion.UsuarioRegulador;

/**
 *
 * @author acmc
 */
public class FachadaDB {

    private static FachadaDB fachada = new FachadaDB();
    private Connection conexion;
    private DAOUsuarioEmpresa daoUsuarioEmpresa;
    private DAOUsuarioInversor daoUsuarioInversor;
    private DAOUsuarioRegulador daoUsuarioRegulador;
    private DAOParticipaciones daoParticipaciones;
    private DAOVentas daoVentas;
    private DAOUsuario daoUsuario;

    public static FachadaDB getFachada() {
        return fachada;
    }

    private FachadaDB() {

        Properties configuracion = new Properties();
        FileInputStream arqConfiguracion;

        try {
            arqConfiguracion = new FileInputStream("baseDatos.properties");
            configuracion.load(arqConfiguracion);
            arqConfiguracion.close();

            Properties usuario = new Properties();

            String gestor = configuracion.getProperty("gestor");

            usuario.setProperty("user", configuracion.getProperty("usuario"));
            usuario.setProperty("password", configuracion.getProperty("clave"));
            usuario.setProperty("ssl", "true");
            String url = "jdbc:" + gestor + "://" + configuracion.getProperty("servidor") + ":"
                    + configuracion.getProperty("puerto") + "/" + configuracion.getProperty("baseDatos");
            this.conexion = java.sql.DriverManager.getConnection(url, usuario);
            this.conexion.setAutoCommit(false);
        } catch (FileNotFoundException f) {
            FachadaAplicacion.muestraExcepcion(f);
        } catch (IOException i) {
            FachadaAplicacion.muestraExcepcion(i);
        } catch (java.sql.SQLException e) {
            FachadaAplicacion.muestraExcepcion(e);
        }
        daoUsuarioRegulador = new DAOUsuarioRegulador(conexion);
        daoUsuarioEmpresa = new DAOUsuarioEmpresa(conexion);
        daoUsuarioInversor = new DAOUsuarioInversor(conexion);
        daoParticipaciones = new DAOParticipaciones(conexion);
        daoVentas = new DAOVentas(conexion);
        daoUsuario = new DAOUsuario(conexion);
    }

    public boolean comprobarContrasena(String contrasenaTextoPlano, String contrasenaEncriptada) {
        return daoUsuario.comprobarContrasena(contrasenaTextoPlano, contrasenaEncriptada);
    }

    public String getContrasenaEncriptada(String contrasenaTextoPlano) {
        return daoUsuario.getContrasenaEncriptada(contrasenaTextoPlano);
    }

    public Set<UsuarioEmpresa> getUsuariosEmpresa() {
        return daoUsuarioEmpresa.getAll();
    }

    public Set<UsuarioInversor> getUsuariosInversores() {
        // Aquí se devuelve null
        return daoUsuarioInversor.getAll();
    }

    public Set<UsuarioDeMercado> getUsuariosDeMercado() {
        Set<UsuarioDeMercado> set = new HashSet<>();
        set.addAll(getUsuariosEmpresa());
        set.addAll(getUsuariosInversores());
        return set;
    }

    public Set<Usuario> getUsuarios() {
        Set<Usuario> set = new HashSet<>();
        set.addAll(getUsuariosDeMercado());
        set.add(getUsuarioRegulador());
        return set;
    }

    public UsuarioRegulador getUsuarioRegulador() {
        return daoUsuarioRegulador.get();
    }

    public Usuario getUsuarioById(String id) {
        Usuario res = null;

        // si son de una empresa
        res = daoUsuarioEmpresa.getById(id);
        if (res != null) {
            return res;
        }

        res = daoUsuarioInversor.getById(id);
        if (res != null) {
            return res;
        }

        // si el id y contraseña son de regulador
        res = daoUsuarioRegulador.get();
        if (res != null && res.getId().equals(id)) {
            return res;
        }
        return null;
    }

    public void add(Usuario u) throws SQLException {
        if (u instanceof UsuarioEmpresa) {
            daoUsuarioEmpresa.add((UsuarioEmpresa) u);
        } else if (u instanceof UsuarioDeMercado) {
            daoUsuarioInversor.add((UsuarioInversor) u);
        } else {
            throw new IllegalArgumentException("No se acepta el tipo de usuario seleccionado");
        }
    }
    
    public void crearParticipacion(UsuarioEmpresa usr, int p) throws SQLException{
        daoParticipaciones.crearParticipaciones(usr,p);
    }

    public void bajaParticipacion(UsuarioEmpresa usr, int p) throws SQLException{
        daoParticipaciones.bajaParticipaciones(usr, p);
    }

    public Set<Participacion> getParticipacionesUsuarioDeMercado(UsuarioDeMercado u) {
        return daoParticipaciones.getAllUsuarioMercado(u);
    }

    public Set<Participacion> getParticipacionesEmpresa(UsuarioEmpresa u) {
        return daoParticipaciones.getAllEmpresa(u);
    }

    public void autorizarRegistro(UsuarioDeMercado u) {
        if (u instanceof UsuarioEmpresa) {
            // autorizamos registro usuario empresa
            daoUsuarioEmpresa.autorizarRegistro((UsuarioEmpresa) u);
        } else if (u instanceof UsuarioInversor) {
            // autorizamos registro de usuario inversor
            daoUsuarioInversor.autorizarRegistro((UsuarioInversor) u);
        }
    }

    public void autorizarBaja(UsuarioDeMercado u) {
        if (u instanceof UsuarioEmpresa) {
            // autorizamos la baja usuario empresa
            daoUsuarioEmpresa.delete((UsuarioEmpresa) u);
        } else if (u instanceof UsuarioInversor) {
            // autorizamos la baja de usuario inversor
            daoUsuarioInversor.delete((UsuarioInversor) u);

        }
    }

    public void solicitarBaja(UsuarioDeMercado u) {
        if (u instanceof UsuarioEmpresa) {
            // actualizamos el estado del usuario empresa
            daoUsuarioEmpresa.solicitarBaja((UsuarioEmpresa) u);
        } else if (u instanceof UsuarioInversor) {
            // actualizamos el estado del usuario inversor
            daoUsuarioInversor.solicitarBaja((UsuarioInversor) u);
        }
    }

    public void actualizarUser(UsuarioDeMercado u) {
        if (u instanceof UsuarioEmpresa) {
            daoUsuarioEmpresa.update((UsuarioEmpresa) u);
        } else if (u instanceof UsuarioInversor) {
            daoUsuarioInversor.update((UsuarioInversor) u);
        }
    }

    public void actualizarUserEID(UsuarioDeMercado u, String oldId) {
        if (u instanceof UsuarioEmpresa) {
            daoUsuarioEmpresa.updateId((UsuarioEmpresa) u, oldId);
        } else if (u instanceof UsuarioInversor) {
            daoUsuarioInversor.updateId((UsuarioInversor) u, oldId);
        }
    }

    public void actualizarComision(UsuarioRegulador u) {
        daoUsuarioRegulador.update(u);
    }

    public void removeParticipacion(UsuarioEmpresa usr, int p) throws SQLException{
        //daoUsuarioEmpresa.removeParticipacion(usr, p);
    }
    
    public void venderParticipacion(UsuarioDeMercado u1, UsuarioEmpresa u2, Integer cant, double precio, double comision) throws SQLException{
        daoVentas.publicarVenta(u1, u2, cant, precio, comision);
    }
    
    public int getParticipacionesDeEmpresaALaVentaPorUsuario(UsuarioDeMercado u1, UsuarioEmpresa u2){
        return daoVentas.getParticipacionesDeEmpresaALaVentaPorUsuario(u1.getId(), u2.getId());
    }
    
    public Set<AnuncioVenta> getAnunciosUsuario(UsuarioDeMercado usr){
        return daoVentas.getAnuncioUsuario(usr);
    }
    
    //NOn ten usos
    public Set<AnuncioVenta> getAnunciosTodos(){
        return daoVentas.getAll();
    }
    
    public Set<UsuarioEmpresa> getEmpresasConAnuncios(){
        return daoVentas.getEmpresasConAnuncios();
    }
    
    public void bajaAnuncioVenta(AnuncioVenta av) throws SQLException{
        daoVentas.retirarVenta(av.getVendedor().getId(), av.getEmpresa().getId(), av.getFecha());
    }
    
    public void anunciarBeneficios(UsuarioEmpresa usr, double precio, Timestamp date)throws SQLException{
        daoParticipaciones.altaBeneficios(usr, precio, date);
    }
    
    public Set<Beneficios> getAllBeneficios(){
       return daoParticipaciones.getAllBeneficios();
    }
    
    public Set<Beneficios> getBeneficiosEmpresa(UsuarioEmpresa usr){
       return daoParticipaciones.getBeneficiosEmpresa(usr);
    }
    
    public void bajaBeneficios(Beneficios b){
        daoParticipaciones.BajaBeneficios(b);
    }

    public void pagarBeneficiosInmediatamente(UsuarioEmpresa u, double pagoPorParticipacion) {
        daoParticipaciones.pagarBeneficiosInmediatamente(u, pagoPorParticipacion);
    }

    public void pagarAnuncioBeneficios(UsuarioEmpresa u, Timestamp fecha) {
        daoParticipaciones.pagarAnuncioBeneficios(u, fecha);
    }
    
    public String getPassEncriptada(String pass){
        return daoUsuario.getContrasenaEncriptada(pass);
    }

    public Compra comprar(UsuarioDeMercado Usuario, UsuarioEmpresa empresa,Integer numero,double precio_max_por_participacion) {
        return daoVentas.comprar(Usuario, empresa, numero, precio_max_por_participacion);
    }

    public Set<Compra> getAllCompras() {
        return daoVentas.getAllCompras();
    }

    /**
     * Puede devolver NULL, si no se han producido compras a la empresa
     * @param empresa
     * @param numCompras
     * @return
     */
    public Double getPrecioMedioComprasEmpresa(UsuarioEmpresa empresa, int numCompras) {
        return daoVentas.getPrecioMedioComprasEmpresa(empresa, numCompras);
    }
}
