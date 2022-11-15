import axios from 'axios'
import React from 'react'
import Cookies from 'universal-cookie'

function TableSolicitud(props) {
    const cookies = new Cookies()
    const rechazar = async(id) => {
        if(!window.confirm("¿Desea rechazar esta solicitud?")){return}
        await axios.post("http://localhost:8080/cambioEstadoSolicitud?estado=Rechazado&id="+id).then(response=>{
            alert("Solicitud rechazada "+response.data)
        }).catch(error=>{
            alert(error)
        })
    }
    const aceptar = async(id) => {
        if(!window.confirm("¿Desea aceptar esta solicitud?")){return}
        await axios.post("http://localhost:8080/cambioEstadoSolicitud?estado=Aceptado&id="+id).then(response=>{
            alert("Solicitud aceptada "+response.data)
        }).catch(error=>{
            alert(error)
        })
    }

  return (
    <>
    {props.data.length > 0 ? <table className="table">
        <thead className='table-primary'>
            <tr>
                {Object.getOwnPropertyNames(props.data[0]).map(value=>(
                    <th scope="col">{value}</th>
                ))}
                {/* {(props.actions || props.inscripcion) &&  */}
                {/* <th>Estudiante</th>
                <th>Materia Actual</th>
                <th>Comision Actual</th>
                <th>Materia Solicitad</th>
                <th>Comision Solicitada</th>*/
                <th>Acciones</th> }
            </tr>
        </thead>
        <tbody className="table-group-divider">
            {Object.values(props.data).map((value)=>(
                <tr>
                        {Object.values(value).map(data=>(
                        <td>{data}</td>
                    ))}                  
                    <td>
                        {<button onClick={()=>aceptar(value.id)} type='button' className='me-2 me-2 btn btn-outline-success'>{props.actions.aceptar}</button>}
                        {<button onClick={()=>rechazar(value.id)} type='button' className='me-2 btn btn-outline-danger'>{props.actions.rechazar}</button>}
                    </td>                
                </tr>
            ))}
        </tbody>
    </table>: <div style={{textAlign: 'center'}}>No hay datos para mostrar</div>}
    </>
  )
}

export default TableSolicitud