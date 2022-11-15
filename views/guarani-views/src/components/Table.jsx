import axios from 'axios'
import React from 'react'
import { ClipboardMinus, ClipboardPlus } from 'react-bootstrap-icons'
import { Link } from 'react-router-dom'
import Cookies from 'universal-cookie'

function Table(props) {
    const cookies = new Cookies()
    const eliminar = async(id) => {
        if(!window.confirm("¿Desea eliminar este usuario?")){return}
        await axios.delete("http://localhost:8080/usuario/"+id).then(response=>{
            alert("Usuario eliminado con id "+response.data)
        }).catch(error=>{
            alert(error)
        })
    }
    const handleInscripcion = async(idCatedra, idUsuario, subRuta, metodo) =>{
        debugger
        await axios({
            "method": metodo,
            "url": props.linkPage+subRuta+"?idUsuario="+idUsuario+"&idCatedra="+idCatedra})
            .then(response=>{
                alert(response.data)
            }).catch(error=>{
                alert(error.response.status + " " + error.response.data.errorCode + "\n" + error.response.data.message)
            })
        window.location.reload()
    }
  return (
    <>
    {props.data.length > 0 ? <table className="table">
        <thead className='table-primary'>
            <tr>
                {Object.getOwnPropertyNames(props.data[0]).map(value=>(
                    <th scope="col">{value}</th>
                ))}
                {(props.actions || props.inscripcion) && <th>acción</th>}
            </tr>
        </thead>
        <tbody className="table-group-divider">
            {Object.values(props.data).map((value)=>(
                <tr>
                    {Object.values(value).map(data=>(
                        <td>{data}</td>
                    ))}
                    {props.actions ?
                    <td>
                        {props.actions.edit && <Link to={"/"+props.linkPage+"?id="+value.id+(value.instancia === undefined ? "" : value.instancia === "Cuatrimestre" ? "&esFinal=false" : "&esFinal=true")} className='me-2 btn btn-outline-primary'>{props.actions.edit}</Link>}
                        {props.actions.delete && <button onClick={()=>eliminar(value.id)} type='button' className='me-2 btn btn-outline-danger'>{props.actions.delete}</button>}
                    </td> : props.inscripcion &&
                    <td>
                        {value.inscripto === "No" ? 
                            <button onClick={()=>handleInscripcion(value.idcatedra, cookies.get('Idusuario'), "Inscripcion", "post")} className='me-2 btn btn-outline-primary'><ClipboardPlus/></button> :
                            <button onClick={()=>handleInscripcion(value.idcatedra, cookies.get('Idusuario'), "Desinscripcion", "delete")} className='me-2 btn btn-outline-danger'><ClipboardMinus/></button>}
                    </td>}
                </tr>
            ))}
        </tbody>
    </table>: <div style={{textAlign: 'center'}}>No hay datos para mostrar</div>}
    </>
  )
}

export default Table