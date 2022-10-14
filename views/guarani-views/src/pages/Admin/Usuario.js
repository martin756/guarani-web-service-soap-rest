import React, { useMemo} from 'react'
import { useLocation } from 'react-router-dom';
import FormUser from '../../components/FormUser'

function Usuario() {
    const { search } = useLocation();
    let query = useMemo(() => new URLSearchParams(search), [search]);

    const idUsuario = query.get('id')
  return (
    <>
      <div className="container">
          <div className="row g-5 mt-4" style={{justifyContent: 'center'}}>
          <div className="col-md-6 col-lg-7">
              {idUsuario == null ?
              <FormUser name="Nuevo usuario"/> :
              <FormUser name="Editar usuario" idUsuario={idUsuario}/>}
          </div>
          </div>
      </div>
    </>
  )
}

export default Usuario