import React, {Component} from 'react';
import getData from "./utils";

type Delivery = {
    userId: number
    name: String
}

type User = {
    favoriteId: number
    name: String
    email: String
    password: String
}

type DeliveryFormState = {
    delivery: Delivery;
    users: User[];
}

interface DeliveryFormProps {}

class DeliveryForm extends Component<DeliveryFormProps, DeliveryFormState> {

    constructor(props: DeliveryFormProps) {
        super(props);
        this.state = {
            delivery: {
                userId: 0,
                name: "",
            },
            users: [],
        }
        this.postRequest = this.postRequest.bind(this)
    }

    async postRequest(event: { preventDefault: () => void; }) {
        event.preventDefault();
        getData('http://localhost:9000/addDelivery/', "POST", {
            id: 0,
            userId: this.state.delivery.userId,
            name: this.state.delivery.name,
        })
    }

    render() {
        return (
            <form onSubmit={this.postRequest}>
                <label>Category</label>
                <input
                    type="text"
                    name="delivery[userId]"
                    value={this.state.delivery.userId}
                />
                <label>Rate</label>
                <input
                    type="text"
                    name="delivery[name]"
                />
                <button>Add Delivery</button>
            </form>
        );
    }

}


export default DeliveryForm;
