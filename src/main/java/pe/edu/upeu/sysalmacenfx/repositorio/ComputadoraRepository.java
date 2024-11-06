// ComputadoraRepository.java
package pe.edu.upeu.sysalmacenfx.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sysalmacenfx.modelo.Computadora;

@Repository
public interface ComputadoraRepository extends JpaRepository<Computadora, Long> {
    // Aquí puedes agregar consultas personalizadas si las necesitas
}