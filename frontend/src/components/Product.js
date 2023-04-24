import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { useContext } from 'react';
import { Store } from '../Store';

const Product=({product})=>{ 
  const { state, dispatch: ctxDispatch } = useContext(Store);
  const {
    cart: { cartItems },
  } = state;

  const addToCartHandler = async (item) => {
    const existItem = cartItems.find((x) => x.id === product.id);
    const quantity = existItem ? existItem.quantity + 1 : 1;
    const { data } = await axios.get(`http://localhost:8001/api/products/${item.id}`);
    if (data.countInStock < quantity) {
      window.alert('Sorry. Product is out of stock');
      return;
    }
    ctxDispatch({
      type: 'CART_ADD_ITEM',
      payload: { ...item, quantity },
    });
  };  
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
     {product.countInStock === 0 ? (
          <Button variant="light" disabled>
            Out of stock
          </Button>
        ) : (
          <Button onClick={() => addToCartHandler(product)}>Add to cart</Button>
        )}
   </div>
 </div>
          
}

export default Product;