import React from 'react'
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import Login from '../pages/Login'
import Signup from '../pages/Signup'
import MenuEstudiante from '../pages/MenuEstudiante';

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='/' element={<Login />}/>
        <Route path='/signup' element={<Signup />}/>
        <Route path='/menuestudiante' element={<MenuEstudiante />}/>
        
      </Routes>
    </BrowserRouter>
  );
}

export default App;
