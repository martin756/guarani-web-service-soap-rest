import axios from 'axios'
import React, { useMemo } from 'react'
import {PencilSquare, Trash} from 'react-bootstrap-icons'
import { Link, useLocation } from 'react-router-dom'

function TableMarks(props) {
    const { search } = useLocation();
    let query = useMemo(() => new URLSearchParams(search), [search]);

    const esFinal = query.get('esFinal')
  return (
    <>
    {props.data.length > 0 ? <table id='table-marks' className="table">
        <thead className='table-primary'>
            <tr>
                {Object.getOwnPropertyNames(props.data[0]).map(value=>(
                        <th scope="col">{value == "tiempos_limites" ? "Tiempos límites de edición" : value}</th>
                ))}
                {esFinal === 'false' ? 
                    <>
                        <th style={{background: '#198754', color: 'white'}}>Nota</th>
                        <th style={{background: '#1EA464', color: 'white'}}>NroParcial</th>
                    </> : 
                    <>
                        <th style={{background: '#198754', color: 'white'}}>Nota Final</th>
                    </> 
                }
            </tr>
        </thead>
        <tbody className="table-group-divider">
            {Object.values(props.data).map((value)=>(
                <tr >
                    {Object.values(value).map(data=>(
                        <>
                        {!Array.isArray(data) &&
                            <td scope='row'>{data}</td>
                        }
                        </>
                    ))}
                    <td scope='row'>
                            {value.tiempos_limites.length > 0 && value.tiempos_limites.map(tiempos=>(
                                <>
                                    {Object.entries(tiempos).map(entry=>(
                                        <div>
                                        {entry[0]}{entry[1]}
                                        </div>
                                    ))}
                                </>
                            ))}
                    </td>
                    {esFinal === 'false' ? 
                    <>
                        <td contentEditable style={{background: '#C6EFCE'}} scope='row'></td>
                        <td contentEditable style={{background: '#D8F3DD'}} scope='row'></td>
                    </> : 
                    <>
                        <td contentEditable style={{background: '#C6EFCE'}} scope='row'></td>
                    </>}
                </tr>
            ))}
        </tbody>
    </table>: <div style={{textAlign: 'center'}}>No hay datos para mostrar</div>}
    </>
  )
}

export default TableMarks