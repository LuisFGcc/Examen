package pe.edu.upeu.sysalmacenfx.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import pe.edu.upeu.sysalmacenfx.modelo.Computadora;
import pe.edu.upeu.sysalmacenfx.repositorio.ComputadoraRepository;

import jakarta.validation.Valid;
import java.util.List;

@Service
@Validated
public class ComputadoraService {
    @Autowired
    private ComputadoraRepository computadoraRepository;

    public List<Computadora> listarTodo() {
        return computadoraRepository.findAll();
    }

    public Page<Computadora> listarPaginado(Pageable pageable) {
        return computadoraRepository.findAll(pageable);
    }

    public Computadora guardar(@Valid Computadora computadora) {
        return computadoraRepository.save(computadora);
    }

    public void eliminar(Long id) {
        computadoraRepository.deleteById(id);
    }

    public Computadora buscarPorId(Long id) {
        return computadoraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Computadora no encontrada"));
    }
}