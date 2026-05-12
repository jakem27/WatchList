import { useState, createContext, useContext } from 'react'
import './App.css'
import AppRouter from './components/AppRouter'
import { UserContext } from './components/users/UserContext';

function App() {
  const [token, setToken] = useState(null);

  return (
    <UserContext.Provider value={{ token, setToken }}>
      <AppRouter />
    </UserContext.Provider>
  )
}

export default App
