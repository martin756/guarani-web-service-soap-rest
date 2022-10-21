import React, { useEffect, useState } from 'react'
import Table from '../../components/Table'
import { traerDatos } from '../../components/Data'
import { BoxArrowInUpLeft } from 'react-bootstrap-icons'
import Cookies from 'universal-cookie'

function MateriasDocente() {
  const baseUrl = "https://localhost:5001/api/DocenteService/MateriasAsignadas/"
  const [materias, setMaterias] = useState([])
  const cookies = new Cookies()

  useEffect(() => {
    const fetchData = async() =>{
      try { setMaterias(await traerDatos(baseUrl+cookies.get('Idusuario'))) }
      catch (error) { alert(error) }
    }
    fetchData()
  }, [])
  return (
    <>
      <div className="container pt-5">
        <div className="row">
          <h4 className="mb-3 text-center">Listado de materias asignadas</h4>
          <Table data={materias} linkPage='listadoalumnos' actions={{edit: <BoxArrowInUpLeft/>}}/>
        </div>
      </div>
    </>
  )
}

export default MateriasDocente