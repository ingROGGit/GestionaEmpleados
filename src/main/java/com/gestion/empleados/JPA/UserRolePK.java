/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gestion.empleados.JPA;

import java.io.Serializable;
//import javax.persistence.Basic;
//import javax.persistence.Column;
//import javax.persistence.Embeddable;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

/**
 *
 * @author ROLIVAREZ
 */
public class UserRolePK implements Serializable {

    private String usu;
    private String role;

//    public UserRolePK() {
//    }
//
//    public UserRolePK(String usu, String role) {
//        this.usu = usu;
//        this.role = role;
//    }
//
//    public String getUsu() {
//        return usu;
//    }
//
//    public void setUsu(String usu) {
//        this.usu = usu;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usu != null ? usu.hashCode() : 0);
        hash += (role != null ? role.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserRolePK)) {
            return false;
        }
        UserRolePK other = (UserRolePK) object;
        if ((this.usu == null && other.usu != null) || (this.usu != null && !this.usu.equals(other.usu))) {
            return false;
        }
        if ((this.role == null && other.role != null) || (this.role != null && !this.role.equals(other.role))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gestion.empleados.JPA.UserRolePK[ usu=" + usu + ", role=" + role + " ]";
    }
    
}
