import React, {Component} from 'react';
import getData from "./utils";

type Promotion = {
    productId: number
    value: number
}

type Product = {
    categoryId: number
    rateId: number
    promotionId: number
    price: number
}

type PromotiontFormState = {
    promotion: Promotion;
    products: Product[];
}

interface PromotionFormProps {}

class PromotionForm extends Component<PromotionFormProps, PromotiontFormState> {

    constructor(props: PromotionFormProps) {
        super(props);
        this.state = {
            promotion: {
                productId: 0,
                value: 0,
            },
            products: []
        }
        this.postRequest = this.postRequest.bind(this)
    }

    async postRequest(event: { preventDefault: () => void; }) {
        event.preventDefault();
        getData('http://localhost:9000/addPromotion/', "POST", {
            id: 0,
            productId: this.state.promotion.productId,
            value: this.state.promotion.value,
        })
    }

    render() {
        return (
            <form onSubmit={this.postRequest}>
                <label>Product</label>
                <input
                    type="text"
                    name="promotion[productId]"
                    value={this.state.promotion.productId}
                />
                <label>Value</label>
                <input
                    type="text"
                    name="promotion[value]"
                    value={this.state.promotion.value}
                />
                <button>Add promotion</button>
            </form>
        );
    }

}


export default PromotionForm;
