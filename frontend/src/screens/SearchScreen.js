import {useEffect,useReducer} from "react" ;
import axios from "axios";
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

import Product from '../components/Product';
import LoadingBox from "../components/LoadingBox";
import MessageBox from "../components/MessageBox";
import { Helmet } from 'react-helmet-async';

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

export default function SearchScreen() {
    const SearchRequest = localStorage.getItem('search');

    const [{ loading, error, products }, dispatch] = useReducer(reducer, {
        products: [],
        loading: true,
        error: '',
    });

    useEffect(()=>{
        const fetchData=async()=>{
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

    const filterProducts = (products) => {
        return products.filter(product => product.name.toLowerCase().includes(SearchRequest.toLowerCase()))
    }

    return(
        <>
            <Helmet>
                <title>Online Shop</title>
            </Helmet>
            <h1>Список одежды</h1>
            <div className="products">
                {loading ? (
                    <LoadingBox />
                ) : error ? (
                    <MessageBox variant="danger">{error}</MessageBox>
                ) : (
                    <Row style={{width: '100%'}}>
                        {filterProducts(products).map((product) => (
                            <Col key={product.id} sm={6} md={5} lg={4} className="mb-3">
                                <Product product={product}></Product>
                            </Col>
                        ))}
                    </Row>)}
            </div>
        </>)
}