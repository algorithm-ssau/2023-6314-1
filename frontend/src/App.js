import './App.css';

import {BrowserRouter,Routes,Route,Link} from 'react-router-dom';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import { LinkContainer } from 'react-router-bootstrap';

import HomeScreen from './screens/HomeScreen';
import ProductScreen from './screens/ProductScreen';


function App() {
  

  return (
    <BrowserRouter>
    <div >
      <header>
         <Navbar bg="dark" variant="dark">
            <Container>
              <LinkContainer to="/">
                <Navbar.Brand>Online Shop</Navbar.Brand>
              </LinkContainer>
            </Container>
          </Navbar>
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
