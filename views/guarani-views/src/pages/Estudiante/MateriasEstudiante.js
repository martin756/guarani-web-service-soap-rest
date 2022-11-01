import React, { useEffect, useState } from 'react'
import Table from '../../components/Table'
import { traerDatos } from '../../components/Data'
import Cookies from 'universal-cookie'

function MateriasEstudiante() {
  const baseUrl = "https://localhost:5003/api/EstudianteService/"
  const [materias, setMaterias] = useState([])
  const [tipoInscripcion, setTipoInscripcion] = useState([])
  const cookies = new Cookies()

  useEffect(() => {
    const fetchData = async() =>{
      try {
        setTipoInscripcion(await traerDatos("http://localhost:8080/fechas_inscripcion"))
        const arrayMaterias = await traerDatos(baseUrl+"ListadoInscripciones?idUsuario="+cookies.get('Idusuario'))
        arrayMaterias.filter(element=>{
          if(element.es_final){
            delete element.dia
          }else{
            delete element.fecha
          }
          delete element.es_final
        })
        setMaterias(arrayMaterias)
      }
      catch (error) {
        alert(error.response.status + " " + error.response.data.errorCode + "\n" + error.response.data.message)
      }
    }
    fetchData()
  }, [])
  return (
    <>
      <div className="container pt-5">
        <div className="row">
          <h4 className="mb-3 text-center">Listado de materias {tipoInscripcion.es_final ? "a finales" : "a cuatrimestres"}</h4>
          <Table data={materias} linkPage={baseUrl} inscripcion={true}/>
        </div>
      </div>
    </>
  )
}

export default MateriasEstudiante