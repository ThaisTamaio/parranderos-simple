package net.javaguides.banking.servicios;
import org.springframework.stereotype.Service;
import net.javaguides.banking.repositorio.SirvenRepository;
import jakarta.transaction.Transactional;

@Service
public class SirvenServicio{
    private SirvenRepository sirvenRepository;

    public SirvenServicio(SirvenRepository sirvenRepository)
    {
        this.sirvenRepository = sirvenRepository;
    }

    @Transactional
    public void transferir_bebida(int bebida, int bar_id_origen, String horario,  int bar_id_destino)
    {
        sirvenRepository.eliminarSirven(bar_id_origen, bebida, horario);
        //sirvenRepository.insertarSirven(bar_id_destino, bebida, horario);
    }
}