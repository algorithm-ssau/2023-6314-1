import './App.css';

import {BrowserRouter,Routes,Route,Link} from 'react-router-dom';

import HomeScreen from './screens/HomeScreen';
import ProductScreen from './screens/ProductScreen';


function App() {
  

  return (
    <BrowserRouter>
    <div >
      <header>
        <Link to="/">Online Shop</Link>
      </header>
      <main>
        <Routes>
          <Route path='/product/:id'element={<ProductScreen/>}/>
          <Route path='/'element={<HomeScreen/>}/>
        </Routes>     
        
      </main>
    </div>
    </BrowserRouter>
  );
}

export default App;
