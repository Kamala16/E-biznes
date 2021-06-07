import React, {Component} from 'react';
import getData from "./utils";

type Discount = {
    userId: number
    value: number
}

type User = {
    favoriteId: number
    name: String
    email: String
    password: String
}

type DiscountFormState = {
    discount: Discount;
    users: User[];
}

interface DiscountFormProps {}

class DiscountForm extends Component<DiscountFormProps, DiscountFormState> {

    constructor(props: DiscountFormProps) {
        super(props);
        this.state = {
            discount: {
                userId: 0,
                value: 0
            },
            users: []
        }
        this.postRequest = this.postRequest.bind(this)
    }

    async postRequest(event: { preventDefault: () => void; }) {
        event.preventDefault();
        getData('http://localhost:9000/addDiscount/', "POST", {
            id: 0,
            userId: this.state.discount.userId,
            value: this.state.discount.value
        })
    }

    render() {
        return (
            <form onSubmit={this.postRequest}>
                <label>User</label>
                <input
                    type="text"
                    name="discount[user]"
                    value={this.state.discount.userId}
                />
                <label>Product</label>
                <input
                    type="text"
                    name="discount[value]"
                    value={this.state.discount.value}
                />
                <button>Add Discount</button>
            </form>
        );
    }

}


export default DiscountForm;
