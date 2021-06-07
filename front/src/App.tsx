import React from 'react';
import './App.css';
import {BrowserRouter, Link, Route} from "react-router-dom";
import Welcome from "./Welcome";
import Cart from "./Cart";
import Category from "./Category";
import Delivery from "./Delivery";
import Discount from "./Discount";
import Favorite from "./Favorite";
import Payment from "./Payment";
import Product from "./Product";
import Promotion from "./Promotion";
import Rate from "./Rate";
import User from "./User";
import CartForm from "./CartForm";
import CategoryForm from "./CategoryForm";
import DeliveryForm from "./DeliveryForm";
import DiscountForm from "./DiscountForm";
import FavoriteForm from "./FavoriteForm";
import PaymentForm from "./PaymentForm";
import ProductForm from "./ProductForm";
import PromotionForm from "./PromotionForm";
import RateForm from "./RateForm";
import UserForm from "./UserForm";


function App() {
  return (
    <div className="App">
      <div>
        <BrowserRouter>
          <ul>
            <li><Link to="/home">Home</Link></li>
            <li><Link to="/cart">Cart</Link></li>
            <li><Link to="/category">Category</Link></li>
            <li><Link to="/delivery">Delivery</Link></li>
            <li><Link to="/discount">Discount</Link></li>
            <li><Link to="/favorite">Favorite</Link></li>
            <li><Link to="/payment">Payment</Link></li>
            <li><Link to="/product">Product</Link></li>
            <li><Link to="/promotion">Promotion</Link></li>
            <li><Link to="/rate">Rate</Link></li>
            <li><Link to="/user">User</Link></li>
          </ul>
          <Route path="/home" component={Welcome}/>
          <Route path="/cart" component={Cart}/>
          <Route path="/category" component={Category}/>
          <Route path="/delivery" component={Delivery}/>
          <Route path="/discount" component={Discount}/>
          <Route path="/favorite" component={Favorite}/>
          <Route path="/payment" component={Payment}/>
          <Route path="/product" component={Product}/>
          <Route path="/promotion" component={Promotion}/>
          <Route path="/rate" component={Rate}/>
          <Route path="/user" component={User}/>

          <Route path="/addCart" component={CartForm}/>
          <Route path="/addCategory" component={CategoryForm}/>
          <Route path="/addDelivery" component={DeliveryForm}/>
          <Route path="/addDiscount" component={DiscountForm}/>
          <Route path="/addFavorite" component={FavoriteForm}/>
          <Route path="/addPayment" component={PaymentForm}/>
          <Route path="/addProduct" component={ProductForm}/>
          <Route path="/addPromotion" component={PromotionForm}/>
          <Route path="/addRate" component={RateForm}/>
          <Route path="/addUser" component={UserForm}/>
        </BrowserRouter>
      </div>
    </div>
  );
}

export default App;
