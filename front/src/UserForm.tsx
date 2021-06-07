import React, {Component} from 'react';
import getData from "./utils";

type User = {
    name: String
    email: String
    password: String
}

type UsertFormState = {
    user: User
}

interface UserFormProps {}

class UserForm extends Component<UserFormProps, UsertFormState> {

    constructor(props: UserFormProps) {
        super(props);
        this.state = {
            user: {
                name: "",
                email: "",
                password: ""
            },
        }
        this.postRequest = this.postRequest.bind(this)
    }

    async postRequest(event: { preventDefault: () => void; }) {
        event.preventDefault();
        getData('http://localhost:9000/addUser/', "POST", {
            id: 0,
            name: this.state.user.name,
            email: this.state.user.email,
            password: this.state.user.password,
        })
    }

    render() {
        return (
            <form onSubmit={this.postRequest}>
                <label>Name</label>
                <input
                    type="text"
                    name="user[name]"
                />
                <label>email</label>
                <input
                    type="text"
                    name="user[email]"
                />
                <label>password</label>
                <input
                    type="text"
                    name="user[password]"
                />
                <button>Add user</button>
            </form>
        );
    }

}


export default UserForm;
