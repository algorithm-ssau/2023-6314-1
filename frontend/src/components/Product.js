import Button from 'react-bootstrap/Button';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { useContext } from 'react';
import { Store } from '../Store';
import { toast } from 'react-toastify';

const Product=({product})=>{ 
  const { state, dispatch: ctxDispatch } = useContext(Store);
  const {
    cart: { cartItems },
  } = state;

  const addToCartHandler = async (item) => {
    const existItem = cartItems.find((x) => x.id === product.id);
    const quantity = existItem ? existItem.quantity + 1 : 1;
    const { data } = await axios.get(`http://localhost:8080/api/products/${item.id}`);
    if (data.countInStock < quantity) {
      window.alert('Простите, продуктов не осталось на складе');
      return;
    }
    ctxDispatch({
      type: 'CART_ADD_ITEM',
      payload: { ...item, quantity },
    });
    toast.success('Товар добавлен в корзину!', {
      position: "top-center",
      autoClose: 3000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
      progress: undefined,
      theme: "light",
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
       <strong>₽{product.cost}</strong>
     </p>
     {product.countInStock === 0 ? (
          <Button variant="light" disabled>
            Нет на складе
          </Button>
        ) : (
          <Button onClick={() => addToCartHandler(product)}>Добавить в корзину</Button>
        )}
   </div>   
 </div>
          
}

export default Product;