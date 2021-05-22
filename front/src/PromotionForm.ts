import React, {Component} from 'react';

class Promotion extends Component {

    constructor() {
        // @ts-ignore
        super();
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event: { preventDefault: () => void; target: HTMLFormElement | undefined; }) {
        event.preventDefault();
        const data = new FormData(event.target);

        var url = 'http://localhost:9000/addPromotion/';

        fetch(url, {
            method: 'POST',
            body: data,
        });
    }

    render() {
        return ("FORMULARZ Promocja"
            //     <form onSubmit={this.handleSubmit}>
            //     <label htmlFor="name">Product name</label>
            // <input id="name" name="name" type="text" />
            //
            // <label htmlFor="description">Description</label>
            //     <input id="description" name="description" type="description" />
            //
            //     <button>Add product</button>
            // </form>
        );
    }

}


export default Promotion;
