import React, {Component} from 'react';
import getData from "./utils";

type Cart = {
    userId: number
    productId: number
    discountId: number
    price: number
}

type Product = {
    categoryId: number
    rateId: number
    promotionId: number
    price: number
}

type User = {
    favoriteId: number
    name: String
    email: String
    password: String
}

type Discount = {
    userId: number
    value: number
}

type CartFormState = {
    cart: Cart;
    products: Product[]
    users: User[];
    discounts: Discount[];
}

interface CartFormProps {}

class CartForm extends Component<CartFormProps, CartFormState> {

    constructor(props: CartFormProps) {
        super(props);
        this.state = {
            cart: {
                userId: 0,
                productId: 0,
                discountId: 0,
                price: 0
            },
            products: [],
            users: [],
            discounts: []

        }
        this.postRequest = this.postRequest.bind(this)
    }

    async postRequest(event: { preventDefault: () => void; }) {
        event.preventDefault();
        getData('http://localhost:9000/addCart/', "POST", {
            id: 0,
            userId: this.state.cart.userId,
            productId: this.state.cart.productId,
            discountId: this.state.cart.discountId,
            price: this.state.cart.price
        })
    }

    render() {
        return (
            <form onSubmit={this.postRequest}>
                <label>User</label>
                <input
                    type="text"
                    name="cart[user]"
                    value={this.state.cart.userId}
                />
                <label>Product</label>
                <input
                    type="text"
                    name="cart[product]"
                    value={this.state.cart.productId}
                />
                <label>Discount</label>
                <input
                    type="text"
                    name="cart[discount]"
                    value={this.state.cart.discountId}
                />
                <label>Price</label>
                <input
                    type="text"
                    name="cart[price]"
                    value={this.state.cart.price}
                />
            </form>
    );
    }

}


export default CartForm;
