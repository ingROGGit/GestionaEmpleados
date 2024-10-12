package com.gestion.empleados.entity;

import java.io.Serializable;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.gestion.empleados.listener.AuditoryUsuariosListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners({ AuditingEntityListener.class,AuditoryUsuariosListener.class})
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
    private Boolean bloqueado;
    
    @Column(name = "disabled")
    private Boolean disabled;
    
    @ManyToMany(fetch=FetchType.EAGER,targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name="usu_roles", joinColumns = @JoinColumn(name="usu_id"),inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<RoleEntity> roles;
    
}
