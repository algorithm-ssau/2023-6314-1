import {useState,useEffect} from "react" ;
import {Link} from 'react-router-dom'
import axios from "axios";
import data from '../data';

function _imageEncode (string) {
   var uint8array = new TextEncoder().encode(string);
   let b64encoded = btoa([].reduce.call(new Uint8Array(uint8array),function(p,c){return p+String.fromCharCode(c)},''))
   let mimetype="image/jpeg"
   return "data:"+mimetype+";base64,"+b64encoded
 }
 
const HomeScreen=()=>{
  const [products,setProducts]=useState([]);
  const [images,setImages]=useState([]);
  
  useEffect(()=>{
    const fetchData=async()=>{
      const result = await axios.get('http://localhost:8001/api/products')
      setProducts(result.data);  
      console.log(result.data);       
    };    
    fetchData();    
   },[])  
   return(
   <>
   <h1>Featured Products</h1>
   <div className="products">
          {products.map((product) => (
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
          ))}
        </div>

   </>)

}

export default HomeScreen;