import { useContext } from 'react';
import axios from 'axios';
import { Store } from '../Store';
import { Helmet } from 'react-helmet-async';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import MessageBox from '../components/MessageBox';
import ListGroup from 'react-bootstrap/ListGroup';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import { Link , useNavigate} from 'react-router-dom';

export default function CartScreen() {
  const { state, dispatch: ctxDispatch } = useContext(Store);
  const navigate = useNavigate();
  const {
    cart: { cartItems },
  } = state;

  const updateCartHandler = async (item, quantity) => {
      const { data } = await axios.get(`http://localhost:8001/api/products/${item.id}`);     
      if (data.countInStock < quantity) {
         window.alert('Простите, продуктов не осталось на складе');
         return;
      } 
      ctxDispatch({
      type: 'CART_ADD_ITEM',
      payload: { ...item, quantity },
      });
   };
   const removeItemHandler = (item) => {
      ctxDispatch({ type: 'CART_REMOVE_ITEM', payload: item });
   };

   const checkoutHandler = () => {
      navigate('/signin?redirect=/shipping');
   };

  return (
    <div>
      <Helmet>
        <title>Shopping Cart</title>
      </Helmet>
      <h1>Корзина</h1>
      <Row>
        <Col md={8}>
          {cartItems.length === 0 ? (
            <MessageBox>
              Корзина пуста.  <Link to="/">Перейти к покупкам</Link>
            </MessageBox>
          ) : (
            <ListGroup>
              {cartItems.map((item) => (
                <ListGroup.Item key={item.id}>
                  <Row className="align-items-center">
                    <Col md={4}>
                      <img
                        src={item.imagesContent[0]}
                        alt={item.name}
                        className="img-fluid rounded img-thumbnail"
                      ></img>{' '}
                      <div>
                        <Link className='Link-style' to={`/product/${item.id}`}>{item.name}</Link>
                      </div>                      
                    </Col>
                    <Col md={3}>
                     <Button
                           onClick={() =>
                           updateCartHandler(item, item.quantity - 1)
                           }
                           variant="light"
                           disabled={item.quantity === 1}
                        >
                        <i className="fas fa-minus-circle"></i>
                      </Button>{' '}
                      <span>{item.quantity}</span>{' '}
                      <Button
                        onClick={() =>
                           updateCartHandler(item, item.quantity + 1)
                        }
                        variant="light"
                        disabled={item.quantity === item.countInStock}>
                        <i className="fas fa-plus-circle"></i>
                      </Button>
                    </Col>
                    <Col md={3}>₽{item.cost}</Col>
                    <Col md={2}>
                      <Button
                        onClick={() => removeItemHandler(item)}
                        variant="light"
                      >
                        <i className="fas fa-trash"></i>
                      </Button>
                    </Col>
                  </Row>
                </ListGroup.Item>
              ))}
            </ListGroup>
          )}
        </Col>
        <Col md={4}>
          <Card>
            <Card.Body>
              <ListGroup variant="flush">
                <ListGroup.Item>
                  <h3>
                    Сумма ({cartItems.reduce((a, c) => a + c.quantity, 0)}{' '}
                    вещей) : ₽
                    {Math.round(parseFloat(cartItems.reduce((a, c) => a + c.cost * c.quantity, 0)) * 100) / 100}
                  </h3>
                </ListGroup.Item>
                <ListGroup.Item>
                  <div className="d-grid">
                    <Button
                      type="button"
                      variant="primary"
                      onClick={checkoutHandler}
                      disabled={cartItems.length === 0}
                    >
                      Перейти к оформлению заказа
                    </Button>
                  </div>
                </ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </div>
  );
}