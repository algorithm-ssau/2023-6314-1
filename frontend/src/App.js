import './App.css';
import ProductScreen from './screens/ProductScreen';
import HomeScreen from './screens/HomeScreen';
import {BrowserRouter,Link, Route, Routes} from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <div >
        <header>
          <Link to="/">Online Shop</Link>
        </header>
        <main>
          <Routes>
            <Route path="/product/:slug" element={<ProductScreen />} />
            <Route path='/' element={<HomeScreen/>}/>
          </Routes>        
        </main>
      </div>
    </BrowserRouter>
  );
}

export default App;
