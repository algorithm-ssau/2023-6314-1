import './App.css';

import {BrowserRouter,Routes,Route,Link} from 'react-router-dom';
import Navbar from 'react-bootstrap/Navbar';
import Container from 'react-bootstrap/Container';
import { LinkContainer } from 'react-router-bootstrap';
import Badge from 'react-bootstrap/Badge';
import Nav from 'react-bootstrap/Nav';
import 'react-toastify/dist/ReactToastify.css';
import { toast, ToastContainer } from 'react-toastify';
import HomeScreen from './screens/HomeScreen';
import ProductScreen from './screens/ProductScreen';
import { useContext, useEffect, useState } from 'react';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Store } from './Store';
import CartScreen from './screens/CartScreen';
import SigninScreen from './screens/SigninScreen';
import ShippingAddressScreen from './screens/ShippingAddressScreen';
import SignupScreen from './screens/SignupScreen';
import ProfileScreen from './screens/ProfileScreen';
import Button from 'react-bootstrap/Button';
import { getError } from './utils';
import axios from 'axios';
import SearchBox from './components/SearchBox';
import SearchScreen from "./screens/SearchScreen";
import PlaceOrderScreen from "./screens/PlaceOrderScreen";
import OrderScreen from "./screens/OrderScreen";
import PaymentMethodScreen from "./screens/PaymentMethodScreen";

function App() {
  const { state, dispatch: ctxDispatch } = useContext(Store);
  const { cart, userInfo } = state;

  const signoutHandler = () => {
    ctxDispatch({ type: 'USER_SIGNOUT' });
    localStorage.removeItem('userInfo');
    localStorage.removeItem('shippingAddress');
      localStorage.removeItem('paymentMethod');

      document.cookie = 'Refresh='
      window.location.href = '/signin';
  };



  return (
    <BrowserRouter>
      <ToastContainer position="bottom-center" limit={1} />
      <header>
         <Navbar bg="dark" variant="dark">
            <Container style={{display: 'flex', justifyContent: 'space-between'}}>

              <LinkContainer to="/">
                <Navbar.Brand>Online Shop</Navbar.Brand>
              </LinkContainer>
              <Nav>
                <Link to="/cart" className="nav-link">
                  Корзина
                  {cart.cartItems.length > 0 && (
                    <Badge pill bg="danger">
                      {cart.cartItems.reduce((a, c) => a + c.quantity, 0)}
                    </Badge>
                  )}
                </Link>
                {userInfo ? (
                    <NavDropdown title={userInfo.name} id="basic-nav-dropdown" drop={'down-centered'}>
                      <LinkContainer to="/profile">
                        <NavDropdown.Item>User Profile</NavDropdown.Item>
                      </LinkContainer>
                      <NavDropdown.Divider />
                      <Link
                          className="dropdown-item"
                          to="#signout"
                          onClick={signoutHandler}
                      >
                        Sign Out
                      </Link>
                    </NavDropdown>
                ) : (
                    <Link className="nav-link" to="/signin">
                      Вход
                    </Link>
                )}
                  <SearchBox />
              </Nav>
            </Container>
          </Navbar>
      </header>

      <main>
        <Container  className="mt-3">
          <Routes>
            <Route path='/product/:id' element={<ProductScreen/>}/>
            <Route path="/cart" element={<CartScreen />} />
            <Route path="/signin" element={<SigninScreen />} />
            <Route
                path="/shipping"
                element={<ShippingAddressScreen />}
            ></Route>
              <Route path="/search" element={<SearchScreen />} />
              <Route path="/signup" element={<SignupScreen />} />
            <Route path="/profile" element={<ProfileScreen />} />
              <Route path="/placeorder" element={<PlaceOrderScreen />} />
              <Route path="/order/:id" element={<OrderScreen />}></Route>
              <Route path="/payment" element={<PaymentMethodScreen />}></Route>
              <Route path='/' element={<HomeScreen/>}/>
          </Routes> 
        </Container>                  
      </main>
      <footer>
          <div className="text-center">All rights reserved</div>
      </footer>
    </BrowserRouter>
  );
}

export default App;
