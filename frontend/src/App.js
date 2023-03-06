
import './App.css';
import HomeScreen from './screens/HomeScreen';
import {BrowserRouter, Route, Routes} from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <div >
        <header>
          <a href="/">Online Shop</a>
        </header>
        <main>
          <Routes>
            <Route path='/' element={<HomeScreen/>}/>
          </Routes>        
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
