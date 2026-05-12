import { useState, createContext, useContext } from 'react'
import './App.css'
import AppRouter from './components/AppRouter'
import { UserContext } from './components/users/UserContext';

function App() {
  const [token, setToken] = useState(null);

  return (
    <UserContext value={token, setToken}>
      <AppRouter />
    </UserContext>
    
  )
}

export default App
