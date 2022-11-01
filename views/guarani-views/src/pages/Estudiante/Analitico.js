import React, { useEffect, useState } from 'react'
import Table from '../../components/Table'
import { descargarArchivo, traerDatos } from '../../components/Data'
import Cookies from 'universal-cookie'
import { Download } from 'react-bootstrap-icons'

function Analitico() {
  const cookies = new Cookies()
  const baseUrl = "https://localhost:5003/api/EstudianteService/"
  const reporte = "http://localhost:8081/pdfAnalitico/"+cookies.get('Idusuario')
  const [materias, setMaterias] = useState([])
  const [promedioGral, setPromedioGral] = useState(0)

  useEffect(() => {
    const fetchData = async() =>{
      try {
        await traerDatos(baseUrl+"Analitico?idUsuario="+cookies.get('Idusuario')).then(data=>{
            const listadoNotas = data.listadoNotas.filter(element=>element.nota_definitiva !== 0)
            setMaterias(listadoNotas)
            setPromedioGral(data.promedio_general)
        })
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
          <h4 className="mb-3 text-center">Analítico: {cookies.get('Nombre')+" "+cookies.get('Apellido')}</h4>
          <div className='d-flex justify-content-end mb-3' style={{alignItems: 'end'}}>
            <h5 className='me-auto'>Promedio General: {promedioGral}</h5>
            <button onClick={()=>descargarArchivo(reporte,"Analitico "+cookies.get('Nombre')+" "+cookies.get('Apellido')+".pdf","data:application/pdf")} className='me-3 btn btn-primary'><Download/> Exportar Analítico</button>
            </div>
          <Table data={materias}/>
        </div>
      </div>
    </>
  )
}

export default Analitico