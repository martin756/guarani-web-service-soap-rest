import React, { useEffect }  from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { useNavigate } from 'react-router-dom';
import Cookies from 'universal-cookie';
import { adminPaths, estudiantePaths, docentePaths } from '../components/Data'

function Header() {
  const cookies = new Cookies()
  const navigate = useNavigate()

  const cerrarSesion = () => {
    cookies.remove('Idusuario')
    cookies.remove('Nombre')
    cookies.remove('Apellido')
    cookies.remove('Dni')
    cookies.remove('tipoUsuario')
    navigate('/')
  }
  
  useEffect(() => {
    if (!cookies.get('Idusuario')) {
      navigate('/')
    }
  }, [])

  return (
    <>
        {
          cookies.get('tipoUsuario') === "ESTUDIANTE" ?
          <Usuario cookies={cookies} navigator={navigate} closeSession={cerrarSesion} paths={estudiantePaths}/>
        : cookies.get('tipoUsuario') === "DOCENTE" ? 
          <Usuario cookies={cookies} navigator={navigate} closeSession={cerrarSesion} paths={docentePaths}/>
        :
          <Usuario cookies={cookies} navigator={navigate} closeSession={cerrarSesion} paths={adminPaths}/>
        }
    </>
  );
}

function Usuario(props){
  return(
    <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark" sticky='top' className='shadow rounded' >
      <Container>
        <Navbar.Brand href="/">SiuGuarani</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="justify-content-end flex-grow-1 pe-3">
            {props.cookies.get('Nombre') &&
            <NavDropdown title={props.cookies.get('Nombre')} id="collasible-nav-dropdown" align="end">
              {props.paths.map(value=>(
                <NavDropdown.Item onClick={()=>{props.navigator(value.path)}}>{value.label}</NavDropdown.Item>
              ))}
              <NavDropdown.Divider />
              <NavDropdown.Item onClick={()=>props.closeSession()}>Cerrar Sesión</NavDropdown.Item>
            </NavDropdown>
            }
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default Header;