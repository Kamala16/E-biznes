import React, {Component} from 'react';
import getData from "./utils";

type Rate = {
    userId: number
    productId: number
    value: number
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

type RateFormState = {
    rate: Rate;
    products: Product[]
    users: User[];
}

interface RateFormProps {}

class RateForm extends Component<RateFormProps, RateFormState> {

    constructor(props: RateFormProps) {
        super(props);
        this.state = {
            rate: {
                userId: 0,
                productId: 0,
                value: 0,
            },
            products: [],
            users: []
        }
        this.postRequest = this.postRequest.bind(this)
    }

    async postRequest(event: { preventDefault: () => void; }) {
        event.preventDefault();
        getData('http://localhost:9000/addRate/', "POST", {
            id: 0,
            userId: this.state.rate.userId,
            productId: this.state.rate.productId,
            value: this.state.rate.value,
        })
    }

    render() {
        return (
            <form onSubmit={this.postRequest}>
                <label>User</label>
                <input
                    type="text"
                    name="rate[userId]"
                    value={this.state.rate.userId}
                />
                <label>Product</label>
                <input
                    type="text"
                    name="rate[product]"
                    value={this.state.rate.productId}
                />
                <label>Value</label>
                <input
                    type="text"
                    name="rate[value]"
                    value={this.state.rate.value}
                />
                <button>Add rate</button>
            </form>
        );
    }

}


export default RateForm;
