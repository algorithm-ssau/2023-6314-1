import React, { useState } from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import { useNavigate } from 'react-router-dom';

export default function SearchBox() {
    const navigate = useNavigate();
    const [query, setQuery] = useState('');
    const submitHandler = (e) => {
        e.preventDefault();
        localStorage.setItem('search', query);

        navigate('/search');
    };

    return (
        <Form className="d-flex me-auto" onSubmit={submitHandler}>
            <InputGroup>
                <FormControl
                    type="text"
                    name="q"
                    id="q"
                    onChange={(e) => setQuery(e.target.value)}
                    placeholder="поиск товаров..."
                    aria-label="поиск товаров"
                    aria-describedby="button-search"
                ></FormControl>
                <Button variant="outline-primary" type="submit" id="button-search">
                    <i className="fas fa-search"></i>
                </Button>
            </InputGroup>
        </Form>
    );
}