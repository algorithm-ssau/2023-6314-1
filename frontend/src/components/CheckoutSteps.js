import React from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

export default function CheckoutSteps(props) {
    return (
        <Row className="checkout-steps">
            <Col className={props.step1 ? 'active' : ''}>Авторизация</Col>
            <Col className={props.step2 ? 'active' : ''}>Оформление</Col>
            <Col className={props.step3 ? 'active' : ''}>Метод оплаты</Col>
            <Col className={props.step4 ? 'active' : ''}>Оформление заказа</Col>
        </Row>
    );
}