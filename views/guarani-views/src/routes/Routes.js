import React from 'react'
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import Login from '../pages/Login'
import Signup from '../pages/Signup'
import MenuAdmin from '../pages/MateriasDocente';
import MateriasEstudiante from '../pages/MateriasEstudiante';
import AbmUsuarios from '../pages/AbmUsuarios';
import Header from '../components/Header';
import MateriasDocente from '../pages/MateriasDocente';

function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path='/' element={<Login />}/>
        {/* Paths de ADMINISTRADORES */}
        <Route path='/abmusuarios' element={<AbmUsuarios />}/>

        {/* Paths de ESTUDIANTES */}
        <Route path='/consultamateriasestudiante' element={<MateriasEstudiante />}/>

        {/* Paths de DOCENTES */}
        <Route path='/consultamateriasdocente' element={<MateriasDocente />}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
