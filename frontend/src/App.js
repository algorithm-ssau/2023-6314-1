import './App.css';

import {BrowserRouter,Routes,Route} from 'react-router-dom';

import HomeScreen from './screens/HomeScreen';



function App() {
  

  return (
    <BrowserRouter>
    <div >
      <header>
        <a href="/">Online Shop</a>
      </header>
      <main>
        <Routes>
          <Route path='/'element={<HomeScreen/>}/>
        </Routes>
      <h1>Featured Products</h1>
        
      </main>
    </div>
    </BrowserRouter>
  );
}

export default App;
