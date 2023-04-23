import {useState,useEffect,useReducer} from "react" ;
import {Link} from 'react-router-dom'
import axios from "axios";
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
   <h1>Featured Products</h1>
   <div className="products">
        {loading ? (
          <div>Loading...</div>
        ) : error ? (
          <div>{error}</div>
        ) : (          
          products.map((product) => (
            <div className="product" key={product.id}>
              <Link to={`/product/${product.id}`}>
                <img src={product.imagesContent[0]} alt={product.name} />
              </Link>
              <div className="product-info">
                <Link to={`/product/${product.id}`}>
                  <p>{product.name}</p>
                </Link>
                <p>
                  <strong>${product.cost}</strong>
                </p>
                <button>Add to cart</button>
              </div>
            </div>
          )))}
        </div>

   </>)

}

export default HomeScreen;