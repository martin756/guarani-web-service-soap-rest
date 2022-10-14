import React, { useEffect, useState } from 'react'
import Table from '../../components/Table'
import axios from 'axios'
import { useNavigate } from 'react-router-dom'
import { traerDatos } from '../../components/Data'
import { PencilSquare, Trash } from 'react-bootstrap-icons'

function AbmUsuarios() {
  const baseUrl = "http://localhost:8080/usuarios"
  const [usuarios, setUsuarios] = useState([])
  const navigate = useNavigate()

  useEffect(() => {
    const fetchData = async() =>{
      try { setUsuarios(await traerDatos(baseUrl)) }
      catch (error) { alert(error) }
    }
    fetchData()
  }, [])
  
  return (
    <>
      <div className="container pt-5">
        <div className="row">
          <h4 className="mb-3 text-center">Listado de usuarios en sistema</h4>
          <button onClick={()=>navigate('/Usuario')} className='mb-2 btn btn-primary'>+ Nuevo</button>
          <Table data={usuarios} linkPage='Usuario' actions={{edit: <PencilSquare/>, delete: <Trash/>}}/>
        </div>
      </div>
    </>
  )
}

export default AbmUsuarios