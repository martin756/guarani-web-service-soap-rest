import axios from 'axios'
import React from 'react'
import {PencilSquare, Trash} from 'react-bootstrap-icons'
import { Link } from 'react-router-dom'

function Table(props) {

    const eliminar = async(id) => {
        alert("No implementado")
        //DELETE NO IMPLEMENTADO
        //props.linkPage !== undefined && 
            //await axios.delete("http://localhost:8080/"+props.linkPage+id)
    }
  return (
    <>
    {props.data.length > 0 && <table className="table">
        <thead className='table-primary'>
            <tr>
                {Object.getOwnPropertyNames(props.data[0]).map(value=>(
                    <th scope="col">{value}</th>
                ))}
                {props.actions && <th>acción</th>}
            </tr>
        </thead>
        <tbody className="table-group-divider">
            {Object.values(props.data).map((value)=>(
                <tr>
                    {Object.values(value).map(data=>(
                        <td scope='row'>{data}</td>
                    ))}
                    {props.actions &&
                    <td scope='row'>
                        {props.actions.edit && <Link to={"/"+props.linkPage+"?id="+value.id} className='me-2 btn btn-outline-primary'>{props.actions.edit}</Link>}
                        {props.actions.delete && <button onClick={()=>eliminar(value.id)} type='button' className='me-2 btn btn-outline-danger'>{props.actions.delete}</button>}
                    </td>}
                </tr>
            ))}
        </tbody>
    </table>}
    </>
  )
}

export default Table