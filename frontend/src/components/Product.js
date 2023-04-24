import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';

const Product=({product})=>{   
   return <div className="product" key={product.id}>
   <Link to={`/product/${product.id}`}>
     <img src={product.imagesContent[0]} alt={product.name} />
   </Link>
   <div className="product-info">
     <Link to={`/product/${product.id}`}>
       <p className='description'>{product.name}</p>
     </Link>
     <p>
       <strong>${product.cost}</strong>
     </p>
     <Button>Add to cart</Button>
   </div>
 </div>
          
}

export default Product;