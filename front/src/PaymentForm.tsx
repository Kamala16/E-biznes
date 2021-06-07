import React, {Component} from 'react';
import getData from "./utils";

type Payment ={
    name: string
}

type PaymentFormState = {
    payment: Payment;
}

interface PaymentFormProps {}

class PaymentForm extends Component<PaymentFormProps, PaymentFormState> {

    constructor(props: PaymentFormProps) {
        super(props);
        this.state = {
            payment: {
                name: ""
            }
        }
        this.postRequest = this.postRequest.bind(this)
    }

    async postRequest(event: { preventDefault: () => void; }) {
        event.preventDefault();
        getData('http://localhost:9000/addPayment/', "POST", {
            id: 0,
            name: this.state.payment.name,
        })
    }

    render() {
        return (
            <form onSubmit={this.postRequest}>
                <label>Name</label>
                <input
                    type="text"
                    name="payment[name]"
                />
                <button>Add payment</button>
            </form>
        );
    }

}


export default PaymentForm;
