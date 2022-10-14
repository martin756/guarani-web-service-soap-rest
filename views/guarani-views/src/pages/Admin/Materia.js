import React, { useMemo} from 'react'
import { useLocation } from 'react-router-dom';
import FormMateria from '../../components/FormMateria';

function Materia() {
    const { search } = useLocation();
    let query = useMemo(() => new URLSearchParams(search), [search]);

    const idMateria = query.get('id')
  return (
    <>
      <div className="container">
          <div className="row g-5 mt-4" style={{justifyContent: 'center'}}>
          <div className="col-md-6 col-lg-7">
              {idMateria == null ?
              <FormMateria name="Agregar materia"/> :
              <FormMateria name="Editar materia" idMateria={idMateria}/>}
          </div>
          </div>
      </div>
    </>
  )
}

export default Materia