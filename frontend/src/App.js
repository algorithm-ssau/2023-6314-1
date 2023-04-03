import './App.css';
import {useState,useEffect} from "react" ;
import axios from "axios";
import data from './data';

function _imageEncode (string) {
  var uint8array = new TextEncoder().encode(string);
  let b64encoded = btoa([].reduce.call(new Uint8Array(uint8array),function(p,c){return p+String.fromCharCode(c)},''))
  let mimetype="image/jpeg"
  return "data:"+mimetype+";base64,"+b64encoded
}

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
      setImages(_imageEncode(result.data.content)); 
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
                <img src={data.products[product.id-1].image} alt={product.name} />
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
