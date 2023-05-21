import axios from 'axios';
import { useEffect, useReducer, useContext} from 'react';
import { useParams } from 'react-router-dom';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import Badge from 'react-bootstrap/Badge';
import Button from 'react-bootstrap/Button';
import LoadingBox from '../components/LoadingBox';
import MessageBox from '../components/MessageBox';
import { Store } from '../Store';
import {toast} from "react-toastify"

import { Helmet } from 'react-helmet-async';

const reducer = (state, action) => {
   switch (action.type) {
     case 'FETCH_REQUEST':
       return { ...state, loading: true };
     case 'FETCH_SUCCESS':
       return { ...state, product: action.payload, loading: false };
     case 'FETCH_FAIL':
       return { ...state, loading: false, error: action.payload };
     default:
       return state;
   }
 };

const ProductScreen=()=>{
   const params=useParams();
   const {id}=params;   
   const [{ loading, error, product }, dispatch] = useReducer(reducer, {
      product: [],
      loading: true,
      error: '',
    });
    
    useEffect(()=>{
      const fetchData=async()=>{      
        dispatch({ type: 'FETCH_REQUEST' });
        try {
          const result = await axios.get(`http://localhost:8001/api/products/${id}`)  
          dispatch({ type: 'FETCH_SUCCESS', payload: result.data });
        } catch (err) {
          dispatch({ type: 'FETCH_FAIL', payload: err.message });
        }            
      };    
      fetchData();    
     },[id])  

     const { state, dispatch: ctxDispatch } = useContext(Store);
     const { cart } = state;
     const addToCartHandler = async () => {
       const existItem = cart.cartItems.find((x) => x.id === product.id);
       const quantity = existItem ? existItem.quantity + 1 : 1;
       const { data } = await axios.get(`http://localhost:8001/api/products/${product.id}`);
       if (data.countInStock < quantity) {
         window.alert('Sorry. Product is out of stock');
         return;
       }
        
        ctxDispatch({
          type: 'CART_ADD_ITEM',
          payload: { ...product, quantity },
        });
        toast("Товар добавлен в коризну");
      };      

     return loading ? (
      <LoadingBox />
    ) : error ? (
      <MessageBox variant="danger">{error}</MessageBox>
    ) : (
      <div className='wrapper'>
         <Row>
          <Col md={5}>
            <img
              className="img-large"
              src={product.imagesContent[0]} 
              alt={product.name}
            ></img>
          </Col>
          <Col md={4}>
            <ListGroup variant="flush">
              <ListGroup.Item>
                <Helmet>
                  <title>{product.name}</title>
                </Helmet>
                <h2>{product.name}</h2>
              </ListGroup.Item>              
              <ListGroup.Item>
                Описание:
                <p>{product.description}</p>
              </ListGroup.Item>
            </ListGroup>
          </Col>
          <Col md={3}>
            <Card>
              <Card.Body>
                <ListGroup variant="flush">
                  <ListGroup.Item>
                    <Row>
                      <Col>Цена:</Col>
                      <Col>₽{product.cost}</Col>
                    </Row>
                  </ListGroup.Item>
                  <ListGroup.Item>
                    <Row>
                      <Col>Статус:</Col>
                      <Col>
                        {product.countInStock > 0 ? (
                          <Badge bg="success">В наличии</Badge>
                        ) : (
                          <Badge bg="danger">Недоступно</Badge>
                        )}
                      </Col>
                    </Row>
                  </ListGroup.Item>
  
                  {product.countInStock > 0 && (
                    <ListGroup.Item>
                      <div className="d-grid">
                        <Button onClick={addToCartHandler} variant="primary">
                          Добавить в корзину
                        </Button>
                      </div>
                    </ListGroup.Item>
                  )}
                </ListGroup>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </div>
    );
 }



export default ProductScreen;