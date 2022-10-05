import React, { useEffect }  from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { useNavigate } from 'react-router-dom';
import Cookies from 'universal-cookie';
// import { Cart } from 'react-bootstrap-icons';

function Header() {
  const cookies = new Cookies()
  const navigate = useNavigate()

  const cerrarSesion = () => {
    cookies.remove('Nombre')
    cookies.remove('Apellido')
    cookies.remove('Dni')
    cookies.remove('Email')
    cookies.remove('User')
    cookies.remove('Password')
    cookies.remove('EsAdministrador')
    navigate('/')
  }
  
  useEffect(() => {
    if (!cookies.get('User')) {
      navigate('/')
    }
  }, [])

  return (
    <div>
        if (JSON.parse(cookies.get('EsAdministrador')){
                <UsuarioAdministrador cookies={cookies} closeSession={cerrarSesion}/>
            } elseif {
                <UsuarioEstudiante cookies={cookies} navigator={navigate} closeSession={cerrarSesion}/>
            } else {
                <UsuarioDocente cookies={cookies} navigator={navigate} closeSession={cerrarSesion}/>}    
    </div>
  );
}

function UsuarioEstudiante(props){
  return(
    <div>
      <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky='top'>
        <Container>
          <Navbar.Brand href="/">SiuGuarani</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="justify-content-end flex-grow-1 pe-3">
              <NavDropdown title={props.cookies.get('Nombre')} id="collasible-nav-dropdown" align="end">
                <NavDropdown.Item onClick={()=>{props.navigator('/consultamateriasestudiante')}}>Consulta de Materias/Exámenes</NavDropdown.Item>
                <NavDropdown.Item onClick={()=>{props.navigator('/inscripcion')}}>Inscripción</NavDropdown.Item>
                <NavDropdown.Item onClick={()=>{props.navigator('/consultaanalitico')}}>Consultar informe analítico</NavDropdown.Item>
                <NavDropdown.Item onClick={()=>{props.navigator('/contacto')}}>Modificación de datos de contacto</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={()=>props.closeSession()}>Cerrar Sesión</NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </div>
  );
}

function UsuarioAdministrador(props){
  return(
    <div>
      <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky='top'>
        <Container>
          <Navbar.Brand href="/">SiuGuarani</Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="justify-content-end flex-grow-1 pe-3">
              <NavDropdown title={props.cookies.get('Nombre')} id="collasible-nav-dropdown" align="end">
                <NavDropdown.Item onClick={()=>{props.navigator('/abmmaterias')}}>ABM de estudiantes y docentes</NavDropdown.Item>
                <NavDropdown.Item onClick={()=>{props.navigator('/cargacuatrimestre')}}>Carga de cuatrimestres</NavDropdown.Item>
                <NavDropdown.Item onClick={()=>{props.navigator('/planillacuatrimestre')}}>Planilla de cuatrimestre</NavDropdown.Item>
                <NavDropdown.Item onClick={()=>{props.navigator('/cargaexamenes')}}>Carga/planilla de mesas de examen</NavDropdown.Item>
                <NavDropdown.Item onClick={()=>{props.navigator('/inscripciones')}}>Ventana de inscripciones</NavDropdown.Item>
                <NavDropdown.Item onClick={()=>props.closeSession()}>Cerrar Sesión</NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </div>
  );
}

function UsuarioDocente(props){
    return(
      <div>
        <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky='top'>
          <Container>
            <Navbar.Brand href="/">SiuGuarani</Navbar.Brand>
            <Navbar.Toggle aria-controls="responsive-navbar-nav" />
            <Navbar.Collapse id="responsive-navbar-nav">
              <Nav className="justify-content-end flex-grow-1 pe-3">
                <NavDropdown title={props.cookies.get('Nombre')} id="collasible-nav-dropdown" align="end">
                  <NavDropdown.Item onClick={()=>{props.navigator('/consultamateriasdocente')}}>Consulta de materias asignadas</NavDropdown.Item>
                  <NavDropdown.Item onClick={()=>{props.navigator('/consultaalumnos')}}>Consulta de listado de alumnos</NavDropdown.Item>
                  <NavDropdown.Item onClick={()=>{props.navigator('/carganotas')}}>Carga de notas de cursada</NavDropdown.Item>
                  <NavDropdown.Item onClick={()=>{props.navigator('/cargafinales')}}>Carga de notas de final</NavDropdown.Item>
                  <NavDropdown.Item onClick={()=>props.closeSession()}>Cerrar Sesión</NavDropdown.Item>
                </NavDropdown>
              </Nav>
            </Navbar.Collapse>
          </Container>
        </Navbar>
      </div>
    );
  }

export default Header;