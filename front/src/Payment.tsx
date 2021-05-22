import {Component} from "react";
import getData from "./utils";
import Product from "./Product";

interface Payment {
    id: number
    name: string
}

interface PaymentState {
    payments: Payment[]
}

interface PaymentProps {}

class Payment extends Component<PaymentProps, PaymentState> {

    constructor(props: PaymentProps) {
        super(props);
        this.state = {
            payments: [],
        };
    }

    async componentDidMount() {
        var url = "http://localhost:9000/api/Payment"

        const data: Payment[] = await getData(url, "GET")
        console.log(data)
        this.setState({payments: data})
    }

    render() {
        return (
            <div className="payments">
                {this.state.payments.map((payment, index) => (
                    <div key={index}>
                        <ul>
                            <li>
                                <h4>{payment.id}</h4>
                                {payment.name}
                            </li>
                        </ul>
                    </div>
                ))}
            </div>
        )
    }
}

export default Payment;