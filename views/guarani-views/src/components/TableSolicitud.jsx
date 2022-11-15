import axios from 'axios'
import React from 'react'

function TableSolicitud(props) {
    const rechazar = async (id) => {
        if (!window.confirm("¿Desea rechazar esta solicitud?")) { return }
        await axios.post("http://localhost:8080/cambioEstadoSolicitud?estado=Rechazado&id=" + id).then(response => {
            alert("Solicitud rechazada " + response.data)
            props.fetchData();

        }).catch(error => {
            alert(error)
        })
    }
    const aceptar = async (id) => {
        if (!window.confirm("¿Desea aceptar esta solicitud?")) { return }
        await axios.post("http://localhost:8080/cambioEstadoSolicitud?estado=Aceptado&id=" + id).then(response => {
            alert("Solicitud aceptada " + response.data)
            props.fetchData();
        }).catch(error => {
            alert(error)
        })
    }

    return (
        <>
            {props.data.length > 0 ? <table className="table">
                <thead className='table-primary'>
                    <tr>
                        {Object.getOwnPropertyNames(props.data[0]).map(value => (
                            <th scope="col">{value}</th>
                        ))}
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody className="table-group-divider">
                    {Object.values(props.data).map((value) => (
                        <tr>
                            {Object.values(value).map(data => (
                                <td>{data}</td>
                            ))}
                            <td>
                                <button onClick={() => aceptar(value.id)} type='button' className='me-2 me-2 btn btn-outline-success'>{props.actions.aceptar}</button>
                                <button onClick={() => rechazar(value.id)} type='button' className='me-2 btn btn-outline-danger'>{
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x" viewBox="0 0 16 16">
                                        <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z" />
                                    </svg>}
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table> : <div style={{ textAlign: 'center' }}>No hay datos para mostrar</div>}
        </>
    )
}

export default TableSolicitud