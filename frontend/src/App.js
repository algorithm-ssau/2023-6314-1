import './App.css';
import {useState,useEffect} from "react" ;
import axios from "axios";
import data from './data';

function App() {
  const [products,setProducts]=useState([]);
  const [images,setImages]=useState([]);
  useEffect(()=>{
    const fetchData=async()=>{
      const result = await axios.get('http://localhost:8001/api/products/')
      setProducts(result.data);      
    };
    fetchData();
  },[])  
  return (
    <div >
      <header>
        <a href="/">Online Shop</a>
      </header>
      <main>
      <h1>Featured Products</h1>
        <div className="products">
          {products.map((product) => (
            <div className="product" key={product.slug}>
              <a href={`/product/${product.slug}`}>
                <img src={product.image} alt={product.name} />
              </a>
              <div className="product-info">
                <a href={`/product/${product.slug}`}>
                  <p>{product.name}</p>
                </a>
                <p>
                  <strong>${product.price}</strong>
                </p>
                <button>Add to cart</button>
              </div>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
}

export default App;
