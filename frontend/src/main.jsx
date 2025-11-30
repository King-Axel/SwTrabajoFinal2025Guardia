import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router-dom'
import './index.css'
import App from './App.jsx'

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <BrowserRouter>
      <div className="font-elms-sans w-full h-screen xl:justify-center lg:bg-gray-200 lg:flex lg:items-center">
        <App />
      </div>
    </BrowserRouter>
  </StrictMode>,
)
