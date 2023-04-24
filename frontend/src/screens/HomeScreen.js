import {useState,useEffect,useReducer} from "react" ;
import {Link} from 'react-router-dom'
import axios from "axios";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Product from '../components/Product';
import { Helmet } from 'react-helmet-async';
import data from '../data';

const reducer = (state, action) => {
  switch (action.type) {
    case 'FETCH_REQUEST':
      return { ...state, loading: true };
    case 'FETCH_SUCCESS':
      return { ...state, products: action.payload, loading: false };
    case 'FETCH_FAIL':
      return { ...state, loading: false, error: action.payload };
    default:
      return state;
  }
};
 
const HomeScreen=()=>{
  /* const [products,setProducts]=useState([]);
  const [images,setImages]=useState([]); */
  const [{ loading, error, products }, dispatch] = useReducer(reducer, {
    products: [],
    loading: true,
    error: '',
  });
  
  useEffect(()=>{
    const fetchData=async()=>{      
      //setProducts(result.data);  
      dispatch({ type: 'FETCH_REQUEST' });
      try {
        const result = await axios.get('http://localhost:8001/api/products')  
        dispatch({ type: 'FETCH_SUCCESS', payload: result.data });
      } catch (err) {
        dispatch({ type: 'FETCH_FAIL', payload: err.message });
      }            
    };    
    fetchData();    
   },[])  
   return(
   <>
   <Helmet>
        <title>Online Shop</title>
   </Helmet>
   <h1>Featured Products</h1>   
   <div className="products">
        {loading ? (
          <div>Loading...</div>
        ) : error ? (
          <div>{error}</div>
        ) : (          
          <Row>
            {products.map((product) => (
              <Col key={product.id} sm={6} md={5} lg={4} className="mb-3">
                <Product product={product}></Product>
              </Col>
            ))}
          </Row>)}
        </div>

   </>)

}

export default HomeScreen;