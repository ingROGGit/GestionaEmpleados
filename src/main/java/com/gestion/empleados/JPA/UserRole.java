/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.empleados.JPA;

import java.io.Serializable;
import java.util.Date;
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.NamedQueries;
//import javax.persistence.NamedQuery;
//import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;
//import javax.validation.constraints.NotNull;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
/**
 *
 * @author ROLIVAREZ
 */
@Entity
@Table(name = "user_role", catalog = "db_gestion_empleados", schema = "public")
@NamedQueries({
@NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")})
@IdClass(UserRolePK.class)
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;
//    @EmbeddedId
//    protected UserRolePK userRolePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "granted_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date grantedDate;
    @JoinColumn(name = "usu", referencedColumnName = "usu", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuarios usuarios;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "usu", nullable = false, length = 20)
    private String usu;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "role", nullable = false, length = 20)
    private String role;
    
    public UserRole() {
    }

    public Date getGrantedDate() {
        return grantedDate;
    }

    public void setGrantedDate(Date grantedDate) {
        this.grantedDate = grantedDate;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof UserRole)) {
//            return false;
//        }
//        UserRole other = (UserRole) object;
//        if ((this.userRolePK == null && other.userRolePK != null) || (this.userRolePK != null && !this.userRolePK.equals(other.userRolePK))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.gestion.empleados.JPA.UserRole[ userRolePK=" + userRolePK + " ]";
//    }
    
}
