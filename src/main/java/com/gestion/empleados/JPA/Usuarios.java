package com.gestion.empleados.JPA;

import java.io.Serializable;
import java.util.List;

//import javax.persistence.CascadeType;
//import javax.persistence.OneToMany;
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/**
 *
 * @author ROLIVAREZ
 */
@Entity
@Table(name = "usuarios", catalog = "db_gestion_empleados", schema = "public")
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Size(max = 20)
    @Column(name = "usu")
    private String usu;
    @Size(max = 100)
    @Column(name = "pass")
    private String pass;
    @Column(name = "bloqueado")
    private Boolean bloqueado;
    @Column(name = "disabled")
    private Boolean disabled;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
    @OneToMany(mappedBy = "usuarios", fetch=FetchType.EAGER)
    private List<UserRole> userRoleList;
    
    public Usuarios() {
    }

    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(Boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }


    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.usu == null && other.usu != null) || (this.usu != null && !this.usu.equals(other.usu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestion.empleados.JPA.Usuarios[ usu=" + usu + " ]";
    }
    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }
    
}
