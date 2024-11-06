package pe.edu.upeu.sysalmacenfx.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Entity
@Table(name = "computadoras")
public class Computadora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La marca es requerida")
    @Size(min = 2, max = 50, message = "La marca debe tener entre 2 y 50 caracteres")
    private String marca;

    @NotBlank(message = "El modelo es requerido")
    @Size(min = 2, max = 50, message = "El modelo debe tener entre 2 y 50 caracteres")
    private String modelo;

    @NotNull(message = "El precio es requerido")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    private Double precio;

    @NotNull(message = "El stock es requerido")
    @Min(value = 0, message = "El stock debe ser mayor o igual a 0")
    private Integer stock;

    @Size(max = 500, message = "La descripción no debe exceder los 500 caracteres")
    private String descripcion;

    @NotBlank(message = "El procesador es requerido")
    private String procesador;

    @NotNull(message = "La memoria RAM es requerida")
    @Min(value = 1, message = "La RAM debe ser al menos 1 GB")
    private Integer ram;

    @NotBlank(message = "El almacenamiento es requerido")
    private String almacenamiento;
}