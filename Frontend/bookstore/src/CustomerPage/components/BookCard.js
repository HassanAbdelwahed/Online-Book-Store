import React from "react";
import './BookCard.css';
import { environment } from "../../Environment";

export default function BookCard(props) {

    const [count, setCount] = React.useState(0);
    const [Error, setError] = React.useState(null);
    function increase() {
         let res = "accepted" ;
         if(props.cartId !== undefined){
        // if he presed  + then send to the backend the book id
        async function Addbook() {
            let result = await fetch(`${environment.env}/addToCart`, {
                method: "POST",
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(
                {
                  customerid:props.location.state.id,
                  bookid: props.id,
                  cartid : props.cartId
                }
              )
            });
             res = await result.json();
        }
        Addbook();

        if(res==="accepted"){
            setCount(old => {
                return old + 1;
            });
        }
        else{
            setError(() => {
                return (<div className="Error">
                    No more items in stock !!
                </div>);
            });
        }
    }

    };
    function decrease() {
        if(props.cartId !== undefined){
        setCount(old => {
            return old > 0 ? old - 1 : 0;
        })
        //  // if he presed  - then send to the backend the book id 
        if(count !== 0){
        async function Removebook() {
            let result = await fetch(`${environment.env}/removeFromCart`, {
                method: "POST",
                headers: {
                    'Content-type': 'application/json'
                },
                body: JSON.stringify(
                {
                  customerid:props.location.state.id,
                  bookid: props.id,
                  cartid : props.cartId
                }
              )
            });
            let res = await result.json();
        }
    Removebook();}}
    };

    return (
        <div className="Card">
            <div className="img_container">
            <img src={props.img} alt={props.name} />
            </div>
            <div className="description">
                     <h3>{props.name}</h3>
                    <h3 id="price">${props.price}</h3>
                    <div className="quantity">
                        <button id="inc" onClick={increase}>+</button>
                        <p>{count}</p>
                        <button id="dec" onClick={decrease}>-</button>
                </div>
            </div>
            {Error}
        </div>
    );
}