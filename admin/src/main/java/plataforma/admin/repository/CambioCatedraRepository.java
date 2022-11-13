package plataforma.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import plataforma.admin.models.CambioCatedra;

public interface CambioCatedraRepository extends CrudRepository<CambioCatedra, Integer>  {
 
    @Query("SELECT c FROM CambioCatedra c WHERE c.solicitud= 'Pendiente'")
    public abstract List<CambioCatedra> getCambioCatedraPendiente();


}

