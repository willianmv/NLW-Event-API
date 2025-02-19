package br.com.nlw.events.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 70, message = "Nome deve ter entre 2 e 70 caracteres")
    @Column(name = "user_name", nullable = false)
    private String name;

    @NotBlank(message = "Email é obrigatório.")
    @Email(message = "Email inválido.")
    @Size(max = 255, message = "Email não pode ter mais de 255 caracteres.")
    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String userName) {
        this.name = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
