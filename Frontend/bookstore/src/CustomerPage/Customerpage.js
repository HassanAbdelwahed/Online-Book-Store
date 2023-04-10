import React from 'react';
import './Customerpage.css';
import BookCard from './components/BookCard';
import { BsFillCartCheckFill } from 'react-icons/bs';
import { useNavigate, useLocation } from 'react-router-dom';
import {BsBoxArrowLeft , BsFillPersonFill, BsFillFilterCircleFill } from 'react-icons/bs';
import {environment} from '../Environment';
export default function Customerpage() {
    let [someBooks, setsomeBooks] = React.useState([]);
    let [Books, setBooks] = React.useState([]);
    let [search, setsearch]= React.useState("Title");
    function search_options(e){
        setsearch(e.target.value);
      }
    const NAV = useNavigate();
    const location = useLocation();

    //get some Books from the stock
    React.useEffect(() => {
        async function getBooks() {
            let result = await fetch(`${environment.Host}/customer/getBooks`, {
                method: "get",
                headers: {
                    'Content-type': 'application/json'
                }
            });
            let res = await result.json();
            setsomeBooks(res);
        }
        getBooks();
    }, []);
    
        for(let i = 0 ; i < 20; i++){
            let ordobj={name:someBooks[i].title,price:someBooks[i].price,id:someBooks[i].book_ISBN,image_location:"../Images/pic4.png"};
            Books.push(ordobj);
        }

        function Logout(){
            NAV('/Signin');
         }
         function Profile(){
             //nav to profile
             NAV('/Customerpage/Profile', {state:location.state.id});
         }
         let [cartid, setcartid]=React.useState();
         let BookHtml ;
        function start_shopping(){
            setcartid(5);
            async function start_shopping() {
                let result = await fetch(`${environment.env}/getcartid`, {
                    method: "POST",
                    headers: {
                        'Content-type': 'application/json'
                    },
                    body: JSON.stringify(
                    {
                      customerid:location.state.id
                    }
                  )
                });
                let res = await result.json();
               cartid=  res;
            }
            start_shopping();

        }
        BookHtml = Books.map((item) => {
            console.log(cartid);
            return (
                <BookCard
                    img={item.image_location}
                    name={item.name}
                    price={item.price}
                    id ={item.id}
                    location={location}
                    cartId = {cartid}
                />
            );
        })

    function Cart(){
        //nav to Cart
      //  NAV('/', {state:location.state.id});
    }
    return (
        <div className="Main">

                <div className='CHeader'>
                <div onClick={Profile} className='Cart_2'>< BsFillPersonFill /></div>
                <div  onClick={Cart} className='Cart_2'><BsFillCartCheckFill /></div>
                <div  onClick={start_shopping} className='Cart_2'>< BsFillFilterCircleFill /></div>
                <input className='search' placeholder={"search by "+search}/>
                <select onChange={search_options}  className='select' name="Search" id="search">
                <option value="Title">Title</option>
                <option value="author">author</option>
                <option value="category">category</option>
                <option value="ISBN">ISBN</option>
                <option value="publisher">publisher</option>
                </select>
                <div  onClick={Logout} className='Cart_1'><BsBoxArrowLeft /></div>
                </div>

            <div className="Explore">
                <div className="container2">
                {BookHtml}
                </div>
                </div>
            <div className='Cart' onClick={Cart}><BsFillCartCheckFill /></div>
        </div>
    );
}