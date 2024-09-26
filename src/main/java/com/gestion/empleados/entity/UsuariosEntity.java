package com.gestion.empleados.entity;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *
 * @author ROLIVAREZ
 */
@Data
@Builder
@Entity
@Table(name = "usuarios", catalog = "db_gestion_empleados", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class UsuariosEntity extends AuditableDateEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(max = 20)
    @Column(name = "username")
    @NotBlank
    private String username;
    @Size(max = 100)
    @Column(name = "pass")
    @NotBlank
    private String pass;
    @Column(name = "bloqueado")
    @NotBlank
    private Boolean bloqueado;
    @Column(name = "disabled")
    @NotBlank
    private Boolean disabled;
    
    @ManyToMany(fetch=FetchType.EAGER,targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name="usu_roles", joinColumns = @JoinColumn(name="usu_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<RoleEntity> roles;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuarios")
//    @OneToMany(mappedBy = "usuarios", fetch=FetchType.EAGER)
//    private List<UserRole> userRoleList;
    
//    public Usuarios() {
//    }

//    public String getUsu() {
//        return usu;
//    }
//
//    public void setUsu(String usu) {
//        this.usu = usu;
//    }
//
//    public String getPass() {
//        return pass;
//    }
//
//    public void setPass(String pass) {
//        this.pass = pass;
//    }
//
//    public Boolean getBloqueado() {
//        return bloqueado;
//    }
//
//    public void setBloqueado(Boolean bloqueado) {
//        this.bloqueado = bloqueado;
//    }
//
//    public Boolean getDisabled() {
//        return disabled;
//    }
//
//    public void setDisabled(Boolean disabled) {
//        this.disabled = disabled;
//    }


//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof UsuariosEntity)) {
//            return false;
//        }
//        UsuariosEntity other = (UsuariosEntity) object;
//        if ((this.usu == null && other.usu != null) || (this.usu != null && !this.usu.equals(other.usu))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "com.gestion.empleados.JPA.Usuarios[ usu=" + usu + " ]";
//    }
//    public List<UserRole> getUserRoleList() {
//        return userRoleList;
//    }
//
//    public void setUserRoleList(List<UserRole> userRoleList) {
//        this.userRoleList = userRoleList;
//    }
    
}
