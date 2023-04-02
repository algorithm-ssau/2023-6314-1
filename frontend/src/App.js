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
      console.log(result.data);       
    };
    const fetchImage=async()=>{
      const result = await axios.get('http://localhost:8005/api/images/1');       
      console.log(result.data);    
    };    
    fetchData();
    fetchImage();
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
            <div className="product" key={product.id}>
              <a href={`/product/${product.id}`}>
                <img src={images[product.id]} alt={product.name} />
              </a>
              <div className="product-info">
                <a href={`/product/${product.id}`}>
                  <p>{product.name}</p>
                </a>
                <p>
                  <strong>${product.cost}</strong>
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
