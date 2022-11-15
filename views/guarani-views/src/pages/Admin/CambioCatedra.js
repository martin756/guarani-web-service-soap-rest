import React, { useEffect, useState } from 'react'
import TableSolicitud from '../../components/TableSolicitud'
import { useNavigate } from 'react-router-dom'
import { traerDatos } from '../../components/Data'
import { PencilSquare, Check } from 'react-bootstrap-icons'

function CambioCatedra() {
  const baseUrl = "http://localhost:8080/cambioCatedra"
  const [cambioCatedra, setCambioCatedra] = useState([])
  const navigate = useNavigate()

  useEffect(() => {
    const fetchData = async() =>{
      try { setCambioCatedra(await traerDatos(baseUrl)) }
      catch (error) { alert(error) }
    }
    fetchData()
  }, [])
  
  return (
    <>
      <div className="container pt-5">
        <div className="row">
          <h4 className="mb-3 text-center">Peticiones pendientes</h4>
          <TableSolicitud data={cambioCatedra} linkPage='CambioCatedra' actions={{aceptar: <Check/>, rechazar: <PencilSquare/>}}/>

        </div>
      </div>
    </>
  )
}

export default CambioCatedra