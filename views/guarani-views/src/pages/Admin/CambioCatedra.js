import React, { useEffect, useState } from 'react'
import TableSolicitud from '../../components/TableSolicitud'
import { traerDatos } from '../../components/Data'
import { Check } from 'react-bootstrap-icons'
import { BASEURL } from '../../const/config'

function CambioCatedra() {
  const [cambioCatedra, setCambioCatedra] = useState([])
  const fetchData = async () => {
    try { setCambioCatedra(await traerDatos(BASEURL)) }
    catch (error) { alert(error) }
  }
  useEffect(() => {
    fetchData()
  }, [])

  return (
    <>
      <div className="container pt-5">
        <div className="row">
          <h4 className="mb-3 text-center">Peticiones pendientes</h4>
          <TableSolicitud data={cambioCatedra} fetchData={fetchData}linkPage='CambioCatedra' actions={{ aceptar: <Check /> }} />

        </div>
      </div>
    </>
  )
}

export default CambioCatedra