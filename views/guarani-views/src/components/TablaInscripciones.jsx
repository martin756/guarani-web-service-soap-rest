import axios from 'axios'
import React from 'react'
import Cookies from 'universal-cookie'

function TableInscripciones(props) {
    const cookies = new Cookies()


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
              }
            </tr>
        </thead>
        <tbody className="table-group-divider">
            {Object.values(props.data).map((value)=>(
                <tr>
                        {Object.values(value).map(data=>(
                        <td>{data}</td>
                    ))}                  
                </tr>
            ))}
        </tbody>
    </table>: <div style={{textAlign: 'center'}}>No hay datos para mostrar</div>}
    </>
  )
}

export default TableInscripciones